package com.minehut.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.Permission;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.util.ItemBuilder;
import com.minehut.cosmetics.util.messaging.Message;
import com.minehut.cosmetics.util.structures.Pair;
import kong.unirest.HttpResponse;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("UnstableApiUsage")
public abstract class Crate extends Cosmetic {

    private final WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table;

    protected Crate(String id, WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> table) {
        super(id, CosmeticCategory.CRATE);
        this.table = table;
    }

    @Override
    public Permission permission() {
        return Permission.none();
    }


    public void playOpenAnimation(Player player, Location crateLoc, ItemStack resultStack, Runnable after) {
        // We use 1.19 features during the animation so if we aren't 1.19 we return
        // TODO: Remove whenever we move above 1.19 or whenever we only support cosmetics on 1.19+ servers
        if (!Bukkit.getVersion().contains("1.19")) return;

        final Location playerLocation = crateLoc.clone().subtract(0, 0, 3.5);
        playerLocation.setYaw(0);

        player.teleport(playerLocation, true);
        player.setGameMode(GameMode.SPECTATOR);

        final Entity base = crateLoc.getWorld().spawn(crateLoc, ArmorStand.class, (entity) -> {
            entity.setInvisible(true);
            entity.setGravity(false);
            entity.getEquipment().setHelmet(ItemBuilder.of(Material.IRON_INGOT).modelData(1_000_001).build());
        });

        final ArmorStand lid = crateLoc.getWorld().spawn(crateLoc, ArmorStand.class, (entity) -> {
            entity.setInvisible(true);
            entity.setGravity(false);
            entity.getEquipment().setHelmet(ItemBuilder.of(Material.IRON_INGOT).modelData(1_000_002).build());
        });

        final AreaEffectCloud displayCloud = crateLoc.getWorld().spawn(crateLoc.clone().add(0, 1.5, 0), AreaEffectCloud.class, (entity) -> {
            entity.setGravity(false);
            entity.setRadius(2);
            entity.setTicksLived(Integer.MAX_VALUE);
            entity.setParticle(Particle.BLOCK_CRACK, Bukkit.createBlockData(Material.AIR));
        });

        final Item displayItem = crateLoc.getWorld().spawn(crateLoc.clone().add(0, .25, 0), Item.class, (entity) -> {
            entity.setGravity(false);
            entity.setCanMobPickup(false);
            entity.setCanPlayerPickup(false);
            entity.setUnlimitedLifetime(true);
            displayCloud.addPassenger(entity);
        });
        player.hideEntity(Cosmetics.get(), displayItem);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) return;
            onlinePlayer.hideEntity(Cosmetics.get(), base);
            onlinePlayer.hideEntity(Cosmetics.get(), lid);
            onlinePlayer.hideEntity(Cosmetics.get(), displayItem);
            onlinePlayer.hideEntity(Cosmetics.get(), displayCloud);
        });

        int totalChunks = 10;
        long ticksPerChunk = 2L;
        long totalOpenTicks = ticksPerChunk * totalChunks;

        for (int chunk = 0; chunk < totalChunks; chunk++) {
            final double completion = (chunk / (double) totalChunks);
            final double angle = (Math.PI / 2) * completion;
            final EulerAngle euler = new EulerAngle(-angle, 0, 0);
            Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> lid.setHeadPose(euler), chunk * ticksPerChunk);
            chunk++;
        }
        Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> player.showEntity(Cosmetics.get(), displayItem), totalOpenTicks);

        int itemRolls = 20;
        long ticksPerRoll = 5L;
        long totalRollTicks = itemRolls * ticksPerRoll;
        for (int roll = 0; roll < itemRolls; roll++) {

            ItemStack headStack = resultStack;
            if (roll < (itemRolls - 1)) {
                final Pair<CosmeticSupplier<? extends Cosmetic>, Integer> result = getTable().roll();
                headStack = result.left().get().menuIcon();
            }
            final ItemStack finalHeadStack = headStack;
            Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> displayItem.setItemStack(finalHeadStack), totalOpenTicks + (roll * ticksPerRoll));
        }

        long idleTicks = 80;
        Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> {
            base.remove();
            lid.remove();
            displayCloud.remove();
            displayItem.remove();
            after.run();

            // return to spawn if possible
            player.performCommand("spawn");
            player.setGameMode(GameMode.ADVENTURE);
        }, totalOpenTicks + totalRollTicks + idleTicks);
    }

    public void open(UUID uuid, int amount) {
        owner(uuid);

        if (Cosmetics.get().crates().isOpening(uuid)) {
            player().ifPresent(player -> player.sendMessage(Message.error("You're already opening a crate!")));
            return;
        }

        // roll for the item reward
        final var result = table.roll();
        final Cosmetic cosmetic = result.left().get();
        final int quantity = result.right();

        final AtomicBoolean success = new AtomicBoolean(false);
        player().ifPresent(player -> Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            // try to consume the item
            final ModifyCosmeticQuantityRequest req = new ModifyCosmeticQuantityRequest(uuid, category().name(), id(), -amount);
            final HttpResponse<Void> response = Cosmetics.get()
                    .api()
                    .modifyCosmeticQuantity(req)
                    .join();

            // handle consuming response
            switch (response.getStatus()) {
                case 200 -> {

                    Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                        Cosmetics.get().crates().opening().add(uuid);
                        playOpenAnimation(player, Cosmetics.get().config().crateLocation(), cosmetic.menuIcon(), () -> {
                            Cosmetics.get().crates().opening().remove(uuid);
                            if (!success.get()) return;

                            final Component content = Component.text()
                                    .append(Component.text("Opened Crate").color(NamedTextColor.GREEN))
                                    .append(Component.newline())
                                    .append(Component.newline())
                                    .append(Component.text("You received").color(NamedTextColor.WHITE))
                                    .append(Component.space())
                                    .append(cosmetic.name())
                                    .append(Component.space())
                                    .append(Component.text("x" + quantity).color(NamedTextColor.WHITE))
                                    .append(Component.newline())
                                    .append(Component.newline())
                                    .append(Component.text("Type"))
                                    .append(Component.space())
                                    .append(Component.text("/cosmetics").color(NamedTextColor.YELLOW))
                                    .build();
                            player.sendMessage(Message.announcement(content));
                        });
                    });

                    final CosmeticData data = new CosmeticData(cosmetic.category().name(), cosmetic.id(), new CosmeticMeta(quantity));
                    Cosmetics.get().api().unlockCosmetic(new UnlockCosmeticRequest(uuid, data));
                    success.set(true);
                }
                // handle error cases
                case 429 -> // if they ratelimit
                        player.sendMessage(Message.error("Please wait a moment and try again."));
                case 412 -> // let them know they have insufficient resources
                        player.sendMessage(Message.error("You do not own enough crates!"));
            }
        }));
    }

    public WeightedTable<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> getTable() {
        return table;
    }

    @Override
    public int salvageAmount() {
        return 0;
    }
}
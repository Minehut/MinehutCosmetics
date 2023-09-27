package com.minehut.cosmetics.cosmetics.crates;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.cosmetics.Cosmetic;
import com.minehut.cosmetics.cosmetics.CosmeticCategory;
import com.minehut.cosmetics.cosmetics.CosmeticSupplier;
import com.minehut.cosmetics.cosmetics.crates.impl.CrateEntry;
import com.minehut.cosmetics.model.profile.CosmeticData;
import com.minehut.cosmetics.model.profile.CosmeticMeta;
import com.minehut.cosmetics.model.request.ModifyCosmeticQuantityRequest;
import com.minehut.cosmetics.model.request.UnlockCosmeticRequest;
import com.minehut.cosmetics.util.GlowUtil;
import com.minehut.cosmetics.util.TeleportUtil;
import com.minehut.cosmetics.util.messaging.Message;
import com.minehut.cosmetics.util.structures.Pair;
import kong.unirest.HttpResponse;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.EulerAngle;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("UnstableApiUsage")
public abstract class Crate extends Cosmetic {
    // lid open variables
    private static final int OPEN_LID_TICKS = 10;
    private static final long MC_TICK_PER_LID_OPEN_TICK = 2L;
    private static final long TOTAL_LID_OPEN_TICKs = MC_TICK_PER_LID_OPEN_TICK * OPEN_LID_TICKS;

    // roll variables
    private static final int ITEM_ROLLS = 20;
    private static final long TICKS_PER_ROLL = 5L;
    private static final long TOTAL_ROLL_TICKS = ITEM_ROLLS * TICKS_PER_ROLL;

    // time they sit there after the roll is complete
    private static final long IDLE_TICKS = 80;

    private final GlowUtil glowUtil = new GlowUtil();

    private final WeightedTable<CrateEntry> table;

    /**
     * Create a new crate that rewards cosmetic items
     *
     * @param id    the identifier for this crate
     * @param table weighted table used for rolling crate items
     */
    public Crate(String id, WeightedTable<CrateEntry> table) {
        super(id, CosmeticCategory.CRATE);
        this.table = table;
    }

    /**
     * Play the opening animation for the crate to the given player
     * at the specified location.
     *
     * @param player   to open the crate to
     * @param crateLoc the location to open the crate at
     * @param result   the result of opening the crate
     * @param after    actions to run after the crate completes opening
     */
    public void playOpenAnimation(Player player, Location crateLoc, Cosmetic result, Runnable after) {
        final Location playerLocation = crateLoc.clone().subtract(0, 0, 3.5);
        playerLocation.setYaw(0);

        TeleportUtil.teleport(player, playerLocation);
        player.setGameMode(GameMode.SPECTATOR);

        // spawn all the entities we'll be using for the animation
        final Entity crateBase = crateLoc.getWorld().spawn(crateLoc, ArmorStand.class, (entity) -> {
            entity.setInvisible(true);
            entity.setGravity(false);
            entity.setInvulnerable(true);
            entity.getEquipment().setHelmet(crateBase());
        });

        final ArmorStand crateLid = crateLoc.getWorld().spawn(crateLoc, ArmorStand.class, (entity) -> {
            entity.setInvisible(true);
            entity.setGravity(false);
            entity.setInvulnerable(true);
            entity.getEquipment().setHelmet(crateLid());
        });

        final AreaEffectCloud displayCloud = crateLoc.getWorld().spawn(crateLoc.clone().add(0, 1.5, 0), AreaEffectCloud.class, (entity) -> {
            entity.setGravity(false);
            entity.setRadius(2);
            entity.setInvulnerable(true);
            entity.setTicksLived(Integer.MAX_VALUE);
            entity.setParticle(Particle.BLOCK_CRACK, Bukkit.createBlockData(Material.AIR));
        });

        final Item displayItem = crateLoc.getWorld().spawn(crateLoc.clone().add(0, .25, 0), Item.class, (entity) -> {
            entity.setGravity(false);
            entity.setCanMobPickup(false);
            entity.setCanPlayerPickup(false);
            entity.setUnlimitedLifetime(true);
            entity.setInvulnerable(true);
            entity.setCustomNameVisible(true);
            displayCloud.addPassenger(entity);
        });

        player.hideEntity(Cosmetics.get(), displayItem);

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) return;
            onlinePlayer.hideEntity(Cosmetics.get(), crateBase);
            onlinePlayer.hideEntity(Cosmetics.get(), crateLid);
            onlinePlayer.hideEntity(Cosmetics.get(), displayItem);
            onlinePlayer.hideEntity(Cosmetics.get(), displayCloud);
        });

        for (int lidTick = 0; lidTick < OPEN_LID_TICKS; lidTick++) {
            final double completion = (lidTick / (double) OPEN_LID_TICKS);
            final double angle = (Math.PI / 2) * completion;
            final EulerAngle euler = new EulerAngle(-angle, 0, 0);
            Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> crateLid.setHeadPose(euler), lidTick * MC_TICK_PER_LID_OPEN_TICK);
            lidTick++;
        }
        Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> player.showEntity(Cosmetics.get(), displayItem), TOTAL_LID_OPEN_TICKs);

        for (int roll = 0; roll < ITEM_ROLLS; roll++) {
            final Cosmetic cosmetic = roll == ITEM_ROLLS - 1
                ? result
                : roll().left().get();

            Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> {
                // set the display item
                displayItem.setItemStack(cosmetic.menuIcon());
                displayItem.customName(cosmetic.name());
                glowUtil.setColoredGlow(displayItem, Bukkit.getScoreboardManager().getMainScoreboard(), cosmetic.rarity().closestNamedColor());
                player.playSound(Sound.sound(Key.key("minecraft", "ui.button.click"), Sound.Source.AMBIENT, .6f, .8f));
            }, TOTAL_LID_OPEN_TICKs + (roll * TICKS_PER_ROLL));
        }

        Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> {
            final Firework firework = crateLoc.getWorld().spawn(crateLoc, Firework.class, fw -> {
                final FireworkMeta meta = fw.getFireworkMeta();
                final NamedTextColor rarityColor = result.rarity().closestNamedColor();

                final FireworkEffect effect = FireworkEffect.builder()
                    .flicker(true)
                    .with(FireworkEffect.Type.STAR)
                    .withColor(Color.fromRGB(rarityColor.red(), rarityColor.green(), rarityColor.blue()))
                    .trail(true)
                    .build();

                meta.addEffect(effect);
                fw.setFireworkMeta(meta);
            });

            // only show the firework to the player who opened it
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) {
                    return;
                }
                player.hideEntity(Cosmetics.get(), firework);
            });

            player.playSound(Sound.sound(Key.key("minecraft", "entity.player.levelup"), Sound.Source.AMBIENT, 1f, 1f));
        }, TOTAL_LID_OPEN_TICKs + TOTAL_ROLL_TICKS);

        Bukkit.getScheduler().runTaskLater(Cosmetics.get(), () -> {
            crateBase.remove();
            crateLid.remove();
            displayCloud.remove();
            displayItem.remove();
            after.run();

            // return to spawn if possible
            player.performCommand("spawn");
            player.setGameMode(GameMode.ADVENTURE);
        }, TOTAL_LID_OPEN_TICKs + TOTAL_ROLL_TICKS + IDLE_TICKS);
    }

    public void open(UUID uuid, int amount) {
        owner(uuid);

        if (Cosmetics.get().crates().isOpening(uuid)) {
            player().ifPresent(player -> player.sendMessage(Message.error("You're already opening a crate!")));
            return;
        }

        // roll for the item reward

        final Pair<CosmeticSupplier<? extends Cosmetic>, Integer> roll = roll();
        final Cosmetic cosmetic = roll.left().get();
        final int quantity = roll.right();

        final AtomicBoolean success = new AtomicBoolean(false);
        player().ifPresent(player -> Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
            // try to consume the item
            final ModifyCosmeticQuantityRequest req = new ModifyCosmeticQuantityRequest(uuid, category().name(), id(), -amount);
            final HttpResponse<Void> response = Cosmetics.get()
                .networkApi()
                .modifyCosmeticQuantity(req)
                .join();

            // handle consuming response
            switch (response.getStatus()) {
                case 200 -> {
                    Bukkit.getScheduler().runTask(Cosmetics.get(), () -> {
                        Cosmetics.get().crates().opening().add(uuid);
                        playOpenAnimation(player, Cosmetics.get().config().crateLocation(), cosmetic, () -> {
                            Cosmetics.get().crates().opening().remove(uuid);
                            if (!success.get()) {
                                return;
                            }

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
                    Cosmetics.get().networkApi().unlockCosmetic(new UnlockCosmeticRequest(uuid, data));
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

    public Pair<CosmeticSupplier<? extends Cosmetic>, Integer> roll() {
        final CrateEntry entry = table.roll();
        final List<Pair<CosmeticSupplier<? extends Cosmetic>, Integer>> items = entry.items();
        return items.get(ThreadLocalRandom.current().nextInt(items.size()));

    }

    public WeightedTable<CrateEntry> getTable() {
        return table;
    }

    public abstract ItemStack crateBase();

    public abstract ItemStack crateLid();

    @Override
    public int salvageAmount() {
        return 0;
    }
}
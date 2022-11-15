package com.minehut.cosmetics.cosmetics.ui;

import com.minehut.cosmetics.Cosmetics;
import com.minehut.cosmetics.config.Mode;
import com.minehut.cosmetics.cosmetics.equipment.CosmeticSlot;
import com.minehut.cosmetics.cosmetics.groups.emoji.EmojiDisplay;
import com.minehut.cosmetics.ui.Menu;
import com.minehut.cosmetics.ui.icon.MenuItem;
import com.minehut.cosmetics.ui.model.Model;
import com.minehut.cosmetics.util.ItemBuilder;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class CosmeticMenu extends Menu {

    private static final Supplier<ItemStack> SKIN_INFO = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("How to apply skins.", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .lore(
                    Component.empty(),
                    Component.text("Hold the item you'd like to skin and type ", NamedTextColor.AQUA)
                            .append(Component.text("/skin", NamedTextColor.GOLD)
                                    .decorate(TextDecoration.BOLD))
            )
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwNWZkOTRhMGM0MzE5MjdmYjRlNjM5YjBmY2ZiNDk3MTdlNDEyMjg1YTAyYjQzOWUwMTEyZGEyMmIyZTJlYyJ9fX0=")
            .supplier();

    private static final Supplier<ItemStack> BLANK_LEGGINGS = ItemBuilder.of(Material.IRON_LEGGINGS)
            .display(Component.text("Leggings", NamedTextColor.DARK_PURPLE).decoration(TextDecoration.ITALIC, false))
            .flags(ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ATTRIBUTES)
            .lore(
                    Component.empty()
            )
            .supplier();

    private static final Supplier<ItemStack> EXIT_ICON = ItemBuilder.of(Material.DARK_OAK_DOOR)
            .display(Component.text("Back", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false))
            .supplier();

    private static final Supplier<ItemStack> SHOP_ICON = ItemBuilder.of(Material.GOLD_INGOT)
            .display(Component.text("Open Shop", NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false))
            .flags(ItemFlag.HIDE_POTION_EFFECTS)
            .modelData(Model.Ui.SHOP_ICON)
            .supplier();

    private static final Supplier<ItemStack> FILLER_ICON = ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE)
            .display(Component.text(" ").decoration(TextDecoration.ITALIC, false))
            .supplier();

    private static final Supplier<ItemStack> EMOJI_ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Emojis").decoration(TextDecoration.ITALIC, false))
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDgxN2E3OTA5ODBhZDAwMmIzMTlmZWI2OTU2N2EwNmEwMjE1NGJhOWFjNDY5OTNkNWNmZDYyMDEwZTBmODNjYiJ9fX0=")
            .supplier();

    private static final Supplier<ItemStack> CRATE_ICON = ItemBuilder.of(Material.PLAYER_HEAD)
            .display(Component.text("Crates").decoration(TextDecoration.ITALIC, false))
            .skullTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWFmOGRhYWRjZGRiMDg4YThlZDg3NTliYTAyNzcwZDcyODIxNGYwN2NkZDkzYTYzMGI4ZTdkM2NhMDM3M2RjIn19fQ==")
            .supplier();

    private static final Component EQUIPMENT_SKIN_MESSAGE = Component.text()
            .append(Component.newline())
            .append(Component.text("How to apply equipment skins:"))
            .append(Component.newline())
            .append(Component.text("Hold the item you'd like to skin and type ").color(NamedTextColor.AQUA).append(Component.text("/skin").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD)))
            .append(Component.newline())
            .build();

    private static final Component HAT_SKIN_MESSAGE = Component.text()
            .append(Component.newline())
            .append(Component.text("How to apply hat skins:"))
            .append(Component.newline())
            .append(Component.text("Hold the helmet you'd like to skin and type ").color(NamedTextColor.AQUA)
                    .append(Component.text("/skin")
                            .color(NamedTextColor.GOLD)
                            .decorate(TextDecoration.BOLD)
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/skin"))))
            .append(Component.newline())
            .build();

    private static final Supplier<ItemStack> AURA_MENU_ICON = ItemBuilder.of(Material.NETHER_STAR)
            .display(Component.text("Aura Menu").color(NamedTextColor.AQUA))
            .lore(
                    Component.empty(),
                    Component.text("Let it glow!").color(NamedTextColor.LIGHT_PURPLE),
                    Component.empty()
            )
            .supplier();

    public CosmeticMenu(Player user) {
        super(Cosmetics.get(), 6, "Cosmetics");

        final Mode mode = Cosmetics.mode();

        //Top menu
        getProxy().setItem(0, EXIT_ICON.get(), (player, ignored) -> {
            player.closeInventory();
            if (Mode.LOBBY == mode) {
                player.performCommand("playerservers");
            }
        });

        getProxy().setItem(1, SHOP_ICON.get(), (player, ignored) -> {
            player.closeInventory();
            player.openBook(Book.book(
                    Component.text("Cosmetics"),
                    Component.text("Minehut"),
                    Component.text("Open Cosmetics Shop ⬈").style(Style.style(NamedTextColor.BLUE, TextDecoration.UNDERLINED)).clickEvent(ClickEvent.openUrl("https://bit.ly/3TGDqMi"))
            ));
        });


        //Filler
        for (int i = 9; i <= 17; i++) {
            getProxy().setItem(i, FILLER_ICON.get());
        }

        // bottom group
        addMenu(TrinketMenu.ICON, () -> new TrinketMenu(user), 47);
        addMenu(CompanionMenu.ICON, () -> new CompanionMenu(user), 48);
        addMenu(ParticleMenu.ICON, () -> new ParticleMenu(user), 49);
        addMenu(BalloonMenu.ICON, () -> new BalloonMenu(user), 50);

        // armor pieces
        addMenu(BackpieceMenu.ICON, () -> new BackpieceMenu(user), 31);
        getProxy().setItem(40, BLANK_LEGGINGS.get());

        getProxy().setItem(34, EMOJI_ICON.get(), (player, ignored) -> EmojiDisplay.open(player));
        getProxy().setItem(43, CRATE_ICON.get(), (player, ignored) -> new CrateMenu().openTo(player));
        getProxy().setItem(52, CRATE_ICON.get(), (player, ignored) -> CosmeticInventoryMenu.open(player);

        // menus that are only visible when in lobby mode
        switch (mode) {
            case LOBBY -> {
                addMenu(ItemMenu.OFF_HAND_ICON, () -> new ItemMenu(user, CosmeticSlot.OFF_HAND), 30);
                addMenu(ItemMenu.MAIN_HAND_ICON, () -> new ItemMenu(user, CosmeticSlot.MAIN_HAND), 32);
                addMenu(HatMenu.ICON, () -> new HatMenu(user), 22);
                getProxy().setItem(51, AURA_MENU_ICON.get(), (player, ignored) -> player.performCommand("aura"));

            }
            case PLAYER_SERVER -> {
                // skin cta
                getProxy().setItem(8, SKIN_INFO.get());

                // hat icon
                getProxy().setItem(22, HatMenu.ICON, (player, ignored) -> {
                    player.sendMessage(HAT_SKIN_MESSAGE);
                    player.closeInventory();
                });

                // equipment icons
                getProxy().setItem(30, ItemMenu.OFF_HAND_ICON, (player, ignored) -> {
                    player.sendMessage(EQUIPMENT_SKIN_MESSAGE);
                    player.closeInventory();
                });
                getProxy().setItem(32, ItemMenu.MAIN_HAND_ICON, (player, ignored) -> {
                    player.sendMessage(EQUIPMENT_SKIN_MESSAGE);
                    player.closeInventory();
                });

                getProxy().setItem(51, AURA_MENU_ICON.get(), (player, ignored) -> {
                    player.sendMessage(Component.text("You cannot use auras on player servers!").color(NamedTextColor.RED));
                });
            }
        }
    }

    private void addMenu(ItemStack icon, Supplier<CosmeticSubMenu> supplier, int slot) {
        getProxy().setItem(slot, icon, (player, ignored) -> {
            // create menus async then open them
            Bukkit.getScheduler().runTaskAsynchronously(Cosmetics.get(), () -> {
                final CosmeticSubMenu subMenu = supplier.get();
                Bukkit.getScheduler().runTask(Cosmetics.get(), () -> subMenu.openTo(player));
            });
        });
    }

    @Override
    public void render() {
    }
}
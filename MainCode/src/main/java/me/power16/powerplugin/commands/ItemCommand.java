package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.ranks.Rank;
import me.power16.powerplugin.ranks.RankManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.util.UUID;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemCommand implements CommandExecutor, TabCompleter {

    private final List<String> items;
    private final RankManager rankManager;
    private final PowerPlugin plugin;

    public ItemCommand(PowerPlugin plugin, RankManager rankManager) {
        this.plugin = plugin;
        this.rankManager = rankManager;
        this.items = Arrays.asList(
                "ASPECT_OF_THE_END", "QBERT_HELMET", "QBERT_CHESTPLATE",
                "QBERT_LEGGINGS", "QBERT_BOOTS", "DCTR_SPACE_HELMET",
                "CYANIDE_PILL", "GAMBLE_COIN", "PILL_REMNANT","MOVE_STICK",
                "WITHER_SWORD_MANA", "RANCHER_BOOTS","MONEY_ITEM"
        );
    }

    private void setTags(ItemMeta meta, String id, Integer intel, Integer mcdpi) {
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "id");
        dataContainer.set(key, PersistentDataType.STRING, id);
        dataContainer.set(new NamespacedKey(plugin, "intelligence"), PersistentDataType.INTEGER, intel);
        dataContainer.set(new NamespacedKey(plugin, "mcdp"), PersistentDataType.INTEGER, mcdpi);
    }

    private ItemStack createItem(Material material, String displayName, List<String> lore, String id, Boolean itemGlow, Boolean canStack, Integer intel, Integer mcdpi) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            if (itemGlow){
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            }
            if (!canStack) {
                meta.getPersistentDataContainer().set(new NamespacedKey("powerplugin", "uuid"), PersistentDataType.INTEGER, (int) (Math.random() * Integer.MAX_VALUE));
            }

            meta.setUnbreakable(true);
            setTags(meta, id, intel, mcdpi);
            item.setItemMeta(meta);
        }
        return item;
    }

    private ItemStack createLeatherItem(Material material, String displayName, List<String> lore, String id, Boolean itemGlow, Integer intel, Integer mcdpi, Color LeatherColor) {
        ItemStack item = new ItemStack(material);


        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(LeatherColor);

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (itemGlow) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        meta.setUnbreakable(true);
        setTags(meta, id, intel, mcdpi);
        item.setItemMeta(meta);


        return item;
        }






    public ItemStack createHyperion() {
        List<String> lore = Arrays.asList(
      ChatColor.GRAY + "Gear Score: " + ChatColor.LIGHT_PURPLE + "615"
      ,ChatColor.GRAY + "Damage: " + ChatColor.RED + "+260"
      ,ChatColor.GRAY + "Strength: " + ChatColor.GREEN + "+350"
      ,ChatColor.GRAY + "Ferocity: " + ChatColor.GREEN + "+30"
      ,ChatColor.GRAY + " "
      ,ChatColor.GRAY + " "
      ,ChatColor.RED + "" + ChatColor.BOLD + " ---- ABILITY COMING SOON ---- "
      ,ChatColor.GRAY + " "
      ,ChatColor.GRAY + " "
      ,ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY DUNGEON SWORD"


        );


        return createItem(Material.IRON_SWORD, ChatColor.GOLD + "Hyperion", lore, "WITHER_SWORD_MANA", true,false, 0, 0);
    }

    public ItemStack createDctrSpaceHelmet(Player player) {
        Rank rank = rankManager.getPlayerRank(player);
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        List<String> lore = Arrays.asList(
                "§7§oA rare space helmet",
                "§7§ofrom shards of moon glass.",
                " ",
                "§7To: " + rank.getPrefix() + player.getDisplayName(),
                "§7From: " + rank.getPrefix() + player.getDisplayName(),
                " ",
                "§8Edition #1",
                "§8" + formattedDate,
                " ",
                "§8This item can be reforged!",
                " ",
                "§c§lSPECIAL§c §c§lHELMET"
        );
        return createItem(Material.RED_STAINED_GLASS, "§cDctr's Space Helmet", lore, "DCTR_SPACE_HELMET",true, false,0, 0);
    }

    public ItemStack createDice() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Mental Status: " + ChatColor.RED + "-∞",
                " ",
                ChatColor.GOLD + "Item Ability: Go big or go home" + ChatColor.YELLOW.toString() + ChatColor.BOLD + " RIGHT CLICK",
                ChatColor.GRAY + "Using this item will have a 50/50",
                ChatColor.GRAY + "chance to double your balance",
                ChatColor.GRAY + "or lose it all!",
                " ",
                ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "MYTHIC ITEM"
        );
        return createItem(Material.SUNFLOWER, ChatColor.LIGHT_PURPLE + "GAMBLING COIN", lore, "GAMBLE_COIN", true,true,0, 0);
    }

    public ItemStack createCyanidePill() {
        List<String> lore = Arrays.asList(
                ChatColor.GOLD + "Item Ability: Give Up" + ChatColor.YELLOW + "" + ChatColor.BOLD.toString() + " RIGHT CLICK",
                ChatColor.GRAY + "Use this ability to",
                ChatColor.GRAY + "instantly die!",
                " ",
                ChatColor.RED.toString() + ChatColor.BOLD + "SPECIAL ITEM"
        );
        return createItem(Material.POLISHED_BLACKSTONE_BUTTON, ChatColor.RED + "Cyanide Pill", lore, "CYANIDE_PILL", true,true,0, 0);
    }

    public ItemStack createAspectOfTheEnd() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Damage: " + ChatColor.RED + "+100",
                ChatColor.GRAY + "Strength: " + ChatColor.RED + "+100",
                ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "✎" + ChatColor.DARK_GRAY + "]",
                " ",
                ChatColor.GOLD + "Item Ability: Instant Transmission " + ChatColor.YELLOW + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "Teleport " + ChatColor.GREEN + "8 blocks " + ChatColor.GRAY + "ahead of",
                ChatColor.GRAY + "you and gain " + ChatColor.WHITE + "+50 Speed" + ChatColor.GRAY + " for",
                ChatColor.GRAY + "3 seconds.",
                ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "50",
                " ",
                ChatColor.DARK_GRAY + "This item can be reforged!",
                " ",
                ChatColor.BLUE + ChatColor.BOLD.toString() + "RARE SWORD"
        );
        return createItem(Material.DIAMOND_SWORD, ChatColor.BLUE + "Aspect of the End", lore, "ASPECT_OF_THE_END", false,false,0, 0);
    }

    public ItemStack createAspectOfTheVoid() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Damage: " + ChatColor.RED + "+120",
                ChatColor.GRAY + "Strength: " + ChatColor.RED + "+100",
                ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "✎" + ChatColor.DARK_GRAY + "]",
                " ",
                ChatColor.GOLD + "Item Ability: Instant Transmission " + ChatColor.YELLOW + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "Teleport " + ChatColor.GREEN + "8 blocks " + ChatColor.GRAY + "ahead of",
                ChatColor.GRAY + "you and gain " + ChatColor.WHITE + "+50 Speed" + ChatColor.GRAY + " for",
                ChatColor.GRAY + "3 seconds.",
                ChatColor.DARK_GRAY + "Mana Cost: " + ChatColor.DARK_AQUA + "50",
                " ",
                ChatColor.DARK_GRAY + "This item can be reforged!",
                " ",
                ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "EPIC SWORD"
        );
        return createItem(Material.DIAMOND_SHOVEL, ChatColor.DARK_PURPLE + "Aspect of the Void", lore, "ASPECT_OF_THE_VOID", false,false,0, 0);
    }

    public ItemStack createQbertHelmet() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Magic Find: " + ChatColor.AQUA + "+600000000%",
                " ",
                ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+999999 HP",
                ChatColor.GRAY + "Defence: " + ChatColor.GREEN + "+99999",
                ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+1 million",
                " ",
                ChatColor.AQUA + ChatColor.BOLD.toString() + "DIVINE HELMET"
        );
        return createItem(Material.CHAINMAIL_HELMET, ChatColor.AQUA + "Qbert Helmet", lore, "QBERT_HELMET",true, false,1000000, 0);
    }

    public ItemStack createQbertChestplate() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Magic Find: " + ChatColor.AQUA + "+600000000%",
                " ",
                ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+999999 HP",
                ChatColor.GRAY + "Defence: " + ChatColor.GREEN + "+99999",
                ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+1 million",
                " ",
                ChatColor.AQUA + ChatColor.BOLD.toString() + "DIVINE CHESTPLATE"
        );
        return createItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.AQUA + "Qbert Chestplate", lore, "QBERT_CHESTPLATE", true,false,1000000, 0);
    }

    public ItemStack createQbertLeggings() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Magic Find: " + ChatColor.AQUA + "+600000000%",
                " ",
                ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+999999 HP",
                ChatColor.GRAY + "Defence: " + ChatColor.GREEN + "+99999",
                ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+1 million",
                " ",
                ChatColor.AQUA + ChatColor.BOLD.toString() + "DIVINE LEGGINGS"
        );
        return createItem(Material.CHAINMAIL_LEGGINGS, ChatColor.AQUA + "Qbert Leggings", lore, "QBERT_LEGGINGS", true,false,1000000, 0);
    }

    public ItemStack createQbertBoots() {
        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Magic Find: " + ChatColor.AQUA + "+600000000%",
                " ",
                ChatColor.GRAY + "Health: " + ChatColor.GREEN + "+999999 HP",
                ChatColor.GRAY + "Defence: " + ChatColor.GREEN + "+99999",
                ChatColor.GRAY + "Intelligence: " + ChatColor.GREEN + "+1 million",
                " ",
                ChatColor.AQUA + ChatColor.BOLD.toString() + "DIVINE BOOTS"
        );
        return createItem(Material.CHAINMAIL_BOOTS, ChatColor.AQUA + "Qbert Boots", lore, "QBERT_BOOTS", true,false,1000000, 0);
    }

    public ItemStack createAdminMoney() {
        List<String> lore = Arrays.asList(
                ChatColor.GOLD + "Ability: $$$ " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "You will instantly recieve $1,000,000",
                ChatColor.GRAY + "into your account!",
                "",
                ChatColor.RED.toString() + ChatColor.BOLD + "SPECIAL ITEM"

        );
        return createItem(Material.GREEN_WOOL, ChatColor.BOLD.toString() + ChatColor.RED + "Millions for free!", lore, "MONEY_ITEM", true, false, 0, 0);
    }

    public ItemStack createPillRemnant() {
        List<String> lore = Arrays.asList(
                ChatColor.GREEN + "Shows that you",
                ChatColor.GREEN + "lived a rough life!",
                " ",
                ChatColor.WHITE.toString() + ChatColor.BOLD + "COMMON ITEM"
        );
        return createItem(Material.GLASS_BOTTLE, ChatColor.WHITE + "Cyanide Pill Remnant", lore, "PILL_REMNANT", true,true,0, 0);
    }

    public ItemStack createMoveStick() {
        List<String> lore = Arrays.asList(
                ChatColor.GOLD + "Item Ability: Channelling " + ChatColor.YELLOW.toString() + ChatColor.BOLD + "RIGHT CLICK",
                ChatColor.GRAY + "Use this item to move in",
                ChatColor.GRAY + "the general direction you",
                ChatColor.GRAY + "are facing. FAST!!!",
                " ",
                ChatColor.RED.toString() + ChatColor.BOLD + "SPECIAL ITEM"
        );

        return createItem(Material.STICK, ChatColor.RED + "Move Stick", lore, "MOVE_STICK", true,false,0,0);
    }

    public ItemStack createRancherBoots() {

        List<String> lore = Arrays.asList(
                ChatColor.GRAY + "Use these boots to",
                ChatColor.GRAY + "not destroy crops when",
                ChatColor.GRAY + "jumping on them!",
                " ",
                ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY BOOTS"
        );




        return createLeatherItem(Material.LEATHER_BOOTS, ChatColor.GOLD + "Rancher's boots", lore, "RANCHER_BOOTS", true, 0, 0, Color.BLACK);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        // Create and populate the inventory
        Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.DARK_BLUE + "Power Custom Items");
        inventory.setItem(0, this.createAspectOfTheEnd());
        inventory.setItem(1, this.createQbertHelmet());
        inventory.setItem(2, this.createQbertChestplate());
        inventory.setItem(3, this.createQbertLeggings());
        inventory.setItem(4, this.createQbertBoots());
        inventory.setItem(5, this.createDctrSpaceHelmet(player));
        inventory.setItem(6, this.createAspectOfTheVoid());
        inventory.setItem(7, this.createDice());
        inventory.setItem(8, this.createCyanidePill());
        inventory.setItem(9, this.createPillRemnant());
        inventory.setItem(10, this.createMoveStick());
        inventory.setItem(11, this.createHyperion());
        inventory.setItem(12, this.createRancherBoots());
        inventory.setItem(13, this.createAdminMoney());

        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta3 = fill.getItemMeta();
        if (meta3 != null) {
            meta3.setDisplayName(" ");
            fill.setItemMeta(meta3);
        }

        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, fill);
            }
        }

        if (args.length == 0) {
            // Open the inventory if no arguments are provided
            player.openInventory(inventory);
            return true;
        }

        String subCommand = args[0].toUpperCase();

        switch (subCommand) {
            case "DCTR_SPACE_HELMET":
                player.getInventory().addItem(createDctrSpaceHelmet(player));
                player.sendMessage("You selected: " + createDctrSpaceHelmet(player).getItemMeta().getDisplayName());
                break;
            case "GAMBLE_COIN":
                player.getInventory().addItem(createDice());
                player.sendMessage("You selected: " + createDice().getItemMeta().getDisplayName());
                break;
            case "CYANIDE_PILL":
                player.getInventory().addItem(createCyanidePill());
                player.sendMessage("You selected: " + createCyanidePill().getItemMeta().getDisplayName());
                break;
            case "ASPECT_OF_THE_END":
                player.getInventory().addItem(createAspectOfTheEnd());
                player.sendMessage("You selected: " + createAspectOfTheEnd().getItemMeta().getDisplayName());
                break;
            case "ASPECT_OF_THE_VOID":
                player.getInventory().addItem(createAspectOfTheVoid());
                player.sendMessage("You selected: " + createAspectOfTheVoid().getItemMeta().getDisplayName());
                break;
            case "QBERT_HELMET":
                player.getInventory().addItem(createQbertHelmet());
                player.sendMessage("You selected: " + createQbertHelmet().getItemMeta().getDisplayName());
                break;
            case "QBERT_CHESTPLATE":
                player.getInventory().addItem(createQbertChestplate());
                player.sendMessage("You selected: " + createQbertChestplate().getItemMeta().getDisplayName());
                break;
            case "QBERT_LEGGINGS":
                player.getInventory().addItem(createQbertLeggings());
                player.sendMessage("You selected: " + createQbertLeggings().getItemMeta().getDisplayName());
                break;
            case "QBERT_BOOTS":
                player.getInventory().addItem(createQbertBoots());
                player.sendMessage("You selected: " + createQbertBoots().getItemMeta().getDisplayName());
                break;
            case "PILL_REMNANT":
                player.getInventory().addItem(createPillRemnant());
                player.sendMessage("You selected: " + createPillRemnant().getItemMeta().getDisplayName());
                break;
            case "MOVE_STICK":
                player.getInventory().addItem(createMoveStick());
                player.sendMessage("You selected: " + createMoveStick().getItemMeta().getDisplayName());
                break;
            case "WITHER_SWORD_MANA":
                player.getInventory().addItem(createHyperion());
                player.sendMessage("You selected: " + createHyperion().getItemMeta().getDisplayName());
                break;
            case "RANCHER_BOOTS":
                player.getInventory().addItem(createRancherBoots());
                player.sendMessage("You selected: " + createRancherBoots().getItemMeta().getDisplayName());
                break;
            case "MONEY_ITEM":
                player.getInventory().addItem(createAdminMoney());
                player.sendMessage("You selected: " + createAdminMoney().getItemMeta().getDisplayName());
                break;
            default:
                player.sendMessage(ChatColor.RED + "Invalid item type id!");
                break;
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the sender is a player and if args.length is 1
        if (args.length == 1) {
            // Filter items based on the current input (args[0])
            String input = args[0].toLowerCase();
            List<String> suggestions = new ArrayList<>();
            for (String item : items) {
                if (item.toLowerCase().startsWith(input)) {
                    suggestions.add(item);
                }
            }
            return suggestions;
        }
        return Collections.emptyList();
    }
}


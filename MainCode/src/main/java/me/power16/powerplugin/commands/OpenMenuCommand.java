package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.ranks.Rank;
import me.power16.powerplugin.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;

public class OpenMenuCommand implements CommandExecutor {

    private final RankManager rankManager;
    private final PowerPlugin plugin;

    public OpenMenuCommand(PowerPlugin plugin, RankManager rankManager) {
        this.plugin = plugin;
        this.rankManager = rankManager;
    }



    public ItemStack getPlayerHead(String Player) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setOwningPlayer(Bukkit.getPlayer(Player));
        skull.setItemMeta(skullMeta);

        return skull;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        Player player = (Player) sender;

        // Check if rankManager is initialized
        if (rankManager == null) {
                player.sendMessage("RankManager is not initialized.");
            return false;
        }

        Rank rank = rankManager.getPlayerRank(player);

        // Create the inventory
        Inventory inventory = Bukkit.createInventory(null, 27, "Warp Menu");

        // Create Sword of Power item
        ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta1 = item1.getItemMeta();
        if (meta1 != null) {
            meta1.setDisplayName(ChatColor.GOLD + "Sword of Power");
            meta1.setUnbreakable(true);
            ArrayList<String> list = new ArrayList<>();
            list.add("test");
            list.add(" ");
            list.add(ChatColor.GOLD + "" + ChatColor.BOLD + "LEGENDARY SWORD");
            meta1.setLore(list);
            item1.setItemMeta(meta1);
        }
        inventory.setItem(12, item1);


        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        String formattedDate = currentDate.format(formatter);

        // Create Dctr's Space Helmet item
        ItemStack dctrHelmet = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta dctrHelmetMeta = dctrHelmet.getItemMeta();
        if (dctrHelmetMeta != null) {
            dctrHelmetMeta.setDisplayName("§cDctr's Space Helmet");
            ArrayList<String> loreDctrList = new ArrayList<>();
            loreDctrList.add("§7§oA rare space helmet");
            loreDctrList.add("§7§ofrom shards of moon glass.");
            loreDctrList.add(" ");
            loreDctrList.add("§7To: " + rank.getPrefix() + "" + player.getDisplayName());
            loreDctrList.add("§7From: " + rank.getPrefix() + "" + player.getDisplayName());
            loreDctrList.add(" ");
            loreDctrList.add("§8Edition #1");
            loreDctrList.add("§8" + formattedDate);
            loreDctrList.add(" ");
            loreDctrList.add("§8This item can be reforged!");
            loreDctrList.add(" ");
            loreDctrList.add("§c§lSPECIAL§c §c§lHELMET");
            dctrHelmetMeta.setLore(loreDctrList);
            dctrHelmet.setItemMeta(dctrHelmetMeta);
        }
        inventory.setItem(13, dctrHelmet);

        // Create Golden Apple item
        ItemStack item2 = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta2 = item2.getItemMeta();
        if (meta2 != null) {
            meta2.setDisplayName("Golden Apple");
            item2.setItemMeta(meta2);
        }
        inventory.setItem(14, item2);

        // Create Current Permissions item
        ItemStack perms = new ItemStack(Material.BLACK_STAINED_GLASS);
        ItemMeta permsMeta = perms.getItemMeta();
        if (permsMeta != null) {
            permsMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Current Permissions: ");
            ArrayList<String> permsListLore = new ArrayList<>();
            for (String permission : rank.getPermissions()) {
                permsListLore.add(permission);
            }
            permsMeta.setLore(permsListLore);
            perms.setItemMeta(permsMeta);
        }
        inventory.setItem(4, perms);


        // Fill empty slots with grey stained glass panes
        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta3 = fill.getItemMeta();
        if (meta3 != null) {
            meta3.setDisplayName(" ");
            fill.setItemMeta(meta3);
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, fill);
            }
        }

        // Open the inventory for the player
        player.openInventory(inventory);
        player.sendMessage("Menu opened!");

        return true;
    }
}

//package me.power16.powerplugin.commands;
//
//import org.bukkit.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.NamespacedKey;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandExecutor;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.persistence.PersistentDataType;
//import me.power16.powerplugin.PowerPlugin;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class HyperionCommand implements CommandExecutor {
//
//    public static ItemStack hyperion;
//
//    static {
//        hyperion = new ItemStack(Material.IRON_SWORD, 1);
//
//        ItemMeta itemMeta = hyperion.getItemMeta();
//        itemMeta.setDisplayName(ChatColor.GOLD + "Hyperion");
//        itemMeta.setUnbreakable(true);
//        List<String> hyperionLore = new ArrayList<>();
//        hyperionLore.add(ChatColor.GRAY + "Gear Score: " + ChatColor.LIGHT_PURPLE + "615");
//        hyperionLore.add(ChatColor.GRAY + "Damage: " + ChatColor.RED + "+260");
//        hyperionLore.add(ChatColor.GRAY + "Strength: " + ChatColor.GREEN + "+350");
//        hyperionLore.add(ChatColor.GRAY + "Ferocity: " + ChatColor.GREEN + "+30");
//        hyperionLore.add(ChatColor.GRAY + " ");
//        hyperionLore.add(hatColor.GRAY + " ");
////        hyperionLore.add(ChatColor.RED + "" + ChatColor.BOLD + " ---- ABILITY COMING SOON ---- ");
////        hyperionLore.add(ChatColor.GRAY + " ");
////        hyperionLore.add(ChatColor.GRAY + " ");
////        hyperionLore.add(ChatColor.GOLD + "" + ChatColoCr.BOLD + "LEGENDARY DUNGEON SWORD");
//
//        itemMeta.setLore(hyperionLore);
//
//        // Set custom ID flag
//        NamespacedKey key = new NamespacedKey(PowerPlugin.getPlugin(PowerPlugin.class), "custom_id");
//        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "PP:HYPERION");
//
//        hyperion.setItemMeta(itemMeta);
//    }
//
//    @Override
//    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
//        if (sender instanceof Player player) {
//            player.getInventory().addItem(hyperion);
//        }
//        return true;
//    }
//}
//
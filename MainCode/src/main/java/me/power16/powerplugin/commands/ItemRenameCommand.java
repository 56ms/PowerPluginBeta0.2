package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRenameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the player is holding an item
        if (item == null || item.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must be holding an item to rename it.");
            return true;
        }

        // Check if arguments were provided
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Please provide a name for the item.");
            return false;
        }

        // Combine the arguments into a single string for the new name
        String newName = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));

        // Set the new name on the item's meta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(newName);
        item.setItemMeta(meta);

        player.sendMessage(ChatColor.GREEN + "Item renamed to: " + ChatColor.RESET + newName);
        return true;
    }
}

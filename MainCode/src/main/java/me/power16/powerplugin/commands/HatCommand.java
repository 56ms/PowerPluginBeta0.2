package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must hold an item in your hand to use this command!");
            return true;
        }

        // Get the current helmet slot item
        ItemStack helmet = player.getInventory().getHelmet();

        // Set the item in hand as the new helmet
        player.getInventory().setHelmet(itemInHand);


        // Set the old helmet (if any) back to the player's hand
        player.getInventory().setItemInMainHand(helmet != null ? helmet : new ItemStack(Material.AIR));

        player.sendMessage(ChatColor.GREEN + "Your hat has been equipped!");
        return true;
    }
}
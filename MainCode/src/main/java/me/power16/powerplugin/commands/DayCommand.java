package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        player.getWorld().setTime(6000);
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "SUCCESS!" + ChatColor.YELLOW + "" + ChatColor.BOLD + " You changed the time to Noon!");

        return true;
    }
}

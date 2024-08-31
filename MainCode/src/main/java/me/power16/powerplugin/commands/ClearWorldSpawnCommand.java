package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearWorldSpawnCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ClearWorldSpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("powerplugin.clearspawn")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }

        // Remove spawn points from the config
        plugin.getConfig().set("spawn", null);
        plugin.saveConfig();

        sender.sendMessage(ChatColor.GREEN + "All world spawn points have been cleared.");

        return true;
    }
}

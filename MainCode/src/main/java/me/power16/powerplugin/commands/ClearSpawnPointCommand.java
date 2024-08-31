package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearSpawnPointCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public ClearSpawnPointCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        // Remove the spawn point from the config
        plugin.getConfig().set("spawn." + player.getUniqueId().toString(), null);
        plugin.saveConfig();

        player.sendMessage(ChatColor.GREEN + "Your spawn point has been cleared.");

        return true;
    }
}

package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetServerSpawnCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public SetServerSpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return false;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /setserverspawn <world>");
            return false;
        }

        Player player = (Player) sender;
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            sender.sendMessage(ChatColor.RED + "World not found: " + args[0]);
            return false;
        }

        Location location = player.getLocation();
        // Save spawn location to the configuration
        plugin.getConfig().set("spawn.world", world.getName());
        plugin.getConfig().set("spawn.x", location.getX());
        plugin.getConfig().set("spawn.y", location.getY());
        plugin.getConfig().set("spawn.z", location.getZ());
        plugin.getConfig().set("spawn.yaw", location.getYaw());
        plugin.getConfig().set("spawn.pitch", location.getPitch());
        plugin.saveConfig();

        sender.sendMessage(ChatColor.GREEN + "Server spawn location set to: " + location.toVector() + " in world: " + world.getName());

        return true;
    }
}

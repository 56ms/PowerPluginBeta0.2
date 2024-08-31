package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MeowCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public MeowCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /meow <player>");
            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        Player meower = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("Player not found.");
            return false;
        }

        String message = "You were meowed by " + meower.getName() + "!";
        target.sendMessage(message);
        meower.sendMessage("You have meowed " + target.getName() + "!");

        return true;
    }
}

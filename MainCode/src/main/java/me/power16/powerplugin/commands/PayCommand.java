package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final PowerPlugin plugin;

    public PayCommand(PowerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /pay <player> <amount>");
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid amount.");
            return true;
        }

        EconomyManager economyManager = plugin.getEconomyManager();

        if (economyManager.removeBalance(player.getUniqueId(), amount)) {
            economyManager.addBalance(target.getUniqueId(), amount);
            player.sendMessage(ChatColor.GREEN + "You paid " + target.getName() + " " + amount + " coins.");
            target.sendMessage(ChatColor.GREEN + "You received " + amount + " coins from " + player.getName() + ".");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough coins.");
        }

        return true;
    }
}

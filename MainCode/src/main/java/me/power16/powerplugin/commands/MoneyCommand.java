package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {
    private final PowerPlugin plugin;

    public MoneyCommand(PowerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /money <player> <add|remove|set> [amount]");
            return true;
        }

        String targetName = args[0];
        String action = args[1];
        double amount;

        try {
            if (args.length > 2) {
                amount = Double.parseDouble(args[2]);
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /money <player> <add|remove|set> [amount]");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Amount must be a number.");
            return true;
        }

        EconomyManager economyManager = plugin.getEconomyManager();
        Player targetPlayer = Bukkit.getPlayer(targetName);

        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        double currentBalance = economyManager.getBalance(targetPlayer.getUniqueId());

        switch (action.toLowerCase()) {
            case "add":
                economyManager.setBalance(targetPlayer.getUniqueId(), currentBalance + amount);
                sender.sendMessage(ChatColor.GREEN + "Added " + amount + " to " + targetName + "'s balance.");
                break;

            case "remove":
                economyManager.setBalance(targetPlayer.getUniqueId(), currentBalance - amount);
                sender.sendMessage(ChatColor.GREEN + "Removed " + amount + " from " + targetName + "'s balance.");
                break;

            case "set":
                economyManager.setBalance(targetPlayer.getUniqueId(), amount);
                sender.sendMessage(ChatColor.GREEN + "Set " + targetName + "'s balance to " + amount + ".");
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Invalid action. Use add, remove, or set.");
                return true;
        }

        return true;
    }
}

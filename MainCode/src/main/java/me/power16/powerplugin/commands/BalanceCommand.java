package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.economy.EconomyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public BalanceCommand(PowerPlugin plugin) {
        this.economyManager = plugin.getEconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        double balance = economyManager.getBalance(player.getUniqueId());
        player.sendMessage("Your balance is: " + economyManager.formatBalance(balance));
        return true;
    }
}

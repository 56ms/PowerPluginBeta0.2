package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage("Only players can run this command!");}
        else {

            Player p = (Player) sender;

            p.openInventory(p.getEnderChest());
            p.sendMessage("You opened your enderchest!");

        }

        return true;
    }
}

package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class INVSeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p) {

            if(args.length == 0) {

                p.sendMessage("You need to choose a player!");
            } else {



                String playerName = args[0];

                Player target = Bukkit.getServer().getPlayerExact(playerName);
                if (target == null) {
                    p.sendMessage("Player not found!");
                }else {
                    ((Player) sender).openInventory(target.getInventory());
                }
            }
        }

        return true;
    }
}

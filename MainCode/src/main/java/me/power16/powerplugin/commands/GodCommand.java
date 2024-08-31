package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (p.isInvulnerable()) {

                p.setInvulnerable(false);
                p.sendMessage(ChatColor.RED + "God Mode is now Disabled!");

            }else {

                p.setInvulnerable(true);
                p.sendMessage(ChatColor.GREEN + "God Mode is now Enabled!");

            }

        }else {

            System.out.println("This command cannot be run by the Console or command blocks!");
        }


        return true;
    }
}

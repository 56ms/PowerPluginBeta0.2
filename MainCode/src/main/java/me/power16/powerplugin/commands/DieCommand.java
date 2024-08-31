package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class DieCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /die - kills the player

        if (command.getName().equals("die")) {

            if (sender instanceof Player){

                Player p = (Player)sender;
                p.setHealth(0.0);
                p.sendMessage(ChatColor.RED + "You have killed yourself!");

            }else if(sender instanceof ConsoleCommandSender) {

                System.out.println("This Command cannot be used by the Console!");

            }else if(sender instanceof BlockCommandSender) {

                System.out.println("This Command cannot be used by Command Blocks!");

            }

        }

        return true;
    }
}

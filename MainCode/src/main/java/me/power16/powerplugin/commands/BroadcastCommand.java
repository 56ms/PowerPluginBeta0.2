package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            Player p = (Player) sender;

            if (args.length == 0) {
                p.sendMessage("You did not specify any arguments when running this command!");
                p.sendMessage("Example: /broadcast <Message Here>");
            } else if (args.length == 1) {

                String word = args[0];

                Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "SERVER" + ChatColor.WHITE + "] " + word);

            } else {

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {

                    builder.append(args[i]);
                    builder.append(" ");


                }
                String finalMessage = builder.toString();
                finalMessage = finalMessage.stripTrailing();

                Bukkit.broadcastMessage(ChatColor.WHITE + "[" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "SERVER" + ChatColor.WHITE + "] " + finalMessage);
            }



            return true;
        }

    }


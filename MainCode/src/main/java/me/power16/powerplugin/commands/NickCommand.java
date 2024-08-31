package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (args.length == 0) {

                p.setDisplayName(p.getName());
                p.sendMessage("Your nickname has been cleared!");
            }else {

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {

                    builder.append(args[i]);
                    builder.append(" ");


                }
                String finalMessage = builder.toString();
                finalMessage = finalMessage.stripTrailing();

                p.setDisplayName(finalMessage);
                p.sendMessage("Your nickname has been set to '" + finalMessage + "'");
            }

        }
        return true;

    }
}
package me.power16.powerplugin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMCCommand implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {sender.sendMessage("Only players can run this command!");}
        else {

            Player p = (Player)sender;

            p.sendMessage("Your gamemode has been set to creative!");
            p.setGameMode(GameMode.CREATIVE);

        }


        return true;
    }
}

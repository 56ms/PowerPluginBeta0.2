package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class GetPermissionsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            List<String> playerPermissions = new ArrayList<>();

            String filter = args.length > 0 ? args[0].toLowerCase() : "";

            for (Permission perm : Bukkit.getPluginManager().getPermissions()) {
                if (player.hasPermission(perm) && perm.getName().toLowerCase().startsWith(filter)) {
                    playerPermissions.add(perm.getName());
                }
            }

            if (playerPermissions.isEmpty()) {
                player.sendMessage("§cYou do not have any permissions" + (filter.isEmpty() ? "." : " that start with \"" + filter + "\"."));
            } else {
                player.sendMessage("§aYour permissions" + (filter.isEmpty() ? ":" : " that start with \"" + filter + "\":"));
                for (String perm : playerPermissions) {
                    player.sendMessage("§7- " + perm);
                }
            }
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }
}

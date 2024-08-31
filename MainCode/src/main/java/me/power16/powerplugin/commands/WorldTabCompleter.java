package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorldTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("list");
            subCommands.add("tp");
            subCommands.add("create");
            subCommands.add("delete");
            subCommands.add("confirm");
            return subCommands.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            return Bukkit.getWorlds().stream().map(World::getName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            return Bukkit.getWorlds().stream().map(World::getName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        }

        return null;
    }
}

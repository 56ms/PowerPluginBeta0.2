package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

public class WorldCommand implements CommandExecutor, TabCompleter {
    private final PowerPlugin plugin;
    private final Map<UUID, String> deleteConfirmations = new HashMap<>();

    public WorldCommand(PowerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: /world <list|tp|create|delete|confirm>");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "list":
                listWorlds(sender);
                break;
            case "tp":
                teleportToWorld(sender, args);
                break;
            case "create":
                createWorld(sender, args);
                break;
            case "delete":
                deleteWorld(sender, args);
                break;
            case "confirm":
                confirmDelete(sender);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown command. Usage: /world <list|tp|create|delete|confirm>");
        }

        return true;
    }

    private void listWorlds(CommandSender sender) {
        List<World> worlds = Bukkit.getWorlds();
        sender.sendMessage(ChatColor.GREEN + "Worlds on the server:");
        for (World world : worlds) {
            sender.sendMessage(ChatColor.YELLOW + " - " + world.getName());
        }
    }

    private void teleportToWorld(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /world tp <world_name>");
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        World world = Bukkit.getWorld(args[1]);

        if (world == null) {
            sender.sendMessage(ChatColor.RED + "World not found: " + args[1]);
            return;
        }

        player.teleport(world.getSpawnLocation());
        sender.sendMessage(ChatColor.GREEN + "Teleported to world: " + args[1]);
    }

    private void createWorld(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /world create <name> [-f|-n] [-c|-s|-a|-sp]");
            return;
        }

        String worldName = args[1];
        World.Environment environment = World.Environment.NORMAL;
        ChunkGenerator generator = null;
        WorldType worldType = WorldType.NORMAL;
        String gameMode = "default";

        for (String arg : args) {
            switch (arg) {
                case "-f":
                    worldType = WorldType.FLAT;
                    break;
                case "-n":
                    worldType = WorldType.NORMAL;
                    break;
                case "-c":
                    gameMode = "CREATIVE";
                    break;
                case "-s":
                    gameMode = "SURVIVAL";
                    break;
                case "-a":
                    gameMode = "ADVENTURE";
                    break;
                case "-sp":
                    gameMode = "SPECTATOR";
                    break;
            }
        }

        if (Bukkit.getWorld(worldName) != null) {
            sender.sendMessage(ChatColor.RED + "World already exists: " + worldName);
            return;
        }

        WorldCreator creator = new WorldCreator(worldName);
        creator.environment(environment);
        if (worldType == WorldType.FLAT) {
            generator = (ChunkGenerator) Bukkit.createWorld(creator.generateStructures(worldType.equals(worldType.FLAT)));
        }
        creator.generator(generator);
        World newWorld = creator.createWorld();

        sender.sendMessage(ChatColor.GREEN + "World created: " + worldName);

        if (!gameMode.equals("default") && sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("powerplugin.gamemode") && !player.isOp()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamemode " + gameMode.toLowerCase() + " " + player.getName());
            }
        }
    }

    private void deleteWorld(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /world delete <world_name>");
            return;
        }

        String worldName = args[1];
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            sender.sendMessage(ChatColor.RED + "World not found: " + worldName);
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        deleteConfirmations.put(player.getUniqueId(), worldName);
        sender.sendMessage(ChatColor.YELLOW + "Type /world confirm to delete the world: " + worldName + ". You have 30 seconds.");

        Bukkit.getScheduler().runTaskLater(plugin, () -> deleteConfirmations.remove(player.getUniqueId()), 600L);
    }

    private void confirmDelete(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return;
        }

        Player player = (Player) sender;
        String worldName = deleteConfirmations.remove(player.getUniqueId());

        if (worldName == null) {
            sender.sendMessage(ChatColor.RED + "No world deletion pending.");
            return;
        }

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            sender.sendMessage(ChatColor.RED + "World not found: " + worldName);
            return;
        }

        Bukkit.unloadWorld(world, false);
        File worldFolder = world.getWorldFolder();

        if (deleteDirectory(worldFolder)) {
            sender.sendMessage(ChatColor.GREEN + "World " + worldName + " deleted successfully.");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to delete world " + worldName);
        }
    }

    private boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!deleteDirectory(file)) {
                        return false;
                    }
                }
            }
        }
        return directory.delete();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("list");
            completions.add("tp");
            completions.add("create");
            completions.add("delete");
            completions.add("confirm");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("delete")) {
                for (World world : Bukkit.getWorlds()) {
                    completions.add(world.getName());
                }
            } else if (args[0].equalsIgnoreCase("create")) {
                completions.add("-f");
                completions.add("-n");
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            completions.add("-c");
            completions.add("-s");
            completions.add("-a");
            completions.add("-sp");
        }

        return completions;
    }
}

package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class RepeatCommandExecutor implements CommandExecutor {

    private final JavaPlugin plugin;

    public RepeatCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /repeatcommand <int> [COMMAND]");
            return false;
        }

        int times;
        try {
            times = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("The first argument must be an integer.");
            return false;
        }

        if (times <= 0) {
            sender.sendMessage("The number of times must be greater than 0.");
            return false;
        }

        // Use StringBuilder to concatenate command arguments
        StringBuilder cmdBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            cmdBuilder.append(args[i]).append(" ");
        }
        String cmd = cmdBuilder.toString().trim(); // Trim to remove the trailing space

        for (int i = 0; i < times; i++) {
            plugin.getServer().dispatchCommand(sender, cmd);
        }

        sender.sendMessage("Command executed " + times + " times.");
        return true;
    }
}

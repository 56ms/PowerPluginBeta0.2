package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.Map;

public class PowerPluginHelpCommand implements CommandExecutor {

    private final Plugin plugin;

    public PowerPluginHelpCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginDescriptionFile pdf = plugin.getDescription();
        Map<String, Map<String, Object>> commands = pdf.getCommands();

        sender.sendMessage(ChatColor.GOLD + "----- " + pdf.getName() + " Commands -----");

        for (Map.Entry<String, Map<String, Object>> entry : commands.entrySet()) {
            String cmd = entry.getKey();
            String description = (String) entry.getValue().get("description");
            sender.sendMessage(ChatColor.YELLOW + "/" + cmd + ": " + ChatColor.WHITE + description);
        }

        return true;
    }
}

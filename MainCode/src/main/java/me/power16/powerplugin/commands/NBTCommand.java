package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NBTCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public NBTCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by players.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) {
            player.sendMessage("You must be holding an item with NBT data.");
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        if (args.length < 1) {
            player.sendMessage("Usage: /nbt <get|tell|add|remove|set> [args...]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "get":
                String nbtData = meta.toString();
                StringSelection stringSelection = new StringSelection(nbtData);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
                player.sendMessage(ChatColor.GREEN + "NBT data copied to clipboard.");
                break;

            case "tell":
                nbtData = meta.toString();
                player.sendMessage(ChatColor.YELLOW + "NBT Data: " + ChatColor.WHITE + nbtData);
                break;

            case "add":
                if (args.length < 4) {
                    player.sendMessage("Usage: /nbt add <flag> <value> [-s | -i]");
                    return true;
                }

                handleNBTData(dataContainer, meta, item, player, args[1], args[2], args.length > 3 ? args[3] : "-i", false);
                break;

            case "remove":
                if (args.length < 2) {
                    player.sendMessage("Usage: /nbt remove <flag>");
                    return true;
                }

                String removeFlag = args[1];
                dataContainer.remove(new NamespacedKey(plugin, removeFlag));
                item.setItemMeta(meta);
                player.sendMessage("Removed NBT data: " + removeFlag);
                break;

            case "set":
                if (args.length < 3) {
                    player.sendMessage("Usage: /nbt set <flag> <value> [-s | -i]");
                    return true;
                }

                handleNBTData(dataContainer, meta, item, player, args[1], args[2], args.length > 3 ? args[3] : "-i", true);
                break;

            default:
                player.sendMessage("Unknown subcommand.");
                break;
        }

        return true;
    }

    private void handleNBTData(PersistentDataContainer dataContainer, ItemMeta meta, ItemStack item, Player player, String flag, String value, String typeFlag, boolean isSetCommand) {
        PersistentDataType<?, ?> dataType = PersistentDataType.INTEGER;
        if (typeFlag.equalsIgnoreCase("-s")) {
            dataType = PersistentDataType.STRING;
        }

        NamespacedKey key = new NamespacedKey(plugin, flag);

        if (isSetCommand && dataContainer.has(key, dataType)) {
            player.sendMessage("Flag " + flag + " is already set. Use the correct type flag to modify.");
            return;
        }

        if (dataType == PersistentDataType.STRING) {
            dataContainer.set(key, PersistentDataType.STRING, value);
        } else {
            try {
                int intValue = Integer.parseInt(value);
                dataContainer.set(key, PersistentDataType.INTEGER, intValue);
            } catch (NumberFormatException e) {
                player.sendMessage("Value must be an integer or specify -s for string.");
                return;
            }
        }

        item.setItemMeta(meta);
        player.sendMessage((isSetCommand ? "Set" : "Added") + " NBT data: " + flag + " = " + value);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("get", "tell", "add", "remove", "set");
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("set"))) {
            return Arrays.asList("<flag>");
        } else if (args.length == 4 && (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("set"))) {
            return Arrays.asList("-s", "-i");
        }
        return new ArrayList<>();
    }
}

package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the player is holding an item
        if (item == null || item.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must be holding an item to modify its lore.");
            return true;
        }

        // Get item meta and lore
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /lore [add|remove|clear|insert|list] [DATA]");
            return false;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "add":
                // If no additional parameters are provided, add a blank line
                String newLoreLine;
                if (args.length < 2) {
                    newLoreLine = " ";  // Add a space to represent a blank line
                } else {
                    // Build the lore line from the provided arguments
                    StringBuilder loreBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        loreBuilder.append(args[i]);
                        if (i < args.length - 1) {
                            loreBuilder.append(" ");
                        }
                    }
                    newLoreLine = ChatColor.translateAlternateColorCodes('&', loreBuilder.toString());
                }

                // Add the new lore line to the item
                lore.add(newLoreLine);
                meta.setLore(lore);
                item.setItemMeta(meta);

                // Notify the player
                player.sendMessage(ChatColor.GREEN + "Added lore: " + (newLoreLine.equals(" ") ? "(Blank Line)" : newLoreLine));
                break;

            case "remove":
                if (args.length != 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /lore remove [LINE]");
                    return false;
                }
                try {
                    int line = Integer.parseInt(args[1]) - 1;
                    if (line < 0 || line >= lore.size()) {
                        player.sendMessage(ChatColor.RED + "Invalid line number.");
                        return false;
                    }
                    String removedLore = lore.remove(line);
                    player.sendMessage(ChatColor.GREEN + "Removed lore: " + removedLore);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid line number.");
                }
                break;

            case "clear":
                lore.clear();
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(ChatColor.GREEN + "Lore cleared.");
                break;

            case "insert":
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Usage: /lore insert [LINE] [LORE]");
                    return false;
                }
                try {
                    int line = Integer.parseInt(args[1]) - 1;
                    if (line < 0 || line > lore.size()) {
                        player.sendMessage(ChatColor.RED + "Invalid line number.");
                        return false;
                    }

                    String loreToInsert;
                    if (args.length == 2) {
                        // If no lore is provided, insert a blank line
                        loreToInsert = " ";
                    } else {
                        // Build the lore line from the provided arguments
                        StringBuilder insertBuilder = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            insertBuilder.append(args[i]);
                            if (i < args.length - 1) {
                                insertBuilder.append(" ");
                            }
                        }
                        loreToInsert = ChatColor.translateAlternateColorCodes('&', insertBuilder.toString());
                    }

                    lore.add(line, loreToInsert);
                    player.sendMessage(ChatColor.GREEN + "Inserted lore at line " + (line + 1) + ": " + (loreToInsert.equals(" ") ? "(Blank Line)" : loreToInsert));
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid line number.");
                }
                break;


            case "list":
                if (lore.isEmpty()) {
                    player.sendMessage(ChatColor.YELLOW + "This item has no lore.");
                } else {
                    player.sendMessage(ChatColor.GREEN + "Lore:");
                    for (int i = 0; i < lore.size(); i++) {
                        player.sendMessage(ChatColor.YELLOW + String.valueOf(i + 1) + ": " + lore.get(i));
                    }
                }
                break;

            default:
                player.sendMessage(ChatColor.RED + "Invalid subcommand. Usage: /lore [add|remove|clear|insert|list] [DATA]");
                return false;
        }

        // Set the updated lore back to the item
        meta.setLore(lore);
        item.setItemMeta(meta);

        return true;
    }
}

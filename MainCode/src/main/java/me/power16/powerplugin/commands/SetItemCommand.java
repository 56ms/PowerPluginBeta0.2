package me.power16.powerplugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SetItemCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /setitem <minecraft:[item_id]>");
            return true;
        }

        String itemId = args[0].toLowerCase();

        // Check if "minecraft:" is not provided and prepend it
        if (!itemId.startsWith("minecraft:")) {
            itemId = "minecraft:" + itemId;
        }

        Material material = Material.getMaterial(itemId.replace("minecraft:", "").toUpperCase());
        player.sendMessage("Debug: Item ID provided - " + itemId);


        if (material == null) {
            player.sendMessage("Invalid item ID.");
            return true;
        }

        // Get the current item in the player's main hand
        ItemStack currentItem = player.getInventory().getItemInMainHand();
        currentItem.setAmount(player.getInventory().getItemInMainHand().getAmount());

        if (currentItem == null || currentItem.getType() == Material.AIR) {
            player.sendMessage("You are not holding any item.");
            return true;
        }

        // Preserve the meta data of the current item
        ItemMeta currentMeta = currentItem.getItemMeta();
        if (currentMeta == null) {
            player.sendMessage("This item cannot have meta data.");
            return true;
        }

        // Create a new ItemStack with the new material type but keep the existing meta
        ItemStack newItem = new ItemStack(material);
        newItem.setAmount(player.getInventory().getItemInMainHand().getAmount());
        newItem.setItemMeta(currentMeta);

        // Update the item in the player's main hand
        player.getInventory().setItemInMainHand(newItem);
        player.sendMessage("Your item has been changed to " + material.name() + " with the same name, lore, and NBT tags.");

        return true;
    }

    @Override
    public List<String> onTabComplete( CommandSender sender, Command command,  String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            List<String> suggestions = new ArrayList<>();
            for (Material material : Material.values()) {
                if (material.isItem() && material.name().toLowerCase().startsWith(prefix)) {
                    suggestions.add(material.name().toLowerCase());
                }
            }
            return suggestions;
        }
        return null;
    }
}

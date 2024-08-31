package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class GetItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        // Get the item in the player's main hand
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType().isAir()) {
            player.sendMessage("You are not holding any item.");
            return true;
        }

        ItemMeta meta = itemInHand.getItemMeta();
        if (meta == null) {
            player.sendMessage("This item cannot have meta data.");
            return true;
        }

        // Build the clipboard content
        StringBuilder itemDetails = new StringBuilder();
        itemDetails.append("Item Type: ").append(itemInHand.getType().name()).append("\n");
        itemDetails.append("Name: ").append(meta.hasDisplayName() ? meta.getDisplayName() : "None").append("\n");
        itemDetails.append("Lore: ").append(meta.hasLore() ? String.join(", ", meta.getLore()) : "None").append("\n");

        // Add NBT tags if applicable
        // NBT tags are not directly accessible in Bukkit, but you can use custom NBT libraries or plugins to handle NBT tags.
        // Here, we assume no NBT handling is required; modify if necessary.

        // Copy to clipboard
        StringSelection selection = new StringSelection(itemDetails.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        player.sendMessage("Item details have been copied to your clipboard.");
        return true;
    }
}

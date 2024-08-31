package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DupeCommand implements CommandExecutor {

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

        int dupeAmount = 1; // Default duplication amount
        if (args.length > 0) {
            try {
                dupeAmount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("Please provide a valid number.");
                return true;
            }
        }

        // Limit the duplication amount to the max stack size if necessary
        int maxStackSize = itemInHand.getMaxStackSize();
        int totalAmount = itemInHand.getAmount() * dupeAmount;

        if (dupeAmount == 1) {
            // Duplicate the entire item stack
            player.getInventory().addItem(itemInHand.clone());
        } else {
            // Create a new ItemStack with the requested duplication amount
            ItemStack newStack = itemInHand.clone();
            newStack.setAmount(Math.min(totalAmount, maxStackSize));
            totalAmount -= maxStackSize;

            player.getInventory().addItem(newStack);

            // Handle additional stacks if the total amount exceeds max stack size
            while (totalAmount > 0) {
                newStack.setAmount(Math.min(totalAmount, maxStackSize));
                player.getInventory().addItem(newStack.clone());
                totalAmount -= maxStackSize;
            }
        }

        player.sendMessage("Duplicated " + dupeAmount + "x " + itemInHand.getType().name() + ".");
        return true;
    }
}

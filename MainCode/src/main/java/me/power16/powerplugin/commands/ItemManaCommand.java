package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ItemManaCommand implements CommandExecutor {

    private final PowerPlugin plugin;

    public ItemManaCommand(PowerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.getType() == Material.AIR) {
            player.sendMessage("You must be holding an item to use this command.");
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("You must have exactly 2 arguments! Try /intel [mana|reduction] [integer]");
            return true;
        }

        int value;
        try {
            value = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("The argument must be a valid integer! Try /intel [mana|reduction] [integer]");
            return true;
        }

        ItemMeta meta = itemInHand.getItemMeta();
        if (meta == null) {
            player.sendMessage("This item cannot have meta data!");
            return true;
        }

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

        if (args[0].equalsIgnoreCase("mana")) {
            dataContainer.set(new NamespacedKey(plugin, "intelligence"), PersistentDataType.INTEGER, value);
            itemInHand.setItemMeta(meta);
            player.sendMessage("Mana set to " + value + " for the item in your hand.");
            return true;
        } else if (args[0].equalsIgnoreCase("reduction")) {
            dataContainer.set(new NamespacedKey(plugin, "mcdp"), PersistentDataType.INTEGER, value);
            itemInHand.setItemMeta(meta);
            player.sendMessage("Reduction set to " + value + "% for the item in your hand.");
            return true;
        } else {
            player.sendMessage("I don't recognize that sub command! Try /intel [mana|reduction] [integer]");
            return true;
        }
    }
}

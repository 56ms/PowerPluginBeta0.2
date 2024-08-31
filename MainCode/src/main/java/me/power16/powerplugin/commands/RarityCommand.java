package me.power16.powerplugin.commands;

import me.power16.powerplugin.utils.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RarityCommand implements CommandExecutor, TabCompleter {

    private final List<String> actions = Arrays.asList("SET", "CLEAR", "CHANGE");
    private final List<String> types = Arrays.asList("SWORD", "BOW", "HELMET","CHESTPLATE","LEGGINGS","BOOTS", "TOOL", "ITEM","BLOCK");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + "Usage: /rarity [SET|CLEAR|CHANGE] [COMMON|UNCOMMON|RARE|EPIC|LEGENDARY|MYTHIC|DIVINE|SPECIAL] [TYPE]");
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        assert lore != null;
        Rarity rarity;



        // Use either the custom name or the localized name
        String itemName = meta.hasDisplayName() ? meta.getDisplayName() : item.getType().name().replace("_", " ");

        if (args[0] == "clear") {

            for (int i = 0; i < 2; i++) {
                lore.remove(lore.size() - 1);
            }
            return true;

        }



        String action = args[0].toUpperCase();
        String rarityString = args[1].toUpperCase();
        String type = args[2].toUpperCase();


        try {
            rarity = Rarity.valueOf(rarityString);
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid rarity!");
            return true;
        }


        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must hold an item in your hand!");
            return true;
        }


        if (meta == null) {
            player.sendMessage(ChatColor.RED + "This item cannot have metadata!");
            return true;
        }



        switch (action) {
            case "SET":
            case "CHANGE":
                if ("CHANGE".equals(action) && lore.size() >= 2) {
                    // Remove last 3 lines (old rarity)
                    for (int i = 0; i < 2; i++) {
                        lore.remove(lore.size() - 1);
                    }
                }
                // Set the new name and lore
                meta.setDisplayName(rarity.getColor() + itemName);
                lore.add("");
                lore.add(rarity.getColor() + "" + ChatColor.BOLD + "" + rarity.name() + rarity.getColor() + " " + ChatColor.BOLD + type);
                break;

            case "CLEAR":
                // Set name to red and clear last 3 lines of lore
                meta.setDisplayName(ChatColor.RED + itemName);
                if (lore.size() >= 3) {
                    for (int i = 0; i < 2; i++) {
                        lore.remove(lore.size() - 1);
                    }
                }
                break;

            default:
                player.sendMessage(ChatColor.RED + "Invalid action!");
                return true;
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        player.sendMessage(ChatColor.GREEN + "Item rarity updated!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            // Suggest the available actions
            return actions.stream()
                    .filter(a -> a.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            // Suggest the available rarities
            return Arrays.stream(Rarity.values())
                    .map(Enum::name)
                    .filter(r -> r.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 3) {
            // Suggest the available types
            return types.stream()
                    .filter(t -> t.toLowerCase().startsWith(args[2].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}

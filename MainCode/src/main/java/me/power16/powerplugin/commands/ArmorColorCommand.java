package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorColorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /armorcolor set|clear {color}");
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        if (item == null || !(item.getItemMeta() instanceof LeatherArmorMeta)) {
            player.sendMessage(ChatColor.RED + "You must be holding a leather armor piece.");
            return false;
        }



        if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Please specify a color.");
                return false;
            }

            Color color = parseColor(args[1]);
            if (color == null) {
                player.sendMessage(ChatColor.RED + "Invalid color. Use a hex code or ChatColor name.");
                return false;
            }

            meta.setColor(color);
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GREEN + "Armor color set!");

        } else if (args[0].equalsIgnoreCase("clear")) {
            meta.setColor(Color.fromRGB(160, 101, 64)); // Default leather color
            item.setItemMeta(meta);
            player.sendMessage(ChatColor.GREEN + "Armor color cleared!");
        } else {
            player.sendMessage(ChatColor.RED + "Invalid option. Use 'set' or 'clear'.");
        }

        return true;
    }

    private Color parseColor(String colorArg) {
        // Check if the color is a hex code
        if (colorArg.startsWith("#")) {
            try {
                int rgb = Integer.parseInt(colorArg.substring(1), 16);
                return Color.fromRGB(rgb);
            } catch (NumberFormatException e) {
                return null; // Invalid hex code
            }
        }


        // Try to convert the ChatColor to a Color
        try {
            ChatColor chatColor = ChatColor.valueOf(colorArg.toUpperCase());
            return getColorFromChatColor(chatColor);
        } catch (IllegalArgumentException e) {
            return null; // Invalid ChatColor name
        }
    }

    private Color getColorFromChatColor(ChatColor chatColor) {
        switch (chatColor) {
            case BLACK: return Color.BLACK;
            case DARK_BLUE: return Color.NAVY;
            case DARK_GREEN: return Color.GREEN;
            case DARK_AQUA: return Color.TEAL;
            case DARK_RED: return Color.MAROON;
            case DARK_PURPLE: return Color.PURPLE;
            case GOLD: return Color.ORANGE;
            case GRAY: return Color.GRAY;
            case BLUE: return Color.BLUE;
            case GREEN: return Color.LIME;
            case AQUA: return Color.AQUA;
            case RED: return Color.RED;
            case LIGHT_PURPLE: return Color.FUCHSIA;
            case YELLOW: return Color.YELLOW;
            case WHITE: return Color.WHITE;
            default: return null;
        }
    }
}

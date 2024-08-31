package me.power16.powerplugin.listeners;

import me.power16.powerplugin.commands.OpenMenuCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuClickListener implements Listener {




    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!event.getView().getTitle().equals("Warp Menu")) {
            return;
        }

        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == player.getInventory()) {return;} else {
        switch (slot) {
            case 4:
                player.getInventory().addItem(clickedInventory.getItem(slot));
                player.sendMessage("You Collected your current Permissions!");
                event.setCancelled(true);
                break;
            case 12:
                player.getInventory().addItem(clickedInventory.getItem(slot));
                player.sendMessage("You clicked on the Sword of Power!");
                event.setCancelled(true);
                break;
            case 13:
                player.getInventory().addItem(clickedInventory.getItem(slot));
                player.sendMessage(ChatColor.GREEN + "You have received the Dctr's Space Helmet!");
                event.setCancelled(true);
                break;

            case 14:
                player.getInventory().addItem(clickedInventory.getItem(slot));

                player.sendMessage("You clicked on the Golden Apple!");
                event.setCancelled(true);
                break;
            default:
                player.getInventory().addItem(clickedInventory.getItem(slot));

                event.setCancelled(true);
                break;
            }
        }
    }
}

package me.power16.powerplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class PlayerMenuListener implements Listener {


    @EventHandler
    public void onclick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!event.getView().getTitle().equals("Player Menu")) {
            return;
        }
        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        if (player.isSneaking() == true) {return;}

        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == player.getInventory()) {return;} else {
            switch (slot) {
                case 1:
                default:
                    event.setCancelled(true);
            }
        }
    }
}
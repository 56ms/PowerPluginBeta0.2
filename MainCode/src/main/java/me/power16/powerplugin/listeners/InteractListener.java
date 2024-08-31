package me.power16.powerplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InteractListener implements Listener {

        // Check if the entity is a player

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }


        Player player = event.getPlayer();
        Player clickedPlayer = (Player) event.getRightClicked();
        if (player.isSneaking() == true) {return;}


        // Get the player's armor pieces
        ItemStack helmet = clickedPlayer.getInventory().getHelmet();
        ItemStack chestplate = clickedPlayer.getInventory().getChestplate();
        ItemStack leggings = clickedPlayer.getInventory().getLeggings();
        ItemStack boots = clickedPlayer.getInventory().getBoots();

        // Create a custom inventory with a size of 54 slots
        Inventory inventory = Bukkit.createInventory(null, 54, "Player Menu");

        ItemStack fill = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta3 = fill.getItemMeta();
        if (meta3 != null) {
            meta3.setDisplayName(" ");
            fill.setItemMeta(meta3);
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, fill);
            }
        }

        ItemStack air = new ItemStack(Material.AIR);

        // Set the armor pieces in the specified slots if they are not null or air
        if (helmet != null && helmet.getType() != Material.AIR) {
            inventory.setItem(11, helmet);
        }else {inventory.setItem(11, air);}

        if (chestplate != null && chestplate.getType() != Material.AIR) {
            inventory.setItem(20, chestplate);
        }else {inventory.setItem(20, air);}

        if (leggings != null && leggings.getType() != Material.AIR) {
            inventory.setItem(29, leggings);
        }else {inventory.setItem(29, air);}

        if (boots != null && boots.getType() != Material.AIR) {
            inventory.setItem(38, boots);
        }else {inventory.setItem(38, air);}

        ItemStack blueblank = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta blueblankmeta = blueblank.getItemMeta();
        blueblankmeta.setDisplayName(" ");
        blueblankmeta.setLore(Arrays.asList(" "," "));
        blueblank.setItemMeta(blueblankmeta);
        inventory.setItem(10, blueblank);
        inventory.setItem(19, blueblank);
        inventory.setItem(28, blueblank);
        inventory.setItem(37, blueblank);

        // Open the GUI for the player



        player.openInventory(inventory);
        }
    }


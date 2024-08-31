package me.power16.powerplugin.listeners;

import me.power16.powerplugin.PlayerStatsManager;
import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.commands.ItemCommand;
import me.power16.powerplugin.economy.EconomyManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.Random;

public class ItemInteractListener implements Listener {

    private final PowerPlugin plugin;
    private final ItemCommand itemCommand; // Add this field
    private final EconomyManager economyManager;

    // Updated constructor
    public ItemInteractListener(PowerPlugin plugin, ItemCommand itemCommand, EconomyManager economyManager) {
        this.plugin = plugin;
        this.itemCommand = itemCommand;
        this.economyManager = economyManager;
    }

    private boolean isItem(String itemId, ItemMeta meta) {
        NamespacedKey idKey = new NamespacedKey("powerplugin", "id");
        return itemId.equals(meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING));
    }

    private final Random random = new Random();

    private Integer getIntel(ItemMeta meta, Integer manaCost) {
        if (meta.getPersistentDataContainer().has(new NamespacedKey("powerplugin", "mcdp"), PersistentDataType.INTEGER)) {
            int reduction1 = manaCost * meta.getPersistentDataContainer().get(new NamespacedKey("powerplugin", "mcdp"), PersistentDataType.INTEGER) / 100;
            manaCost -= reduction1;
        }
        return manaCost;
    }

    private void teleport8blocks(Player player, int manaCost, ItemMeta meta) {
        PlayerStatsManager.PlayerStats stats = plugin.getPlayerStatsManager().getPlayerStats(player);

        if (stats.getMana() >= manaCost) {
            stats.consumeMana(manaCost);
            Location startLocation = player.getLocation();
            Location aboveLocation = startLocation.clone().add(0, 1, 0);

            if (aboveLocation.getBlock().getType() == Material.AIR) {
                Location aboveAboveLocation = aboveLocation.clone().add(0, 1, 0);
                if (aboveAboveLocation.getBlock().getType() == Material.AIR) {
                    player.teleport(aboveLocation);
                    startLocation = aboveLocation;
                }
            }

            Vector direction = startLocation.getDirection().normalize();
            double maxDistance = 8.0;
            Location targetLocation = startLocation.clone();
            double distanceTraveled = 0.0;

            while (distanceTraveled < maxDistance) {
                targetLocation.add(direction);
                distanceTraveled += 1.0;

                Block block = targetLocation.getBlock();
                if (block.getType() != Material.AIR && block.getType() != Material.VOID_AIR) {
                    targetLocation.subtract(direction);
                    break;
                }
            }

            if (targetLocation.getBlock().getType() != Material.AIR && targetLocation.getBlock().getType() != Material.VOID_AIR) {
                targetLocation.subtract(direction);
            }

            player.teleport(targetLocation);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        // Check if the player is interacting with an inventory other than their own inventory (hotbar)

        // Check if an item is in the player's hand and has metadata
        if (item != null && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            // Check if the item has a custom identifier
            if (meta.getPersistentDataContainer().has(new NamespacedKey("powerplugin", "id"), PersistentDataType.STRING)) {
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    // Process item actions if no other inventory is open
                    if (isItem("ASPECT_OF_THE_END", meta)) {
                        event.setCancelled(true);
                        int cost = getIntel(meta, 50);
                        teleport8blocks(player, cost, meta);
                    } else if (isItem("ASPECT_OF_THE_VOID", meta)) {
                        event.setCancelled(true);
                        int cost = getIntel(meta, 45);
                        teleport8blocks(player, cost, meta);
                    } else if (isItem("CYANIDE_PILL", meta)) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor.RED + "You had too much cyanide and died :'(");
                        ItemStack bottle = new ItemStack(itemCommand.createPillRemnant());
                        bottle.setItemMeta(itemCommand.createPillRemnant().getItemMeta());
                        player.getInventory().addItem(bottle);
                        int amt = player.getInventory().getItemInMainHand().getAmount();
                        player.getInventory().getItemInMainHand().setAmount(amt - 1);
                        player.setHealth(0);
                    } else if (isItem("MOVE_STICK", meta)) {
                        Vector direction = player.getLocation().getDirection();  // Get the player's current direction
                        direction.normalize();  // Normalize the direction vector to keep the direction but ignore the magnitude

                        double magnitude = 5;
                        direction.multiply(magnitude);  // Scale the direction vector by the magnitude

                        player.setVelocity(direction);  // Set the player's velocity to the calculated direction
                        event.setCancelled(true);  // Cancel the event to prevent other interactions
                    } else if (isItem("GAMBLE_COIN", meta)) {
                        event.setCancelled(true);
                        double balance = economyManager.getBalance(player.getUniqueId());
                        if (balance < 1) {
                            player.sendMessage("Your balance is too low. You need at least 1 balance!");
                            return;
                        }

                        Random rand = new Random();
                        int outcome = rand.nextInt(2); // Random number 0 or 1

                        if (outcome == 1) {
                            double winnings = balance * 2;
                            economyManager.setBalance(player.getUniqueId(), winnings);
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "WIN!" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + " You now have " + economyManager.formatBalance(winnings));
                        } else {
                            economyManager.setBalance(player.getUniqueId(), 0);
                            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "LOSE!" + ChatColor.RESET + "" + ChatColor.DARK_RED + " You lost everything!");
                            ItemStack bottle = new ItemStack(itemCommand.createCyanidePill());
                            player.getInventory().addItem(bottle);
                            int amt = player.getInventory().getItemInMainHand().getAmount();
                            player.getInventory().getItemInMainHand().setAmount(amt - 1);
                        }
                    } else if (isItem("MONEY_ITEM", meta)) {
                        economyManager.addBalance(player.getUniqueId(), 1000000);
                        player.sendMessage(ChatColor.GREEN + "You gained $1m!");
                        event.setCancelled(true);

                    }
                }
            } else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                // Process left-click actions if no other inventory is open
                    // Add your left-click functionality here
                    //player.sendMessage(ChatColor.GOLD + "You just left-clicked with " + item.getItemMeta().getDisplayName() + "!");

            }
        }else{
            System.out.println("Player: " + player.getDisplayName() + " tried to use an invalid item: " + item);
        }
    }
}




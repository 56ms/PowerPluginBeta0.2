package me.power16.powerplugin.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import me.power16.powerplugin.PowerPlugin; // Import your main plugin class

public class RainbowCycleTask extends BukkitRunnable {

    private final Player player;
    private final PowerPlugin plugin; // Plugin instance
    private int currentColorIndex = 0;
    private static final Material[] COLORS = {
            Material.RED_STAINED_GLASS,
            Material.ORANGE_STAINED_GLASS,
            Material.YELLOW_STAINED_GLASS,
            Material.LIME_STAINED_GLASS,
            Material.GREEN_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS,
            Material.BLUE_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS,
            Material.MAGENTA_STAINED_GLASS,
            Material.PINK_STAINED_GLASS
    };

    public RainbowCycleTask(Player player, PowerPlugin plugin) {
        this.player = player;
        this.plugin = plugin; // Initialize plugin instance
    }

    @Override
    public void run() {
        ItemStack helmet = player.getInventory().getHelmet();


        if (helmet != null && helmet.getType().name().contains("STAINED_GLASS")) {
            // Store previous item meta
            ItemMeta previousMeta = helmet.getItemMeta();
            if (previousMeta == null) {
                this.cancel();
                return;
            }

            // Cycle through colors
            Material newColor = COLORS[currentColorIndex];
            currentColorIndex = (currentColorIndex + 1) % COLORS.length;

            // Create new helmet with the current color
            ItemStack newHelmet = new ItemStack(newColor);
            ItemMeta newMeta = newHelmet.getItemMeta();
            newHelmet.setAmount(helmet.getAmount());
            if (newMeta != null) {
                // Preserve previous item's name, lore, and any NBT tags
                newMeta.setDisplayName(previousMeta.getDisplayName());
                newMeta.setLore(previousMeta.getLore());
                newHelmet.setItemMeta(newMeta);
            }

            // Update helmet in player's inventory
            player.getInventory().setHelmet(newHelmet);
        } else {
            // Cancel task if the player is not wearing stained glass
            this.cancel();
        }
    }
}

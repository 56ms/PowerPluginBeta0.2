package me.power16.powerplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomDamageListener implements Listener {

    private final JavaPlugin plugin;

    public CustomDamageListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();

            // Check if entity is a custom mob or player
            if (isCustomMob(entity)) {// Cancel default damage

                double damage = getCustomDamage(entity);
                applyCustomDamage(entity, damage);
            }
        }
    }

    private boolean isCustomMob(LivingEntity entity) {
        // Add logic to identify custom mobs
        return false;
    }

    private double getCustomDamage(LivingEntity entity) {
        // Implement logic to determine the damage to apply
        return 10.0; // Example default damage
    }

    private void applyCustomDamage(LivingEntity entity, double damage) {
        // Use NMS or custom logic to apply damage
    }
}

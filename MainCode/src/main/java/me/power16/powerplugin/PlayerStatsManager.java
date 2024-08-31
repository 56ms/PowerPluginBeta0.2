package me.power16.powerplugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatsManager {
    private final Map<Player, PlayerStats> playerStatsMap = new HashMap<>();

    public PlayerStats getPlayerStats(Player player) {
        return playerStatsMap.computeIfAbsent(player, k -> new PlayerStats());
    }

    public void removePlayerStats(Player player) {
        playerStatsMap.remove(player);
    }

    public void updatePlayerStats(Player player) {
        PlayerStats stats = getPlayerStats(player);
        int totalIntelligence = 0;

        // Update player health based on custom logic
        int currentHealth = stats.getHealth(); // Use custom health
        player.setHealth(Math.min(currentHealth, 20));
        PlayerInventory inventory = player.getInventory();

        // Get items from specific slots
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();
        ItemStack mainHand = inventory.getItemInMainHand();
        ItemStack offHand = inventory.getItemInOffHand();

        // Check each item for intelligence
        totalIntelligence += getItemIntelligence(helmet);
        totalIntelligence += getItemIntelligence(chestplate);
        totalIntelligence += getItemIntelligence(leggings);
        totalIntelligence += getItemIntelligence(boots);
        totalIntelligence += getItemIntelligence(mainHand);
        totalIntelligence += getItemIntelligence(offHand);

        stats.setIntelligence(totalIntelligence);
    }

    private int getItemIntelligence(ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer() != null) {
            Integer itemIntelligence = item.getItemMeta().getPersistentDataContainer()
                    .get(new NamespacedKey("powerplugin", "intelligence"), PersistentDataType.INTEGER);
            if (itemIntelligence != null) {
                return itemIntelligence;
            }
        }
        return 0;
    }

    public static class PlayerStats {
        private int health = 100;
        private int mana = 100;
        private int defense = 0;
        private int intelligence = 0;
        private int totaldamage = 0;
        private int strength = 0;
        private int critdamage = 100;
        private int critChance = 50;

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = Math.max(0, health); // Health cannot be negative
        }

        public int getMana() {
            return mana;
        }

        public void setMana(int mana) {
            this.mana = Math.max(0, Math.min(mana, getMaxMana())); // Mana cannot be negative and should not exceed max mana
        }

        public int getDefense() {
            return defense;
        }

        public void setDefense(int defense) {
            this.defense = defense;
        }

        public int getIntelligence() {
            return intelligence;
        }

        public void setIntelligence(int intelligence) {
            this.intelligence = intelligence;
        }

        public int getMaxMana() {
            return 100 + (intelligence * 10); // Max mana is calculated based on intelligence
        }

        public void takeDamage(int damage) {
            int effectiveDamage = Math.max(0, damage - defense);
            setHealth(getHealth() - effectiveDamage);
        }

        public void consumeMana(int amount) {
            setMana(getMana() - amount);
        }
    }
}

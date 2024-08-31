package me.power16.powerplugin.commands;

import me.power16.powerplugin.PowerPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PPSCommand implements CommandExecutor, TabCompleter {

    // List of custom mobs
    private static final List<String> CUSTOM_MOBS = Arrays.asList("CRYPT_GHOUL");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                String mobName = args[0].toUpperCase();

                if (CUSTOM_MOBS.contains(mobName)) {
                    if ("CRYPT_GHOUL".equalsIgnoreCase(mobName)) {
                        spawnCryptGhoul(player);
                        player.sendMessage(ChatColor.GREEN + "Spawned a Crypt Ghoul with special attributes!");
                    }
                } else {
                    try {
                        EntityType entityType = EntityType.valueOf(mobName);

                        // Check if the entity type is spawnable and is not 'UNKNOWN'
                        if (!entityType.isSpawnable() || entityType == EntityType.UNKNOWN) {
                            throw new IllegalArgumentException();
                        }

                        spawnMob(player, entityType);
                        player.sendMessage(ChatColor.GREEN + "Spawned a " + mobName + " with special attributes!");
                    } catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "Invalid mob type! Use a valid spawnable mob name or 'CRYPT_GHOUL'.");
                    }
                }
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /ppspawn <mob>");
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
            return false;
        }
    }

    private void spawnMob(Player player, EntityType entityType) {
        Mob mob = (Mob) player.getWorld().spawnEntity(player.getLocation(), entityType);
        int health = (int) mob.getHealth();
        // Set default attribute
        setMobHealth(mob, health);
        mob.setAI(true);
        mob.setAware(true);
        mob.setCustomNameVisible(true);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        // Schedule a task to update the health display name
        PowerPlugin.getInstance().updateAllMobsHealth(1);
    }

    private void spawnCryptGhoul(Player player) {
        // Spawn the mob as a Zombie
        Mob mob = (Mob) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);

        // Set the mob's max health
        double maxHealth = 2000.0;
        mob.setAI(true);
        mob.setAware(true);
        mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        setMobHealth(mob, maxHealth);

        // Set the mob's custom name
        mob.setCustomName(ChatColor.GRAY + "[lvl 5] " + ChatColor.RED + "Crypt Ghoul " + (int) mob.getHealth() + "/" + (int) maxHealth);
        mob.setCustomNameVisible(true);

        // Equip the mob with chainmail armor
        ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
        ItemStack chainmailChestplate = new ItemStack(org.bukkit.Material.CHAINMAIL_CHESTPLATE);
        ItemStack chainmailLeggings = new ItemStack(org.bukkit.Material.CHAINMAIL_LEGGINGS);
        ItemStack chainmailBoots = new ItemStack(org.bukkit.Material.CHAINMAIL_BOOTS);

        mob.getEquipment().setHelmet(helmet);
        mob.getEquipment().setChestplate(chainmailChestplate);
        mob.getEquipment().setLeggings(chainmailLeggings);
        mob.getEquipment().setBoots(chainmailBoots);

        // Prevent the mob from burning in sunlight



        // Schedule a task to update the health display name
        PowerPlugin.getInstance().updateAllMobsHealth(1);
    }

    private void setMobHealth(Mob mob, double health) {
        // Get the attribute instance for maximum health
        AttributeInstance maxHealthAttribute = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute != null) {
            // Set the maximum health and current health
            maxHealthAttribute.setBaseValue(health);
            mob.setHealth(health);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>(CUSTOM_MOBS);
            suggestions.addAll(
                    Arrays.stream(EntityType.values())
                            .filter(EntityType::isSpawnable)
                            .map(EntityType::name)
                            .collect(Collectors.toList())
            );
            return suggestions.stream()
                    .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}

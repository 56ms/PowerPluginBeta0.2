package me.power16.powerplugin.ranks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.permissions.PermissionAttachment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankManager {
    private final Map<UUID, Rank> playerRanks = new HashMap<>();
    private final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();
    private final File ranksFile;
    private FileConfiguration ranksConfig;
    private final Plugin plugin;

    public RankManager(Plugin plugin, File dataFolder) {
        this.plugin = plugin;
        this.ranksFile = new File(plugin.getDataFolder(), "ranks.yml");
        loadRanks();
    }

    public void setPlayerRank(Player player, Rank rank) {
        UUID playerId = player.getUniqueId();
        PermissionAttachment attachment;

        // Debug message to check if the method is being called
        System.out.println("Setting rank for player: " + player.getName());

        // If the player already has an attachment, use it, otherwise create a new one
        if (playerPermissions.containsKey(playerId)) {
            attachment = playerPermissions.get(playerId);
            // Debug message to confirm existing attachment
            System.out.println("Using existing attachment for player: " + player.getName());
            // Remove all current permissions
            attachment.getPermissions().keySet().forEach(attachment::unsetPermission);
        } else {
            attachment = player.addAttachment(plugin);
            playerPermissions.put(playerId, attachment);
            // Debug message to confirm new attachment
            System.out.println("Created new attachment for player: " + player.getName());
        }

        // Assign the new rank
        playerRanks.put(playerId, rank);
        saveRanks();

        // Set the permissions for the new rank
        for (String permission : rank.getPermissions()) {
            attachment.setPermission(permission, true);
            // Debug message to confirm permissions being set
            System.out.println("Added permission: " + permission + " to player: " + player.getName());
        }

        player.sendMessage("Your rank has been set to " + rank.getDisplayName() + ".");
    }

    public Rank getPlayerRank(Player player) {
        return playerRanks.getOrDefault(player.getUniqueId(), Rank.PLAYER);
    }

    private void loadRanks() {
        if (!ranksFile.exists()) {
            return;
        }

        ranksConfig = YamlConfiguration.loadConfiguration(ranksFile);

        for (String uuidString : ranksConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(uuidString);
            String rankName = ranksConfig.getString(uuidString + ".rank");
            try {
                Rank rank = Rank.valueOf(rankName.toUpperCase());
                playerRanks.put(uuid, rank);
            } catch (IllegalArgumentException e) {
                // Invalid rank, handle appropriately
            }
        }
    }

    private void saveRanks() {
        if (ranksConfig == null) {
            ranksConfig = new YamlConfiguration();
        }

        for (Map.Entry<UUID, Rank> entry : playerRanks.entrySet()) {
            UUID uuid = entry.getKey();
            Rank rank = entry.getValue();
            ranksConfig.set(uuid.toString() + ".rank", rank.name());
        }

        try {
            ranksConfig.save(ranksFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getAvailableRanks() {
        Rank[] ranks = Rank.values();
        String[] rankNames = new String[ranks.length];
        for (int i = 0; i < ranks.length; i++) {
            rankNames[i] = ranks[i].name();
        }
        return rankNames;
    }
}

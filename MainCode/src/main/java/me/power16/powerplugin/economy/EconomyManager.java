package me.power16.powerplugin.economy;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class EconomyManager {

    private final File file;
    private final FileConfiguration config;

    public EconomyManager(File dataFolder) {
        this.file = new File(dataFolder, "economy.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public double getBalance(UUID playerUUID) {
        return config.getDouble(playerUUID.toString(), 0.0);
    }

    public void setBalance(UUID playerUUID, double amount) {
        config.set(playerUUID.toString(), amount);
        saveConfig();
    }

    public void addBalance(UUID playerUUID, double amount) {
        double currentBalance = getBalance(playerUUID);
        setBalance(playerUUID, currentBalance + amount);
    }

    public boolean removeBalance(UUID playerUUID, double amount) {
        double currentBalance = getBalance(playerUUID);
        if (currentBalance >= amount) {
            setBalance(playerUUID, currentBalance - amount);
            return true;
        } else {
            return false;
        }
    }

    public String formatBalance(double amount) {
        if (amount < 10_000) {
            return String.format(String.valueOf(amount));
        }

        String[] abbreviations = {"", "k", "m", "b", "t", "q"};
        int i = 0;

        while (amount >= 1_000 && i < abbreviations.length - 1) {
            amount /= 1_000;
            i++;
        }

        return String.format("%.2f%s", amount, abbreviations[i]);
    }


    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

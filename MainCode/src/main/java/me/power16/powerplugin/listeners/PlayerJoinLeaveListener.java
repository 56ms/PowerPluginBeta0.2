package me.power16.powerplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        e.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.YELLOW + player.getDisplayName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        World hubWorld = Bukkit.getWorld("Hub");

        if (hubWorld != null) {
            player.setBedSpawnLocation(hubWorld.getSpawnLocation(), true);
        }

        if (player.hasPlayedBefore()) {
            e.setJoinMessage(ChatColor.GREEN + " Welcome back to the SSMP " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.GREEN + "! We missed you <3");
        } else {
            e.setJoinMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Welcome to the SSMP " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.BOLD + "" + ChatColor.GREEN + "! Enjoy your time here! <3");
        }
    }
}

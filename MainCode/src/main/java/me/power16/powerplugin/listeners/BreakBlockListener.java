package me.power16.powerplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BreakBlockListener implements Listener {

    private List<Material> allowedBlocks;

    // Constructor to initialize the allowed blocks list
    public BreakBlockListener() {
        allowedBlocks = new ArrayList<>();
        allowedBlocks.add(Material.STONE);
        allowedBlocks.add(Material.WHEAT_SEEDS);
        allowedBlocks.add(Material.WHEAT);
        allowedBlocks.add(Material.POTATO);
        allowedBlocks.add(Material.POTATOES);
        allowedBlocks.add(Material.NETHER_WART);
    }

    private boolean isBlockAllowed(Block block) {
        // Check if the block type is in the allowed list
        return allowedBlocks.contains(block.getType());
    }

    @EventHandler
    private void onBreakBlock(BlockBreakEvent e) {
        World world = e.getBlock().getWorld();
        Player player = e.getPlayer();
        Block block = e.getBlock();

        // Allow admins in creative mode with the appropriate permission to break blocks
        if (player.getGameMode() == GameMode.CREATIVE && player.hasPermission("admin.breakBlock")) {
            return;
        }

        // Allow breaking blocks in the "Hub" world only if the block is in the allowed list
        if (world.getName().equals("Hub")) {
            if (!isBlockAllowed(block)) {
                e.setCancelled(true);
            }
        } else {
            // For all other worlds, cancel the break event
            e.setCancelled(true);

        }
    }
}

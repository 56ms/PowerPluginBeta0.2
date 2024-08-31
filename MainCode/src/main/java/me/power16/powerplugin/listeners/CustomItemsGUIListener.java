package me.power16.powerplugin.listeners;

import me.power16.powerplugin.PowerPlugin;
import me.power16.powerplugin.commands.ItemCommand;
import me.power16.powerplugin.ranks.RankManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomItemsGUIListener implements Listener {

    private final ItemCommand itemCommand;

    // Constructor to inject PowerPlugin and RankManager
    public CustomItemsGUIListener(PowerPlugin plugin, RankManager rankManager) {
        // Initialize ItemCommand with the required arguments
        this.itemCommand = new ItemCommand(plugin, rankManager);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!event.getView().getTitle().equals(ChatColor.DARK_BLUE + "Power Custom Items")) {
            return;
        }

        int slot = event.getRawSlot();
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == player.getInventory()) {
            return;
        } else {
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);
            switch (slot) {
                case 5:
                    inventory.setItem(5, itemCommand.createDctrSpaceHelmet(player));
                    player.getInventory().addItem(item);
                    break;
                case 10:
                    inventory.setItem(10, itemCommand.createMoveStick());
                    player.getInventory().addItem(item);
                    break;
                case 13:
                    inventory.setItem(13, itemCommand.createAdminMoney());
                    player.getInventory().addItem(item);
                    break;
                default:
                    player.getInventory().addItem(item);
                    break;
            }
        }
    }
}

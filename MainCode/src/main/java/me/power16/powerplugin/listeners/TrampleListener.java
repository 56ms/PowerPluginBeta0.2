package me.power16.powerplugin.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class TrampleListener implements Listener {

    private boolean isItem(String itemId, ItemMeta meta) {
        NamespacedKey idKey = new NamespacedKey("powerplugin", "id");
        return itemId.equals(meta.getPersistentDataContainer().get(idKey, PersistentDataType.STRING));
    }

    @EventHandler
    public void onTrample(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemMeta meta = player.getInventory().getBoots().getItemMeta();

        if (meta != null && isItem("RANCHER_BOOTS", meta)) {
            if (e.getAction() == Action.PHYSICAL) {
                Block block = e.getClickedBlock();
                if ((block.getType() == Material.DIRT || block.getType() == Material.FARMLAND)) {
                    e.setCancelled(true); // Prevents trampling
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Block blocke = e.getClickedBlock();
                    if (blocke != null && blocke.getBlockData() instanceof Ageable) {
                        player.breakBlock(blocke); // Allows breaking crops
                    }
                }
            }
        }
    }
}

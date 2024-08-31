package me.power16.powerplugin.listeners;

import me.power16.powerplugin.ranks.Rank;
import me.power16.powerplugin.ranks.RankManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

public class ChatListener implements Listener {
    private final RankManager rankManager;

    public ChatListener(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Rank rank = rankManager.getPlayerRank(player);
        String prefix = rank.getPrefix();

        event.setFormat(prefix + player.getDisplayName() + ": " + event.getMessage());
    }
}

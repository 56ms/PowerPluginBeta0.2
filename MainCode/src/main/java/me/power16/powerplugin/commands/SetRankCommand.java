package me.power16.powerplugin.commands;

import me.power16.powerplugin.ranks.Rank;
import me.power16.powerplugin.ranks.RankManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRankCommand implements CommandExecutor {
    private final RankManager rankManager;

    public SetRankCommand(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can set ranks.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("Usage: /setrank <player> <rank>");
            return true;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        try {
            Rank rank = Rank.valueOf(args[1].toUpperCase());
            rankManager.setPlayerRank(target, rank);
            sender.sendMessage("Set " + target.getName() + "'s rank to " + rank.getDisplayName() + ".");
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Rank not found. Available ranks: " + String.join(", ", rankManager.getAvailableRanks()));
        }

        return true;
    }
}

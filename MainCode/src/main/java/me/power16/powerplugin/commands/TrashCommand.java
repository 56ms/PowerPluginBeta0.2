package me.power16.powerplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class TrashCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            Inventory trash = Bukkit.createInventory(null, 54, "Trash Bin");
            p.openInventory(trash);
            p.sendMessage("You opened up the trash menu!");
        }else {sender.sendMessage("Only players can run this command!");}



        return true;
    }
}

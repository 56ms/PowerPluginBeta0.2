package me.power16.powerplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetAmountCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                sender.sendMessage("You must specify an amount to set!");
            }else if(args.length > 1) {
                sender.sendMessage("You can only have 1 integer!");
            }Integer amount = Integer.parseInt(args[0]);
            Player p = (Player)sender;
            p.getInventory().getItemInMainHand().setAmount(amount);
            p.sendMessage(ChatColor.GREEN + "You set your itemstack to the value of " + amount);


        }else {sender.sendMessage("Only players can run this command!");}


        return true;
    }
}

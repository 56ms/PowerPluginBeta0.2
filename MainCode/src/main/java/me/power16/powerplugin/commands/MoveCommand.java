package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class MoveCommand implements CommandExecutor {

    private final Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can run this command!");
            return true;
        } else {
            Player player = (Player) sender;

            // Generate a random vector
            Vector direction = new Vector(
                    random.nextDouble() * 2 - 1,  // Random x value between -1 and 1
                    random.nextDouble() * 2 - 1,  // Random y value between -1 and 1
                    random.nextDouble() * 2 - 1   // Random z value between -1 and 1
            );

            // Normalize the vector
            direction.normalize();

            // Scale the vector to a random magnitude between 0.1 and 5
            double magnitude = 0.1 + (5 - 0.1) * random.nextDouble();
            direction.multiply(magnitude);

            // Set the player's velocity
            player.setVelocity(direction);

            // Optionally, send a message to the player
            player.sendMessage("You have been moved with a random velocity!");

        }
        return true;
    }
}

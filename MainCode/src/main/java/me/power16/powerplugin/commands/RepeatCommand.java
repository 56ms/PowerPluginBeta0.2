package me.power16.powerplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RepeatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (sender instanceof Player) {
            Player p = (Player)sender;

            if (args.length == 0) {
                p.sendMessage("You did not specify any arguments when running this command!");
                p.sendMessage("Example: /repeat <Message Here>");
            }else if(args.length == 1){

                String word = args[0];

                p.sendMessage("Message: " + word);

            }else {

                StringBuilder builder = new StringBuilder();
                    for(int i = 0; i < args.length; i++){

                        builder.append(args[i]);
                        builder.append(" ");


                    }
                String finalMessage = builder.toString();
                finalMessage = finalMessage.stripTrailing();

                p.sendMessage("Message: " + finalMessage);
            }


        } else {

            System.out.println("Only Players can use this command");
        }





        return true;
    }
}

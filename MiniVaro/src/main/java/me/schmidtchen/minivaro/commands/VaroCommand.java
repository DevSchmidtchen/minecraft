package me.schmidtchen.minivaro.commands;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 06.01.17.
 */
public class VaroCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "/varo");
            } else {

            }
        }
        return true;
    }
}

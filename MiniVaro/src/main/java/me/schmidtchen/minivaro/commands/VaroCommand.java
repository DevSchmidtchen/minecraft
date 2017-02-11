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
                MiniVaro.getInstance().getMenuManager().openVaroInventory(player);
            } else {
                commandSender.sendMessage("[VaroBuild] Nur Spieler können diesen Command ausführen!");
            }
        } else {
            if (commandSender instanceof Player) commandSender.sendMessage(MiniVaro.getInstance().getPrefix() + "/varo");
            else commandSender.sendMessage("[VaroBuild] Nur Spieler können diesen Command ausführen!");
        }
        return true;
    }
}

package me.schmidtchen.minivaro.commands;

import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 22.04.17.
 * » http://schmidtchen.me «
 */
public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            int ping = ((CraftPlayer) player).getHandle().ping;
            player.sendMessage(MiniVaro.getInstance().getPrefix() + "Dein Ping: §6" + ping + "ms");
        } else {
            commandSender.sendMessage("[VaroBuild] Nur Spieler können diesen Command ausführen!");
        }
        return false;
    }

}

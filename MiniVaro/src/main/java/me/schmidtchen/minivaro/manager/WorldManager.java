package me.schmidtchen.minivaro.manager;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
@Setter
public class WorldManager {

    public List<Player> inVaro = new ArrayList<>();

    public void switchWorld(Player player) {
        if (inVaro.contains(player)) {
            player.teleport(MiniVaro.getInstance().getServer().getWorld("world").getSpawnLocation());
            inVaro.remove(player);
        } else {
            player.teleport(MiniVaro.getInstance().getServer().getWorld("varo").getSpawnLocation());
            inVaro.add(player);
        }
    }

}

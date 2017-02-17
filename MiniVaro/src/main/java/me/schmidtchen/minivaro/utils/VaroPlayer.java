package me.schmidtchen.minivaro.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
public class VaroPlayer {

    public String uuid;
    public boolean dead = false;
    public int kills = 0;
    public VaroLocation varoLocation = null;
    public VaroLocation buildLocation = null;
    public long lastVaroSession = -1;

    public VaroPlayer (String uuid) {
        this.uuid = uuid;
        Player player = MiniVaro.getInstance().getServer().getPlayer(UUID.fromString(uuid));
        if (player != null && MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
            varoLocation = new VaroLocation(player.getLocation());
            lastVaroSession = System.currentTimeMillis();
        } else if (player != null && !MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) {
            buildLocation = new VaroLocation(player.getLocation());
        }
    }

    public void addKill() {
        kills++;
        try {
            MiniVaro.getInstance().getMainConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

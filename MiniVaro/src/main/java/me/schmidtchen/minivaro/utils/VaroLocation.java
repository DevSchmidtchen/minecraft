package me.schmidtchen.minivaro.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.YamlConfig;
import org.bukkit.Location;

/**
 * Created by Matti on 16.02.17.
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class VaroLocation extends YamlConfig {

    public VaroLocation(Location location) {
        world = location.getWorld().getName();
        x = location.getX();
        y = location.getY();
        z = location.getZ();
        yaw = location.getYaw();
        pitch = location.getPitch();
    }

    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Location toBukkitLocation() {
        Location location = new Location(MiniVaro.getInstance().getServer().getWorld(world), x, y, z, yaw, pitch);

        return location;
    }

}

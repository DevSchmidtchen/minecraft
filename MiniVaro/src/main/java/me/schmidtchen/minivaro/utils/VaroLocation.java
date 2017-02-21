package me.schmidtchen.minivaro.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.YamlConfig;
import org.bukkit.Location;

/**
 * Created by Matti on 16.02.17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaroLocation extends YamlConfig {

    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    public VaroLocation(Location location) {
        if (location != null) {
            world = location.getWorld().getName();
            x = location.getX();
            y = location.getY();
            z = location.getZ();
            yaw = location.getYaw();
            pitch = location.getPitch();
        }
    }

    public VaroLocation(Location location, boolean view) {
        if (location != null) {
            world = location.getWorld().getName();
            x = location.getX();
            y = location.getY();
            z = location.getZ();
            yaw = view ? location.getYaw() : null;
            pitch = view ? location.getPitch() : null;
        }
    }

    public Location toBukkitLocation() {
        Location bukkitLocation = new Location(MiniVaro.getInstance().getServer().getWorld(world), x, y, z);

        if (yaw != null && pitch != null) {
            bukkitLocation.setYaw(yaw);
            bukkitLocation.setPitch(pitch);
        }

        return bukkitLocation;
    }

}

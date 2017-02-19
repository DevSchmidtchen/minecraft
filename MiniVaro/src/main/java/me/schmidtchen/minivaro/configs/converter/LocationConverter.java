package me.schmidtchen.minivaro.configs.converter;

import lombok.NoArgsConstructor;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;
import org.bukkit.Location;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 19.01.17.
 */
@NoArgsConstructor
public class LocationConverter implements Converter {

    public LocationConverter(InternalConverter converter) { }

    @Override
    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        if (o == null) {
            return null;
        }
        Location location = (Location) o;

        Map<String, Object> info = new HashMap<>();
        if (location.getWorld() == null) {
            System.out.println("[VaroBuild] Couldn't save varoCenter!");
        } else {
            info.put("world", location.getWorld().getName());
            info.put("x", location.getBlockX());
            info.put("y", location.getBlockY());
            info.put("z", location.getBlockZ());
            info.put("yaw", location.getYaw());
            info.put("pitch", location.getPitch());
        }

        System.out.println("[VaroBuild] " + info);

        return info;
    }

    @Override
    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        if (o == null) {
            return null;
        }
        Map info;

        if (o instanceof Map) {
            info = (Map<String, Object>) o;
        } else {
            info = (Map<String, Object>) ((ConfigSection) o).getRawMap();
        }

        if (!info.isEmpty()) {
            double x = info.get("x") instanceof Double ? (double) info.get("x") : ((Integer) info.get("x")).doubleValue();
            double y = info.get("y") instanceof Double ? (double) info.get("y") : ((Integer) info.get("y")).doubleValue();
            double z = info.get("z") instanceof Double ? (double) info.get("z") : ((Integer) info.get("z")).doubleValue();
            String world = (String) info.get("world");
            Location location = new Location(MiniVaro.getInstance().getServer().getWorld(world), x, y, z);
            location.setYaw(info.get("yaw") instanceof Float ? (float) info.get("yaw") : ((Double) info.get("yaw")).floatValue());
            location.setPitch(info.get("pitch") instanceof Float ? (float) info.get("pitch") : ((Double) info.get("pitch")).floatValue());

            return location;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Location.class.isAssignableFrom(aClass);
    }
}

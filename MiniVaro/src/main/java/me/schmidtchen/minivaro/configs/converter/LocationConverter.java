package me.schmidtchen.minivaro.configs.converter;

import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 19.01.17.
 */
public class LocationConverter implements Converter {
    @Override
    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        Location location = (Location) o;

        Map<String, Object> info = new HashMap<>();
        info.put("world", location.getWorld().getName());
        info.put("x", location.getBlockX());
        info.put("y", location.getBlockY());
        info.put("z", location.getBlockZ());
        info.put("yaw", location.getYaw());
        info.put("pitch", location.getPitch());

        return info;
    }

    @Override
    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        Map info;

        if (o instanceof Map) {
            info = (Map) o;
        } else {
            info = ((ConfigSection) o).getRawMap();
        }

        Location location = new Location(Bukkit.getWorld((String) info.get("world")), (double) info.get("x"), (double) info.get("y"), (double) info.get("z"));
        location.setYaw(info.get("yaw") instanceof Float ? (float) info.get("yaw") : ((Double) info.get("yaw")).floatValue());
        location.setPitch(info.get("pitch") instanceof Float ? (float) info.get("pitch") : ((Double) info.get("pitch")).floatValue());

        return location;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Location.class.isAssignableFrom(aClass);
    }
}

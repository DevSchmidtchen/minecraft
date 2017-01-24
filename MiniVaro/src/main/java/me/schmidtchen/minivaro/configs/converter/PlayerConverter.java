package me.schmidtchen.minivaro.configs.converter;

import me.schmidtchen.minivaro.utils.VaroPlayer;
import me.schmidtchen.minivaro.utils.VaroTeam;
import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import org.bukkit.Color;
import org.bukkit.Location;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
public class PlayerConverter implements Converter{


    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        VaroPlayer varoPlayer = (VaroPlayer) o;

        Map<String, Object> info = new HashMap<>();
        info.put("uuid", varoPlayer.getUuid());
        info.put("dead", varoPlayer.isDead());

        return info;
    }

    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        Map info;

        if (o instanceof Map) {
            info = (Map) o;
        } else {
            info = ((ConfigSection) o).getRawMap();
        }

        VaroPlayer varoPlayer = new VaroPlayer((UUID) info.get("uuid"));
        varoPlayer.setDead((boolean) info.get("dead"));

        return varoPlayer;
    }

    public boolean supports(Class<?> aClass) {
        return VaroPlayer.class.isAssignableFrom(aClass);
    }

}

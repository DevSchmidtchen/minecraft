package me.schmidtchen.minivaro.configs.converter;

import me.schmidtchen.minivaro.utils.VaroTeam;
import net.cubespace.Yamler.Config.Converter.Converter;
import org.bukkit.Color;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */
public class TeamConverter implements Converter{


    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        VaroTeam team = (VaroTeam) o;

        Map<String, Object> info = new HashMap<>();
        info.put("name", team.getName());
        info.put("color", team.getColor().serialize());
        info.put("members", team.getMembers());

        return info;
    }

    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        Map info = (HashMap) o;

        return new VaroTeam(Color.deserialize((Map<String, Object>) info.get("color")), (String) info.get("name"), (List<UUID>) info.get("members"));
    }

    public boolean supports(Class<?> aClass) {
        return VaroTeam.class.isAssignableFrom(aClass);
    }

}

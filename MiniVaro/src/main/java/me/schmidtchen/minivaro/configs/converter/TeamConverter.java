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
        info.put("teamchest", team.getTeamChest());

        return info;
    }

    public Object fromConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        Map<String, Object> info;

        if (o instanceof Map) {
            info = (Map<String, Object>) o;
        } else {
            info = (Map<String, Object>) ((ConfigSection) o).getRawMap();
        }

        VaroTeam varoTeam = new VaroTeam(Color.deserialize((Map<String, Object>) info.get("color")), (String) info.get("name"), (List<VaroPlayer>) info.get("members"));
        varoTeam.setTeamchest(((Location[]) info.get("teamchest")));

        return varoTeam;
    }

    public boolean supports(Class<?> aClass) {
        return VaroTeam.class.isAssignableFrom(aClass);
    }

}

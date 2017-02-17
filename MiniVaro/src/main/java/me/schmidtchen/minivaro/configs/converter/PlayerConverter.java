package me.schmidtchen.minivaro.configs.converter;

import lombok.NoArgsConstructor;
import me.schmidtchen.minivaro.utils.VaroLocation;
import me.schmidtchen.minivaro.utils.VaroPlayer;
import net.cubespace.Yamler.Config.ConfigSection;
import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 06.01.17.
 */
@NoArgsConstructor
public class PlayerConverter implements Converter{

    public PlayerConverter(InternalConverter converter) { }

    @Override
    public Object toConfig(Class<?> aClass, Object o, ParameterizedType parameterizedType) throws Exception {
        VaroPlayer varoPlayer = (VaroPlayer) o;

        Map<String, Object> info = new HashMap<>();
        info.put("uuid", varoPlayer.getUuid());
        info.put("dead", varoPlayer.isDead());
        info.put("kills", varoPlayer.getKills());
        info.put("varoLocation", varoPlayer.getVaroLocation() == null ? null : varoPlayer.getVaroLocation());
        info.put("buildLocation", varoPlayer.getBuildLocation() == null ? null : varoPlayer.getBuildLocation());
        long lastVaroSession = varoPlayer.getLastVaroSession();
        System.out.println("[VaroBuild] Gespeicherter Wert: " + varoPlayer.getUuid() + " - " + lastVaroSession);
        info.put("lastVaroSession", lastVaroSession);

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

        VaroPlayer varoPlayer = new VaroPlayer((String) info.get("uuid"));
        varoPlayer.setDead((boolean) info.get("dead"));
        varoPlayer.setKills((int) info.get("kills"));
        varoPlayer.setVaroLocation((VaroLocation) info.get("varoLocation"));
        varoPlayer.setBuildLocation((VaroLocation) info.get("buildLocation"));
        varoPlayer.setLastVaroSession(info.get("lastVaroSession") instanceof Long ? (long) info.get("lastVaroSession") : ((Integer) info.get("lastVaroSession")).longValue());

        return varoPlayer;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return VaroPlayer.class.isAssignableFrom(aClass);
    }

}

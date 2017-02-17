package me.schmidtchen.minivaro.configs;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.configs.converter.PlayerConverter;
import me.schmidtchen.minivaro.utils.VaroPlayer;
import net.cubespace.Yamler.Config.InvalidConverterException;
import net.cubespace.Yamler.Config.YamlConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matti on 06.01.17.
 */

@Getter
@Setter
public class MainConfig extends YamlConfig {

    private Map<String, String> players = new HashMap<>();
    private String varoState = "STARTING";
    private List<VaroPlayer> varoPlayer = new ArrayList<>();

    public MainConfig (File file) {
        CONFIG_FILE = file;
        try {
            addConverter(PlayerConverter.class);
        } catch (InvalidConverterException e) {
            e.printStackTrace();
        }
    }

}

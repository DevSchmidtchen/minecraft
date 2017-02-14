package me.schmidtchen.minivaro.configs;

import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.YamlConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 06.01.17.
 */

@Getter
@Setter
public class MainConfig extends YamlConfig {

    private Map<String, String> players = new HashMap<>();
    private String varoState = "STARTING";

    public MainConfig (File file) {
        CONFIG_FILE = file;
    }

}

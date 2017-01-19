package me.schmidtchen.minivaro.configs;

import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.Config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Matti on 06.01.17.
 */

@Getter
@Setter
public class MainConfig extends Config {

    Map<UUID, String> players = new HashMap<>();

    public MainConfig (File file) {
        CONFIG_FILE = file;
    }

}

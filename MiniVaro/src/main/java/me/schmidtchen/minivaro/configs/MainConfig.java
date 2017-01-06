package me.schmidtchen.minivaro.configs;

import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.Config;

import java.io.File;

/**
 * Created by Matti on 06.01.17.
 */

@Getter
@Setter
public class MainConfig extends Config {

    public MainConfig (File file) {
        CONFIG_FILE = file;
    }

}

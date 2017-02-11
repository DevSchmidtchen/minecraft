package me.schmidtchen.minivaro.configs;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.configs.converter.LocationConverter;
import me.schmidtchen.minivaro.configs.converter.TeamConverter;
import me.schmidtchen.minivaro.utils.VaroTeam;
import net.cubespace.Yamler.Config.InvalidConverterException;
import net.cubespace.Yamler.Config.YamlConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
@Setter
public class TeamConfig extends YamlConfig {

    private List<VaroTeam> teams = new ArrayList<>();

    public TeamConfig(File file) {
        CONFIG_FILE = file;
        try {
            addConverter(TeamConverter.class);
            addConverter(LocationConverter.class);
        } catch (InvalidConverterException exception) {
            exception.printStackTrace();
        }
    }

}

package me.schmidtchen.minivaro.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.cubespace.Yamler.Config.YamlConfig;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
@Data
@NoArgsConstructor
public class VaroPlayer extends YamlConfig {

    public String uuid;
    public boolean dead = false;
    public int kills = 0;

    public VaroPlayer (String uuid) {
        this.uuid = uuid;
    }

    public void addKill() {

        kills++;

    }
}

package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
public class VaroPlayer {

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

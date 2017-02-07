package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
public class VaroPlayer {

    public UUID uuid;
    public boolean dead = false;
    public int kills = 0;

    public VaroPlayer (UUID uuid) {
        this.uuid = uuid;
    }

    public void addKill() {

        kills++;

    }
}

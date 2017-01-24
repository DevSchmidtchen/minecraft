package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
public class Varo {

    public VaroState varoState;

    public void start() {
        varoState = VaroState.STARTING;
    }

    public void restart() {

    }

}

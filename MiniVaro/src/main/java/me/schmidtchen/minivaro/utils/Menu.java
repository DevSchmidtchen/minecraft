package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import me.schmidtchen.minivaro.items.SwitchItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matti on 22.01.17.
 */
@Getter
public enum Menu {

    MAIN("Hauptmenü", 27),
    SWITCH("Spielmodus wechseln", 27),
    CREATE("Team erstellen", 27),
    LIST("Teamliste", 27),
    REMOVE("Team entfernen", 27),
    RESTART("Varo neustarten", 27);

    String name;
    int size;

    Menu(String name, int size) {
        this.name = name;
        this.size = size;
    }

    private Map<Integer, ItemStack> getContent(Player player) {
        Map<Integer, ItemStack> map = new HashMap<>();

        switch (this) {
            case MAIN:
                break;
            case SWITCH:
                for (int i = 10; i <= 13; i++) {
                    map.put(i, new SwitchItem().build());
                }
                if (player.isOp()) {

                } else {
                    map.put(14, new SwitchItem().build());
                }
                break;
            case LIST:
                break;
            case CREATE:
                break;
            case REMOVE:
                break;
            case RESTART:
                break;
        }

        return map;
    }
}

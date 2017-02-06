package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.items.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matti on 22.01.17.
 */
@Getter
public enum Menu {

    MAIN("Hauptmenü", 27),
    SWITCH("Spielmodus wechseln", 27),
    COLOR("Farbe einstellen", 27),
    LIST("Teamliste", 27),
    INFO("Teaminformationen", 27),
    RESTART("Varo neustarten", 27),
    CONFIRM("Bestätigen", 27);

    String name;
    int size;

    Menu(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public Map<Integer, ItemStack> getContent(Player player) {
        Map<Integer, ItemStack> map = new HashMap<>();

        ItemStack switchItem = new SwitchItem().build();
        ItemStack backItem = new BackItem().build();
        ItemStack settingsItem = new SettingsItem().build();
        ItemStack confirmationItem = new ConfirmationItem().build();
        ItemStack createTeamItem = new CreateTeamItem().build();
        ItemStack listTeamsItem = new ListTeamsItem().build();
        ItemStack restartItem = new RestartItem().build();

        int inventoryStart = 10;

        switch (this) {
            case MAIN:
                map.put(11, createTeamItem);
                if (MiniVaro.getInstance().getVaro().getVaroState().equals(VaroState.END)) {
                    map.put(12, listTeamsItem);
                    map.put(14, restartItem);
                } else {
                    map.put(13, listTeamsItem);
                }
                break;
            case SWITCH:
                for (int i = 10; i <= 13; i++) {
                    map.put(i, switchItem);
                }
                if (player.isOp()) {
                    map.put(15, settingsItem);
                } else {
                    map.put(14, switchItem);
                }
                break;
            case LIST:
                for (VaroTeam varoTeam : MiniVaro.getInstance().getTeamManager().getTeams()) {
                    map.put(inventoryStart, new TeamItem(varoTeam.getColor(), varoTeam.getName()).build());
                    inventoryStart++;
                }
                inventoryStart = 10;
                break;
            case COLOR:


                break;
            case CONFIRM:
                map.put(11, confirmationItem);
                map.put(14, backItem);
                break;
        }
        if (!this.equals(COLOR)) {
            map.put(16, backItem);
        }
        return map;
    }
}

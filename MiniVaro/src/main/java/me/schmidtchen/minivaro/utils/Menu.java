package me.schmidtchen.minivaro.utils;

/**
 * Created by Matti on 22.01.17.
 */
public enum Menu {

    MAIN("Hauptmenü", 45),
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
}

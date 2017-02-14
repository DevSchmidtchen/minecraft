package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import org.bukkit.entity.Player;

/**
 * Created by Matti on 24.01.17.
 */
@Getter
@Setter
public class Varo {

    public Varo() {
        varoState = VaroState.valueOf(MiniVaro.getInstance().getMainConfig().getVaroState());
    }

    public VaroState varoState;
    int scheduler;

    public void setVaroState(VaroState varoState) {
        this.varoState = varoState;
        MiniVaro.getInstance().getMainConfig().setVaroState(varoState.name());
    }

    public void start() {
        setVaroState(VaroState.RUNNING);
        for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
            player.setWalkSpeed(0);
        }
        scheduler = MiniVaro.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(MiniVaro.getInstance(), new Runnable() {
            int countdown = 60;
            @Override
            public void run() {
                if (countdown % 10 == 0 && countdown != 0) {
                    MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + "Varo startet in §f" + countdown + " §7Sekunden!");
                }
                if (countdown <= 5 && countdown > 0) {
                    ActionBarAPI.sendActionBarToAllPlayers("§8» §aVaro startet in §c" + countdown + " §aSekunden §8«");
                }
                if (countdown == 0) {
                    for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
                        player.setWalkSpeed(0.2F);
                    }
                    MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + "§aLos geht's!");
                    MiniVaro.getInstance().getServer().getScheduler().cancelTask(scheduler);
                }
                countdown--;
            }
        }, 20, 20);
    }

    public void restart() {
        setVaroState(VaroState.STARTING);
    }

}

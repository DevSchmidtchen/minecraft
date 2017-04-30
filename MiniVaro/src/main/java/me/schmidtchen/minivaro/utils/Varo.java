package me.schmidtchen.minivaro.utils;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;

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
    Map<Player, Integer> endScheduler = new HashMap<>();

    public void setVaroState(VaroState varoState) {
        this.varoState = varoState;
        MiniVaro.getInstance().getMainConfig().setVaroState(varoState.name());
    }

    public void start() {
        for (Player player : MiniVaro.getInstance().getWorldManager().getInVaro()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.setWalkSpeed(0);
            player.getInventory().clear();
            player.getInventory().setBoots(new ItemStack(Material.AIR));
            player.getInventory().setLeggings(new ItemStack(Material.AIR));
            player.getInventory().setChestplate(new ItemStack(Material.AIR));
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
            player.setHealth(20.0);
            player.setFoodLevel(6);
            player.setLevel(0);
            player.setExp(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 61*20, 128, false, false));
        }
        setVaroState(VaroState.COUNTDOWN);
        scheduler = MiniVaro.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(MiniVaro.getInstance(), new Runnable() {
            int countdown = 60;
            @Override
            public void run() {
                if (countdown % 10 == 0 && countdown != 0) {
                    MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + "Varo startet in §f" + countdown + " §7Sekunden!");
                }
                if (countdown <= 5 && countdown > 0) {
                    for (Player player : MiniVaro.getInstance().getServer().getOnlinePlayers()) {
                        TitleAPI.sendTitle(player, 5, 15, 0, "§4" + countdown, null);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 5, 5);
                    }
                }
                if (countdown == 0) {
                    setVaroState(VaroState.RUNNING);
                    for (Player player : MiniVaro.getInstance().getWorldManager().getInVaro()) {
                        player.setWalkSpeed(0.2F);
                        player.setFoodLevel(20);
                        player.setExhaustion(0);
                        player.setSaturation(5F);
                        TitleAPI.sendTitle(player, 5, 35, 10, "§6Go!", "§aViel Spaß!");
                        if (player.isOp()) {
                            MiniVaro.getInstance().getWorldManager().getOperators().add(player);
                            player.setOp(false);
                        }
                        MiniVaro.getInstance().getServer().getScheduler().runTaskAsynchronously(MiniVaro.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                startVaroSession(player);
                            }
                        });
                    }
                    MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + "§aLos geht's!");
                    MiniVaro.getInstance().getWorldManager().loadWorlds();
                    MiniVaro.getInstance().getServer().getScheduler().cancelTask(scheduler);
                }
                ActionBarAPI.sendActionBarToAllPlayers("§8» §aVaro startet in §c" + countdown + " §aSekunde(n) §8«");
                countdown--;
            }
        }, 20, 20);
    }

    public void restart() {
        for (Player player : MiniVaro.getInstance().getServer().getWorld("varo").getPlayers()) {
            player.teleport(MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation() == null ? MiniVaro.getInstance().getServer().getWorld("varo").getSpawnLocation() : MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation().toBukkitLocation());
            MiniVaro.getInstance().getWorldManager().getInVaro().remove(player);
            if (MiniVaro.getInstance().getWorldManager().getOperators().contains(player)) {
                MiniVaro.getInstance().getWorldManager().getOperators().remove(player);
                player.setOp(true);
            }
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setVaroLocation(null);
        }
        MiniVaro.getInstance().getMainConfig().setVaroCenter(null);
        for (VaroPlayer varoPlayer : MiniVaro.getInstance().getMainConfig().getVaroPlayer()) {
            varoPlayer.setLastVaroSession(-1);
            varoPlayer.setDead(false);
        }
        try {
            MiniVaro.getInstance().getMainConfig().save();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        File varoWorld = MiniVaro.getInstance().getServer().getWorld("varo").getWorldFolder();
        File varoNether = MiniVaro.getInstance().getServer().getWorld("varo_nether").getWorldFolder();
        MiniVaro.getInstance().getServer().unloadWorld(MiniVaro.getInstance().getServer().getWorld("varo"), true);
        MiniVaro.getInstance().getServer().unloadWorld(MiniVaro.getInstance().getServer().getWorld("varo_nether"), true);
        deleteWorld(varoWorld);
        deleteWorld(varoNether);
        MiniVaro.getInstance().getWorldManager().loadWorlds();
        setVaroState(VaroState.STARTING);
        MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + "Varo wurde erfolgreich zurückgesetzt!");
    }

    private boolean deleteWorld(File path) {
        if(path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                return false;
            }
            for(int i=0; i < files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public boolean checkVaroSession(Player player) {
        VaroPlayer varoPlayer = MiniVaro.getInstance().getTeamManager().getVaroPlayer(player);
        if (varoPlayer.getLastVaroSession() == -1) {
            return true;
        }
        Calendar now = new GregorianCalendar();
        Date lastVaroSession = new Date(varoPlayer.getLastVaroSession());
        Calendar lastVaroSessionCalendar = new GregorianCalendar();
        lastVaroSessionCalendar.setTime(lastVaroSession);
        if (getVaroState().equals(VaroState.STARTING) || lastVaroSessionCalendar.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR) || lastVaroSessionCalendar.get(Calendar.YEAR) != now.get(Calendar.YEAR)) {
            return true;
        } else {
            player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu hast heute schon Varo gespielt!");
            return false;
        }
    }

    public void startVaroSession(Player player) {
        if (MiniVaro.getInstance().getVaro().getVaroState().equals(VaroState.RUNNING)) {
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setLastVaroSession(System.currentTimeMillis());
            try {
                MiniVaro.getInstance().getMainConfig().save();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }
            MiniVaro.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(MiniVaro.getInstance(), () -> {
                if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player)) endVaroSession(player);
            }, 19 * 60 * 20);
        }
    }

    public void endVaroSession(Player player) {
        endScheduler.put(player, MiniVaro.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(MiniVaro.getInstance(), new Runnable() {
            int countdown = 60;
            @Override
            public void run() {
                if (countdown % 10 == 0 && countdown != 0) {
                    player.sendMessage(MiniVaro.getInstance().getPrefix() + "Du wirst in §f" + countdown + " §7Sekunde(n) gekickt!");
                }
                if (countdown <= 10 && countdown > 0) {
                    ActionBarAPI.sendActionBar(player, "§8» §cDu wirst in §6" + countdown + " §cSekunde(n) gekickt §8«");
                }
                if (countdown == 0) {
                    MiniVaro.getInstance().getWorldManager().switchWorld(player);
                    MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7wurde aus Varo gekickt!");
                    MiniVaro.getInstance().getServer().getScheduler().cancelTask(endScheduler.get(player));
                    endScheduler.remove(player);
                }
                countdown--;
            }
        }, 0, 20));
    }

}

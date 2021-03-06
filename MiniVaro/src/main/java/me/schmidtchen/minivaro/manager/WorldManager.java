package me.schmidtchen.minivaro.manager;

import lombok.Getter;
import lombok.Setter;
import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroLocation;
import me.schmidtchen.minivaro.utils.VaroState;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 06.01.17.
 */
@Getter
@Setter
public class WorldManager {

    public List<Player> inVaro = new ArrayList<>();
    public List<Player> operators = new ArrayList<>();

    public void switchWorld(Player player) {
        if (inVaro.contains(player)) {
            MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setVaroLocation(new VaroLocation(player.getLocation()));
            player.teleport(MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation() == null ? MiniVaro.getInstance().getServer().getWorld("world").getSpawnLocation() : MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getBuildLocation().toBukkitLocation());
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
            inVaro.remove(player);
            if (operators.contains(player)) {
                operators.remove(player);
                player.setOp(true);
            }
        } else {
            if (MiniVaro.getInstance().getTeamManager().hasTeam(player) && !MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).isDead() && MiniVaro.getInstance().getVaro().checkVaroSession(player)) {
                MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).setBuildLocation(new VaroLocation(player.getLocation()));
                loadWorlds();
                player.teleport(MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getVaroLocation() == null ? (MiniVaro.getInstance().getMainConfig().getVaroCenter() != null ? MiniVaro.getInstance().getMainConfig().getVaroCenter().toBukkitLocation() : MiniVaro.getInstance().getServer().getWorld("varo").getSpawnLocation()) : MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).getVaroLocation().toBukkitLocation());
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(8);
                inVaro.add(player);
                if (player.isOp() && MiniVaro.getInstance().getVaro().getVaroState().equals(VaroState.RUNNING)) {
                    operators.add(player);
                    player.setOp(false);
                }
                MiniVaro.getInstance().getServer().broadcastMessage(MiniVaro.getInstance().getPrefix() + player.getDisplayName() + " §7ist Varo beigetreten!");
                MiniVaro.getInstance().getVaro().startVaroSession(player);
            } else if (MiniVaro.getInstance().getTeamManager().getVaroPlayer(player).isDead()) {
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu bist in Varo bereits ausgeschieden!");
            } else if (!MiniVaro.getInstance().getTeamManager().hasTeam(player)) {
                player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDu wurdest noch keinem Team zugeordnet!");
            }
        }
    }

    public void loadWorlds() {
        MiniVaro.getInstance().getServer().createWorld(new WorldCreator("varo").type(WorldType.NORMAL));
        if (MiniVaro.getInstance().getVaro().getVaroState().equals(VaroState.RUNNING)) {
            WorldBorder worldBorder = MiniVaro.getInstance().getServer().getWorld("varo").getWorldBorder();
            if (MiniVaro.getInstance().getMainConfig().getVaroCenter() == null || MiniVaro.getInstance().getMainConfig().getVaroCenter().getWorld() == null) {
                worldBorder.setCenter(0, 0);
            } else {
                worldBorder.setCenter(MiniVaro.getInstance().getMainConfig().getVaroCenter().toBukkitLocation());
            }
            worldBorder.setSize(500);
        }
        WorldCreator worldCreator = new WorldCreator("varo_nether");
        worldCreator.type(WorldType.NORMAL);
        worldCreator.environment(World.Environment.NETHER);
        MiniVaro.getInstance().getServer().createWorld(worldCreator);
    }

}

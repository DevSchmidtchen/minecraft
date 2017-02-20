package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroLocation;
import me.schmidtchen.minivaro.utils.VaroTeam;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Sign;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Matti on 07.02.17.
 */
public class SignEditListener implements Listener {

    @EventHandler
    public void onSignEdit(SignChangeEvent event) {
        Player player = event.getPlayer();
        List<VaroLocation> locations = new ArrayList<>();

        if (MiniVaro.getInstance().getWorldManager().getInVaro().contains(player) && (event.getBlock().getType().equals(Material.WALL_SIGN) || event.getBlock().getType().equals(Material.SIGN_POST))) {
            Sign sign = (Sign) event.getBlock().getState().getData();
            org.bukkit.block.Sign signState = (org.bukkit.block.Sign) event.getBlock().getState();
            for (String line : event.getLines()) {
                if (line != null && !line.isEmpty() && line.startsWith("#")) {
                    String teamName = line.substring(1);
                    if (event.getBlock().getRelative(sign.getAttachedFace()).getType().equals(Material.CHEST)) {
                        locations.add(new VaroLocation(event.getBlock().getRelative(sign.getAttachedFace()).getLocation()));
                        for (BlockFace blockFace : BlockFace.values()) {
                            if (locations.get(0).toBukkitLocation().getBlock().getRelative(blockFace).getType().equals(Material.CHEST) && blockFace != BlockFace.DOWN && blockFace != BlockFace.UP && blockFace != BlockFace.SELF) {
                                locations.add(new VaroLocation(locations.get(0).toBukkitLocation().getBlock().getRelative(blockFace).getLocation()));
                            }
                        }
                    }
                    Optional<VaroTeam> varoTeam = MiniVaro.getInstance().getTeamManager().getLivingTeams().stream().filter(team -> team.getName().equalsIgnoreCase(teamName)).findFirst();
                    if (varoTeam.isPresent()) {
                        varoTeam.get().setTeamchest(locations);
                        varoTeam.get().sendMessage("§2Eure Teamchest wurde gesetzt!");
                        event.setLine(0, null);
                        event.setLine(1, "§8[Teamchest]");
                        event.setLine(2, "§7» " + MiniVaro.getInstance().getChatColor(varoTeam.get().getColor()) + varoTeam.get().getName());
                        event.setLine(3, null);
                        try {
                            MiniVaro.getInstance().getTeamConfig().save();
                        } catch (InvalidConfigurationException e) {
                            e.printStackTrace();
                        }
                    } else {
                        player.sendMessage(MiniVaro.getInstance().getPrefix() + "§cDieses Team existiert nicht!");
                    }
                }
            }
        }
    }

}

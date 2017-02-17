package me.schmidtchen.minivaro.listeners;

import me.schmidtchen.minivaro.MiniVaro;
import me.schmidtchen.minivaro.utils.VaroTeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.material.Sign;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matti on 07.02.17.
 */
public class SignEditListener implements Listener {

    @EventHandler
    public void onSignEdit(SignChangeEvent event) {
        Player player = event.getPlayer();
        List<Location> locations = new ArrayList<>();

        if (event.getBlock().getType().equals(Material.WALL_SIGN) || event.getBlock().getType().equals(Material.SIGN_POST)) {
            Sign sign = (Sign) event.getBlock().getState().getData();
            org.bukkit.block.Sign signState = (org.bukkit.block.Sign) event.getBlock().getState();
            for (String line : signState.getLines()) {
                if (line.startsWith("#")) {
                    String teamName = line.substring(1);
                    if (event.getBlock().getRelative(sign.getAttachedFace()).getType().equals(Material.CHEST)) {
                        locations.add(event.getBlock().getRelative(sign.getAttachedFace()).getLocation());
                        for (BlockFace blockFace : BlockFace.values()) {
                            if (locations.get(0).getBlock().getRelative(blockFace).getType().equals(Material.CHEST) && blockFace != BlockFace.DOWN && blockFace != BlockFace.UP) {
                                locations.add(locations.get(0).getBlock().getRelative(blockFace).getLocation());
                            }
                        }
                    }
                    VaroTeam varoTeam = MiniVaro.getInstance().getTeamManager().getLivingTeams().stream().filter(team -> team.getName().equalsIgnoreCase(teamName)).findFirst().get();
                    varoTeam.setTeamchest((Location[]) locations.toArray());
                    varoTeam.sendMessage("§2Eure Teamchest wurde gesetzt!");
                }
            }
        }
    }

}

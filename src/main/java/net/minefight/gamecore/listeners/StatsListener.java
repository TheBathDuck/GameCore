package net.minefight.gamecore.listeners;

import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class StatsListener implements Listener {

    private final PlayerManager playerManager;

    public StatsListener() {
        playerManager = GameCore.getInstance().getPlayerManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        data.setMined(data.getMined() + 1);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        PlayerData diedPlayer = playerManager.getPlayerData(player.getUniqueId());
        diedPlayer.setDeaths(diedPlayer.getDeaths() + 1);

        if(player.getKiller() == null) return;
        Player killer = player.getKiller();
        if(killer == null) return;
        PlayerData killerData = playerManager.getPlayerData(killer.getUniqueId());
        killerData.setKills(killerData.getKills() + 1);
    }



}

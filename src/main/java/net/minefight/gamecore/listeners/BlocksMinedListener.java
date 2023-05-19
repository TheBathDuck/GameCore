package net.minefight.gamecore.listeners;

import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlocksMinedListener implements Listener {

    private final PlayerManager playerManager;

    public BlocksMinedListener() {
        playerManager = GameCore.getInstance().getPlayerManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        data.setMined(data.getMined() + 1);
    }

}

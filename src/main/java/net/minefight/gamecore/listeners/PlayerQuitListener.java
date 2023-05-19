package net.minefight.gamecore.listeners;

import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final GameCore plugin;
    private final PlayerManager playerManager;
    private final Database database;

    public PlayerQuitListener() {
        this.plugin = GameCore.getInstance();
        this.playerManager = plugin.getPlayerManager();
        this.database = plugin.getDatabase();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());

        if(playerManager.isCached(player.getUniqueId())) {
            playerManager.removePlayerData(player.getUniqueId());
            plugin.getLogger().info("[PlayerData] Unloaded " + player.getName() + "'s data.");
        }

        database.updateData(data).thenAccept((v) -> {
            plugin.getLogger().info("[PlayerData] Updated " + player.getName() + "'s data (eur: " + data.getBalance() + ").");
        });

    }

}

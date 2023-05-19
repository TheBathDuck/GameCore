package net.minefight.gamecore.listeners;

import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameCore plugin = GameCore.getInstance();
        Database database = GameCore.getInstance().getDatabase();
        PlayerManager playerManager = GameCore.getInstance().getPlayerManager();
        player.sendMessage(ChatUtils.color("<primary>Loading playerdata.."));
        long start = System.currentTimeMillis();

        if(playerManager.isCached(player.getUniqueId())) {
            player.sendMessage(ChatUtils.color("<primary>Your data was still loaded!"));
            return;
        }

        database.isInDatabase(player.getUniqueId()).thenAccept((isInDatabase) -> {
            if(!isInDatabase) {
                database.createPlayer(player.getUniqueId(), System.currentTimeMillis()).thenAccept((data) -> {
                    player.sendMessage(ChatUtils.color("<primary>Your playerdata was <secondary>succesfully <primary>created!"));
                    plugin.getLogger().info("[PlayerData] " + player.getName() + "'s data was created in " + (start - System.currentTimeMillis()) + "ms.");
                    playerManager.registerPlayerData(data);
                });
                return;
            }
            database.getPlayer(player.getUniqueId()).thenAccept(data -> {
                player.sendMessage(ChatUtils.color("<primary>Your playerdata was <secondary>succesfully <primary>loaded!"));
                plugin.getLogger().info("[PlayerData] " + player.getName() + "'s data was loaded in " + (start - System.currentTimeMillis()) + "ms.");
                playerManager.registerPlayerData(data);
            });
        });

    }

}

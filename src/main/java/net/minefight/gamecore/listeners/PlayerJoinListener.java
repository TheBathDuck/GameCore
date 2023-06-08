package net.minefight.gamecore.listeners;

import net.kyori.adventure.text.Component;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;

import javax.swing.plaf.basic.ComboPopup;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameCore plugin = GameCore.getInstance();
        Database database = GameCore.getInstance().getDatabase();
        PlayerManager playerManager = GameCore.getInstance().getPlayerManager();
        player.sendActionBar(ChatUtils.color("<primary>Loading player data"));
        long start = System.currentTimeMillis();

        if(playerManager.isCached(player.getUniqueId())) {
            player.sendMessage(ChatUtils.color("<primary>Your data was still loaded!"));
            return;
        }

        database.isInDatabase(player.getUniqueId()).thenAccept((isInDatabase) -> {
            if(!isInDatabase) {
                database.createPlayer(player.getUniqueId(), System.currentTimeMillis()).thenAccept((data) -> {
                    long current = System.currentTimeMillis();
                    player.sendActionBar(ChatUtils.color("<primary>Your playerdata was <secondary>succesfully <primary>created!"));
                    plugin.getLogger().info("[PlayerData] " + player.getName() + "'s data was created in " + (current - start) + "ms.");
                    playerManager.registerPlayerData(data);
                    setupNewPlayer(player);
                    data.setLastJoin(current);
                });
                return;
            }
            database.getPlayer(player.getUniqueId()).thenAccept(data -> {
                player.sendActionBar(ChatUtils.color("<primary>Your playerdata was <secondary>succesfully <primary>loaded!"));
                plugin.getLogger().info("[PlayerData] " + player.getName() + "'s data was loaded in " + (System.currentTimeMillis() - start) + "ms.");
                playerManager.registerPlayerData(data);

                database.getPurchaseHistory(player.getUniqueId()).thenAccept(data::setPurchaseHistory);
                data.setLastJoin(System.currentTimeMillis());
            });
        });

    }

    private void setupNewPlayer(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
    }
}

package net.minefight.gamecore.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.swing.*;

public class PlayerChatListener implements Listener {

    private final LuckPerms luckPerms;
    private final PlayerManager playerManager;

    public PlayerChatListener() {
        luckPerms = LuckPermsProvider.get();
        playerManager = GameCore.getInstance().getPlayerManager();
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(((player, displayName, message, viewer) -> {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            CachedMetaData metaData = user.getCachedData().getMetaData();
            PlayerData data = playerManager.getPlayerData(player.getUniqueId());
            String tagcolor = metaData.getMetaValue("tagcolor");
            String prefix = metaData.getMetaValue("chatprefix");
            String rank = "<hover:show_text:'<" + tagcolor + ">" + player.getName() + "s <white>Stats<newline><newline>" +
                    "<white>Kills: <green>" + data.getKills() + "<newline>" +
                    "<white>Deaths: <red>" + data.getDeaths() + "<newline><newline>" +
                    "<white>Rank: " + prefix + "<newline>" +
                    "<white>Joined Since: <aqua>" + data.getJoinDate() +
                    "'><" + tagcolor + ">" + prefix + player.getName() + "</" + tagcolor + "></hover><reset>";

            return ChatUtils.color(rank + "<white>: ").append(message);
        }));
    }


}
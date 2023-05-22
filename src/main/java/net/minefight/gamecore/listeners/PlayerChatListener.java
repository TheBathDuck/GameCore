package net.minefight.gamecore.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.swing.*;

public class PlayerChatListener implements Listener {

    private final LuckPerms luckPerms;

    public PlayerChatListener() {
        luckPerms = LuckPermsProvider.get();
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(((player, displayName, message, viewer) -> {
            User user = luckPerms.getUserManager().getUser(player.getUniqueId());
            CachedMetaData metaData = user.getCachedData().getMetaData();

            String tagcolor = metaData.getMetaValue("tagcolor");
            String prefix = metaData.getMetaValue("chatprefix");

            return ChatUtils.color(prefix + " " + player.getName() + "<white>: ").append(message);
        }));
    }


}
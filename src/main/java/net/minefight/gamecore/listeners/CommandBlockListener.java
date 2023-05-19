package net.minefight.gamecore.listeners;

import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class CommandBlockListener implements Listener {


    private List<String> blockedCommands = Arrays.asList(
            "/minecraft:stop",
            "/stop",
            "/spigot:restart",
            "/restart"
    );

    @EventHandler
    public void blockedCommandListener(PlayerCommandPreprocessEvent event) {
        try {
            String command = event.getMessage();
            Player executor = event.getPlayer();

            if(!blockedCommands.contains(command)) return;
            event.setCancelled(true);
            executor.sendMessage(ChatUtils.color("<danger>This command is blocked by the server."));
        } catch (Exception e) {
            GameCore plugin = GameCore.getInstance();
            plugin.getLogger().warning("Could not block command \"" + event.getMessage() + "\" for " + event.getPlayer().getName() + ".");
        }
    }

}

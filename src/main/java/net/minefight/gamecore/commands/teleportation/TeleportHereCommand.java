package net.minefight.gamecore.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("tphere|s")
@CommandPermission("minefight.command.tphere")
@Description("Teleport someone to you.")
public class TeleportHereCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Description("Teleport someone to you.")
    public void teleportHere(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();
        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.teleport(player);
        player.sendMessage(ChatUtils.color("<primary>Teleported <secondary>" + target.getName() + " <primary>to you."));
    }

}

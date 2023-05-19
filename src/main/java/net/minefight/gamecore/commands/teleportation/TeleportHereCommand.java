package net.minefight.gamecore.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("tphere|s")
@CommandPermission("minefight.command.tphere")
public class TeleportHereCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void teleportHere(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();
        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.teleport(player);
        player.sendMessage(ChatUtils.color("<primary>Teleported <secondary>" + player.getName() + " <primary>to you."));
    }

}

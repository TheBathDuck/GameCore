package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("ping")
@Description("Check that ping")
public class PingCommand extends BaseCommand {

    @Default
    @Description("Check your ping.")
    public void sendPing(Player player) {
        player.sendMessage(ChatUtils.color("<primary>Your current ping is <secondary>" + player.spigot().getPing() + "ms<primary>."));
    }

    @Default
    @CommandCompletion("@players")
    @Syntax("<player>")
    @Description("Check a player's ping.")
    public void sendPing(Player player, OnlinePlayer onlineTarget) {
        Player target = onlineTarget.getPlayer();
        player.sendMessage(ChatUtils.color("<secondary>" + target.getName() + "'s <primary>current ping is <secondary>" + target.spigot().getPing() + "ms<primary>."));
    }

}

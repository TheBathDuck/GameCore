package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("fly")
@Description("Allows you to fly!")
public class FlyCommand extends BaseCommand {

    @Default
    @CommandPermission("minefight.command.fly")
    public void flySelf(Player player) {
        if(!player.getAllowFlight()) {
            player.setAllowFlight(true);
            player.sendMessage(ChatUtils.color("<primary>You <secondary>enabled <primary>fly mode."));
            return;
        }
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(ChatUtils.color("<primary>You <secondary>disabled <primary>fly mode."));
    }

    @Default
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.fly.others")
    public void flyOthers(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();
        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        if(!target.getAllowFlight()) {
            target.setAllowFlight(true);
            player.sendMessage(ChatUtils.color("<primary>You <secondary>enabled <primary>fly mode for <secondary>" + target.getName() + "<primary>."));
            return;
        }
        target.setAllowFlight(false);
        target.setFlying(false);
        player.sendMessage(ChatUtils.color("<primary>You <secondary>disabled <primary>fly mode for <secondary>" + target.getName() + "<primary>."));
    }




}

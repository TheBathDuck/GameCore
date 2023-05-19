package net.minefight.gamecore.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gmsp")
@CommandPermission("minefight.command.gamemode.spectator")
public class GamemodeSpectatorCommand extends BaseCommand {

    @Default
    public void spectator(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Spectator<primary>."));
        return;
    }

    @Default
    @CommandCompletion("@players")
    public void spectatorOther(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();

        if (target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.setGameMode(GameMode.SPECTATOR);
        target.sendMessage(ChatUtils.color("<primary>Your gamemode has been updated to <secondary>Spectator<primary> by <secondary>" + player.getName() + "<primary>."));
        if (player != target) {
            player.sendMessage(ChatUtils.color("<primary>Set gamemode of <secondary>" + target.getName() + " <primary>to <secondary>Spectator<primary>."));
        }
    }
}
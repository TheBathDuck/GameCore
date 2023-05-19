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

@CommandAlias("gms")
@CommandPermission("minefight.command.gamemode.survival")
public class GamemodeSurvivalCommand extends BaseCommand {

    @Default
    public void survivalCommand(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Survival<primary>."));
        return;
    }

    @Default
    @CommandCompletion("@players")
    public void survivalOther(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();

        if (target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.setGameMode(GameMode.SURVIVAL);
        target.sendMessage(ChatUtils.color("<primary>Your gamemode has been updated to <secondary>Survival<primary> by <secondary>" + player.getName() + "<primary>."));
        if (player != target) {
            player.sendMessage(ChatUtils.color("<primary>Set gamemode of <secondary>" + target.getName() + " <primary>to <secondary>Survival<primary>."));
        }
    }
}
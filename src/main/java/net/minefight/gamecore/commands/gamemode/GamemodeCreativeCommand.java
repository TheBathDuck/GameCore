package net.minefight.gamecore.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gmc")
@CommandPermission("minefight.command.gamemode.creative")
public class GamemodeCreativeCommand extends BaseCommand {

    @Default
    @Description("Change your gamemode to Creative")
    public void creativeCommand(Player player) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Creative<primary>."));
        return;
    }

    @Default
    @CommandCompletion("@players")
    @Description("Change a player's gamemode to Creative")
    @Syntax("<player>")
    public void creativeOther(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();

        if (target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.setGameMode(GameMode.CREATIVE);
        target.sendMessage(ChatUtils.color("<primary>Your gamemode has been updated to <secondary>Creative<primary> by <secondary>" + player.getName() + "<primary>."));
        if (player != target) {
            player.sendMessage(ChatUtils.color("<primary>Set gamemode of <secondary>" + target.getName() + " <primary>to <secondary>Creative<primary>."));
        }
    }
}
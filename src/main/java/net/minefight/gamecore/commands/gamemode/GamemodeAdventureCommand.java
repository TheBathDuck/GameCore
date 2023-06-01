package net.minefight.gamecore.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gma")
@CommandPermission("minefight.command.gamemode.adventure")
public class GamemodeAdventureCommand extends BaseCommand {

    @Default
    @Description("Change your gamemode to Adventure")
    public void adventure(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Adventure<primary>."));
        return;
    }

    @Default
    @CommandCompletion("@players")
    @Syntax("<player>")
    @Description("Change a player's gamemode to Adventure")
    public void adventureOther(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();

        if (target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        target.setGameMode(GameMode.ADVENTURE);
        target.sendMessage(ChatUtils.color("<primary>Your gamemode has been updated to <secondary>Adventure<primary> by <secondary>" + player.getName() + "<primary>."));
        if (player != target) {
            player.sendMessage(ChatUtils.color("<primary>Set gamemode of <secondary>" + target.getName() + " <primary>to <secondary>Adventure<primary>."));
        }
    }
}

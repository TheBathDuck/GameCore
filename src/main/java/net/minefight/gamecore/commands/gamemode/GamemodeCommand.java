package net.minefight.gamecore.commands.gamemode;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.google.common.collect.ImmutableList;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@CommandAlias("gm|gamemode")
@Description("Change your gamemode.")
public class GamemodeCommand extends BaseCommand {

    public GamemodeCommand() {
        GameCore plugin = GameCore.getInstance();

        plugin.getCommandManager().getCommandCompletions().registerCompletion("gamemode", c -> {
            return ImmutableList.of("creative", "survival", "adventure", "spectator", "c", "s", "a", "sp");
        });

    }

    @Default
    @CommandCompletion("@gamemode")
    @CommandPermission("minefight.command.gamemode")
    public void defaultCommand(Player player) {
        player.sendMessage(ChatUtils.color("<primary>/gamemode creative <gray>- <secondary>changes your gamemode to creative."));
    }

    @Subcommand("creative|c")
    @CommandPermission("minefight.command.gamemode.creative")
    public void creativeSelf(Player player) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Creative<primary>."));
    }

    @Subcommand("creative|c")
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.gamemode.creative.other")
    public void creativeOthers(Player player, OnlinePlayer onlinePlayer) {
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

    @Subcommand("survival|s")
    @CommandPermission("minefight.command.gamemode.survival")
    public void survivalSelf(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Survival<primary>."));
    }

    @Subcommand("survival|s")
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.gamemode.survival.other")
    public void survivalOthers(Player player, OnlinePlayer onlinePlayer) {
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

    @Subcommand("adventure|a")
    @CommandPermission("minefight.command.adventure.creative")
    public void adventureSelf(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Adventure<primary>."));
    }

    @Subcommand("adventure|a")
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.gamemode.adventure.other")
    public void adventureOthers(Player player, OnlinePlayer onlinePlayer) {
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

    @Subcommand("spectator|sp")
    @CommandPermission("minefight.command.gamemode.spectator")
    public void spectatorSelf(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatUtils.color("<primary>You changed your gamemode to <secondary>Spectator<primary>."));
    }

    @Subcommand("spectator|sp")
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.gamemode.spectator.other")
    public void spectatorOthers(Player player, OnlinePlayer onlinePlayer) {
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

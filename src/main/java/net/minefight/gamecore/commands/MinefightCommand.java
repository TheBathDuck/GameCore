package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("minefight")
@CommandPermission("minefight.command.admin")
public class MinefightCommand extends BaseCommand {

    @Default
    public void help(Player player) {
        player.sendMessage(ChatUtils.color("<primary>/minefight reload <gray>- <secondary>Reloads the configuration."));
        player.sendMessage(ChatUtils.color("<primary>/minefight parse <message> <gray>- <secondary>Parse a message with MiniMessage."));
    }

    @Subcommand("reload")
    public void reload(Player player) {
        GameCore plugin = GameCore.getInstance();
        player.sendMessage(ChatUtils.color("<primary>Reloading configuration."));
        plugin.getGameConfig().reload();
        player.sendMessage(ChatUtils.color("<primary>Server configuration reloaded."));
    }

    @Subcommand("parsemessage")
    public void parseMessage(Player player, String message) {
        player.sendMessage(ChatUtils.color(message));
    }

}

package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.google.common.collect.ImmutableList;
import net.kyori.adventure.audience.Audience;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("broadcast|bc")
@CommandPermission("minefight.command.broadcast")
public class BroadcastCommand extends BaseCommand {

//    public BroadcastCommand() {
//        GameCore plugin = GameCore.getInstance();
//        plugin.getCommandManager().getCommandCompletions().registerCompletion("type", type -> {
//            return ImmutableList.of("legacy", "modern");
//        });
//    }

    @Default
    public void help(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("<primary>/broadcast <secondary>legacy <message> <gray>- <primary>Send a broadcast with legacy formatting."));
        sender.sendMessage(ChatUtils.color("<primary>/broadcast <secondary>modern <message> <gray>- <primary>Send a broadcast with modern formatting."));
        sender.sendMessage(ChatUtils.color(""));
        sender.sendMessage(ChatUtils.color("<primary><u>Formatting Styles<u>:"));
        sender.sendMessage(ChatUtils.color("<primary>Legacy: <secondary>Colorformatting with &."));
        sender.sendMessage(ChatUtils.color("<primary>Modern: <secondary>Colorformatting minimessage more <u><click:open_url:https://docs.advntr.dev/minimessage/format.html>info</click></u> here."));
    }

    @Subcommand("legacy")
    public void legacy(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.legacyColor(message));
        }
    }

    @Subcommand("modern")
    public void modern(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.color(message));
        }
    }

}

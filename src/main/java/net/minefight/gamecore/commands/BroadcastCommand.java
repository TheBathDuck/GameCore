package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.google.common.collect.ImmutableList;
import net.kyori.adventure.audience.Audience;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("broadcast|bc")
@CommandPermission("minefight.command.broadcast")
@Description("Send a broadcast.")
public class BroadcastCommand extends BaseCommand {

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
    @Description("Send a legacy broadcast with the & formatting.")
    public void legacy(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.legacyColor(message));
        }
    }

    @Subcommand("modern")
    @Description("Send a modern broadcast with minimessage formatting")
    public void modern(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatUtils.color(message));
        }
    }

}

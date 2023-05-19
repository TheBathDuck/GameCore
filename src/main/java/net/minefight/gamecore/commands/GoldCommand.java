package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("gold")
public class GoldCommand extends BaseCommand {

    private final PlayerManager playerManager;
    private final Database database;

    public GoldCommand() {
        GameCore plugin = GameCore.getInstance();
        playerManager = plugin.getPlayerManager();
        database = plugin.getDatabase();
    }

    @Default
    public void checkGold(CommandSender sender) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("<danger>Usage: /gold <player>"));
            return;
        }
        Player player = (Player) sender;
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        player.sendMessage(ChatUtils.color("<primary>You currently have <secondary>" + data.getGold() + " Gold<primary>."));
    }

    @Default
    @CommandPermission("minefight.command.gold.others")
    @Syntax("<player>")
    public void checkGoldOther(CommandSender sender, OnlinePlayer onlineTarget) {
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        sender.sendMessage(ChatUtils.color("<secondary>" + target.getName() + " <primary>currently has <secondary>" + targetData.getGold() + " Gold<primary>."));
    }

    @CommandPermission("minefight.command.gold.help")
    public void helpGold(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("<primary>/gold set <player> <amount> <gray>- <secondary>Set a player's gold."));
        sender.sendMessage(ChatUtils.color("<primary>/gold give <player> <amount> <gray>- <secondary>Add a amount to player's gold."));
        sender.sendMessage(ChatUtils.color("<primary>/gold take <player> <amount> <gray>- <secondary>Take a amount from a player's gold."));
        sender.sendMessage(ChatUtils.color("<primary>/gold reset <player> <gray>- <secondary>Reset a player's gold."));
    }

    @Subcommand("set")
    @CommandPermission("minefight.command.gold.set")
    @Syntax("<player> <amount>")
    public void setGold(CommandSender sender, OnlinePlayer onlineTarget, int amount) {
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        targetData.setGold(amount);
        sender.sendMessage(ChatUtils.color("<primary>Gold of <secondary>" + target.getName() + " <primary>has been set to <secondary>" + targetData.getGold() + "<primary>."));
        target.sendMessage(ChatUtils.color("<primary>Your gold has been set to <secondary>" + targetData.getGold() + " Gold<primary>."));
        updateData(targetData);
    }

    @Subcommand("give")
    @CommandPermission("minefight.command.gold.give")
    @Syntax("<player> <amount>")
    public void giveGold(CommandSender sender, OnlinePlayer onlineTarget, int amount) {
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        targetData.setGold(targetData.getGold() + amount);
        sender.sendMessage(ChatUtils.color("<primary>You added <secondary>" + amount + " Gold <primary>to <secondary>" + target.getName() + "'s <primary>account."));
        target.sendMessage(ChatUtils.color("<secondary>" + targetData.getGold() + " Gold <primary>has been added to your account."));
        updateData(targetData);
    }

    @Subcommand("take")
    @CommandPermission("minefight.command.gold.take")
    @Syntax("<player> <amount>")
    public void takeGold(CommandSender sender, OnlinePlayer onlineTarget, int amount) {
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        int gold = targetData.getGold();

        if (gold < amount) {
            sender.sendMessage(ChatUtils.color("<danger>" + target.getName() + " only has " + gold + " Gold."));
            return;
        }

        targetData.setGold(targetData.getGold() - amount);
        sender.sendMessage(ChatUtils.color("<primary>You removed <secondary>" + amount + " Gold <primary>from <secondary>" + target.getName() + "'s <primary>account."));
        target.sendMessage(ChatUtils.color("<secondary>" + targetData.getGold() + " Gold <primary>has been taken from your account."));
        updateData(targetData);
    }

    @Subcommand("reset")
    @CommandPermission("minefight.command.gold.reset")
    @Syntax("<player>")
    public void resetGold(CommandSender sender, OnlinePlayer onlineTarget) {
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        targetData.setGold(0);
        sender.sendMessage(ChatUtils.color("<primary>Gold of <secondary>" + target.getName() + " <primary>has been set to <secondary>" + targetData.getGold() + "<primary>."));
        target.sendMessage(ChatUtils.color("<primary>Your gold has been reset to <secondary>0 Gold<primary>."));
        updateData(targetData);
    }

    private void updateData(PlayerData data) {
        database.updateData(data).thenAccept((d) -> {
        });
    }

}

package net.minefight.gamecore.commands.economy;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

@CommandAlias("eco")
public class EconomyCommand extends BaseCommand {

    public final PlayerManager playerManager;
    private final Database database;
    private final GameConfig config;
    public DecimalFormat decimalFormat;

    public EconomyCommand() {
        GameCore plugin = GameCore.getInstance();
        config = plugin.getGameConfig();
        playerManager = plugin.getPlayerManager();
        database = plugin.getDatabase();
        decimalFormat = config.getEconomyFormat();
    }

    @Default
    public void help(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("<primary>/eco set <player> <amount> <gray>- <secondary>Set a player's balance."));
        sender.sendMessage(ChatUtils.color("<primary>/eco give <player> <amount> <gray>- <secondary>Add a amount to player's balance."));
        sender.sendMessage(ChatUtils.color("<primary>/eco take <player> <amount> <gray>- <secondary>Take a amount from a player's balance."));
        sender.sendMessage(ChatUtils.color("<primary>/eco reset <player> <amount> <gray>- <secondary>Reset a player's balance."));
    }

    @Subcommand("set")
    @CommandCompletion("@players")
    @Syntax("<player> <amount>")
    public void setEconomy(CommandSender sender, OnlinePlayer onlineTarget, double amount) {
        if (onlineTarget == null) {
            sender.sendMessage(ChatUtils.color("<danger>Target is not a valid player."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());

        targetData.setBalance(amount);
        sender.sendMessage(ChatUtils.color("<primary>You set <secondary>" + target.getName() + "'s <primary>balance to <secondary>" + config.getEconomySign() + format(amount) + "<primary>."));
        target.sendMessage(ChatUtils.color("<primary>Your balance was set to <secondary>" + config.getEconomySign() + format(amount) + "."));
        updateData(targetData);
    }

    @Subcommand("give")
    @CommandCompletion("@players")
    @Syntax("<player> <amount>")
    public void giveEconomy(CommandSender sender, OnlinePlayer onlineTarget, double amount) {
        if (onlineTarget == null) {
            sender.sendMessage(ChatUtils.color("<danger>Target is not a valid player."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());

        targetData.setBalance(targetData.getBalance() + amount);

        sender.sendMessage(ChatUtils.color("<primary>You deposited <secondary>" + config.getEconomySign() + format(amount) + " <primary>to <secondary>" + target.getName() + "'s<primary> balance."));
        target.sendMessage(ChatUtils.color("<primary>You received <secondary>" + config.getEconomySign() + format(amount) + " <primary>from <secondary>" + sender.getName() + "<primary>."));
        updateData(targetData);
    }

    @Subcommand("take")
    @CommandCompletion("@players")
    @Syntax("<player> <amount>")
    public void takeEconomy(CommandSender sender, OnlinePlayer onlineTarget, double amount) {
        if (onlineTarget == null) {
            sender.sendMessage(ChatUtils.color("<danger>Target is not a valid player."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        double balance = targetData.getBalance();

        if (balance < amount) {
            sender.sendMessage(ChatUtils.color("<danger>" + target.getName() + " only has " + config.getEconomySign() + format(balance) + "."));
            return;
        }

        targetData.setBalance(balance - amount);
        sender.sendMessage(ChatUtils.color("<primary>You took <secondary>" + config.getEconomySign() + format(amount) + " <primary>from <secondary>" + target.getName()));
        target.sendMessage(ChatUtils.color("<secondary>" + config.getEconomySign() + amount + " <primary>has been taken from your account."));
        updateData(targetData);
    }

    @Subcommand("reset")
    @CommandCompletion("@players")
    @Syntax("<player> <amount>")
    public void resetEconomy(CommandSender sender, OnlinePlayer onlineTarget) {
        if (onlineTarget == null) {
            sender.sendMessage(ChatUtils.color("<danger>Target is not a valid player."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        targetData.setBalance(0);

        sender.sendMessage(ChatUtils.color("<primary>Balance of <secondary>" + target.getName() + " <primary>has been reset and set to <secondary>" + config.getEconomySign() + format(targetData.getBalance()) + "<primary>."));
        target.sendMessage(ChatUtils.color("<primary>Your balance has been reset to <secondary>" + config.getEconomySign() + format(targetData.getBalance())));
        updateData(targetData);
    }

    private String format(double number) {
        return decimalFormat.format(number);
    }

    private void updateData(PlayerData data) {
        database.updateData(data).thenAccept((d) -> {
        });
    }


}

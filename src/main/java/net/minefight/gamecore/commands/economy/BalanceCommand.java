package net.minefight.gamecore.commands.economy;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

@CommandAlias("bal|money|balance")
public class BalanceCommand extends BaseCommand {

    private final PlayerManager playerManager;
    private final DecimalFormat decimalFormat;
    private final GameConfig config;

    public BalanceCommand() {
        GameCore plugin = GameCore.getInstance();
        config = plugin.getGameConfig();
        playerManager = plugin.getPlayerManager();
        decimalFormat = config.getEconomyFormat();
    }

    @Default
    @Description("Check your balance.")
    public void balance(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("<danger>Use: /balance <player>."));
            return;
        }
        Player player = (Player) sender;
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        sender.sendMessage(ChatUtils.color("<primary>You currently have <secondary>" + config.getEconomySign() + format(data.getBalance()) + " <primary>in your balance."));
    }

    @Default
    @CommandPermission("minefight.command.balance.other")
    @Description("Check a players balance.")
    public void checkOther(CommandSender sender, OnlinePlayer onlineTarget) {
        if (onlineTarget == null) {
            sender.sendMessage(ChatUtils.color("<danger>Target is not a valid player."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());
        sender.sendMessage(ChatUtils.color("<secondary>" + target.getName() + " <primary>currently has <secondary>" + config.getEconomySign() + format(targetData.getBalance()) + "<primary>."));
    }

    private String format(double number) {
        return decimalFormat.format(number);
    }


}

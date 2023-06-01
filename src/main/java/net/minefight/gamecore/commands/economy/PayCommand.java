package net.minefight.gamecore.commands.economy;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.w3c.dom.CDATASection;

@CommandAlias("pay")
public class PayCommand extends BaseCommand {

    private final PlayerManager playerManager;
    private final Database database;
    private GameConfig gameConfig;

    public PayCommand() {
        playerManager = GameCore.getInstance().getPlayerManager();
        database = GameCore.getInstance().getDatabase();
        gameConfig = GameCore.getInstance().getGameConfig();
    }

    @Default
    public void pay(Player player, OnlinePlayer onlineTarget, double amount) {
        Player target = onlineTarget.getPlayer();

        if(player.getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(ChatUtils.color("<danger>You can't pay yourself."));
            return;
        }

        PlayerData playerData = playerManager.getPlayerData(player.getUniqueId());
        PlayerData targetData = playerManager.getPlayerData(target.getUniqueId());

        if(amount <= 0) {
            player.sendMessage(ChatUtils.color("<danger>Amount can't be negative or zero."));
            return;
        }

        if(playerData.getBalance() < amount) {
            player.sendMessage(ChatUtils.color("<danger>You don't have enough money."));
            return;
        }

        playerData.setBalance(playerData.getBalance() - amount);
        targetData.setBalance(targetData.getBalance() + amount);

        player.sendMessage(ChatUtils.color("<primary>You sent <secondary>" + gameConfig.getEconomySign() + amount + " <primary>to <secondary>" + target.getName() + "<primary>."));
        target.sendMessage(ChatUtils.color("<primary>You received <secondary>" + gameConfig.getEconomySign() + amount + " <primary>from <secondary>" + player.getName() + "<primary>."));

        database.updateData(playerData);
        database.updateData(targetData);
    }



}

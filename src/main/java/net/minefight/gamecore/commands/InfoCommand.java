package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

@CommandAlias("pinfo|playerinfo")
@CommandPermission("minefight.command.playerinfo")
public class InfoCommand extends BaseCommand {

    @Default
    @Description("Get information about a player")
    @Syntax("<player name>")
    @CommandCompletion("@players")
    public void informationCommand(CommandSender sender, String targetName) {
        PlayerManager playerManager = GameCore.getInstance().getPlayerManager();
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            PlayerData data = playerManager.getPlayerData(target.getUniqueId());
            sendInformation(sender, target.getName(), data, true);
            return;
        }

        if(sender instanceof Player player) {
            player.sendActionBar(ChatUtils.color("<gray>Loading <dark_gray>(Offline) <gray>" + targetName + "'s <gray>data."));
        }

        // retrieve offline player information.
        Bukkit.getScheduler().runTaskAsynchronously(GameCore.getInstance(), () -> {
            Database database = GameCore.getInstance().getDatabase();
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);
            if (!offlinePlayer.hasPlayedBefore()) {
                return;
            }

            database.isInDatabase(offlinePlayer.getUniqueId()).thenAccept(contains -> {
                if(!contains) {
                    sender.sendMessage(ChatUtils.color("<danger>Player isn't in the database."));
                    return;
                }

                database.getPlayer(offlinePlayer.getUniqueId()).thenAccept((data) -> {
                    sendInformation(sender, offlinePlayer.getName(), data, false);
                }).exceptionally(e -> {
                    sender.sendMessage(ChatUtils.color("<danger>??"));
                    e.printStackTrace();
                    return null;
                });

            });
        });

    }

    private void sendInformation(CommandSender sender, String name, PlayerData data, boolean online) {
        GameConfig config = GameCore.getInstance().getGameConfig();

        Date firstJoin = new Date(data.getFirstJoin());
        Date lastJoin = new Date(data.getLastJoin());

        String lastJoinLine = online ? "Online" : config.getDateFormat().format(lastJoin);

        sender.sendMessage(ChatUtils.color("<primary>Stats of <secondary>" + name));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.color("<primary>First Join: <secondary>" + config.getDateFormat().format(firstJoin)));
        sender.sendMessage(ChatUtils.color("<primary>Last Join: <secondary>" + lastJoinLine));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.color("<primary>Balance " + config.getEconomySign() + ": <secondary>" + config.getEconomyFormat().format(data.getBalance())));
        sender.sendMessage(ChatUtils.color("<primary>Gold: <secondary>" + data.getGold()));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.color("<primary>Kills / Deaths: <secondary>" + data.getKills() + " / " + data.getDeaths()));
    }

}

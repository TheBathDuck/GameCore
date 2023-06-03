package net.minefight.gamecore.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    private final DecimalFormat economyFormat;
    private final NumberFormat goldFormat;
    private final GameConfig config;
    private final LuckPerms luckPerms;

    public PlaceholderAPIHook() {
        GameCore plugin = GameCore.getInstance();
        config = plugin.getGameConfig();
        economyFormat = config.getEconomyFormat();
        goldFormat = config.getGoldFormat();
        luckPerms = LuckPermsProvider.get();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mf";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheBathDuck";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }


    @Override
    public String onRequest(OfflinePlayer player, String params) {
        PlayerManager playerManager = GameCore.getInstance().getPlayerManager();
        if (!player.isOnline()) return null;
        if (!playerManager.isCached(player.getUniqueId())) return "Loading..";
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());

        switch (params.toLowerCase()) {
            case "gold":
                return String.valueOf(data.getGold());
            case "gold_formatted":
                return goldFormat.format(data.getGold());
            case "blocksmined":
                return String.valueOf(data.getMined());
            case "eco_sign":
                return config.getEconomySign();
            case "bal", "balance", "money":
                return String.valueOf(data.getBalance());
            case "bal_formatted":
                return economyFormat.format(data.getBalance());
            case "kills":
                return String.valueOf(data.getKills());
            case "deaths":
                return String.valueOf(data.getDeaths());
            case "lp_weight":
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                if(user == null) return "-1";
                return user.getCachedData().getMetaData().getMetaValue("weight");
        }

        return "Unknown";
    }

}

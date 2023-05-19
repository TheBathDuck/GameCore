package net.minefight.gamecore.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

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
        if(!player.isOnline()) return null;
        if(!playerManager.isCached(player.getUniqueId())) return "Loading..";
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());

        if(params.equals("gold")) {
            return String.valueOf(data.getGold());
        }

        if(params.equals("blocksmined")) {
            return String.valueOf(data.getMined());
        }

        if(params.equals("bal") || params.equals("balance") || params.equals("money")) {
            return String.valueOf(data.getBalance());
        }

        return null;
    }

}

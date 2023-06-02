package net.minefight.gamecore.hooks;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class EconomyImplementation implements Economy {

    private final PlayerManager playerManager;

    public EconomyImplementation() {
        playerManager = GameCore.getInstance().getPlayerManager();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "MineFight-Economy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return GameCore.getInstance().getGameConfig().getEconomySign();
    }

    @Override
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if(player == null) return false;
        return playerManager.isCached(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return playerManager.isCached(offlinePlayer.getUniqueId());
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return hasAccount(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return playerManager.isCached(offlinePlayer.getUniqueId());
    }

    @Override
    public double getBalance(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        if(player == null) return 0;
        return playerManager.getPlayerData(player.getUniqueId()).getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        if(!playerManager.isCached(offlinePlayer.getUniqueId())) return 0;
        return playerManager.getPlayerData(offlinePlayer.getUniqueId()).getBalance();
    }

    @Override
    public double getBalance(String s, String s1) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double amount) {
        double balance = getBalance(s);
        return amount <= balance;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double amount) {
        double balance = getBalance(offlinePlayer);
        return amount <= balance;
    }

    @Override
    public boolean has(String s, String s1, double amount) {
        double balance = getBalance(s);
        return amount <= balance;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        double balance = getBalance(offlinePlayer);
        return v <= balance;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        if(player == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        data.setBalance(data.getBalance() - v);
        return new EconomyResponse(v, data.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Withdrawn " + v + " from " + player.getName());
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if(!offlinePlayer.isOnline()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        PlayerData data = playerManager.getPlayerData(offlinePlayer.getUniqueId());
        data.setBalance(data.getBalance() - v);
        return new EconomyResponse(v, data.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Withdrawn " + v + " from " + offlinePlayer.getName());
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        if(player == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        data.setBalance(data.getBalance() + v);
        return new EconomyResponse(v, data.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Deposited " + v + " to " + player.getName());
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        if(!offlinePlayer.isOnline()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player is not online");
        PlayerData data = playerManager.getPlayerData(offlinePlayer.getUniqueId());
        data.setBalance(data.getBalance() + v);
        return new EconomyResponse(v, data.getBalance(), EconomyResponse.ResponseType.SUCCESS, "Deposited " + v + " to " + offlinePlayer.getName());
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Banks are not supported.");
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}

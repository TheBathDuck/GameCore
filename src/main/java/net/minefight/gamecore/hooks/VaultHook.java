package net.minefight.gamecore.hooks;

import net.milkbowl.vault.economy.Economy;
import net.minefight.gamecore.GameCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private Economy provider;
    private GameCore plugin;

    public void hook() {
        plugin = GameCore.getInstance();
        provider = plugin.getEconomy();

        Bukkit.getServicesManager().register(Economy.class, provider, plugin, ServicePriority.Normal);
        plugin.getLogger().info("[Economy] Hooked into vault.");
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, provider);
        plugin.getLogger().info("[Economy] Unhooked from vault.");
    }

}

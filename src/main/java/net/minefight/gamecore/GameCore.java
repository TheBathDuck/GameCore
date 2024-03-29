package net.minefight.gamecore;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.minefight.gamecore.combat.CombatCommand;
import net.minefight.gamecore.combat.CombatManager;
import net.minefight.gamecore.combat.CombatTimer;
import net.minefight.gamecore.commands.*;
import net.minefight.gamecore.commands.economy.BalanceCommand;
import net.minefight.gamecore.commands.economy.EconomyCommand;
import net.minefight.gamecore.commands.economy.PayCommand;
import net.minefight.gamecore.commands.gamemode.*;
import net.minefight.gamecore.commands.moderation.InventoryCheckCommand;
import net.minefight.gamecore.commands.teleportation.SpawnCommand;
import net.minefight.gamecore.commands.teleportation.TeleportAllCommand;
import net.minefight.gamecore.commands.teleportation.TeleportCommand;
import net.minefight.gamecore.commands.teleportation.TeleportHereCommand;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.database.Database;
import net.minefight.gamecore.hooks.EconomyImplementation;
import net.minefight.gamecore.hooks.PlaceholderAPIHook;
import net.minefight.gamecore.hooks.VaultHook;
import net.minefight.gamecore.listeners.*;
import net.minefight.gamecore.menus.MenuManager;
import net.minefight.gamecore.players.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class GameCore extends JavaPlugin {

    private static @Getter GameCore instance;

    private @Getter PaperCommandManager commandManager;
    private @Getter Database database;
    private @Getter PlayerManager playerManager;
    private @Getter GameConfig gameConfig;
    private @Getter MenuManager menuManager;
    private @Getter CombatManager combatManager;

    private @Getter VaultHook vaultHook;
    private @Getter Economy economy;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;

        saveDefaultConfig();
        gameConfig = new GameConfig();
        playerManager = new PlayerManager();
        commandManager = new PaperCommandManager(this);
        menuManager = new MenuManager();
        combatManager = new CombatManager();

        database = new Database(
                getConfig().getString("database.host"),
                getConfig().getInt("database.port"),
                getConfig().getString("database.database"),
                getConfig().getString("database.username"),
                getConfig().getString("database.password")
        );

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIHook().register();
        }

        registerCommands();
        registerListeners();

        hookIntoVault();
        getLogger().warning("Plugin started in " + (System.currentTimeMillis() - start) + "ms!");
    }

    @Override
    public void onDisable() {
        vaultHook.unhook();

        for(CombatTimer timer : combatManager.getCombatTimers().values()) {
            getLogger().info("[CombatManager] Killed timer with ID " + timer.getTaskId());
            timer.cancel();
        }

    }

    public void registerListeners() {
        Arrays.asList(
                new CommandBlockListener(),
                new PlayerJoinListener(),
                new PlayerQuitListener(),
                new PlayerChatListener(),
                new StatsListener(),
                new CitizensStoreListener()
        ).forEach(event -> Bukkit.getPluginManager().registerEvents(event, this));
    }

    public void registerCommands() {
        /* Unsorted Commands */
        commandManager.registerCommand(new SpawnCommand());
        commandManager.registerCommand(new PingCommand());


        /* Staff Commands */
        commandManager.registerCommand(new BroadcastCommand());
        commandManager.registerCommand(new FlyCommand());
        commandManager.registerCommand(new TopCommand());
        commandManager.registerCommand(new SpeedCommand());
        commandManager.registerCommand(new InventoryCheckCommand());
        commandManager.registerCommand(new HealCommand());
        commandManager.registerCommand(new EnderChestCommand());
        commandManager.registerCommand(new GamemodeCommand());
        commandManager.registerCommand(new GamemodeCreativeCommand());
        commandManager.registerCommand(new GamemodeSurvivalCommand());
        commandManager.registerCommand(new GamemodeAdventureCommand());
        commandManager.registerCommand(new GamemodeSpectatorCommand());
        commandManager.registerCommand(new ItemCommand());
        commandManager.registerCommand(new SkullCommand());
        commandManager.registerCommand(new InfoCommand());

        /* Teleportation Commands */
        commandManager.registerCommand(new TeleportCommand());
        commandManager.registerCommand(new TeleportHereCommand());
        commandManager.registerCommand(new TeleportAllCommand());

        /* Economy Commands */
        commandManager.registerCommand(new EconomyCommand());
        commandManager.registerCommand(new BalanceCommand());
        commandManager.registerCommand(new PayCommand());
        commandManager.registerCommand(new GoldCommand());

        /* Admin Commands */
        commandManager.registerCommand(new MinefightCommand());
        commandManager.registerCommand(new LagCommand());
        commandManager.registerCommand(new CombatCommand());
        commandManager.registerCommand(new LoopCommand());
    }

    public void reloadGameConfig() {
        reloadConfig();
        gameConfig = new GameConfig();
    }

    private void hookIntoVault() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            getLogger().warning("[Economy] Couldn't hook into Vault as it's not instaled or disabled.");
            return;
        }

        economy = new EconomyImplementation();
        vaultHook = new VaultHook();
        vaultHook.hook();
    }


}

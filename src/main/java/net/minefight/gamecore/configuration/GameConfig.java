package net.minefight.gamecore.configuration;

import lombok.Getter;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.SerializeUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class GameConfig {

    private @Getter Location serverSpawn;
    private @Getter String economySign;
    private @Getter DecimalFormat economyFormat;
    private @Getter NumberFormat goldFormat;
    private @Getter SimpleDateFormat dateFormat;

    public GameConfig() {
        GameCore plugin = GameCore.getInstance();
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        reload();
    }

    public void reload() {
        GameCore plugin = GameCore.getInstance();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        if(config.contains("locations.spawn")) {
            serverSpawn = SerializeUtils.stringToLocation(config.getString("locations.spawn"));
        }
        if(config.contains("economy.sign")) {
            economySign = config.getString("economy.sign");
        }
        if(config.contains("economy.format")) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            symbols.setGroupingSeparator('.');
            economyFormat = new DecimalFormat(config.getString("economy.format"), symbols);
        }

        goldFormat = NumberFormat.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    }


}

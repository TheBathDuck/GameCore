package net.minefight.gamecore.configuration;

import lombok.Data;
import lombok.Getter;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.SerializeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;


public class ServerConfig {

    private @Getter Location spawn;

    public ServerConfig() {
        GameCore plugin = GameCore.getInstance();
        FileConfiguration config = plugin.getConfig();

        spawn = SerializeUtils.stringToLocation(config.getString("locations.spawn"));

        plugin.getLogger().info("Loaded server configuration.");
    }


}

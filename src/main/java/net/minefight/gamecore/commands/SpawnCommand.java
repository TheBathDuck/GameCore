package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.SerializeUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {

    @Default
    public void spawn(Player player) {
        GameConfig config = GameCore.getInstance().getGameConfig();
        player.teleport(config.getServerSpawn());
        player.sendMessage(ChatUtils.color("<primary>Teleported you to <secondary>spawn<primary>."));
    }

}

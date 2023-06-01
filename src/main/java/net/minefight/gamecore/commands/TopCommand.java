package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@CommandAlias("top")
@CommandPermission("minefight.command.top")
@Description("Teleport yourself to the top.")
public class TopCommand extends BaseCommand {

    @Default
    public void topCommand(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();

        Location highestLocation = world.getHighestBlockAt(location).getLocation();

        if(highestLocation.getY() <= -64) {
            player.sendMessage(ChatUtils.color("<danger>Can't teleport to a safe location."));
            return;
        }

        highestLocation.setY(highestLocation.getY() + 1);

        player.teleport(highestLocation);
        player.sendMessage(ChatUtils.color("<primary>Teleported to the top."));
    }

}

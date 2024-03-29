package net.minefight.gamecore.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("teleport|tp")
@CommandPermission("minefight.command.teleport")
@Description("Teleport to a player or coords.")
public class TeleportCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Description("Teleport yourself to someone.")
    @Syntax("<player>")
    public void teleport(Player player, OnlinePlayer onlinePlayer) {
        Player target = onlinePlayer.getPlayer();
        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        player.teleport(target);
        player.sendMessage(ChatUtils.color("<primary>You teleported to <secondary>" + target.getName() + "<primary>."));
    }

    @Default
    @CommandCompletion("@players")
    @CommandPermission("minefight.command.teleport.coords")
    @Description("Teleport yourself to coords.")
    @Syntax("<x> <y> <z>")
    public void teleportCoords(Player player, double x, double y, double z) {
        Location target = player.getLocation();
        target.setX(x);
        target.setY(y);
        target.setZ(z);

        String formatted = target.getWorld().getName() + "<primary>, <secondary>" + x + "<primary>, <secondary>" + y + "<primary>, <secondary>" + z;

        player.teleport(target);
        player.sendMessage(ChatUtils.color("<primary>You teleported to <secondary>" + formatted + "<primary>."));
    }

    @Default
    @CommandCompletion("@players")
    @Syntax("<player> <x> <y> <z>")
    @Description("Teleport someone to coords.")
    @CommandPermission("minefight.command.teleport.coords.other")
    public void teleportCoordsOthers(OnlinePlayer onlinePlayer, Player player, double x, double y, double z) {
        Player target = onlinePlayer.getPlayer();
        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>The given target is currently not online."));
            return;
        }

        Location targetLocation = target.getLocation();
        targetLocation.setX(x);
        targetLocation.setY(y);
        targetLocation.setZ(z);

        String formatted = target.getWorld().getName() + "<primary>, <secondary>" + x + "<primary>, <secondary>" + y + "<primary>, <secondary>" + z;

        target.teleport(targetLocation);
        player.sendMessage(ChatUtils.color("<primary>You teleported <secondary>" + target.getName() + "<primary> to <secondary>" + formatted + "."));
    }

}

package net.minefight.gamecore.commands.moderation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("invsee|inventory")
@CommandPermission("minefight.command.invsee")
public class InventoryCheckCommand extends BaseCommand {

    @Default
    public void help(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("<primary>/invsee <secondary><player> <gray>- <primary>Check the inventory of a player."));
    }

    @Default
    public void checkInventory(Player player, OnlinePlayer target) {

        if(target == null) {
            player.sendMessage(ChatUtils.color("<danger>Player not found."));
            return;
        }
        Player targetPlayer = target.getPlayer();
        player.openInventory(targetPlayer.getInventory());
        player.sendMessage(ChatUtils.color("<primary>Opened inventory of <secondary>" + targetPlayer.getName() + "<primary>."));
    }

}

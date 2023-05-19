package net.minefight.gamecore.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpall")
@CommandPermission("minefight.command.tpall")
public class TeleportAllCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void teleportAll(Player player) {

        for(Player target : Bukkit.getOnlinePlayers()) {
            target.teleport(player);
        }

        player.sendMessage(ChatUtils.color("<primary>Teleported <secondary>everyone <primary>to you."));
    }

}

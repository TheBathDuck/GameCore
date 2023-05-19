package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("ec|enderchest")
@CommandPermission("minefight.command.enderchest")
public class EnderChestCommand extends BaseCommand {

    @Default
    public void enderChest(Player player, @Optional OnlinePlayer onlineTarget) {

        if(onlineTarget == null) {
            player.openInventory(player.getEnderChest());
            player.sendMessage(ChatUtils.color("<primary>Opened your ender chest."));
            return;
        }
        Player target = onlineTarget.getPlayer();
        player.openInventory(target.getEnderChest());
        player.sendMessage(ChatUtils.color("<primary>Opened ender chest of <secondary>" + target.getName() + "<primary>."));
    }

}

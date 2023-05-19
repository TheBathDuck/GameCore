package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

@CommandAlias("heal")
@CommandPermission("minefight.command.heal")
public class HealCommand extends BaseCommand {

    @Default
    public void heal(Player player, @Optional OnlinePlayer onlineTarget) {

        if(onlineTarget == null) {
            heal(player);
            player.sendMessage(ChatUtils.color("<primary>You have been healed."));
            return;
        }

        Player target = onlineTarget.getPlayer();
        heal(target);
        player.sendMessage(ChatUtils.color("<primary>You have healed <secondary>" + target.getName() + "<primary>."));
    }

    private void heal(Player player) {
        double maxHealth = 20;
        if(player.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        }
        player.setHealth(maxHealth);
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }

}

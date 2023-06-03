package net.minefight.gamecore.combat;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("combattimer")
@CommandPermission("minefight.command.combattimer")
public class CombatCommand extends BaseCommand {

    @Default
    public void timer(Player player, int secs) {
        CombatManager combatManager = GameCore.getInstance().getCombatManager();

        if(combatManager.isInCombat(player.getUniqueId())) {
            combatManager.setCombat(player.getUniqueId(), secs);
            player.sendMessage(ChatUtils.color("<primary>Edited seconds to: <secondary>" + secs));
            return;
        }

        combatManager.setCombat(player.getUniqueId(), secs);
        player.sendMessage(ChatUtils.color("<primary>New timer made with <secondary>" + secs + " seconds"));
    }

}

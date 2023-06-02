package net.minefight.gamecore.commands.teleportation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.combat.CombatManager;
import net.minefight.gamecore.combat.CombatTimer;
import net.minefight.gamecore.configuration.GameConfig;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.SerializeUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@CommandAlias("spawn")
@Description("Teleport to the server spawn.")
public class SpawnCommand extends BaseCommand {

    @Default
    public void spawn(Player player) {
        CombatManager combatManager = GameCore.getInstance().getCombatManager();
        if(combatManager.isInCombat(player.getUniqueId())) {
            CombatTimer timer = combatManager.getTimer(player.getUniqueId());
            player.sendMessage(ChatUtils.color("<danger>You can't teleport to spawn as you have " + timer.getSeconds() + "seconds in combat left."));
            return;
        }

        GameConfig config = GameCore.getInstance().getGameConfig();
        player.teleport(config.getServerSpawn());
        player.sendMessage(ChatUtils.color("<primary>Teleported you to <secondary>spawn<primary>."));
    }

}

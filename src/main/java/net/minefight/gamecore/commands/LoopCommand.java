package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.tasks.LoopTask;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

@CommandAlias("loop")
@CommandPermission("minefight.command.loop")
public class LoopCommand extends BaseCommand {

    @Default
    @Syntax("<amount> <delay> <command>")
    public void loopCommand(Player player, int amount, int delay, String command) {
        player.sendMessage(ChatUtils.color("<primary>Executing \"<secondary>/" + command + "<primary>\" " + amount + " times with " + delay + " tick delay."));

        if(command.startsWith("/")) {
            command = command.replaceFirst("/", "");
        }

        if (delay == 0) {
            for (int i = 0; i <= amount; i++) {
                player.performCommand(command.replaceAll("%current%", String.valueOf(i)));
            }
            return;
        }

        new LoopTask(player, command, amount).runTaskTimer(GameCore.getInstance(), 0L, delay);
    }

}

package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.Getter;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.tasks.LoopTask;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.*;

@CommandAlias("loop")
@CommandPermission("minefight.command.loop")
public class LoopCommand extends BaseCommand {

    private @Getter Map<UUID, LoopTask> loopTasks = new HashMap<>();

    @Default
    @Syntax("<amount> <delay> <command>")
    public void loopCommand(Player player, int amount, int delay, String command) {
        if(loopTasks.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatUtils.color("<danger>You can only start 1 loop at a time, stop this loop using \"/loop stop\"."));
            return;
        }
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

        LoopTask task = new LoopTask(this, player, command, amount);
        loopTasks.put(player.getUniqueId(), task);
        task.runTaskTimer(GameCore.getInstance(), 0L, delay);
    }

    @Subcommand("stop")
    public void stopLoop(Player player) {
        LoopTask task = loopTasks.get(player.getUniqueId());
        if(task == null) {
            player.sendMessage(ChatUtils.color("<danger>No loop found."));
            return;
        }

        task.cancel();
        loopTasks.remove(player.getUniqueId());
        player.sendMessage(ChatUtils.color("<primary>Stopped your loop."));
    }

}

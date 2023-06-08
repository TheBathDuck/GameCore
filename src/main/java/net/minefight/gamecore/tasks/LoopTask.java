package net.minefight.gamecore.tasks;

import net.kyori.adventure.bossbar.BossBar;
import net.minefight.gamecore.commands.LoopCommand;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoopTask extends BukkitRunnable {

    private LoopCommand loopCommand;
    public Player player;
    public String command;
    private int amount;
    private int currentAmount;
    private BossBar bossBar;

    public LoopTask(LoopCommand loopCommand, Player player, String command, int amount) {
        this.loopCommand = loopCommand;
        this.player = player;
        this.command = command;
        this.amount = amount;
        this.currentAmount = 0;
        this.bossBar = BossBar.bossBar(ChatUtils.color("<white>Looping command <gray>\"" + command + "\"<white>. <white>Progress: <gray>0<dark_gray>/<gray>" + amount + " <dark_gray>(<gray>" + 0 + "%<dark_gray>)"), 0, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
        player.showBossBar(bossBar);
    }

    @Override
    public void run() {
        currentAmount++;
        updateBar();
        player.performCommand(command.replaceAll("%current%", String.valueOf(currentAmount)));
        if (currentAmount >= amount) {
            cancel();
        }
    }

    @Override
    public void cancel() {
        loopCommand.getLoopTasks().remove(player.getUniqueId());
        player.hideBossBar(bossBar);
        bossBar = null;
        Bukkit.getScheduler().cancelTask(getTaskId());
    }

    public void updateBar() {
        float value = (float) currentAmount / amount;
        float percentage = (currentAmount * 100.0f) / amount;
        bossBar.progress(value);
        bossBar.name(ChatUtils.color("<white>Looping command <gray>\"" + command + "\"<white>. <white>Progress: <gray>" + currentAmount + "<dark_gray>/<gray>" + amount + " <dark_gray>(<gray>" + (int) percentage + "%<dark_gray>)"));
    }

}

package net.minefight.gamecore.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoopTask extends BukkitRunnable {

    public Player player;
    public String command;
    private int amount;
    private int currentAmount;

    public LoopTask(Player player, String command, int amount) {
        this.player = player;
        this.command = command;
        this.amount = amount;
        this.currentAmount = 0;
    }

    @Override
    public void run() {
        currentAmount++;
        player.performCommand(command.replaceAll("%current%", String.valueOf(currentAmount)));
        if(currentAmount >= amount) {
            cancel();
        }
    }
}

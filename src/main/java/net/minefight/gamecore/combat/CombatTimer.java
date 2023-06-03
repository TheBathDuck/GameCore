package net.minefight.gamecore.combat;

import net.kyori.adventure.bossbar.BossBar;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CombatTimer extends BukkitRunnable {

    private final UUID uuid;
    private int seconds;
    private int total;
    private BossBar bossBar;
    private String title = "<primary>You are in combat for <secondary>%seconds% seconds<primary>.";

    public CombatTimer(UUID uuid, int seconds) {
        this.uuid = uuid;
        this.seconds = seconds + 1;
        this.total = this.seconds;
        this.bossBar = BossBar.bossBar(ChatUtils.color(title.replaceAll("%seconds%", String.valueOf(seconds))), 1f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);
        player().showBossBar(bossBar);
    }

    public Player player() {
        return Bukkit.getPlayer(uuid);
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public void run() {
        seconds--;
        updateBar();
        if(seconds <= 0) {
            this.cancel();
        }
    }

    private void updateBar() {
        float progress = (float) seconds / total;
        progress = Math.max(0, Math.min(1, progress));

        bossBar.name(ChatUtils.color(title.replaceAll("%seconds%", String.valueOf(seconds))));
        bossBar.progress(progress);
    }

    @Override
    public void cancel() {
        player().hideBossBar(bossBar);
        bossBar = null;
        CombatManager combatManager = GameCore.getInstance().getCombatManager();
        combatManager.getCombatTimers().remove(player().getUniqueId());
        Bukkit.getScheduler().cancelTask(this.getTaskId());
    }

    public int getSeconds() {
        return seconds;
    }
}

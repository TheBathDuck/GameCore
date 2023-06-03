package net.minefight.gamecore.combat;

import lombok.Getter;
import net.minefight.gamecore.GameCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager {

    private final GameCore plugin;
    private @Getter Map<UUID, CombatTimer> combatTimers;

    public CombatManager() {
        plugin = GameCore.getInstance();
        combatTimers = new HashMap<>();
    }


    public void setCombat(UUID uuid, int amount) {
        if(isInCombat(uuid)) {
            CombatTimer timer = getTimer(uuid);
            timer.setSeconds(amount + 1);
            return;
        }
        CombatTimer timer = new CombatTimer(uuid, amount);
        combatTimers.put(uuid, timer);
        timer.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    public boolean isInCombat(UUID uuid) {
        return combatTimers.containsKey(uuid);
    }

    public CombatTimer getTimer(UUID uuid) {
        return combatTimers.get(uuid);
    }


}

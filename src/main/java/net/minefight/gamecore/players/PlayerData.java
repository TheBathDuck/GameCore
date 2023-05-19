package net.minefight.gamecore.players;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PlayerData {

    private final @Getter UUID uuid;
    private @Getter @Setter int gold;
    private @Getter long firstJoin;
    private @Getter @Setter long lastJoin;
    private @Getter @Setter int mined;
    private @Getter @Setter double balance;

    public PlayerData(UUID uuid, int gold, long firstJoin, long lastJoin, int mined, double balance) {
        this.uuid = uuid;
        this.gold = gold;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.mined = mined;
        this.balance = balance;
    }

}

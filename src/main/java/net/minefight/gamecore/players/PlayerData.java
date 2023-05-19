package net.minefight.gamecore.players;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PlayerData {

    private final @Getter UUID uuid;
    private @Getter @Setter int gold;
    private @Getter long firstJoin;
    private @Getter @Setter long lastJoin;
    private @Getter @Setter double balance;

    private @Getter @Setter int mined;
    private @Getter @Setter int kills;
    private @Getter @Setter int deaths;

    public PlayerData(UUID uuid, int gold, long firstJoin, long lastJoin, int mined, double balance, int kills, int deaths) {
        this.uuid = uuid;
        this.gold = gold;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.mined = mined;
        this.balance = balance;
        this.kills = kills;
        this.deaths = deaths;
    }

}

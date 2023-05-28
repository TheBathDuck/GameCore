package net.minefight.gamecore.players;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minefight.gamecore.GameCore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerData {

    private final UUID uuid;
    private int gold;
    private long firstJoin;
    private long lastJoin;
    private double balance;

    private int mined;
    private int kills;
    private int deaths;
    private List<PurchaseHistory> purchaseHistory;

    public PlayerData(UUID uuid, int gold, long firstJoin, long lastJoin, int mined, double balance, int kills, int deaths) {
        this.uuid = uuid;
        this.gold = gold;
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
        this.mined = mined;
        this.balance = balance;
        this.kills = kills;
        this.deaths = deaths;
        this.purchaseHistory = new ArrayList<>();
    }

    public void setGold(int gold, boolean updateDatabase) {
        setGold(gold);
        if(!updateDatabase) return;
        GameCore.getInstance().getDatabase().updateData(this);
    }

    public void addPurchaseHistory(PurchaseHistory ph) {
        purchaseHistory.add(ph);
        GameCore.getInstance().getDatabase().addPurchaseHistory(uuid, ph);
    }

}

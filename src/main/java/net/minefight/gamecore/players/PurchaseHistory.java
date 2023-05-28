package net.minefight.gamecore.players;

import lombok.Getter;
import lombok.Setter;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.configuration.GameConfig;
import org.bukkit.Material;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class PurchaseHistory {

    @Getter
    private final UUID purchaseUUID;

    @Getter
    private final Material material;

    @Getter
    private final String purchaseItem;

    @Getter
    private final int cost;

    @Getter
    @Setter
    private final boolean visible;
    private @Getter long dateLong;

    public PurchaseHistory(UUID purchaseUUID, String material, String purchaseItem, int cost, boolean visible, long dateLong) {
        this.purchaseUUID = purchaseUUID;
        this.material = Material.getMaterial(material);
        this.purchaseItem = purchaseItem;
        this.cost = cost;
        this.visible = visible;
        this.dateLong = dateLong;
    }

    public String getDate() {
        return GameCore.getInstance().getGameConfig().getDateFormat().format(dateLong);
    }

}

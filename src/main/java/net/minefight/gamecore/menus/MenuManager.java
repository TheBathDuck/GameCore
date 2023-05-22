package net.minefight.gamecore.menus;

import dev.triumphteam.gui.guis.GuiItem;
import lombok.Getter;

public class MenuManager {

    private final @Getter PurchaseHistoryMenu purchaseHistory;
    private final @Getter ServerStoreMenu serverStore;

    public MenuManager() {
        purchaseHistory = new PurchaseHistoryMenu();
        serverStore = new ServerStoreMenu();
    }


}

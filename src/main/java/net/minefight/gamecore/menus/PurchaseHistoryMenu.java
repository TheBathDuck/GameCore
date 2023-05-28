package net.minefight.gamecore.menus;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.players.PurchaseHistory;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.GUIUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;

public class PurchaseHistoryMenu {

    private final GameCore plugin;
    private final GuiItem borderItem;
    private final PlayerManager playerManager;
    private final NumberFormat goldFormat;

    public PurchaseHistoryMenu() {
        plugin = GameCore.getInstance();
        goldFormat = plugin.getGameConfig().getGoldFormat();
        playerManager = GameCore.getInstance().getPlayerManager();
        borderItem = new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), action -> action.setResult(Event.Result.DENY));
    }

    public void open(Player player) {
        PlayerData data = playerManager.getPlayerData(player.getUniqueId());
        PaginatedGui gui = Gui.paginated()
                .title(ChatUtils.color("<secondary><u>Purchase History"))
                .rows(6)
                .pageSize(28)
                .disableAllInteractions()
                .create();

        gui.setItem(GUIUtils.getBorderSlots(), borderItem);
        loadButtons(player, gui);

        for (PurchaseHistory history : data.getPurchaseHistory()) {
            ItemStack historyItem = new ItemBuilder(history.getMaterial())
                    .setName("<primary>" + history.getPurchaseItem())
                    .addLoreLine("<gray>Bought on: <gold>" + history.getDate())
                    .addLoreLine("<gray>Bought for: <gold>" + goldFormat.format(history.getCost()) + " Gold")
                    .build();
            gui.addItem(new GuiItem(historyItem));
        }

        gui.open(player);
    }

    private void loadButtons(Player player, PaginatedGui gui) {

        GuiItem returnItem = new GuiItem(new ItemBuilder(Material.BOOK).setName("<primary>Return").build(), action -> {
            action.setResult(Event.Result.DENY);
            GameCore.getInstance().getMenuManager().getServerStore().open(player);
        });
        gui.setItem(49, returnItem);

        GuiItem previous = new GuiItem(new ItemBuilder(Material.ARROW).setName("<primary>Previous Page").build(), action -> {
            action.setResult(Event.Result.DENY);
            gui.previous();
        });
        gui.setItem(47, previous);

        GuiItem next = new GuiItem(new ItemBuilder(Material.ARROW).setName("<primary>Next Page").build(), action -> {
            action.setResult(Event.Result.DENY);
            gui.next();
        });
        gui.setItem(51, next);
    }
}

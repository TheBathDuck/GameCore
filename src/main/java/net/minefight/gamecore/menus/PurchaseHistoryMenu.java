package net.minefight.gamecore.menus;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.GUIUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class PurchaseHistoryMenu {

    private final GuiItem borderItem;
    public PurchaseHistoryMenu() {
        borderItem = new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), action -> action.setResult(Event.Result.DENY));
    }

    public void open(Player player) {
        PaginatedGui gui = Gui.paginated()
                .title(ChatUtils.color("<secondary><u>Purchase History"))
                .rows(6)
                .pageSize(28)
                .disableAllInteractions()
                .create();

        gui.setItem(GUIUtils.getBorderSlots(), borderItem);
        loadButtons(player, gui);


        for(int i = 0; i < 100; i++) {
            GuiItem holderItem = new GuiItem(new ItemBuilder(Material.GOLD_INGOT).setName("Item: " + i).build());
            gui.addItem(holderItem);
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

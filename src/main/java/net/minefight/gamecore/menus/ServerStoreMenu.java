package net.minefight.gamecore.menus;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.GUIUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ServerStoreMenu {

    public void open(Player player) {
        PlayerData data = GameCore.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
        Gui gui = Gui.gui()
                .title(ChatUtils.color("<secondary><u>Server Store"))
                .rows(6)
                .disableAllInteractions()
                .create();
        GuiItem borderItem = new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), action -> action.setResult(Event.Result.DENY));
        gui.setItem(GUIUtils.getBorderSlots(), borderItem);
        loadButtons(player, data, gui);

        GuiItem goldRank = new GuiItem(new ItemBuilder(Material.GOLD_INGOT).setName("<#FFCC33>Gold")
                .addLoreLine("")
                .addLoreLine("<green>✔ <#FFCC33>Gold</gradient> <white>Nametag")
                .addLoreLine("<green>✔ <#FFCC33><b>GOLD</b> <white>Prefix")
                .addLoreLine("<green>✔ <white>Weekly <gold>50 <white>gold")
                .addLoreLine("<green>✔ <#7289DA>Discord <white>Gold role.")
                .addLoreLine("")
                .addLoreLine("<primary>Purchase for 1,000 Gold")
                .build(),
                action -> {
                    action.setResult(Event.Result.DENY);
                }
        );
        gui.setItem(22, goldRank);

        gui.open(player);
    }

    private void loadButtons(Player player, PlayerData data, Gui gui) {
        GuiItem purchaseHistoryItem = new GuiItem(
                new ItemBuilder(Material.BOOK).setName("<primary>Purchase History")
                        .addLoreLine("<gray>View your purchase history for")
                        .addLoreLine("<gray>gold and store payments")
                        .addLoreLine("<gray>from here.")
                        .build(),
                action -> {
                    action.setResult(Event.Result.DENY);
                    GameCore.getInstance().getMenuManager().getPurchaseHistory().open(player);
                }
        );
        gui.setItem(48, purchaseHistoryItem);

        GuiItem closeItem = new GuiItem(new ItemBuilder(Material.BARRIER).setName("<danger>Close").build(), action -> {
            action.setResult(Event.Result.DENY);
            player.closeInventory();
        });
        gui.setItem(49, closeItem);

        GuiItem goldItem = new GuiItem(
                new ItemBuilder(Material.GOLD_NUGGET).setName("<primary>You have " + data.getGold() + " Gold.")
                        .addLoreLine("<gray>With gold you can purchase ranks,")
                        .addLoreLine("<gray>perks and cosmetical items.")
                        .addLoreLine("<gray>You can earn gold with events and")
                        .addLoreLine("<gray>specials quests. You can also purchase")
                        .addLoreLine("<gray>gold on our store.")
                        .addLoreLine("<primary>store.minefight.net")
                        .build(),
                action -> {
                    action.setResult(Event.Result.DENY);
                    player.sendMessage(ChatUtils.color("<primary>Purchase gold here: <secondary>https://store.minefight.net/"));
                }
        );
        gui.setItem(50, goldItem);
    }

}

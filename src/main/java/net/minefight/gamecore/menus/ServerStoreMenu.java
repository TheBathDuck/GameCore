package net.minefight.gamecore.menus;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PurchaseHistory;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.GUIUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Collection;
import java.util.UUID;

public class ServerStoreMenu {

    private final LuckPerms luckPerms;

    public ServerStoreMenu() {
        luckPerms = LuckPermsProvider.get();
    }

    public void open(Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if(user == null) {
            player.sendMessage(ChatUtils.color("<danger>Something went wrong while trying to fetch your data, please relog."));
            return;
        }


        PlayerData data = GameCore.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
        Gui gui = Gui.gui()
                .title(ChatUtils.color("<secondary><u>Server Store"))
                .rows(6)
                .disableAllInteractions()
                .create();
        GuiItem borderItem = new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), action -> action.setResult(Event.Result.DENY));
        gui.setItem(GUIUtils.getBorderSlots(), borderItem);
        loadButtons(player, data, gui);


        String purchaseGoldLine = isInGroup(user, "gold") ? "<primary><st>Purchase for 1,000 Gold</st> <red>PURCHASED!" : "<primary>Purchase for 1,000 Gold";
        GuiItem goldRank = new GuiItem(new ItemBuilder(Material.GOLD_INGOT).setName("<#FFCC33>Gold")
                .addLoreLine("")
                .addLoreLine("<green>✔ <gray>Name Color: <#FFCC33>Gold")
                .addLoreLine("<green>✔ <gray>Chat Prefix: <#FFCC33><b>GOLD</b>")
                .addLoreLine("<green>✔ <gray>Weekly Gold: <gold>50")
                .addLoreLine("<green>✔ <gray>Money Multiplier: <white>1.5x")
                .addLoreLine("<green>✔ <gray>Discord Role: <#55FFFF>Donator")
                .addLoreLine("")
                .addLoreLine(purchaseGoldLine)
                .build(),
                action -> {
                    action.setResult(Event.Result.DENY);
                    purchaseRank(player, "gold", "<#FFCC33><b>GOLD</b>", "Gold Rank", "GOLD_INGOT", 1000);
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

        GuiItem goldAmountItem = new GuiItem(
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
        gui.setItem(50, goldAmountItem);
    }

    private boolean isInGroup(User user, String groupName) {
        Collection<Group> playerGroups = user.getInheritedGroups(user.getQueryOptions());
        return playerGroups.stream().anyMatch(group -> group.getName().equals(groupName));
    }

    private void purchaseRank(Player player, String rank, String displayRank, String fancyName, String material, int cost) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        PlayerData data = GameCore.getInstance().getPlayerManager().getPlayerData(player.getUniqueId());
        if(isInGroup(user, rank)) {
            player.sendMessage(ChatUtils.color("<danger>You already own this rank!"));
            return;
        }
        int currentGold = data.getGold();
        if(currentGold < 1000) {
            player.sendMessage(ChatUtils.color("<danger>You don't have enough gold for this."));
            return;
        }
        player.closeInventory();
        data.setGold(currentGold - cost, true);
        DataMutateResult result = user.data().add(InheritanceNode.builder(rank).value(true).build());
        luckPerms.getUserManager().saveUser(user);
        if(!result.wasSuccessful()) {
            player.sendMessage(ChatUtils.color("<danger>There wen't something wrong while trying to add your rank. Your gold was returned. Please contact an admin."));
            data.setGold(data.getGold() + cost, true);
            return;
        }
        player.sendMessage(ChatUtils.color("<primary>You purchased " + displayRank + " <primary>rank!"));
        data.addPurchaseHistory(new PurchaseHistory(UUID.randomUUID(), material, fancyName, cost, true, System.currentTimeMillis()));
    }


}

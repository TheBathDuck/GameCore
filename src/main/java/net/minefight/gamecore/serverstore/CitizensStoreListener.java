package net.minefight.gamecore.serverstore;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.menus.MenuManager;
import net.minefight.gamecore.players.PlayerData;
import net.minefight.gamecore.players.PlayerManager;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.GUIUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensStoreListener implements Listener {

    private final MenuManager menuManager;

    public CitizensStoreListener() {
        GameCore plugin = GameCore.getInstance();
        menuManager = plugin.getMenuManager();
    }

    @EventHandler
    public void citizensClick(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();

        if (!npc.data().get("MINEFIGHT_NPC").equals("STORE")) return;
        menuManager.getServerStore().open(player);
    }

}

package net.minefight.gamecore.serverstore;

import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.MetadataStore;
import net.citizensnpcs.api.npc.NPC;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CitizensStoreListener implements Listener {

    @EventHandler
    public void citizensClick(NPCRightClickEvent event) {
        NPC npc = event.getNPC();
        Player player = event.getClicker();

        if(!npc.data().get("MINEFIGHT_NPC").equals("STORE")) return;
        List<Integer> borderSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26);

        Gui gui = Gui.gui()
                .title(ChatUtils.color("<yellow><u>Server Store"))
                .rows(3)
                .create();

        GuiItem borderItem = new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build(), action -> action.setResult(Event.Result.DENY));
        gui.setItem(borderSlots, borderItem);

        GuiItem closeItem = new GuiItem(new ItemBuilder(Material.BARRIER).setName("<danger>Close").build(), action -> {
            action.setResult(Event.Result.DENY);
            player.closeInventory();
        });
        gui.setItem(22, closeItem);

        gui.open(player);
    }

}

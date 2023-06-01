package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.concurrent.CompletableFuture;

@CommandAlias("skull|head")
@CommandPermission("minefight.command.skull")
public class SkullCommand extends BaseCommand {

    @Default
    public void selfSkull(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
        player.sendMessage(ChatUtils.color("<primary>Your skull was added to your inventory."));
    }

    @Default
    public void otherSkulls(Player player, String name) {
        player.sendMessage(ChatUtils.color("<primary>Fetching skull of <secondary>" + name + "<primary>."));
        Bukkit.getScheduler().runTaskAsynchronously(GameCore.getInstance(), () -> {
            ItemStack item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(name);
            item.setItemMeta(meta);
            player.getInventory().addItem(item);
            player.sendMessage(ChatUtils.color("<primary>Skull of <secondary>" + name + "<primary> added to your inventory."));
        });
    }


}

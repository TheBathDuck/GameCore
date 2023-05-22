package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.minefight.gamecore.utils.ChatUtils;
import net.minefight.gamecore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


@CommandAlias("item")
@CommandPermission("minefight.command.item")
public class ItemCommand extends BaseCommand {

    @Default
    public void help(CommandSender sender) {
        sender.sendMessage(ChatUtils.color("<primary>/item setname <name> <gray>- <secondary>Set the name of an item."));

        sender.sendMessage(ChatUtils.color("<primary>/item lore add <text> <gray>- <secondary>Add a lore line."));
        sender.sendMessage(ChatUtils.color("<primary>/item lore set <line> <text> <gray>- <secondary>Edit a lore line."));
        sender.sendMessage(ChatUtils.color("<primary>/item lore remove <line> <gray>- <secondary>Remove a lore line."));
        sender.sendMessage(ChatUtils.color("<primary>/item lore clear <gray>- <secondary>Remove an item's lore."));
    }

    @Subcommand("setname")
    @Syntax("<name>")
    public void setName(Player player, String name) {
        ItemStack item = getItem(player);
        if(item == null) return;
        ItemBuilder builder = new ItemBuilder(item);
        builder.setName(name);
        player.sendMessage(ChatUtils.color("<primary>Succesfully edited the name to <secondary>" + name));
    }

    @Subcommand("lore add")
    @Syntax("<text>")
    public void addLore(Player player, String text) {
        ItemStack item = getItem(player);
        if(item == null) return;
        ItemBuilder builder = new ItemBuilder(item);

        builder.addLoreLine(text);
        player.sendMessage(ChatUtils.color("<primary>Added lore line: <secondary>" + text));
    }

    @Subcommand("lore set")
    @Syntax("<line> <text>")
    public void setLore(Player player, int line, String text) {
        ItemStack item = getItem(player);
        if(item == null) return;
        if(item.lore() == null) {
            player.sendMessage(ChatUtils.color("<danger>This item doesn't have any lore. Please add one using \"/item lore add <text>\"."));
            return;
        }
        List<Component> lore = item.lore();
        if(line >= lore.size()) {
            player.sendMessage(ChatUtils.color("<danger>This item only has " + lore.size() + " line(s) of lore."));
            return;
        }
        lore.set(line, ChatUtils.color("<italic:false>" + text));
        ItemBuilder builder = new ItemBuilder(item);
        builder.setComponentLore(lore);
        player.sendMessage(ChatUtils.color("<primary>Edited line <secondary>" + line + " <primary>to: <secondary>" + text));
    }

    @Subcommand("lore remove")
    @Syntax("<line>")
    public void removeLore(Player player, int line) {
        ItemStack item = getItem(player);
        if(item == null) return;
        if(item.lore() == null) {
            player.sendMessage(ChatUtils.color("<danger>This item doesn't have any lore."));
            return;
        }
        List<Component> lore = item.lore();
        if(line >= lore.size()) {
            player.sendMessage(ChatUtils.color("<danger>This item only has " + lore.size() + " line(s) of lore."));
            return;
        }
        lore.remove(line);
        ItemBuilder builder = new ItemBuilder(item);
        builder.setComponentLore(lore);
        player.sendMessage(ChatUtils.color("<primary>Removed line <secondary>" + line + "<primary>."));
    }

    @Subcommand("lore clear")
    public void clearLore(Player player) {
        ItemStack item = getItem(player);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(new ArrayList<>());
        player.sendMessage(ChatUtils.color("<primary>Removed lore from item."));
    }



    private ItemStack getItem(Player player) {
        if(player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            player.sendMessage(ChatUtils.color("<danger>You can't edit air."));
            return null;
        }
        return player.getInventory().getItemInMainHand();
    }

}

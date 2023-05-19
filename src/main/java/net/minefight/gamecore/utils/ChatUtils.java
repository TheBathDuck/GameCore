package net.minefight.gamecore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtils {

    public static void sendMessage(Player player, String message) {
        player.sendMessage(color(message));
    }

    public static Component color(String message) {
        MiniMessage miniMessage = MiniMessage.builder()
                .editTags(tags -> {
                    tags.resolver(TagResolver.resolver("primary", Tag.styling(TextColor.fromHexString("#43FF61"))));
                    tags.resolver(TagResolver.resolver("secondary", Tag.styling(TextColor.fromHexString("#35bd62")))); /* Backup: #50C878 */
                    tags.resolver(TagResolver.resolver("danger", Tag.styling(TextColor.fromHexString("#FF0000"))));
                }).build();
        return miniMessage.deserialize(message);
    }



    public static String legacyColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }


}
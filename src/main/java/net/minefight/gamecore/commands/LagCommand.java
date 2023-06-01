package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("lag|gc")
@CommandPermission("minefight.command.lag")
@Description("Check the server stats.")
public class LagCommand extends BaseCommand {

    @Default
    @Description("Check the server stats.")
    public void lag(Player player) {

        String tpsColor = "<gray>";
        double tps = Bukkit.getTPS()[0];
        if(tps >= 18.0) {
            tpsColor = "<secondary>";
        } else if (tps >= 15.0) {
            tpsColor = "<yellow>";
        } else {
            tpsColor = "<danger>";
        }
        Runtime runtime = Runtime.getRuntime();

        player.sendMessage(ChatUtils.color("<primary>TPS: " + tpsColor + tps));
        player.sendMessage(ChatUtils.color("<primary>Maximum Memory: <secondary>" + (runtime.maxMemory() / 1024 / 1024)));
        player.sendMessage(ChatUtils.color("<primary>Allocated Memory: <secondary>" + (runtime.totalMemory() / 1024 / 1024)));
        player.sendMessage(ChatUtils.color("<primary>Free Memory: <secondary>" + (runtime.freeMemory() / 1024 / 1024)));
        player.sendMessage(ChatUtils.color(""));;

        for(World world : Bukkit.getWorlds()) {
            String environment = getEnvironmentType(world.getEnvironment());

            int tileEntities = 0;
            try {
                for (final Chunk chunk : world.getLoadedChunks()) {
                    tileEntities += chunk.getTileEntities().length;
                }
            } catch (ClassCastException ex) {
                // amazing java code 10/10 skip this shit.
            }
            player.sendMessage(ChatUtils.color("<primary>" + environment + " \"<secondary>" + world.getName() + "<primary>\": <secondary>" + world.getLoadedChunks().length + " <primary>chunks, <secondary>" + world.getEntities().size() + " <primary>entities, <secondary>" + tileEntities + " <primary>tile entities."));

        }

    }

    private String getEnvironmentType(World.Environment environment) {
        switch (environment) {
            case NETHER:
                return "Nether";
            case THE_END:
                return "The End";
            default:
                return "World";
        }
    }

}

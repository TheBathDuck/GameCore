package net.minefight.gamecore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.google.common.collect.ImmutableList;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("speed|walkspeed|flyspeed")
@CommandPermission("minefight.command.speed")
public class SpeedCommand extends BaseCommand {

    public SpeedCommand() {
        GameCore core = GameCore.getInstance();
        core.getCommandManager().getCommandCompletions().registerCompletion("speedtype", type -> {
            return ImmutableList.of("walk", "fly");
        });
    }

    @Default
    @CommandCompletion("@range:0-10")
    public void defaultSpeed(CommandSender sender, Integer s) {

        if(s > 10) s = 10;
        float speed = s.floatValue() / 10;

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("<danger>Only players can execute this, to set speed use: /speed set <walk/fly> <player> <amount>"));
            return;
        }

        Player player = (Player) sender;
        if(player.isFlying()) {
            player.setFlySpeed(speed);
            player.sendMessage(ChatUtils.color("<primary>Set your <secondary>flying <primary>speed to <secondary>" + speed * 10 + "<primary>."));
            return;
        }
        player.setWalkSpeed(speed);
        player.sendMessage(ChatUtils.color("<primary>Set your <secondary>walking <primary>speed to <secondary>" + speed * 10 + "<primary>."));
    }

    @Subcommand("set")
    @CommandCompletion("@speedtype @players @range:0-10")
    @Syntax("<speedtype> <target> <speed>")
    public void setSpeed(CommandSender sender, String speedtype, OnlinePlayer target, Integer speed) {

        if(target == null) {
            sender.sendMessage(ChatUtils.color("<dange>Target not found."));
            return;
        }
        Player targetPlayer = target.getPlayer();

        if(speed > 10) speed = 10;
        float fSpeed = speed.floatValue() / 10;

        if(speedtype.equalsIgnoreCase("fly") || speedtype.equalsIgnoreCase("flying") || speedtype.equalsIgnoreCase("f")) {
            targetPlayer.setFlySpeed(fSpeed);
            sender.sendMessage(ChatUtils.color("<primary>Set <secondary>" + targetPlayer.getName() + "'s <primary>flying speed to <secondary>" + fSpeed * 10 + "<primary>."));
            return;
        }

        targetPlayer.setWalkSpeed(fSpeed);
        sender.sendMessage(ChatUtils.color("<primary>Set <secondary>" + targetPlayer.getName() + "'s <primary>walking speed to <secondary>" + fSpeed * 10 + "<primary>."));

    }

    @Subcommand("reset")
    @CommandCompletion("@players")
    public void resetSpeed(CommandSender sender, @Optional OnlinePlayer target) {

        if(target == null) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatUtils.color("<danger>Target not found."));
                return;
            }
            Player player = (Player) sender;
            if(player.isFlying()) {
                player.setFlySpeed(0.1f);
                player.sendMessage(ChatUtils.color("<primary>Reset your <secondary>flying <primary>speed."));
                return;
            }
            player.setWalkSpeed(0.2f);
            player.sendMessage(ChatUtils.color("<primary>Reset your <secondary>walking <primary>speed."));
            return;
        }

        Player targetPlayer = target.getPlayer();
        if(targetPlayer.isFlying()) {
            targetPlayer.setFlySpeed(0.1f);
            sender.sendMessage(ChatUtils.color("<primary>Reset <secondary>" + targetPlayer.getName() + "'s <primary>flying speed."));
            return;
        }
        targetPlayer.setWalkSpeed(0.2f);
        sender.sendMessage(ChatUtils.color("<primary>Reset <secondary>" + targetPlayer.getName() + "'s <primary>walking speed."));
    }
    
}

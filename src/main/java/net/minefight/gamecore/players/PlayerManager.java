package net.minefight.gamecore.players;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final @Getter List<PlayerData> players = new ArrayList<>();

    public PlayerData getPlayerData(UUID uuid) {
        return players.stream()
                .filter(data -> data.getUuid().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public void registerPlayerData(PlayerData playerData) {
        players.add(playerData);
    }

    public boolean isCached(UUID uuid) {
        return players.stream().anyMatch(data -> data.getUuid().equals(uuid));
    }

    public void removePlayerData(UUID uuid) {
        if(!isCached(uuid)) return;
        PlayerData data = getPlayerData(uuid);
        players.remove(data);
    }

    public void removePlayerData(PlayerData data) {
        players.remove(data);
    }

}

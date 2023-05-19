package net.minefight.gamecore.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import net.minefight.gamecore.GameCore;
import net.minefight.gamecore.players.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Database {

    private final @Getter HikariDataSource hikari;
    private final ExecutorService executorService;

    public Database(String ip, int port, String database, String username, String password) {
        Logger logger = GameCore.getInstance().getLogger();
        logger.info("[Database] Connecting to database...");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.addDataSourceProperty("serverName", ip);
        hikariConfig.addDataSourceProperty("port", port);
        hikariConfig.addDataSourceProperty("databaseName", database);
        hikariConfig.addDataSourceProperty("user", username);
        hikariConfig.addDataSourceProperty("password", password);
        hikariConfig.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        hikariConfig.setMaximumPoolSize(6);
        hikariConfig.addDataSourceProperty("cachePrepStmts", true);
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariConfig.setMaxLifetime(1800000);
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setLeakDetectionThreshold(10000);
        hikari = new HikariDataSource(hikariConfig);
        logger.info("[Database] Connected to database.");

        executorService = Executors.newCachedThreadPool();

        createTable();
    }

    public void createTable() {
        try (Connection connection = hikari.getConnection()) {

            Statement playerData = connection.createStatement();
            String dataTableQuery = "CREATE TABLE IF NOT EXISTS Players (" +
                    "uuid VARCHAR(36) NOT NULL, " +
                    "gold INTEGER DEFAULT 0, " +
                    "firstJoin BIGINT NOT NULL, " +
                    "lastJoin BIGINT NOT NULL, " +
                    "mined INTEGER DEFAULT 0, " +
                    "balance DOUBLE DEFAULT 0, " +
                    "PRIMARY KEY (uuid));";
            playerData.executeUpdate(dataTableQuery);
            Bukkit.getLogger().info("-> " + dataTableQuery);
            playerData.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> isInDatabase(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try (Connection connection = hikari.getConnection()) {
                statement = connection.prepareStatement("SELECT * FROM Players WHERE uuid = ?");
                statement.setString(1, uuid.toString());
                resultSet = statement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    statement.close();
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);
    }

    public CompletableFuture<PlayerData> createPlayer(UUID uuid, int gold, long firstJoin, long lastJoin, int blocksMined, double balance) {
        return CompletableFuture.supplyAsync(() -> {
            PreparedStatement statement = null;
            try (Connection connection = hikari.getConnection()) {
                statement = connection.prepareStatement("INSERT INTO Players (uuid, gold, firstJoin, lastJoin, mined, balance) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, uuid.toString());
                statement.setInt(2, gold);
                statement.setLong(3, firstJoin);
                statement.setLong(4, lastJoin);
                statement.setInt(5, blocksMined);
                statement.setDouble(6, balance);
                statement.executeUpdate();
                return new PlayerData(uuid, gold, firstJoin, lastJoin, blocksMined, balance);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }, executorService);
    }

    public CompletableFuture<Void> updateBalance(UUID uuid, double balance) {
        return CompletableFuture.runAsync(() -> {
            PreparedStatement statement = null;
            try (Connection connection = hikari.getConnection()) {
                statement = connection.prepareStatement("UPDATE Players SET balance = ? WHERE uuid = ?");
                statement.setDouble(1, balance);
                statement.setString(2, uuid.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);
    }

    public CompletableFuture<Void> updateData(PlayerData data) {
        return CompletableFuture.runAsync(() -> {
            PreparedStatement statement = null;
            try (Connection connection = hikari.getConnection()) {

                statement = connection.prepareStatement("UPDATE Players SET gold = ?, lastJoin = ?, mined = ?, balance = ? WHERE uuid = ?");
                statement.setInt(1, data.getGold());
                statement.setLong(2, data.getLastJoin());
                statement.setInt(3, data.getMined());
                statement.setDouble(4, data.getBalance());
                statement.setString(5, data.getUuid().toString());
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, executorService);
    }

    public CompletableFuture<PlayerData> getPlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try (Connection connection = hikari.getConnection()) {
                statement = connection.prepareStatement("SELECT * from Players WHERE uuid=?");
                statement.setString(1, uuid.toString());
                resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    int gold = resultSet.getInt("gold");
                    long firstJoin = resultSet.getLong("firstJoin");
                    long lastJoin = resultSet.getLong("lastJoin");
                    int mined = resultSet.getInt("mined");
                    double balance = resultSet.getDouble("balance");
                    return new PlayerData(uuid, gold, firstJoin, lastJoin, mined, balance);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }, executorService);
    }

}

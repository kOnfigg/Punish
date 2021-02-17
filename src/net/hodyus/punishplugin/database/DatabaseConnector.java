package net.hodyus.punishplugin.database;

import net.hodyus.punishplugin.manager.CommandManager;
import net.hodyus.punishplugin.manager.ListenerManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class DatabaseConnector {

    private Connection connection;
    private ExecutorService executorService;

    public DatabaseConnector() {
        this.connection = Plugin
        createTables();
        new CommandManager();
        new ListenerManager();
        this.executorService = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    }

    public void createTables() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Punishments (" +
                    "id int auto_increment primary key," +
                    "name varchar(16)," +
                    "time long," +
                    "reason text," +
                    "type int," +
                    "constraint Punishments_uindex unique (id)," +
                    "constraint Punishments_name_uindex unique (name)" +
                    ") " +
                    "Engine=InnoDB");

            preparedStatement.execute();

            connection.prepareStatement("CREATE TABLE IF NOT EXISTS Mutes (" +
                    "player varchar(18), " +
                    "reason text, " +
                    "long time) " +
                    "Engine=InnoDB").execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setBanned(String player, String reason, long time) {
        executorService.submit(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Punishments (name, reason, time, type) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, player);
                preparedStatement.setString(2, reason);
                preparedStatement.setLong(3, time);
                preparedStatement.setInt(4, 1);

                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void setMuted(String player, String reason, long time) {
        executorService.submit(() -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Punishments (name, reason, time, type) VALUES (?, ?, ?, ?)");
                preparedStatement.setString(1, player);
                preparedStatement.setString(2, reason);
                preparedStatement.setLong(3, time);
                preparedStatement.setInt(4, 1);

                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public String getReason(String player) {
        String reason = "";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Punishments WHERE name = ?");
            preparedStatement.setString(1, player);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reason += resultSet.getInt("id") + "#";
                reason += resultSet.getString("reason");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return reason;
    }



}

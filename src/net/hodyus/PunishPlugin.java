package net.hodyus;

import net.hodyus.punishplugin.concurrent.ConcurrentMemory;
import net.hodyus.punishplugin.database.DatabaseConnector;
import org.bukkit.plugin.java.JavaPlugin;

public class PunishPlugin extends JavaPlugin {

    public DatabaseConnector databaseConnector;
    public ConcurrentMemory concurrentMemory;

    public static PunishPlugin getInstance() {
        return JavaPlugin.getPlugin(PunishPlugin.class);
    }

    public ConcurrentMemory getConcurrentMemory() {
        return concurrentMemory;
    }

    public void onEnable() {
        databaseConnector = new DatabaseConnector();
        concurrentMemory = new ConcurrentMemory();
    }

    public void onDisable() {

    }
    
}

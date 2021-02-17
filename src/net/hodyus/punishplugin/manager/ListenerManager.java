package net.hodyus.punishplugin.manager;

import net.hodyus.PunishPlugin;
import net.hodyus.punishplugin.listener.PlayerLoginListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    public ListenerManager() {
        PunishPlugin plugin = PunishPlugin.getInstance();
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerLoginListener(), plugin);
    }

}

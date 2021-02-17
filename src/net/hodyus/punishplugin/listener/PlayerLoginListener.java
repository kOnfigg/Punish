package net.hodyus.punishplugin.listener;

import net.hodyus.PunishPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class PlayerLoginListener implements Listener {

    private PunishPlugin plugin = PunishPlugin.getInstance();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.isBanned()) {

            String reason = plugin.databaseConnector.getReason(player.getName());

            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "§c§lHodyus " +
                    "\n\n§cVocê está banido permanentemente do servidor!" +
                    "\n§cMotivo: §f" + reason.split("#")[1] + "" +
                    "§c\n§cTempo Restante: §fPermanente" +
                    "\n§cUse o id §e#" + reason.split("#")[0] + " §cpara criar uma revisão em §c§lnosso discord");

        }
    }

}

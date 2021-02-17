package net.hodyus.punishplugin.command;

import net.hodyus.PunishPlugin;
import net.hodyus.libsplugin.command.CommandRegister;
import net.hodyus.punishplugin.object.Mute;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class MuteCommand extends Command {

    private PunishPlugin plugin = PunishPlugin.getInstance();

    public MuteCommand() {
        super("mute");
        new CommandRegister(this, "factions");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if (!commandSender.hasPermission("factions.mod")) return true;

        if (args.length < 3) {
            commandSender.sendMessage("§cUse: '/mute <jogador> <minutos> <motivo> - <prova>'.");
            return true;
        }

        String targetString = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetString);

        if (!isNumber(args[1])) {
            commandSender.sendMessage("§c" + args[1] + " não é um tempo válido.");
            return true;
        }

        long time = TimeUnit.MINUTES.toMillis(Integer.parseInt(args[1]));

        if (time <= 0) {
            commandSender.sendMessage("§c" + args[1] + " não é um tempo válido.");
            return true;
        }

        String reason = "";

        for (int i = 2; i < args.length; i++) {
            reason += args[i] + " ";
        }

        if (!reason.contains(" - ")) {
            commandSender.sendMessage("§cUse: '/mute <jogador> <minutos> <motivo> - <prova>'.");
            return true;
        }

        Mute mute = PunishPlugin.getInstance().getConcurrentMemory().findMute(targetString);

        if (mute != null) {
            commandSender.sendMessage("§cO jogador " + targetString + " já se encontra mutado.");
            return true;
        }

        plugin.databaseConnector.setMuted(targetString, reason, time);
        PunishPlugin.getInstance().getConcurrentMemory().createMute(targetString, reason, time);

        if (commandSender instanceof Player) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§c* " + targetString + " foi punido mutado temporáriamente por " + commandSender.getName());
            Bukkit.broadcastMessage("§c* Motivo: " + reason.split(" - ")[0]);
            Bukkit.broadcastMessage("§c* Prova: " + reason.split(" - ")[1]);
            Bukkit.broadcastMessage("§c* Duração: " + time/60000 + " minutos");
            Bukkit.broadcastMessage("§c* Duração: Permanente");
            Bukkit.broadcastMessage("");
        }

        return false;
    }

    public boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}

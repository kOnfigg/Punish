package net.hodyus.punishplugin.command;

import net.hodyus.PunishPlugin;
import net.hodyus.libsplugin.command.CommandRegister;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand extends Command {

    private PunishPlugin plugin = PunishPlugin.getInstance();

    public BanCommand() {
        super("ban");
        new CommandRegister(this, "factions");
    }

    @Override
    public boolean execute(CommandSender commandSender, String label, String[] args) {
        if (!commandSender.hasPermission("factions.mod")) return true;

        if (args.length < 3) {
            commandSender.sendMessage("§cUse: '/ban <jogador> <motivo> - <prova>'.");
            return true;
        }

        String targetString = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetString);

        if (target == null) {
            commandSender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        String reason = "";

        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }

        if (!reason.contains(" - ")) {
            commandSender.sendMessage("§cUse: '/ban <jogador> <motivo> - <prova>'.");
            return true;
        }

        plugin.databaseConnector.setBanned(targetString, reason, 0L);

        target.getPlayer().setBanned(true);

        if (target.isOnline()) {
            target.getPlayer().kickPlayer("§c§lHodyus" +
                    "\n\n§cVocê está banido permanentemente de nosso servidor!" +
                    "\n§cMotivo: §f" + reason + "" +
                    "§c\n§cTempo Restante: §fPermanente" +
                    "\n\n§7Foi banido injustamente? Faça uma revisão em nosso discord: §nhttps://discord.gg/V8Yx9Ws6xR");
        }

        if (commandSender instanceof Player) {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§c* " + targetString + " foi punido permanentemente por " + commandSender.getName());
            Bukkit.broadcastMessage("§c* Motivo: " + reason.split(" - ")[0]);
            Bukkit.broadcastMessage("§c* Prova: " + reason.split(" - ")[1]);
            Bukkit.broadcastMessage("§c* Duração: Permanente");
            Bukkit.broadcastMessage("");
        }

        return false;
    }
}

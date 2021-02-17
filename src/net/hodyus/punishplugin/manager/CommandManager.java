package net.hodyus.punishplugin.manager;

import net.hodyus.punishplugin.command.BanCommand;
import net.hodyus.punishplugin.command.MuteCommand;

public class CommandManager {

    public CommandManager() {
        new BanCommand();
        new MuteCommand();
    }

}

package net.hodyus.punishplugin.concurrent;

import com.google.common.collect.Lists;
import net.hodyus.punishplugin.object.Mute;
import org.bukkit.entity.Player;

import java.util.List;

public class ConcurrentMemory {

    private List<Mute> concurrentList;

    public ConcurrentMemory() {
        this.concurrentList = Lists.newLinkedList();
    }

    public Mute findMute(String player) {
        Mute mute = concurrentList
                .stream()
                .filter(filter -> filter.getName().equals(player))
                .findFirst().orElse(null);

        return mute;
    }

    public void createMute(String player, String reason, long time) {
        Mute mute = new Mute(player, time, reason);

        concurrentList.add(mute);
    }

}

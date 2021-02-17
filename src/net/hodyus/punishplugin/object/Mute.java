package net.hodyus.punishplugin.object;

public class Mute {

    private String name;
    private long time;
    private String reason;

    public Mute(String name, long time, String reason) {
        this.name = name;
        this.time = time;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

}

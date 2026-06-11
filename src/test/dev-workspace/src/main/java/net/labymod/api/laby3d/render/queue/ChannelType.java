package net.labymod.api.laby3d.render.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/ChannelType.class */
public final class ChannelType {
    private static final Map<String, ChannelType> CACHE = new HashMap();
    private final String name;

    private ChannelType(String name) {
        this.name = name;
    }

    public static ChannelType of(String name) {
        return CACHE.computeIfAbsent(name, ChannelType::new);
    }

    public String name() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ChannelType that = (ChannelType) obj;
        return Objects.equals(this.name, that.name);
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public String toString() {
        return "ChannelType[name=" + this.name + "]";
    }
}

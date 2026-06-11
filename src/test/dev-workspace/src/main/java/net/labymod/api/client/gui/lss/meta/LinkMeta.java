package net.labymod.api.client.gui.lss.meta;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/meta/LinkMeta.class */
public class LinkMeta implements Comparable<LinkMeta> {
    private final String path;
    private final int priority;
    private final Class<?> definer;

    public LinkMeta(String path, int priority, Class<?> definer) {
        this.path = path;
        this.priority = priority;
        this.definer = definer;
    }

    public LinkMeta(String path, Class<?> definer) {
        this(path, 0, definer);
    }

    public String getPath() {
        return this.path;
    }

    public Class<?> getDefiner() {
        return this.definer;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull LinkMeta o) {
        return Integer.compare(this.priority, o.priority);
    }
}

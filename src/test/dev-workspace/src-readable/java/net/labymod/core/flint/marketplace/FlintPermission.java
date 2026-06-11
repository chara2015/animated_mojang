package net.labymod.core.flint.marketplace;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintPermission.class */
public class FlintPermission {
    private final String key;
    private final int id = -1;
    private final boolean critical = true;

    public FlintPermission(String key) {
        this.key = key;
    }

    public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public boolean isCritical() {
        return this.critical;
    }
}

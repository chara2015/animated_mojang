package net.labymod.api.client.world;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/BossBarOverlay.class */
public enum BossBarOverlay {
    PROGRESS("progress"),
    NOTCHED_6("notched_6"),
    NOTCHED_10("notched_10"),
    NOTCHED_12("notched_12"),
    NOTCHED_20("notched_20");

    private static final BossBarOverlay[] VALUES = values();
    private final String name;

    BossBarOverlay(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static BossBarOverlay[] getValues() {
        return VALUES;
    }

    public static BossBarOverlay getByName(String name) {
        for (BossBarOverlay value : VALUES) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No boss bar color \"" + name + "\"");
    }

    public static BossBarOverlay get(String name) {
        for (BossBarOverlay value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + BossBarOverlay.class.getCanonicalName() + "." + name);
    }

    public static BossBarOverlay getOrDefault(String name, BossBarOverlay defaultValue) {
        for (BossBarOverlay value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}

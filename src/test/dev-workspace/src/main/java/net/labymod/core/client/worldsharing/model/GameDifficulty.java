package net.labymod.core.client.worldsharing.model;

import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/GameDifficulty.class */
public enum GameDifficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    private static final GameDifficulty[] VALUES = values();

    public static GameDifficulty fromId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return null;
        }
        return VALUES[id];
    }

    public int getId() {
        return ordinal();
    }

    @Override // java.lang.Enum
    public String toString() {
        return Laby.labyAPI().minecraft().getTranslation("options.difficulty." + name().toLowerCase());
    }
}

package net.labymod.core.client.worldsharing.model;

import net.labymod.api.util.I18n;
import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/worldsharing/model/GameMode.class */
public enum GameMode {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    private static final GameMode[] VALUES = values();
    private final String lowercase = StringUtil.toLowercase(name());

    GameMode() {
    }

    public static GameMode fromId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return null;
        }
        return VALUES[id];
    }

    public int getId() {
        return ordinal();
    }

    public String getName() {
        return name().toLowerCase();
    }

    @Override // java.lang.Enum
    public String toString() {
        return I18n.getTranslation("labymod.misc.gameMode." + this.lowercase, new Object[0]);
    }
}

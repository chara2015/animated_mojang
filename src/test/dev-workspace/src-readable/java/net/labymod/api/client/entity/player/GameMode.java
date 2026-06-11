package net.labymod.api.client.entity.player;

import net.labymod.api.client.component.Component;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/GameMode.class */
public enum GameMode {
    UNKNOWN(-1),
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    private static final GameMode[] VALUES = values();
    private final int id;
    private final String name;
    private final String lowercase;
    private final Component displayName;

    GameMode(int id) {
        this.id = id;
        String name = name();
        this.lowercase = StringUtil.toLowercase(name);
        this.name = CharSequences.toString(CharSequences.capitalize(this.lowercase));
        this.displayName = Component.translatable("labymod.misc.gameMode." + this.lowercase, new Component[0]);
    }

    public static GameMode fromId(int id) {
        for (GameMode gameMode : VALUES) {
            if (gameMode.getId() == id) {
                return gameMode;
            }
        }
        return UNKNOWN;
    }

    @NotNull
    public String toLowerCase() {
        return this.lowercase;
    }

    public String getName() {
        return this.name;
    }

    public Component displayName() {
        return this.displayName;
    }

    public int getId() {
        return this.id;
    }

    @Deprecated
    @NotNull
    public String lowercase() {
        return toLowerCase();
    }
}

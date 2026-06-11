package net.labymod.api.client.options;

import java.util.List;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/options/MainHand.class */
public enum MainHand {
    LEFT,
    RIGHT;

    public static final List<MainHand> VALUES = CollectionHelper.asUnmodifiableList(values());

    public MainHand opposite() {
        return this == LEFT ? RIGHT : LEFT;
    }
}

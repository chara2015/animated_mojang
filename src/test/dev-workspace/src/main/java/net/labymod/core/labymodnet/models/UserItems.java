package net.labymod.core.labymodnet.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/UserItems.class */
public class UserItems {
    private final List<Cosmetic> cosmetics = Collections.emptyList();
    private final List<Emote> emotes = new ArrayList();

    public List<Cosmetic> getCosmetics() {
        return this.cosmetics;
    }

    public List<Emote> getEmotes() {
        return this.emotes;
    }
}

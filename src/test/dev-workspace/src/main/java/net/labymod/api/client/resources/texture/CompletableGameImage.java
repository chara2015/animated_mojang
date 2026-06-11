package net.labymod.api.client.resources.texture;

import net.labymod.api.util.concurrent.AbstractCompletable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/CompletableGameImage.class */
public class CompletableGameImage extends AbstractCompletable<GameImage> {
    public CompletableGameImage(GameImage fallback) {
        super(fallback);
    }
}

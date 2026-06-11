package net.labymod.api.client.render.font;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/TextOverflowStrategy.class */
public enum TextOverflowStrategy {
    WRAP(0.0f),
    CLIP(3.0f);

    private final float lineEndOffset;

    TextOverflowStrategy(float lineEndOffset) {
        this.lineEndOffset = lineEndOffset;
    }

    public float getLineEndOffset() {
        return this.lineEndOffset;
    }
}

package net.labymod.api.client.gui.screen.state;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/UVCoordinates.class */
public final class UVCoordinates {
    public static final UVCoordinates DEFAULT = new UVCoordinates(0.0f, 1.0f, 0.0f, 1.0f);
    public static final int DEFAULT_RESOLUTION_WIDTH = 256;
    public static final int DEFAULT_RESOLUTION_HEIGHT = 256;
    private final float minU;
    private final float maxU;
    private final float minV;
    private final float maxV;

    private UVCoordinates(float minU, float maxU, float minV, float maxV) {
        this.minU = minU;
        this.maxU = maxU;
        this.minV = minV;
        this.maxV = maxV;
    }

    public static UVCoordinates of(int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
        return of(spriteX, spriteY, spriteWidth, spriteHeight, 256, 256);
    }

    public static UVCoordinates of(int spriteX, int spriteY, int spriteWidth, int spriteHeight, int resolutionWidth, int resolutionHeight) {
        return of(spriteX, spriteY, spriteWidth, spriteHeight, resolutionWidth, resolutionHeight);
    }

    public static UVCoordinates of(float spriteX, float spriteY, float spriteWidth, float spriteHeight) {
        return of(spriteX, spriteY, spriteWidth, spriteHeight, 256, 256);
    }

    public static UVCoordinates of(float spriteX, float spriteY, float spriteWidth, float spriteHeight, int resolutionWidth, int resolutionHeight) {
        return of(spriteX, spriteY, spriteWidth, spriteHeight, resolutionWidth, resolutionHeight);
    }

    public static UVCoordinates of(float spriteX, float spriteY, float spriteWidth, float spriteHeight, float resolutionWidth, float resolutionHeight) {
        float minU = spriteX / resolutionWidth;
        float maxU = (spriteX + spriteWidth) / resolutionWidth;
        float minV = spriteY / resolutionHeight;
        float maxV = (spriteY + spriteHeight) / resolutionHeight;
        return new UVCoordinates(minU, maxU, minV, maxV);
    }

    public float getMinU() {
        return this.minU;
    }

    public float getMaxU() {
        return this.maxU;
    }

    public float getMinV() {
        return this.minV;
    }

    public float getMaxV() {
        return this.maxV;
    }
}

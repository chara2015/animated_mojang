package net.labymod.core.main.user.shop.item.geometry;

import java.util.Objects;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/DepthMap.class */
public class DepthMap {
    private final GameImage image;
    private final int width;
    private final int height;

    public DepthMap(GameImage image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public boolean shouldRenderFace(Rectangle bounds, int x, int y, int face) {
        return hasDepthAt(bounds, x, y) && !hasDepthInFacing(bounds, x, y, face);
    }

    public boolean hasDepthInFacing(Rectangle bounds, int x, int y, int face) {
        switch (face) {
            case 0:
                return hasDepthAt(bounds, x + 1, y);
            case 1:
                return hasDepthAt(bounds, x - 1, y);
            case 2:
                return hasDepthAt(bounds, x, y - 1);
            case 3:
                return hasDepthAt(bounds, x, y + 1);
            default:
                return false;
        }
    }

    public boolean hasDepthAt(Rectangle bounds, int x, int y) {
        return ((float) x) >= bounds.getX() && ((float) y) >= bounds.getY() && ((float) x) < bounds.getX() + bounds.getWidth() && ((float) y) < bounds.getY() + bounds.getHeight() && hasDepth(this.image.getARGB(x, y));
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private boolean hasDepth(int color) {
        return ColorFormat.ARGB32.alpha(color) > 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepthMap depthMap = (DepthMap) o;
        if (this.width != depthMap.width || this.height != depthMap.height) {
            return false;
        }
        return Objects.equals(this.image, depthMap.image);
    }

    public int hashCode() {
        int result = this.image != null ? this.image.hashCode() : 0;
        return (31 * ((31 * result) + this.width)) + this.height;
    }
}

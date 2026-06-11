package net.labymod.core.client.world.rplace.art;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.service.Identifiable;
import net.labymod.core.client.world.rplace.RPlaceRegistry;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/art/PixelArt.class */
public class PixelArt implements Identifiable {
    private static final RPlaceRegistry PLACE_REGISTRY = LabyMod.references().rPlaceRegistry();
    private final GameImage gameImage;
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final int z;
    private final Map<Integer, ColoredBlock> offset2block = new HashMap();
    private ResourceLocation resourceLocation;
    private final boolean labyConnect;
    private boolean disposed;

    public PixelArt(InputStream in, int x, int y, int z, int size, boolean labyConnect) throws IOException {
        int width;
        int height;
        GameImage gameImage = Laby.references().gameImageProvider().getImage(in);
        if (gameImage == null) {
            throw new IOException("Failed to load image");
        }
        if (size > 0) {
            double aspectRatio = ((double) gameImage.getWidth()) / ((double) gameImage.getHeight());
            if (aspectRatio > 1.0d) {
                width = size;
                height = (int) (((double) size) / aspectRatio);
            } else {
                width = (int) (((double) size) * aspectRatio);
                height = size;
            }
            this.gameImage = gameImage.scale(width, height);
            gameImage.close();
        } else {
            this.gameImage = gameImage;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.labyConnect = labyConnect;
        this.width = this.gameImage.getWidth();
        this.height = this.gameImage.getHeight();
    }

    public void load() {
        Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            Resources resources = Laby.labyAPI().renderPipeline().resources();
            ResourceLocation resourceLocation = resources.resourceLocationFactory().create("labymod", "pixelart/" + String.valueOf(UUID.randomUUID()) + ".png");
            SimpleTexture texture = SimpleTexture.simple(resourceLocation, this.gameImage);
            texture.upload();
            texture.bindTo();
            this.resourceLocation = resourceLocation;
        });
    }

    public void dispose() {
        if (this.resourceLocation != null) {
            this.disposed = true;
            Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(this.resourceLocation);
            this.gameImage.close();
            this.resourceLocation = null;
        }
    }

    public ColoredBlock getBlockAt(int x, int y) {
        if (x < 0 || x >= this.width || y < 0 || y >= this.height || this.disposed) {
            return null;
        }
        int offset = pack(x, y);
        return this.offset2block.computeIfAbsent(Integer.valueOf(offset), integer -> {
            int argb = this.gameImage.getARGB(x, y);
            return PLACE_REGISTRY.getNearestBlock(argb);
        });
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getCenterX() {
        return this.x;
    }

    public int getCenterZ() {
        return this.z;
    }

    public int getFromX() {
        return this.x + (this.width / 2);
    }

    public int getFromZ() {
        return this.z + (this.height / 2);
    }

    public int getToX() {
        return this.x - (this.width / 2);
    }

    public int getToZ() {
        return this.z - (this.height / 2);
    }

    public int getMinX() {
        return Math.min(getFromX(), getToX());
    }

    public int getMaxX() {
        return Math.max(getFromX(), getToX());
    }

    public int getMinZ() {
        return Math.min(getFromZ(), getToZ());
    }

    public int getMaxZ() {
        return Math.max(getFromZ(), getToZ());
    }

    public int getY() {
        return this.y;
    }

    public boolean isLabyConnect() {
        return this.labyConnect;
    }

    public boolean intersects(PixelArt art) {
        return getMinX() <= art.getMaxX() && getMaxX() >= art.getMinX() && getMinZ() <= art.getMaxZ() && getMaxZ() >= art.getMinZ();
    }

    public static int pack(int x, int y) {
        return (x & 65535) | ((y & 65535) << 16);
    }

    public static int unpackX(int packed) {
        return packed & 65535;
    }

    public static int unpackY(int packed) {
        return (packed >> 16) & 65535;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return String.valueOf(pack(this.x, this.y));
    }
}

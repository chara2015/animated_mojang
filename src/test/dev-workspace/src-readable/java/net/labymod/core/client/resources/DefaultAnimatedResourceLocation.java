package net.labymod.core.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultAnimatedResourceLocation.class */
public class DefaultAnimatedResourceLocation implements AnimatedResourceLocation {
    protected static final Logging LOGGER = DefaultLoggingFactory.createLogger("Animated Texture");
    protected final List<AnimatedResourceLocation.AnimatedFrameResourceLocation> animatedResourceLocations = new ArrayList();
    private final Resources resources = Laby.references().resources();
    private final long delay;

    @Nullable
    private final Runnable completableListener;
    private GameImage spriteImage;
    private boolean prepared;
    private int frameCount;
    private int currentFrame;

    public DefaultAnimatedResourceLocation(String namespace, String locationPath, InputStream spriteImageStream, int ratioWidth, int ratioHeight, long delay, @Nullable Runnable completableListener) {
        this.delay = MathHelper.clamp(delay, 1L, 5000L);
        this.completableListener = completableListener;
        ReferenceStorage references = Laby.references();
        references.asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            GameImage image;
            try {
                image = references.gameImageProvider().getImage(spriteImageStream);
            } catch (IOException exception) {
                image = null;
                LOGGER.error("The animated texture cannot be created because the image could not be read", exception);
            }
            this.spriteImage = image;
        }, () -> {
            prepareResourceLocations(namespace, locationPath, ratioWidth, ratioHeight);
        });
    }

    public DefaultAnimatedResourceLocation(ResourceLocation[] locations, long delay, @Nullable Runnable completableListener) {
        this.delay = MathHelper.clamp(delay, 1L, 5000L);
        this.completableListener = completableListener;
        uploadTextures(locations);
    }

    private void prepareResourceLocations(String namespace, String locationPath, int ratioWidth, int ratioHeight) {
        if (this.spriteImage == null) {
            return;
        }
        int frameWidth = this.spriteImage.getWidth();
        int frameHeight = (int) (((double) frameWidth) * (((double) ratioHeight) / ((double) ratioWidth)));
        this.frameCount = this.spriteImage.getHeight() / frameHeight;
        this.currentFrame = 0;
        List<SimpleTexture> textures = new ArrayList<>();
        Laby.references().asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            for (int frame = 0; frame < this.frameCount; frame++) {
                ResourceLocation animatedResourceLocation = this.resources.resourceLocationFactory().create(namespace, locationPath + "_" + frame + ".png");
                GameImage sprite = this.spriteImage.getSubImage(0, frame * frameHeight, frameWidth, frameHeight);
                textures.add(SimpleTexture.simple(animatedResourceLocation, sprite));
            }
        }, () -> {
            Iterator i$ = textures.iterator();
            while (i$.hasNext()) {
                SimpleTexture texture = (SimpleTexture) i$.next();
                texture.upload();
                texture.bindTo();
                this.animatedResourceLocations.add(new DefaultAnimatedFrameResourceLocation(texture.getTextureLocation()));
            }
            textures.clear();
            setPrepared();
        });
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation
    public AnimatedResourceLocation.AnimatedFrameResourceLocation getDefault() {
        if (this.animatedResourceLocations.isEmpty()) {
            return null;
        }
        return (AnimatedResourceLocation.AnimatedFrameResourceLocation) this.animatedResourceLocations.getFirst();
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation
    public AnimatedResourceLocation.AnimatedFrameResourceLocation getCurrentResourceLocation() {
        return getResourceLocationAt(this.currentFrame);
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation
    public AnimatedResourceLocation.AnimatedFrameResourceLocation getResourceLocationAt(int position) {
        if (this.animatedResourceLocations.size() - 1 < position) {
            return null;
        }
        return this.animatedResourceLocations.get(position);
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation
    public void update(long timestamp) {
        if (!this.prepared) {
            return;
        }
        long ticks = timestamp / this.delay;
        int frame = (int) (ticks % ((long) this.frameCount));
        this.currentFrame = (frame < 0 || frame > this.frameCount) ? 0 : frame;
    }

    @Override // net.labymod.api.client.resources.AnimatedResourceLocation
    public List<AnimatedResourceLocation.AnimatedFrameResourceLocation> getAnimatedResourceLocations() {
        return this.animatedResourceLocations;
    }

    private void setPrepared() {
        this.prepared = true;
        if (this.completableListener != null) {
            this.completableListener.run();
        }
        if (this.spriteImage != null) {
            this.spriteImage.close();
        }
        this.spriteImage = null;
    }

    private void uploadTextures(ResourceLocation[] locations) {
        List<SimpleTexture> textures = new ArrayList<>();
        ReferenceStorage references = Laby.references();
        references.asynchronousTextureUploader().prepareAndUploadTexture(() -> {
            for (ResourceLocation location : locations) {
                textures.add(SimpleTexture.simple(location));
            }
        }, () -> {
            Iterator i$ = textures.iterator();
            while (i$.hasNext()) {
                SimpleTexture texture = (SimpleTexture) i$.next();
                texture.upload();
                texture.bindTo();
                this.animatedResourceLocations.add(new DefaultAnimatedFrameResourceLocation(texture.getTextureLocation()));
            }
            textures.clear();
            this.frameCount = locations.length;
            setPrepared();
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultAnimatedResourceLocation$DefaultAnimatedFrameResourceLocation.class */
    private static class DefaultAnimatedFrameResourceLocation implements AnimatedResourceLocation.AnimatedFrameResourceLocation {
        private final ResourceLocation delegate;

        public DefaultAnimatedFrameResourceLocation(ResourceLocation delegate) {
            this.delegate = delegate;
        }

        @Override // net.labymod.api.client.resources.ResourceLocation
        public <T> T getMinecraftLocation() {
            return (T) this.delegate.getMinecraftLocation();
        }

        @Override // net.labymod.api.client.resources.ResourceLocation
        public String getNamespace() {
            return this.delegate.getNamespace();
        }

        @Override // net.labymod.api.client.resources.ResourceLocation
        public String getPath() {
            return this.delegate.getPath();
        }

        @Override // net.labymod.api.client.resources.ResourceLocation
        public InputStream openStream() throws IOException {
            return this.delegate.openStream();
        }

        @Override // net.labymod.api.client.resources.ResourceLocation
        public boolean exists() {
            return this.delegate.exists();
        }

        @Override // net.labymod.api.metadata.MetadataExtension
        public void metadata(Metadata metadata) {
            this.delegate.metadata(metadata);
        }

        @Override // net.labymod.api.metadata.MetadataExtension
        public Metadata metadata() {
            return this.delegate.metadata();
        }
    }
}

package net.minecraft.client.renderer.texture.atlas.sources;

import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/sources/LazyLoadedImage.class */
public class LazyLoadedImage {
    private final Identifier id;
    private final Resource resource;
    private final AtomicReference<NativeImage> image = new AtomicReference<>();
    private final AtomicInteger referenceCount;

    public LazyLoadedImage(Identifier $$0, Resource $$1, int $$2) {
        this.id = $$0;
        this.resource = $$1;
        this.referenceCount = new AtomicInteger($$2);
    }

    public NativeImage get() throws IOException {
        NativeImage $$0 = this.image.get();
        if ($$0 == null) {
            synchronized (this) {
                $$0 = this.image.get();
                if ($$0 == null) {
                    try {
                        InputStream $$1 = this.resource.open();
                        try {
                            $$0 = NativeImage.read($$1);
                            this.image.set($$0);
                            if ($$1 != null) {
                                $$1.close();
                            }
                        } catch (Throwable th) {
                            if ($$1 != null) {
                                try {
                                    $$1.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException $$2) {
                        throw new IOException("Failed to load image " + String.valueOf(this.id), $$2);
                    }
                }
            }
        }
        return $$0;
    }

    public void release() {
        NativeImage $$1;
        int $$0 = this.referenceCount.decrementAndGet();
        if ($$0 <= 0 && ($$1 = this.image.getAndSet(null)) != null) {
            $$1.close();
        }
    }
}

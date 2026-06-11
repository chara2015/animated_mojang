package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.gui.screens.AddRealmPopupScreen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/TextureManager.class */
public class TextureManager implements PreparableReloadListener, AutoCloseable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Identifier INTENTIONAL_MISSING_TEXTURE = Identifier.withDefaultNamespace("");
    private final Map<Identifier, AbstractTexture> byPath = new HashMap();
    private final Set<TickableTexture> tickableTextures = new HashSet();
    private final ResourceManager resourceManager;

    public TextureManager(ResourceManager $$0) {
        this.resourceManager = $$0;
        NativeImage $$1 = MissingTextureAtlasSprite.generateMissingImage();
        register(MissingTextureAtlasSprite.getLocation(), new DynamicTexture(() -> {
            return "(intentionally-)Missing Texture";
        }, $$1));
    }

    public void registerAndLoad(Identifier $$0, ReloadableTexture $$1) {
        try {
            $$1.apply(loadContentsSafe($$0, $$1));
            register($$0, $$1);
        } catch (Throwable $$2) {
            CrashReport $$3 = CrashReport.forThrowable($$2, "Uploading texture");
            CrashReportCategory $$4 = $$3.addCategory("Uploaded texture");
            $$4.setDetail("Resource location", $$1.resourceId());
            $$4.setDetail("Texture id", $$0);
            throw new ReportedException($$3);
        }
    }

    private TextureContents loadContentsSafe(Identifier $$0, ReloadableTexture $$1) {
        try {
            return loadContents(this.resourceManager, $$0, $$1);
        } catch (Exception $$2) {
            LOGGER.error("Failed to load texture {} into slot {}", new Object[]{$$1.resourceId(), $$0, $$2});
            return TextureContents.createMissing();
        }
    }

    public void registerForNextReload(Identifier $$0) {
        register($$0, new SimpleTexture($$0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void register(Identifier $$0, AbstractTexture abstractTexture) {
        AbstractTexture $$2 = this.byPath.put($$0, abstractTexture);
        if ($$2 != abstractTexture) {
            if ($$2 != null) {
                safeClose($$0, $$2);
            }
            if (abstractTexture instanceof TickableTexture) {
                TickableTexture $$3 = (TickableTexture) abstractTexture;
                this.tickableTextures.add($$3);
            }
        }
    }

    private void safeClose(Identifier $$0, AbstractTexture $$1) {
        this.tickableTextures.remove($$1);
        try {
            $$1.close();
        } catch (Exception $$2) {
            LOGGER.warn("Failed to close texture {}", $$0, $$2);
        }
    }

    public AbstractTexture getTexture(Identifier $$0) {
        AbstractTexture $$1 = this.byPath.get($$0);
        if ($$1 != null) {
            return $$1;
        }
        SimpleTexture $$2 = new SimpleTexture($$0);
        registerAndLoad($$0, $$2);
        return $$2;
    }

    public void tick() {
        for (TickableTexture $$0 : this.tickableTextures) {
            $$0.tick();
        }
    }

    public void release(Identifier $$0) {
        AbstractTexture $$1 = this.byPath.remove($$0);
        if ($$1 != null) {
            safeClose($$0, $$1);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.byPath.forEach(this::safeClose);
        this.byPath.clear();
        this.tickableTextures.clear();
    }

    @Override // net.minecraft.server.packs.resources.PreparableReloadListener
    public CompletableFuture<Void> reload(PreparableReloadListener.SharedState $$0, Executor $$1, PreparableReloadListener.PreparationBarrier $$2, Executor $$3) {
        ResourceManager $$4 = $$0.resourceManager();
        List<PendingReload> $$5 = new ArrayList<>();
        this.byPath.forEach(($$32, $$42) -> {
            if ($$42 instanceof ReloadableTexture) {
                ReloadableTexture $$52 = (ReloadableTexture) $$42;
                $$5.add(scheduleLoad($$4, $$32, $$52, $$1));
            }
        });
        CompletableFuture<Void> completableFutureAllOf = CompletableFuture.allOf((CompletableFuture[]) $$5.stream().map((v0) -> {
            return v0.newContents();
        }).toArray($$02 -> {
            return new CompletableFuture[$$02];
        }));
        Objects.requireNonNull($$2);
        return completableFutureAllOf.thenCompose((v1) -> {
            return r1.wait(v1);
        }).thenAcceptAsync((Consumer<? super U>) $$12 -> {
            AddRealmPopupScreen.updateCarouselImages(this.resourceManager);
            Iterator it = $$5.iterator();
            while (it.hasNext()) {
                PendingReload $$22 = (PendingReload) it.next();
                $$22.texture.apply($$22.newContents.join());
            }
        }, $$3);
    }

    public void dumpAllSheets(Path $$0) {
        try {
            Files.createDirectories($$0, new FileAttribute[0]);
            this.byPath.forEach(($$1, abstractTexture) -> {
                if (abstractTexture instanceof Dumpable) {
                    Dumpable $$3 = (Dumpable) abstractTexture;
                    try {
                        $$3.dumpContents($$1, $$0);
                    } catch (Exception $$4) {
                        LOGGER.error("Failed to dump texture {}", $$1, $$4);
                    }
                }
            });
        } catch (IOException $$12) {
            LOGGER.error("Failed to create directory {}", $$0, $$12);
        }
    }

    private static TextureContents loadContents(ResourceManager $$0, Identifier $$1, ReloadableTexture $$2) throws IOException {
        try {
            return $$2.loadContents($$0);
        } catch (FileNotFoundException e) {
            if ($$1 != INTENTIONAL_MISSING_TEXTURE) {
                LOGGER.warn("Missing resource {} referenced from {}", $$2.resourceId(), $$1);
            }
            return TextureContents.createMissing();
        }
    }

    private static PendingReload scheduleLoad(ResourceManager $$0, Identifier $$1, ReloadableTexture $$2, Executor $$3) {
        return new PendingReload($$2, CompletableFuture.supplyAsync(() -> {
            try {
                return loadContents($$0, $$1, $$2);
            } catch (IOException $$32) {
                throw new UncheckedIOException($$32);
            }
        }, $$3));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/TextureManager$PendingReload.class */
    static final class PendingReload extends Record {
        private final ReloadableTexture texture;
        private final CompletableFuture<TextureContents> newContents;

        PendingReload(ReloadableTexture $$0, CompletableFuture<TextureContents> $$1) {
            this.texture = $$0;
            this.newContents = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PendingReload.class), PendingReload.class, "texture;newContents", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->texture:Lnet/minecraft/client/renderer/texture/ReloadableTexture;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->newContents:Ljava/util/concurrent/CompletableFuture;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PendingReload.class), PendingReload.class, "texture;newContents", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->texture:Lnet/minecraft/client/renderer/texture/ReloadableTexture;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->newContents:Ljava/util/concurrent/CompletableFuture;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PendingReload.class, Object.class), PendingReload.class, "texture;newContents", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->texture:Lnet/minecraft/client/renderer/texture/ReloadableTexture;", "FIELD:Lnet/minecraft/client/renderer/texture/TextureManager$PendingReload;->newContents:Ljava/util/concurrent/CompletableFuture;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ReloadableTexture texture() {
            return this.texture;
        }

        public CompletableFuture<TextureContents> newContents() {
            return this.newContents;
        }
    }
}

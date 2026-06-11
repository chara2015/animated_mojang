package net.labymod.core.main.user.shop.cosmetic.texture;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.TextureRepository;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler.class */
public class TextureUploadThrottler {
    private static final int MAX_UPLOADS_PER_FRAME = 2;
    private final Queue<PendingUpload> pendingUploads = new ConcurrentLinkedQueue();

    public void queueUpload(TextureDetails details, TextureRepository textureRepository, Consumer<CompletableResourceLocation> callback) {
        this.pendingUploads.add(new PendingUpload(details, textureRepository, callback));
    }

    public void processFrame() {
        PendingUpload pending;
        for (int processed = 0; processed < 2 && (pending = this.pendingUploads.poll()) != null; processed++) {
            CompletableResourceLocation result = pending.textureRepository.getOrRegisterTexture(pending.details);
            pending.callback.accept(result);
        }
    }

    public boolean hasPending() {
        return !this.pendingUploads.isEmpty();
    }

    public int pendingCount() {
        return this.pendingUploads.size();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload.class */
    private static final class PendingUpload extends Record {
        private final TextureDetails details;
        private final TextureRepository textureRepository;
        private final Consumer<CompletableResourceLocation> callback;

        private PendingUpload(TextureDetails details, TextureRepository textureRepository, Consumer<CompletableResourceLocation> callback) {
            this.details = details;
            this.textureRepository = textureRepository;
            this.callback = callback;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PendingUpload.class), PendingUpload.class, "details;textureRepository;callback", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->details:Lnet/labymod/api/client/resources/texture/TextureDetails;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->textureRepository:Lnet/labymod/api/client/resources/texture/TextureRepository;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->callback:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PendingUpload.class), PendingUpload.class, "details;textureRepository;callback", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->details:Lnet/labymod/api/client/resources/texture/TextureDetails;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->textureRepository:Lnet/labymod/api/client/resources/texture/TextureRepository;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->callback:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PendingUpload.class, Object.class), PendingUpload.class, "details;textureRepository;callback", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->details:Lnet/labymod/api/client/resources/texture/TextureDetails;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->textureRepository:Lnet/labymod/api/client/resources/texture/TextureRepository;", "FIELD:Lnet/labymod/core/main/user/shop/cosmetic/texture/TextureUploadThrottler$PendingUpload;->callback:Ljava/util/function/Consumer;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public TextureDetails details() {
            return this.details;
        }

        public TextureRepository textureRepository() {
            return this.textureRepository;
        }

        public Consumer<CompletableResourceLocation> callback() {
            return this.callback;
        }
    }
}

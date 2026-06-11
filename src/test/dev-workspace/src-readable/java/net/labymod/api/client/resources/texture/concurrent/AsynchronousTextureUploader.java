package net.labymod.api.client.resources.texture.concurrent;

import java.util.concurrent.CompletableFuture;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/concurrent/AsynchronousTextureUploader.class */
@Referenceable
public interface AsynchronousTextureUploader {
    CompletableFuture<Void> prepareAndUploadTexture(Runnable runnable, AsynchronousTextureTask asynchronousTextureTask);
}

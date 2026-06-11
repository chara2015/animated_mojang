package net.labymod.core.client.resources.texture.concurrent;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureTask;
import net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureUploader;
import net.labymod.api.models.Implements;
import net.labymod.api.util.io.LabyExecutors;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/concurrent/DefaultAsynchronousTextureUploader.class */
@Singleton
@Implements(AsynchronousTextureUploader.class)
public class DefaultAsynchronousTextureUploader implements AsynchronousTextureUploader, FrameContext {
    private final ExecutorService singleThreadExecutor = LabyExecutors.newSingleThreadExecutor("Asynchronous-Texture-Uploader-%d");
    private final Queue<AsynchronousTextureTask> taskQueue = new ArrayDeque();

    @Inject
    public DefaultAsynchronousTextureUploader() {
        Laby.references().frameContextRegistry().register(this);
    }

    @Override // net.labymod.api.client.resources.texture.concurrent.AsynchronousTextureUploader
    public CompletableFuture<Void> prepareAndUploadTexture(Runnable prepareTextureTask, AsynchronousTextureTask uploadTask) {
        return CompletableFuture.runAsync(prepareTextureTask, this.singleThreadExecutor).thenRun(() -> {
            queueTask(uploadTask);
        });
    }

    private void queueTask(AsynchronousTextureTask task) {
        this.taskQueue.add(task);
    }

    @Override // net.labymod.api.client.gfx.pipeline.context.FrameContext
    public void beginFrame() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.context.FrameContext
    public void endFrame() {
        AsynchronousTextureTask task;
        if (!this.taskQueue.isEmpty() && (task = this.taskQueue.poll()) != null) {
            task.run();
        }
    }
}

package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundBufferLibrary.class */
public class SoundBufferLibrary {
    private final ResourceProvider resourceManager;
    private final Map<Identifier, CompletableFuture<SoundBuffer>> cache = Maps.newHashMap();

    public SoundBufferLibrary(ResourceProvider $$0) {
        this.resourceManager = $$0;
    }

    public CompletableFuture<SoundBuffer> getCompleteBuffer(Identifier $$0) {
        return this.cache.computeIfAbsent($$0, $$02 -> {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    InputStream $$1 = this.resourceManager.open($$02);
                    try {
                        FiniteAudioStream $$2 = new JOrbisAudioStream($$1);
                        try {
                            ByteBuffer $$3 = $$2.readAll();
                            SoundBuffer soundBuffer = new SoundBuffer($$3, $$2.getFormat());
                            $$2.close();
                            if ($$1 != null) {
                                $$1.close();
                            }
                            return soundBuffer;
                        } catch (Throwable th) {
                            try {
                                $$2.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } finally {
                    }
                } catch (IOException $$4) {
                    throw new CompletionException($$4);
                }
            }, Util.nonCriticalIoPool());
        });
    }

    public CompletableFuture<AudioStream> getStream(Identifier $$0, boolean $$1) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                InputStream $$2 = this.resourceManager.open($$0);
                return $$1 ? new LoopingAudioStream(JOrbisAudioStream::new, $$2) : new JOrbisAudioStream($$2);
            } catch (IOException $$3) {
                throw new CompletionException($$3);
            }
        }, Util.nonCriticalIoPool());
    }

    public void clear() {
        this.cache.values().forEach($$0 -> {
            $$0.thenAccept((v0) -> {
                v0.discardAlBuffer();
            });
        });
        this.cache.clear();
    }

    public CompletableFuture<?> preload(Collection<Sound> $$0) {
        return CompletableFuture.allOf((CompletableFuture[]) $$0.stream().map($$02 -> {
            return getCompleteBuffer($$02.getPath());
        }).toArray($$03 -> {
            return new CompletableFuture[$$03];
        }));
    }
}

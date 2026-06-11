package net.minecraft.server.level.progress;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/progress/LevelLoadListener.class */
public interface LevelLoadListener {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/progress/LevelLoadListener$Stage.class */
    public enum Stage {
        START_SERVER,
        PREPARE_GLOBAL_SPAWN,
        LOAD_INITIAL_CHUNKS,
        LOAD_PLAYER_CHUNKS
    }

    void start(Stage stage, int i);

    void update(Stage stage, int i, int i2);

    void finish(Stage stage);

    void updateFocus(ResourceKey<Level> resourceKey, ChunkPos chunkPos);

    static LevelLoadListener compose(LevelLoadListener $$0, final LevelLoadListener $$1) {
        return new LevelLoadListener() { // from class: net.minecraft.server.level.progress.LevelLoadListener.1
            @Override // net.minecraft.server.level.progress.LevelLoadListener
            public void start(Stage $$02, int $$12) {
                LevelLoadListener.this.start($$02, $$12);
                $$1.start($$02, $$12);
            }

            @Override // net.minecraft.server.level.progress.LevelLoadListener
            public void update(Stage $$02, int $$12, int $$2) {
                LevelLoadListener.this.update($$02, $$12, $$2);
                $$1.update($$02, $$12, $$2);
            }

            @Override // net.minecraft.server.level.progress.LevelLoadListener
            public void finish(Stage $$02) {
                LevelLoadListener.this.finish($$02);
                $$1.finish($$02);
            }

            @Override // net.minecraft.server.level.progress.LevelLoadListener
            public void updateFocus(ResourceKey<Level> $$02, ChunkPos $$12) {
                LevelLoadListener.this.updateFocus($$02, $$12);
                $$1.updateFocus($$02, $$12);
            }
        };
    }
}

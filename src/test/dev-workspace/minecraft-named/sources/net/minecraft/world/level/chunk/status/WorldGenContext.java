package net.minecraft.world.level.chunk.status;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.concurrent.Executor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/status/WorldGenContext.class */
public final class WorldGenContext extends Record {
    private final ServerLevel level;
    private final ChunkGenerator generator;
    private final StructureTemplateManager structureManager;
    private final ThreadedLevelLightEngine lightEngine;
    private final Executor mainThreadExecutor;
    private final LevelChunk.UnsavedListener unsavedListener;

    public WorldGenContext(ServerLevel $$0, ChunkGenerator $$1, StructureTemplateManager $$2, ThreadedLevelLightEngine $$3, Executor $$4, LevelChunk.UnsavedListener $$5) {
        this.level = $$0;
        this.generator = $$1;
        this.structureManager = $$2;
        this.lightEngine = $$3;
        this.mainThreadExecutor = $$4;
        this.unsavedListener = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WorldGenContext.class), WorldGenContext.class, "level;generator;structureManager;lightEngine;mainThreadExecutor;unsavedListener", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->level:Lnet/minecraft/server/level/ServerLevel;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->generator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->structureManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->lightEngine:Lnet/minecraft/server/level/ThreadedLevelLightEngine;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->mainThreadExecutor:Ljava/util/concurrent/Executor;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->unsavedListener:Lnet/minecraft/world/level/chunk/LevelChunk$UnsavedListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WorldGenContext.class), WorldGenContext.class, "level;generator;structureManager;lightEngine;mainThreadExecutor;unsavedListener", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->level:Lnet/minecraft/server/level/ServerLevel;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->generator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->structureManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->lightEngine:Lnet/minecraft/server/level/ThreadedLevelLightEngine;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->mainThreadExecutor:Ljava/util/concurrent/Executor;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->unsavedListener:Lnet/minecraft/world/level/chunk/LevelChunk$UnsavedListener;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WorldGenContext.class, Object.class), WorldGenContext.class, "level;generator;structureManager;lightEngine;mainThreadExecutor;unsavedListener", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->level:Lnet/minecraft/server/level/ServerLevel;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->generator:Lnet/minecraft/world/level/chunk/ChunkGenerator;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->structureManager:Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->lightEngine:Lnet/minecraft/server/level/ThreadedLevelLightEngine;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->mainThreadExecutor:Ljava/util/concurrent/Executor;", "FIELD:Lnet/minecraft/world/level/chunk/status/WorldGenContext;->unsavedListener:Lnet/minecraft/world/level/chunk/LevelChunk$UnsavedListener;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ServerLevel level() {
        return this.level;
    }

    public ChunkGenerator generator() {
        return this.generator;
    }

    public StructureTemplateManager structureManager() {
        return this.structureManager;
    }

    public ThreadedLevelLightEngine lightEngine() {
        return this.lightEngine;
    }

    public Executor mainThreadExecutor() {
        return this.mainThreadExecutor;
    }

    public LevelChunk.UnsavedListener unsavedListener() {
        return this.unsavedListener;
    }
}

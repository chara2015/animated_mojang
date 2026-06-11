package net.minecraft.world.level.chunk;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.world.level.chunk.Palette;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/Configuration.class */
public interface Configuration {
    boolean alwaysRepack();

    int bitsInMemory();

    int bitsInStorage();

    <T> Palette<T> createPalette(Strategy<T> strategy, List<T> list);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/Configuration$Simple.class */
    public static final class Simple extends Record implements Configuration {
        private final Palette.Factory factory;
        private final int bits;

        public Simple(Palette.Factory $$0, int $$1) {
            this.factory = $$0;
            this.bits = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Simple.class), Simple.class, "factory;bits", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->factory:Lnet/minecraft/world/level/chunk/Palette$Factory;", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->bits:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Simple.class), Simple.class, "factory;bits", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->factory:Lnet/minecraft/world/level/chunk/Palette$Factory;", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->bits:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Simple.class, Object.class), Simple.class, "factory;bits", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->factory:Lnet/minecraft/world/level/chunk/Palette$Factory;", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Simple;->bits:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Palette.Factory factory() {
            return this.factory;
        }

        public int bits() {
            return this.bits;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public boolean alwaysRepack() {
            return false;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public <T> Palette<T> createPalette(Strategy<T> $$0, List<T> $$1) {
            return this.factory.create(this.bits, $$1);
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public int bitsInMemory() {
            return this.bits;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public int bitsInStorage() {
            return this.bits;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/Configuration$Global.class */
    public static final class Global extends Record implements Configuration {
        private final int bitsInMemory;
        private final int bitsInStorage;

        public Global(int $$0, int $$1) {
            this.bitsInMemory = $$0;
            this.bitsInStorage = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Global.class), Global.class, "bitsInMemory;bitsInStorage", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInMemory:I", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInStorage:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Global.class), Global.class, "bitsInMemory;bitsInStorage", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInMemory:I", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInStorage:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Global.class, Object.class), Global.class, "bitsInMemory;bitsInStorage", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInMemory:I", "FIELD:Lnet/minecraft/world/level/chunk/Configuration$Global;->bitsInStorage:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public int bitsInMemory() {
            return this.bitsInMemory;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public int bitsInStorage() {
            return this.bitsInStorage;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public boolean alwaysRepack() {
            return true;
        }

        @Override // net.minecraft.world.level.chunk.Configuration
        public <T> Palette<T> createPalette(Strategy<T> $$0, List<T> $$1) {
            return $$0.globalPalette();
        }
    }
}

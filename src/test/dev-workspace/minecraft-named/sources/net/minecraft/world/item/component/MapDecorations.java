package net.minecraft.world.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/MapDecorations.class */
public final class MapDecorations extends Record {
    private final Map<String, Entry> decorations;
    public static final MapDecorations EMPTY = new MapDecorations(Map.of());
    public static final Codec<MapDecorations> CODEC = Codec.unboundedMap(Codec.STRING, Entry.CODEC).xmap(MapDecorations::new, (v0) -> {
        return v0.decorations();
    });

    public MapDecorations(Map<String, Entry> $$0) {
        this.decorations = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MapDecorations.class), MapDecorations.class, "decorations", "FIELD:Lnet/minecraft/world/item/component/MapDecorations;->decorations:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MapDecorations.class), MapDecorations.class, "decorations", "FIELD:Lnet/minecraft/world/item/component/MapDecorations;->decorations:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MapDecorations.class, Object.class), MapDecorations.class, "decorations", "FIELD:Lnet/minecraft/world/item/component/MapDecorations;->decorations:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<String, Entry> decorations() {
        return this.decorations;
    }

    public MapDecorations withDecoration(String $$0, Entry $$1) {
        return new MapDecorations(Util.copyAndPut(this.decorations, $$0, $$1));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/MapDecorations$Entry.class */
    public static final class Entry extends Record {
        private final Holder<MapDecorationType> type;
        private final double x;
        private final double z;
        private final float rotation;
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(MapDecorationType.CODEC.fieldOf(ChunkRegionIoEvent.Fields.TYPE).forGetter((v0) -> {
                return v0.type();
            }), Codec.DOUBLE.fieldOf("x").forGetter((v0) -> {
                return v0.x();
            }), Codec.DOUBLE.fieldOf("z").forGetter((v0) -> {
                return v0.z();
            }), Codec.FLOAT.fieldOf("rotation").forGetter((v0) -> {
                return v0.rotation();
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new Entry(v1, v2, v3, v4);
            });
        });

        public Entry(Holder<MapDecorationType> $$0, double $$1, double $$2, float $$3) {
            this.type = $$0;
            this.x = $$1;
            this.z = $$2;
            this.rotation = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Entry.class), Entry.class, "type;x;z;rotation", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->x:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->z:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->rotation:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Entry.class), Entry.class, "type;x;z;rotation", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->x:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->z:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->rotation:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Entry.class, Object.class), Entry.class, "type;x;z;rotation", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->type:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->x:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->z:D", "FIELD:Lnet/minecraft/world/item/component/MapDecorations$Entry;->rotation:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<MapDecorationType> type() {
            return this.type;
        }

        public double x() {
            return this.x;
        }

        public double z() {
            return this.z;
        }

        public float rotation() {
            return this.rotation;
        }
    }
}

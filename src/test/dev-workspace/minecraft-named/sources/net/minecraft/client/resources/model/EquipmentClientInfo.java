package net.minecraft.client.resources.model;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.component.DyedItemColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentClientInfo.class */
public final class EquipmentClientInfo extends Record {
    private final Map<LayerType, List<Layer>> layers;
    private static final Codec<List<Layer>> LAYER_LIST_CODEC = ExtraCodecs.nonEmptyList(Layer.CODEC.listOf());
    public static final Codec<EquipmentClientInfo> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.nonEmptyMap(Codec.unboundedMap(LayerType.CODEC, LAYER_LIST_CODEC)).fieldOf("layers").forGetter((v0) -> {
            return v0.layers();
        })).apply($$0, EquipmentClientInfo::new);
    });

    public EquipmentClientInfo(Map<LayerType, List<Layer>> $$0) {
        this.layers = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EquipmentClientInfo.class), EquipmentClientInfo.class, "layers", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo;->layers:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EquipmentClientInfo.class), EquipmentClientInfo.class, "layers", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo;->layers:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EquipmentClientInfo.class, Object.class), EquipmentClientInfo.class, "layers", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo;->layers:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Map<LayerType, List<Layer>> layers() {
        return this.layers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Layer> getLayers(LayerType $$0) {
        return this.layers.getOrDefault($$0, List.of());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentClientInfo$Layer.class */
    public static final class Layer extends Record {
        private final Identifier textureId;
        private final Optional<Dyeable> dyeable;
        private final boolean usePlayerTexture;
        public static final Codec<Layer> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Identifier.CODEC.fieldOf("texture").forGetter((v0) -> {
                return v0.textureId();
            }), Dyeable.CODEC.optionalFieldOf("dyeable").forGetter((v0) -> {
                return v0.dyeable();
            }), Codec.BOOL.optionalFieldOf("use_player_texture", false).forGetter((v0) -> {
                return v0.usePlayerTexture();
            })).apply($$0, (v1, v2, v3) -> {
                return new Layer(v1, v2, v3);
            });
        });

        public Layer(Identifier $$0, Optional<Dyeable> $$1, boolean $$2) {
            this.textureId = $$0;
            this.dyeable = $$1;
            this.usePlayerTexture = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Layer.class), Layer.class, "textureId;dyeable;usePlayerTexture", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->textureId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->dyeable:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->usePlayerTexture:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Layer.class), Layer.class, "textureId;dyeable;usePlayerTexture", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->textureId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->dyeable:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->usePlayerTexture:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Layer.class, Object.class), Layer.class, "textureId;dyeable;usePlayerTexture", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->textureId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->dyeable:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Layer;->usePlayerTexture:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier textureId() {
            return this.textureId;
        }

        public Optional<Dyeable> dyeable() {
            return this.dyeable;
        }

        public boolean usePlayerTexture() {
            return this.usePlayerTexture;
        }

        public Layer(Identifier $$0) {
            this($$0, Optional.empty(), false);
        }

        public static Layer leatherDyeable(Identifier $$0, boolean $$1) {
            return new Layer($$0, $$1 ? Optional.of(new Dyeable(Optional.of(Integer.valueOf(DyedItemColor.LEATHER_COLOR)))) : Optional.empty(), false);
        }

        public static Layer onlyIfDyed(Identifier $$0, boolean $$1) {
            return new Layer($$0, $$1 ? Optional.of(new Dyeable(Optional.empty())) : Optional.empty(), false);
        }

        public Identifier getTextureLocation(LayerType $$0) {
            return this.textureId.withPath($$1 -> {
                return "textures/entity/equipment/" + $$0.getSerializedName() + "/" + $$1 + ".png";
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentClientInfo$Dyeable.class */
    public static final class Dyeable extends Record {
        private final Optional<Integer> colorWhenUndyed;
        public static final Codec<Dyeable> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.optionalFieldOf("color_when_undyed").forGetter((v0) -> {
                return v0.colorWhenUndyed();
            })).apply($$0, Dyeable::new);
        });

        public Dyeable(Optional<Integer> $$0) {
            this.colorWhenUndyed = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Dyeable.class), Dyeable.class, "colorWhenUndyed", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Dyeable;->colorWhenUndyed:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Dyeable.class), Dyeable.class, "colorWhenUndyed", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Dyeable;->colorWhenUndyed:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Dyeable.class, Object.class), Dyeable.class, "colorWhenUndyed", "FIELD:Lnet/minecraft/client/resources/model/EquipmentClientInfo$Dyeable;->colorWhenUndyed:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Optional<Integer> colorWhenUndyed() {
            return this.colorWhenUndyed;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentClientInfo$Builder.class */
    public static class Builder {
        private final Map<LayerType, List<Layer>> layersByType = new EnumMap(LayerType.class);

        Builder() {
        }

        public Builder addHumanoidLayers(Identifier $$0) {
            return addHumanoidLayers($$0, false);
        }

        public Builder addHumanoidLayers(Identifier $$0, boolean $$1) {
            addLayers(LayerType.HUMANOID_LEGGINGS, Layer.leatherDyeable($$0, $$1));
            addMainHumanoidLayer($$0, $$1);
            return this;
        }

        public Builder addMainHumanoidLayer(Identifier $$0, boolean $$1) {
            return addLayers(LayerType.HUMANOID, Layer.leatherDyeable($$0, $$1));
        }

        public Builder addLayers(LayerType $$0, Layer... $$1) {
            Collections.addAll(this.layersByType.computeIfAbsent($$0, $$02 -> {
                return new ArrayList();
            }), $$1);
            return this;
        }

        public EquipmentClientInfo build() {
            return new EquipmentClientInfo((Map) this.layersByType.entrySet().stream().collect(ImmutableMap.toImmutableMap((v0) -> {
                return v0.getKey();
            }, $$0 -> {
                return List.copyOf((Collection) $$0.getValue());
            })));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/EquipmentClientInfo$LayerType.class */
    public enum LayerType implements StringRepresentable {
        HUMANOID("humanoid"),
        HUMANOID_LEGGINGS("humanoid_leggings"),
        WINGS("wings"),
        WOLF_BODY("wolf_body"),
        HORSE_BODY("horse_body"),
        LLAMA_BODY("llama_body"),
        PIG_SADDLE("pig_saddle"),
        STRIDER_SADDLE("strider_saddle"),
        CAMEL_SADDLE("camel_saddle"),
        CAMEL_HUSK_SADDLE("camel_husk_saddle"),
        HORSE_SADDLE("horse_saddle"),
        DONKEY_SADDLE("donkey_saddle"),
        MULE_SADDLE("mule_saddle"),
        ZOMBIE_HORSE_SADDLE("zombie_horse_saddle"),
        SKELETON_HORSE_SADDLE("skeleton_horse_saddle"),
        HAPPY_GHAST_BODY("happy_ghast_body"),
        NAUTILUS_SADDLE("nautilus_saddle"),
        NAUTILUS_BODY("nautilus_body");

        public static final Codec<LayerType> CODEC = StringRepresentable.fromEnum(LayerType::values);
        private final String id;

        LayerType(String $$0) {
            this.id = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.id;
        }

        public String trimAssetPrefix() {
            return "trims/entity/" + this.id;
        }
    }
}

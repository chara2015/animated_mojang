package net.minecraft.world.entity.animal.chicken;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/chicken/ChickenVariant.class */
public final class ChickenVariant extends Record implements PriorityProvider<SpawnContext, SpawnCondition> {
    private final ModelAndTexture<ModelType> modelAndTexture;
    private final SpawnPrioritySelectors spawnConditions;
    public static final Codec<ChickenVariant> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ModelAndTexture.codec(ModelType.CODEC, ModelType.NORMAL).forGetter((v0) -> {
            return v0.modelAndTexture();
        }), SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter((v0) -> {
            return v0.spawnConditions();
        })).apply($$0, ChickenVariant::new);
    });
    public static final Codec<ChickenVariant> NETWORK_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ModelAndTexture.codec(ModelType.CODEC, ModelType.NORMAL).forGetter((v0) -> {
            return v0.modelAndTexture();
        })).apply($$0, ChickenVariant::new);
    });
    public static final Codec<Holder<ChickenVariant>> CODEC = RegistryFixedCodec.create(Registries.CHICKEN_VARIANT);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ChickenVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(Registries.CHICKEN_VARIANT);

    public ChickenVariant(ModelAndTexture<ModelType> $$0, SpawnPrioritySelectors $$1) {
        this.modelAndTexture = $$0;
        this.spawnConditions = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChickenVariant.class), ChickenVariant.class, "modelAndTexture;spawnConditions", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->modelAndTexture:Lnet/minecraft/world/entity/variant/ModelAndTexture;", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->spawnConditions:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChickenVariant.class), ChickenVariant.class, "modelAndTexture;spawnConditions", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->modelAndTexture:Lnet/minecraft/world/entity/variant/ModelAndTexture;", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->spawnConditions:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChickenVariant.class, Object.class), ChickenVariant.class, "modelAndTexture;spawnConditions", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->modelAndTexture:Lnet/minecraft/world/entity/variant/ModelAndTexture;", "FIELD:Lnet/minecraft/world/entity/animal/chicken/ChickenVariant;->spawnConditions:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ModelAndTexture<ModelType> modelAndTexture() {
        return this.modelAndTexture;
    }

    public SpawnPrioritySelectors spawnConditions() {
        return this.spawnConditions;
    }

    private ChickenVariant(ModelAndTexture<ModelType> $$0) {
        this($$0, SpawnPrioritySelectors.EMPTY);
    }

    @Override // net.minecraft.world.entity.variant.PriorityProvider
    public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/animal/chicken/ChickenVariant$ModelType.class */
    public enum ModelType implements StringRepresentable {
        NORMAL("normal"),
        COLD("cold");

        public static final Codec<ModelType> CODEC = StringRepresentable.fromEnum(ModelType::values);
        private final String name;

        ModelType(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}

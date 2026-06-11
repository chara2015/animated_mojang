package net.minecraft.world.food;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.level.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/food/FoodProperties.class */
public final class FoodProperties extends Record implements ConsumableListener {
    private final int nutrition;
    private final float saturation;
    private final boolean canAlwaysEat;
    public static final Codec<FoodProperties> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("nutrition").forGetter((v0) -> {
            return v0.nutrition();
        }), Codec.FLOAT.fieldOf("saturation").forGetter((v0) -> {
            return v0.saturation();
        }), Codec.BOOL.optionalFieldOf("can_always_eat", false).forGetter((v0) -> {
            return v0.canAlwaysEat();
        })).apply($$0, (v1, v2, v3) -> {
            return new FoodProperties(v1, v2, v3);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, FoodProperties> DIRECT_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.nutrition();
    }, ByteBufCodecs.FLOAT, (v0) -> {
        return v0.saturation();
    }, ByteBufCodecs.BOOL, (v0) -> {
        return v0.canAlwaysEat();
    }, (v1, v2, v3) -> {
        return new FoodProperties(v1, v2, v3);
    });

    public FoodProperties(int $$0, float $$1, boolean $$2) {
        this.nutrition = $$0;
        this.saturation = $$1;
        this.canAlwaysEat = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FoodProperties.class), FoodProperties.class, "nutrition;saturation;canAlwaysEat", "FIELD:Lnet/minecraft/world/food/FoodProperties;->nutrition:I", "FIELD:Lnet/minecraft/world/food/FoodProperties;->saturation:F", "FIELD:Lnet/minecraft/world/food/FoodProperties;->canAlwaysEat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FoodProperties.class), FoodProperties.class, "nutrition;saturation;canAlwaysEat", "FIELD:Lnet/minecraft/world/food/FoodProperties;->nutrition:I", "FIELD:Lnet/minecraft/world/food/FoodProperties;->saturation:F", "FIELD:Lnet/minecraft/world/food/FoodProperties;->canAlwaysEat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FoodProperties.class, Object.class), FoodProperties.class, "nutrition;saturation;canAlwaysEat", "FIELD:Lnet/minecraft/world/food/FoodProperties;->nutrition:I", "FIELD:Lnet/minecraft/world/food/FoodProperties;->saturation:F", "FIELD:Lnet/minecraft/world/food/FoodProperties;->canAlwaysEat:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int nutrition() {
        return this.nutrition;
    }

    public float saturation() {
        return this.saturation;
    }

    public boolean canAlwaysEat() {
        return this.canAlwaysEat;
    }

    @Override // net.minecraft.world.item.component.ConsumableListener
    public void onConsume(Level $$0, LivingEntity $$1, ItemStack $$2, Consumable $$3) {
        RandomSource $$4 = $$1.getRandom();
        $$0.playSound((Entity) null, $$1.getX(), $$1.getY(), $$1.getZ(), $$3.sound().value(), SoundSource.NEUTRAL, 1.0f, $$4.triangle(1.0f, 0.4f));
        if ($$1 instanceof Player) {
            Player $$5 = (Player) $$1;
            $$5.getFoodData().eat(this);
            $$0.playSound((Entity) null, $$5.getX(), $$5.getY(), $$5.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5f, Mth.randomBetween($$4, 0.9f, 1.0f));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/food/FoodProperties$Builder.class */
    public static class Builder {
        private int nutrition;
        private float saturationModifier;
        private boolean canAlwaysEat;

        public Builder nutrition(int $$0) {
            this.nutrition = $$0;
            return this;
        }

        public Builder saturationModifier(float $$0) {
            this.saturationModifier = $$0;
            return this;
        }

        public Builder alwaysEdible() {
            this.canAlwaysEat = true;
            return this;
        }

        public FoodProperties build() {
            float $$0 = FoodConstants.saturationByModifier(this.nutrition, this.saturationModifier);
            return new FoodProperties(this.nutrition, $$0, this.canAlwaysEat);
        }
    }
}

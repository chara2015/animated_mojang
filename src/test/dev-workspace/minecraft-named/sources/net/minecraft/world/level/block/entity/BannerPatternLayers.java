package net.minecraft.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BannerPatternLayers.class */
public final class BannerPatternLayers extends Record implements TooltipProvider {
    private final List<Layer> layers;
    static final Logger LOGGER = LogUtils.getLogger();
    public static final BannerPatternLayers EMPTY = new BannerPatternLayers(List.of());
    public static final Codec<BannerPatternLayers> CODEC = Layer.CODEC.listOf().xmap(BannerPatternLayers::new, (v0) -> {
        return v0.layers();
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, BannerPatternLayers> STREAM_CODEC = Layer.STREAM_CODEC.apply(ByteBufCodecs.list()).map(BannerPatternLayers::new, (v0) -> {
        return v0.layers();
    });

    public BannerPatternLayers(List<Layer> $$0) {
        this.layers = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BannerPatternLayers.class), BannerPatternLayers.class, "layers", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;->layers:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BannerPatternLayers.class), BannerPatternLayers.class, "layers", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;->layers:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BannerPatternLayers.class, Object.class), BannerPatternLayers.class, "layers", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers;->layers:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<Layer> layers() {
        return this.layers;
    }

    public BannerPatternLayers removeLast() {
        return new BannerPatternLayers(List.copyOf(this.layers.subList(0, this.layers.size() - 1)));
    }

    @Override // net.minecraft.world.item.component.TooltipProvider
    public void addToTooltip(Item.TooltipContext $$0, Consumer<Component> $$1, TooltipFlag $$2, DataComponentGetter $$3) {
        for (int $$4 = 0; $$4 < Math.min(layers().size(), 6); $$4++) {
            $$1.accept(layers().get($$4).description().withStyle(ChatFormatting.GRAY));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BannerPatternLayers$Layer.class */
    public static final class Layer extends Record {
        private final Holder<BannerPattern> pattern;
        private final DyeColor color;
        public static final Codec<Layer> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(BannerPattern.CODEC.fieldOf("pattern").forGetter((v0) -> {
                return v0.pattern();
            }), DyeColor.CODEC.fieldOf("color").forGetter((v0) -> {
                return v0.color();
            })).apply($$0, Layer::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, Layer> STREAM_CODEC = StreamCodec.composite(BannerPattern.STREAM_CODEC, (v0) -> {
            return v0.pattern();
        }, DyeColor.STREAM_CODEC, (v0) -> {
            return v0.color();
        }, Layer::new);

        public Layer(Holder<BannerPattern> $$0, DyeColor $$1) {
            this.pattern = $$0;
            this.color = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Layer.class), Layer.class, "pattern;color", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->pattern:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->color:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Layer.class), Layer.class, "pattern;color", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->pattern:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->color:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Layer.class, Object.class), Layer.class, "pattern;color", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->pattern:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/level/block/entity/BannerPatternLayers$Layer;->color:Lnet/minecraft/world/item/DyeColor;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<BannerPattern> pattern() {
            return this.pattern;
        }

        public DyeColor color() {
            return this.color;
        }

        public MutableComponent description() {
            String $$0 = this.pattern.value().translationKey();
            return Component.translatable($$0 + "." + this.color.getName());
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BannerPatternLayers$Builder.class */
    public static class Builder {
        private final ImmutableList.Builder<Layer> layers = ImmutableList.builder();

        @Deprecated
        public Builder addIfRegistered(HolderGetter<BannerPattern> $$0, ResourceKey<BannerPattern> $$1, DyeColor $$2) {
            Optional<Holder.Reference<BannerPattern>> $$3 = $$0.get($$1);
            if ($$3.isEmpty()) {
                BannerPatternLayers.LOGGER.warn("Unable to find banner pattern with id: '{}'", $$1.identifier());
                return this;
            }
            return add($$3.get(), $$2);
        }

        public Builder add(Holder<BannerPattern> $$0, DyeColor $$1) {
            return add(new Layer($$0, $$1));
        }

        public Builder add(Layer $$0) {
            this.layers.add($$0);
            return this;
        }

        public Builder addAll(BannerPatternLayers $$0) {
            this.layers.addAll($$0.layers);
            return this;
        }

        public BannerPatternLayers build() {
            return new BannerPatternLayers(this.layers.build());
        }
    }
}

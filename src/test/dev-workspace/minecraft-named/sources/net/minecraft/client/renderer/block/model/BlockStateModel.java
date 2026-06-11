package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.client.resources.model.WeightedVariants;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockStateModel.class */
public interface BlockStateModel {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockStateModel$UnbakedRoot.class */
    public interface UnbakedRoot extends ResolvableModel {
        BlockStateModel bake(BlockState blockState, ModelBaker modelBaker);

        Object visualEqualityGroup(BlockState blockState);
    }

    void collectParts(RandomSource randomSource, List<BlockModelPart> list);

    TextureAtlasSprite particleIcon();

    default List<BlockModelPart> collectParts(RandomSource $$0) {
        ObjectArrayList objectArrayList = new ObjectArrayList();
        collectParts($$0, objectArrayList);
        return objectArrayList;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockStateModel$Unbaked.class */
    public interface Unbaked extends ResolvableModel {
        public static final Codec<Weighted<Variant>> ELEMENT_CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(Variant.MAP_CODEC.forGetter((v0) -> {
                return v0.value();
            }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("weight", 1).forGetter((v0) -> {
                return v0.weight();
            })).apply($$0, (v1, v2) -> {
                return new Weighted(v1, v2);
            });
        });
        public static final Codec<WeightedVariants.Unbaked> HARDCODED_WEIGHTED_CODEC = ExtraCodecs.nonEmptyList(ELEMENT_CODEC.listOf()).flatComapMap($$0 -> {
            return new WeightedVariants.Unbaked(WeightedList.of(Lists.transform($$0, $$0 -> {
                return $$0.map(SingleVariant.Unbaked::new);
            })));
        }, $$02 -> {
            List<Weighted<Unbaked>> $$1 = $$02.entries().unwrap();
            List<Weighted<Variant>> $$2 = new ArrayList<>($$1.size());
            for (Weighted<Unbaked> $$3 : $$1) {
                Object $$4 = $$3.value();
                if ($$4 instanceof SingleVariant.Unbaked) {
                    SingleVariant.Unbaked $$5 = (SingleVariant.Unbaked) $$4;
                    $$2.add(new Weighted<>($$5.variant(), $$3.weight()));
                } else {
                    return DataResult.error(() -> {
                        return "Only single variants are supported";
                    });
                }
            }
            return DataResult.success($$2);
        });
        public static final Codec<Unbaked> CODEC = Codec.either(HARDCODED_WEIGHTED_CODEC, SingleVariant.Unbaked.CODEC).flatComapMap($$0 -> {
            return (Unbaked) $$0.map($$0 -> {
                return $$0;
            }, $$02 -> {
                return $$02;
            });
        }, $$02 -> {
            Objects.requireNonNull($$02);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), SingleVariant.Unbaked.class, WeightedVariants.Unbaked.class).dynamicInvoker().invoke($$02, 0) /* invoke-custom */) {
                case 0:
                    SingleVariant.Unbaked $$3 = (SingleVariant.Unbaked) $$02;
                    return DataResult.success(Either.right($$3));
                case 1:
                    WeightedVariants.Unbaked $$4 = (WeightedVariants.Unbaked) $$02;
                    return DataResult.success(Either.left($$4));
                default:
                    return DataResult.error(() -> {
                        return "Only a single variant or a list of variants are supported";
                    });
            }
        });

        BlockStateModel bake(ModelBaker modelBaker);

        default UnbakedRoot asRoot() {
            return new SimpleCachedUnbakedRoot(this);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/BlockStateModel$SimpleCachedUnbakedRoot.class */
    public static class SimpleCachedUnbakedRoot implements UnbakedRoot {
        final Unbaked contents;
        private final ModelBaker.SharedOperationKey<BlockStateModel> bakingKey = new ModelBaker.SharedOperationKey<BlockStateModel>() { // from class: net.minecraft.client.renderer.block.model.BlockStateModel.SimpleCachedUnbakedRoot.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.minecraft.client.resources.model.ModelBaker.SharedOperationKey
            public BlockStateModel compute(ModelBaker $$0) {
                return SimpleCachedUnbakedRoot.this.contents.bake($$0);
            }
        };

        public SimpleCachedUnbakedRoot(Unbaked $$0) {
            this.contents = $$0;
        }

        @Override // net.minecraft.client.resources.model.ResolvableModel
        public void resolveDependencies(ResolvableModel.Resolver $$0) {
            this.contents.resolveDependencies($$0);
        }

        @Override // net.minecraft.client.renderer.block.model.BlockStateModel.UnbakedRoot
        public BlockStateModel bake(BlockState $$0, ModelBaker $$1) {
            return (BlockStateModel) $$1.compute(this.bakingKey);
        }

        @Override // net.minecraft.client.renderer.block.model.BlockStateModel.UnbakedRoot
        public Object visualEqualityGroup(BlockState $$0) {
            return this;
        }
    }
}

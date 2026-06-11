package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/MultiPartModel.class */
public class MultiPartModel implements BlockStateModel {
    private final SharedBakedState shared;
    private final BlockState blockState;
    private List<BlockStateModel> models;

    MultiPartModel(SharedBakedState $$0, BlockState $$1) {
        this.shared = $$0;
        this.blockState = $$1;
    }

    @Override // net.minecraft.client.renderer.block.model.BlockStateModel
    public TextureAtlasSprite particleIcon() {
        return this.shared.particleIcon;
    }

    @Override // net.minecraft.client.renderer.block.model.BlockStateModel
    public void collectParts(RandomSource $$0, List<BlockModelPart> $$1) {
        if (this.models == null) {
            this.models = this.shared.selectModels(this.blockState);
        }
        long $$2 = $$0.nextLong();
        for (BlockStateModel $$3 : this.models) {
            $$0.setSeed($$2);
            $$3.collectParts($$0, $$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector.class */
    public static final class Selector<T> extends Record {
        private final Predicate<BlockState> condition;
        private final T model;

        public Selector(Predicate<BlockState> $$0, T $$1) {
            this.condition = $$0;
            this.model = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Selector.class), Selector.class, "condition;model", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->condition:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->model:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Selector.class), Selector.class, "condition;model", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->condition:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->model:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Selector.class, Object.class), Selector.class, "condition;model", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->condition:Ljava/util/function/Predicate;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Selector;->model:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Predicate<BlockState> condition() {
            return this.condition;
        }

        public T model() {
            return this.model;
        }

        public <S> Selector<S> with(S $$0) {
            return new Selector<>(this.condition, $$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/MultiPartModel$SharedBakedState.class */
    static final class SharedBakedState {
        private final List<Selector<BlockStateModel>> selectors;
        final TextureAtlasSprite particleIcon;
        private final Map<BitSet, List<BlockStateModel>> subsets = new ConcurrentHashMap();

        private static BlockStateModel getFirstModel(List<Selector<BlockStateModel>> $$0) {
            if ($$0.isEmpty()) {
                throw new IllegalArgumentException("Model must have at least one selector");
            }
            return (BlockStateModel) ((Selector) $$0.getFirst()).model();
        }

        public SharedBakedState(List<Selector<BlockStateModel>> $$0) {
            this.selectors = $$0;
            BlockStateModel $$1 = getFirstModel($$0);
            this.particleIcon = $$1.particleIcon();
        }

        public List<BlockStateModel> selectModels(BlockState $$0) {
            BitSet $$1 = new BitSet();
            for (int $$2 = 0; $$2 < this.selectors.size(); $$2++) {
                if (((Selector) this.selectors.get($$2)).condition.test($$0)) {
                    $$1.set($$2);
                }
            }
            return this.subsets.computeIfAbsent($$1, $$02 -> {
                ImmutableList.Builder<BlockStateModel> $$12 = ImmutableList.builder();
                for (int $$22 = 0; $$22 < this.selectors.size(); $$22++) {
                    if ($$02.get($$22)) {
                        $$12.add(((Selector) this.selectors.get($$22)).model);
                    }
                }
                return $$12.build();
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked.class */
    public static class Unbaked implements BlockStateModel.UnbakedRoot {
        final List<Selector<BlockStateModel.Unbaked>> selectors;
        private final ModelBaker.SharedOperationKey<SharedBakedState> sharedStateKey = new ModelBaker.SharedOperationKey<SharedBakedState>() { // from class: net.minecraft.client.renderer.block.model.multipart.MultiPartModel.Unbaked.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.minecraft.client.resources.model.ModelBaker.SharedOperationKey
            public SharedBakedState compute(ModelBaker $$0) {
                ImmutableList.Builder<Selector<BlockStateModel>> $$1 = ImmutableList.builderWithExpectedSize(Unbaked.this.selectors.size());
                for (Selector<BlockStateModel.Unbaked> $$2 : Unbaked.this.selectors) {
                    $$1.add($$2.with(((Selector) $$2).model.bake($$0)));
                }
                return new SharedBakedState($$1.build());
            }
        };

        public Unbaked(List<Selector<BlockStateModel.Unbaked>> $$0) {
            this.selectors = $$0;
        }

        @Override // net.minecraft.client.renderer.block.model.BlockStateModel.UnbakedRoot
        public Object visualEqualityGroup(BlockState $$0) {
            IntArrayList intArrayList = new IntArrayList();
            for (int $$2 = 0; $$2 < this.selectors.size(); $$2++) {
                if (((Selector) this.selectors.get($$2)).condition.test($$0)) {
                    intArrayList.add($$2);
                }
            }
            return new Record(this, intArrayList) { // from class: net.minecraft.client.renderer.block.model.multipart.MultiPartModel.Unbaked.1Key
                private final Unbaked model;
                private final IntList selectors;

                {
                    this.model = this;
                    this.selectors = intArrayList;
                }

                @Override // java.lang.Record
                public final String toString() {
                    return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, C1Key.class), C1Key.class, "model;selectors", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->model:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->selectors:Lit/unimi/dsi/fastutil/ints/IntList;").dynamicInvoker().invoke(this) /* invoke-custom */;
                }

                @Override // java.lang.Record
                public final int hashCode() {
                    return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, C1Key.class), C1Key.class, "model;selectors", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->model:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->selectors:Lit/unimi/dsi/fastutil/ints/IntList;").dynamicInvoker().invoke(this) /* invoke-custom */;
                }

                @Override // java.lang.Record
                public final boolean equals(Object $$02) {
                    return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, C1Key.class, Object.class), C1Key.class, "model;selectors", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->model:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/MultiPartModel$Unbaked$1Key;->selectors:Lit/unimi/dsi/fastutil/ints/IntList;").dynamicInvoker().invoke(this, $$02) /* invoke-custom */;
                }

                public Unbaked model() {
                    return this.model;
                }

                public IntList selectors() {
                    return this.selectors;
                }
            };
        }

        @Override // net.minecraft.client.resources.model.ResolvableModel
        public void resolveDependencies(ResolvableModel.Resolver $$0) {
            this.selectors.forEach($$1 -> {
                ((BlockStateModel.Unbaked) $$1.model).resolveDependencies($$0);
            });
        }

        @Override // net.minecraft.client.renderer.block.model.BlockStateModel.UnbakedRoot
        public BlockStateModel bake(BlockState $$0, ModelBaker $$1) {
            SharedBakedState $$2 = (SharedBakedState) $$1.compute(this.sharedStateKey);
            return new MultiPartModel($$2, $$0);
        }
    }
}

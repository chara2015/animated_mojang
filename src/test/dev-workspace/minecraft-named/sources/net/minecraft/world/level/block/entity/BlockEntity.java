package net.minecraft.world.level.block.entity;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.debug.DebugValueSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BlockEntity.class */
public abstract class BlockEntity implements DebugValueSource {
    private static final Codec<BlockEntityType<?>> TYPE_CODEC = BuiltInRegistries.BLOCK_ENTITY_TYPE.byNameCodec();
    private static final Logger LOGGER = LogUtils.getLogger();
    private final BlockEntityType<?> type;
    protected Level level;
    protected final BlockPos worldPosition;
    protected boolean remove;
    private BlockState blockState;
    private DataComponentMap components = DataComponentMap.EMPTY;

    public BlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
        this.type = $$0;
        this.worldPosition = $$1.immutable();
        validateBlockState($$2);
        this.blockState = $$2;
    }

    private void validateBlockState(BlockState $$0) {
        if (!isValidBlockState($$0)) {
            throw new IllegalStateException("Invalid block entity " + getNameForReporting() + " state at " + String.valueOf(this.worldPosition) + ", got " + String.valueOf($$0));
        }
    }

    public boolean isValidBlockState(BlockState $$0) {
        return this.type.isValid($$0);
    }

    public static BlockPos getPosFromTag(ChunkPos $$0, CompoundTag $$1) {
        int $$2 = $$1.getIntOr("x", 0);
        int $$3 = $$1.getIntOr("y", 0);
        int $$4 = $$1.getIntOr("z", 0);
        int $$5 = SectionPos.blockToSectionCoord($$2);
        int $$6 = SectionPos.blockToSectionCoord($$4);
        if ($$5 != $$0.x || $$6 != $$0.z) {
            LOGGER.warn("Block entity {} found in a wrong chunk, expected position from chunk {}", $$1, $$0);
            $$2 = $$0.getBlockX(SectionPos.sectionRelative($$2));
            $$4 = $$0.getBlockZ(SectionPos.sectionRelative($$4));
        }
        return new BlockPos($$2, $$3, $$4);
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level $$0) {
        this.level = $$0;
    }

    public boolean hasLevel() {
        return this.level != null;
    }

    protected void loadAdditional(ValueInput $$0) {
    }

    public final void loadWithComponents(ValueInput $$0) {
        loadAdditional($$0);
        this.components = (DataComponentMap) $$0.read("components", DataComponentMap.CODEC).orElse(DataComponentMap.EMPTY);
    }

    public final void loadCustomOnly(ValueInput $$0) {
        loadAdditional($$0);
    }

    protected void saveAdditional(ValueOutput $$0) {
    }

    public final CompoundTag saveWithFullMetadata(HolderLookup.Provider $$0) {
        ProblemReporter.ScopedCollector $$1 = new ProblemReporter.ScopedCollector(problemPath(), LOGGER);
        try {
            TagValueOutput $$2 = TagValueOutput.createWithContext($$1, $$0);
            saveWithFullMetadata($$2);
            CompoundTag compoundTagBuildResult = $$2.buildResult();
            $$1.close();
            return compoundTagBuildResult;
        } catch (Throwable th) {
            try {
                $$1.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void saveWithFullMetadata(ValueOutput $$0) {
        saveWithoutMetadata($$0);
        saveMetadata($$0);
    }

    public void saveWithId(ValueOutput $$0) {
        saveWithoutMetadata($$0);
        saveId($$0);
    }

    public final CompoundTag saveWithoutMetadata(HolderLookup.Provider $$0) {
        ProblemReporter.ScopedCollector $$1 = new ProblemReporter.ScopedCollector(problemPath(), LOGGER);
        try {
            TagValueOutput $$2 = TagValueOutput.createWithContext($$1, $$0);
            saveWithoutMetadata($$2);
            CompoundTag compoundTagBuildResult = $$2.buildResult();
            $$1.close();
            return compoundTagBuildResult;
        } catch (Throwable th) {
            try {
                $$1.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void saveWithoutMetadata(ValueOutput $$0) {
        saveAdditional($$0);
        $$0.store("components", DataComponentMap.CODEC, this.components);
    }

    public final CompoundTag saveCustomOnly(HolderLookup.Provider $$0) {
        ProblemReporter.ScopedCollector $$1 = new ProblemReporter.ScopedCollector(problemPath(), LOGGER);
        try {
            TagValueOutput $$2 = TagValueOutput.createWithContext($$1, $$0);
            saveCustomOnly($$2);
            CompoundTag compoundTagBuildResult = $$2.buildResult();
            $$1.close();
            return compoundTagBuildResult;
        } catch (Throwable th) {
            try {
                $$1.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void saveCustomOnly(ValueOutput $$0) {
        saveAdditional($$0);
    }

    private void saveId(ValueOutput $$0) {
        addEntityType($$0, getType());
    }

    public static void addEntityType(ValueOutput $$0, BlockEntityType<?> $$1) {
        $$0.store(Entity.TAG_ID, TYPE_CODEC, $$1);
    }

    private void saveMetadata(ValueOutput $$0) {
        saveId($$0);
        $$0.putInt("x", this.worldPosition.getX());
        $$0.putInt("y", this.worldPosition.getY());
        $$0.putInt("z", this.worldPosition.getZ());
    }

    public static BlockEntity loadStatic(BlockPos $$0, BlockState $$1, CompoundTag $$2, HolderLookup.Provider $$3) {
        BlockEntityType<?> $$4 = (BlockEntityType) $$2.read(Entity.TAG_ID, TYPE_CODEC).orElse(null);
        if ($$4 == null) {
            LOGGER.error("Skipping block entity with invalid type: {}", $$2.get(Entity.TAG_ID));
            return null;
        }
        try {
            BlockEntity $$7 = $$4.create($$0, $$1);
            try {
                ProblemReporter.ScopedCollector $$8 = new ProblemReporter.ScopedCollector($$7.problemPath(), LOGGER);
                try {
                    $$7.loadWithComponents(TagValueInput.create($$8, $$3, $$2));
                    $$8.close();
                    return $$7;
                } finally {
                }
            } catch (Throwable $$9) {
                LOGGER.error("Failed to load data for block entity {} for block {} at position {}", new Object[]{$$4, $$0, $$1, $$9});
                return null;
            }
        } catch (Throwable $$6) {
            LOGGER.error("Failed to create block entity {} for block {} at position {} ", new Object[]{$$4, $$0, $$1, $$6});
            return null;
        }
    }

    public void setChanged() {
        if (this.level != null) {
            setChanged(this.level, this.worldPosition, this.blockState);
        }
    }

    protected static void setChanged(Level $$0, BlockPos $$1, BlockState $$2) {
        $$0.blockEntityChanged($$1);
        if (!$$2.isAir()) {
            $$0.updateNeighbourForOutputSignal($$1, $$2.getBlock());
        }
    }

    public BlockPos getBlockPos() {
        return this.worldPosition;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return null;
    }

    public CompoundTag getUpdateTag(HolderLookup.Provider $$0) {
        return new CompoundTag();
    }

    public boolean isRemoved() {
        return this.remove;
    }

    public void setRemoved() {
        this.remove = true;
    }

    public void clearRemoved() {
        this.remove = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void preRemoveSideEffects(BlockPos $$0, BlockState $$1) {
        if (this instanceof Container) {
            Container $$2 = (Container) this;
            if (this.level != null) {
                Containers.dropContents(this.level, $$0, $$2);
            }
        }
    }

    public boolean triggerEvent(int $$0, int $$1) {
        return false;
    }

    public void fillCrashReportCategory(CrashReportCategory $$0) {
        $$0.setDetail(StateHolder.NAME_TAG, this::getNameForReporting);
        BlockState blockState = getBlockState();
        Objects.requireNonNull(blockState);
        $$0.setDetail("Cached block", blockState::toString);
        if (this.level == null) {
            $$0.setDetail("Block location", () -> {
                return String.valueOf(this.worldPosition) + " (world missing)";
            });
            return;
        }
        BlockState blockState2 = this.level.getBlockState(this.worldPosition);
        Objects.requireNonNull(blockState2);
        $$0.setDetail("Actual block", blockState2::toString);
        CrashReportCategory.populateBlockLocationDetails($$0, this.level, this.worldPosition);
    }

    public String getNameForReporting() {
        return String.valueOf(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(getType())) + " // " + getClass().getCanonicalName();
    }

    public BlockEntityType<?> getType() {
        return this.type;
    }

    @Deprecated
    public void setBlockState(BlockState $$0) {
        validateBlockState($$0);
        this.blockState = $$0;
    }

    protected void applyImplicitComponents(DataComponentGetter $$0) {
    }

    public final void applyComponentsFromItemStack(ItemStack $$0) {
        applyComponents($$0.getPrototype(), $$0.getComponentsPatch());
    }

    public final void applyComponents(DataComponentMap $$0, DataComponentPatch $$1) {
        final Set<DataComponentType<?>> $$2 = new HashSet<>();
        $$2.add(DataComponents.BLOCK_ENTITY_DATA);
        $$2.add(DataComponents.BLOCK_STATE);
        final DataComponentMap $$3 = PatchedDataComponentMap.fromPatch($$0, $$1);
        applyImplicitComponents(new DataComponentGetter(this) { // from class: net.minecraft.world.level.block.entity.BlockEntity.1
            @Override // net.minecraft.core.component.DataComponentGetter
            public <T> T get(DataComponentType<? extends T> dataComponentType) {
                $$2.add(dataComponentType);
                return (T) $$3.get(dataComponentType);
            }

            @Override // net.minecraft.core.component.DataComponentGetter
            public <T> T getOrDefault(DataComponentType<? extends T> dataComponentType, T t) {
                $$2.add(dataComponentType);
                return (T) $$3.getOrDefault(dataComponentType, t);
            }
        });
        Objects.requireNonNull($$2);
        DataComponentPatch $$4 = $$1.forget((v1) -> {
            return r1.contains(v1);
        });
        this.components = $$4.split().added();
    }

    protected void collectImplicitComponents(DataComponentMap.Builder $$0) {
    }

    @Deprecated
    public void removeComponentsFromTag(ValueOutput $$0) {
    }

    public final DataComponentMap collectComponents() {
        DataComponentMap.Builder $$0 = DataComponentMap.builder();
        $$0.addAll(this.components);
        collectImplicitComponents($$0);
        return $$0.build();
    }

    public DataComponentMap components() {
        return this.components;
    }

    public void setComponents(DataComponentMap $$0) {
        this.components = $$0;
    }

    public static Component parseCustomNameSafe(ValueInput $$0, String $$1) {
        return (Component) $$0.read($$1, ComponentSerialization.CODEC).orElse(null);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/entity/BlockEntity$BlockEntityPathElement.class */
    static final class BlockEntityPathElement extends Record implements ProblemReporter.PathElement {
        private final BlockEntity blockEntity;

        BlockEntityPathElement(BlockEntity $$0) {
            this.blockEntity = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockEntityPathElement.class), BlockEntityPathElement.class, "blockEntity", "FIELD:Lnet/minecraft/world/level/block/entity/BlockEntity$BlockEntityPathElement;->blockEntity:Lnet/minecraft/world/level/block/entity/BlockEntity;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockEntityPathElement.class), BlockEntityPathElement.class, "blockEntity", "FIELD:Lnet/minecraft/world/level/block/entity/BlockEntity$BlockEntityPathElement;->blockEntity:Lnet/minecraft/world/level/block/entity/BlockEntity;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockEntityPathElement.class, Object.class), BlockEntityPathElement.class, "blockEntity", "FIELD:Lnet/minecraft/world/level/block/entity/BlockEntity$BlockEntityPathElement;->blockEntity:Lnet/minecraft/world/level/block/entity/BlockEntity;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public BlockEntity blockEntity() {
            return this.blockEntity;
        }

        @Override // net.minecraft.util.ProblemReporter.PathElement
        public String get() {
            return this.blockEntity.getNameForReporting() + "@" + String.valueOf(this.blockEntity.getBlockPos());
        }
    }

    public ProblemReporter.PathElement problemPath() {
        return new BlockEntityPathElement(this);
    }

    @Override // net.minecraft.util.debug.DebugValueSource
    public void registerDebugValues(ServerLevel $$0, DebugValueSource.Registration $$1) {
    }
}

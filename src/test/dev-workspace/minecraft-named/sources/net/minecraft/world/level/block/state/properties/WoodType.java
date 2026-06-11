package net.minecraft.world.level.block.state.properties;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/WoodType.class */
public final class WoodType extends Record {
    private final String name;
    private final BlockSetType setType;
    private final SoundType soundType;
    private final SoundType hangingSignSoundType;
    private final SoundEvent fenceGateClose;
    private final SoundEvent fenceGateOpen;
    private static final Map<String, WoodType> TYPES = new Object2ObjectArrayMap();
    public static final Codec<WoodType> CODEC;
    public static final WoodType OAK;
    public static final WoodType SPRUCE;
    public static final WoodType BIRCH;
    public static final WoodType ACACIA;
    public static final WoodType CHERRY;
    public static final WoodType JUNGLE;
    public static final WoodType DARK_OAK;
    public static final WoodType PALE_OAK;
    public static final WoodType CRIMSON;
    public static final WoodType WARPED;
    public static final WoodType MANGROVE;
    public static final WoodType BAMBOO;

    public WoodType(String $$0, BlockSetType $$1, SoundType $$2, SoundType $$3, SoundEvent $$4, SoundEvent $$5) {
        this.name = $$0;
        this.setType = $$1;
        this.soundType = $$2;
        this.hangingSignSoundType = $$3;
        this.fenceGateClose = $$4;
        this.fenceGateOpen = $$5;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WoodType.class), WoodType.class, "name;setType;soundType;hangingSignSoundType;fenceGateClose;fenceGateOpen", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->setType:Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->hangingSignSoundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateOpen:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WoodType.class), WoodType.class, "name;setType;soundType;hangingSignSoundType;fenceGateClose;fenceGateOpen", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->setType:Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->hangingSignSoundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateOpen:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WoodType.class, Object.class), WoodType.class, "name;setType;soundType;hangingSignSoundType;fenceGateClose;fenceGateOpen", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->setType:Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->hangingSignSoundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/WoodType;->fenceGateOpen:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public BlockSetType setType() {
        return this.setType;
    }

    public SoundType soundType() {
        return this.soundType;
    }

    public SoundType hangingSignSoundType() {
        return this.hangingSignSoundType;
    }

    public SoundEvent fenceGateClose() {
        return this.fenceGateClose;
    }

    public SoundEvent fenceGateOpen() {
        return this.fenceGateOpen;
    }

    static {
        Function function = (v0) -> {
            return v0.name();
        };
        Map<String, WoodType> map = TYPES;
        Objects.requireNonNull(map);
        CODEC = Codec.stringResolver(function, (v1) -> {
            return r1.get(v1);
        });
        OAK = register(new WoodType("oak", BlockSetType.OAK));
        SPRUCE = register(new WoodType("spruce", BlockSetType.SPRUCE));
        BIRCH = register(new WoodType("birch", BlockSetType.BIRCH));
        ACACIA = register(new WoodType("acacia", BlockSetType.ACACIA));
        CHERRY = register(new WoodType("cherry", BlockSetType.CHERRY, SoundType.CHERRY_WOOD, SoundType.CHERRY_WOOD_HANGING_SIGN, SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE, SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN));
        JUNGLE = register(new WoodType("jungle", BlockSetType.JUNGLE));
        DARK_OAK = register(new WoodType("dark_oak", BlockSetType.DARK_OAK));
        PALE_OAK = register(new WoodType("pale_oak", BlockSetType.PALE_OAK));
        CRIMSON = register(new WoodType("crimson", BlockSetType.CRIMSON, SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN));
        WARPED = register(new WoodType("warped", BlockSetType.WARPED, SoundType.NETHER_WOOD, SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN));
        MANGROVE = register(new WoodType("mangrove", BlockSetType.MANGROVE));
        BAMBOO = register(new WoodType("bamboo", BlockSetType.BAMBOO, SoundType.BAMBOO_WOOD, SoundType.BAMBOO_WOOD_HANGING_SIGN, SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN));
    }

    public WoodType(String $$0, BlockSetType $$1) {
        this($$0, $$1, SoundType.WOOD, SoundType.HANGING_SIGN, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }

    private static WoodType register(WoodType $$0) {
        TYPES.put($$0.name(), $$0);
        return $$0;
    }

    public static Stream<WoodType> values() {
        return TYPES.values().stream();
    }
}

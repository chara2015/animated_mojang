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

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/BlockSetType.class */
public final class BlockSetType extends Record {
    private final String name;
    private final boolean canOpenByHand;
    private final boolean canOpenByWindCharge;
    private final boolean canButtonBeActivatedByArrows;
    private final PressurePlateSensitivity pressurePlateSensitivity;
    private final SoundType soundType;
    private final SoundEvent doorClose;
    private final SoundEvent doorOpen;
    private final SoundEvent trapdoorClose;
    private final SoundEvent trapdoorOpen;
    private final SoundEvent pressurePlateClickOff;
    private final SoundEvent pressurePlateClickOn;
    private final SoundEvent buttonClickOff;
    private final SoundEvent buttonClickOn;
    private static final Map<String, BlockSetType> TYPES = new Object2ObjectArrayMap();
    public static final Codec<BlockSetType> CODEC;
    public static final BlockSetType IRON;
    public static final BlockSetType COPPER;
    public static final BlockSetType GOLD;
    public static final BlockSetType STONE;
    public static final BlockSetType POLISHED_BLACKSTONE;
    public static final BlockSetType OAK;
    public static final BlockSetType SPRUCE;
    public static final BlockSetType BIRCH;
    public static final BlockSetType ACACIA;
    public static final BlockSetType CHERRY;
    public static final BlockSetType JUNGLE;
    public static final BlockSetType DARK_OAK;
    public static final BlockSetType PALE_OAK;
    public static final BlockSetType CRIMSON;
    public static final BlockSetType WARPED;
    public static final BlockSetType MANGROVE;
    public static final BlockSetType BAMBOO;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/BlockSetType$PressurePlateSensitivity.class */
    public enum PressurePlateSensitivity {
        EVERYTHING,
        MOBS
    }

    public BlockSetType(String $$0, boolean $$1, boolean $$2, boolean $$3, PressurePlateSensitivity $$4, SoundType $$5, SoundEvent $$6, SoundEvent $$7, SoundEvent $$8, SoundEvent $$9, SoundEvent $$10, SoundEvent $$11, SoundEvent $$12, SoundEvent $$13) {
        this.name = $$0;
        this.canOpenByHand = $$1;
        this.canOpenByWindCharge = $$2;
        this.canButtonBeActivatedByArrows = $$3;
        this.pressurePlateSensitivity = $$4;
        this.soundType = $$5;
        this.doorClose = $$6;
        this.doorOpen = $$7;
        this.trapdoorClose = $$8;
        this.trapdoorOpen = $$9;
        this.pressurePlateClickOff = $$10;
        this.pressurePlateClickOn = $$11;
        this.buttonClickOff = $$12;
        this.buttonClickOn = $$13;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockSetType.class), BlockSetType.class, "name;canOpenByHand;canOpenByWindCharge;canButtonBeActivatedByArrows;pressurePlateSensitivity;soundType;doorClose;doorOpen;trapdoorClose;trapdoorOpen;pressurePlateClickOff;pressurePlateClickOn;buttonClickOff;buttonClickOn", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByHand:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByWindCharge:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canButtonBeActivatedByArrows:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateSensitivity:Lnet/minecraft/world/level/block/state/properties/BlockSetType$PressurePlateSensitivity;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOn:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOn:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockSetType.class), BlockSetType.class, "name;canOpenByHand;canOpenByWindCharge;canButtonBeActivatedByArrows;pressurePlateSensitivity;soundType;doorClose;doorOpen;trapdoorClose;trapdoorOpen;pressurePlateClickOff;pressurePlateClickOn;buttonClickOff;buttonClickOn", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByHand:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByWindCharge:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canButtonBeActivatedByArrows:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateSensitivity:Lnet/minecraft/world/level/block/state/properties/BlockSetType$PressurePlateSensitivity;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOn:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOn:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockSetType.class, Object.class), BlockSetType.class, "name;canOpenByHand;canOpenByWindCharge;canButtonBeActivatedByArrows;pressurePlateSensitivity;soundType;doorClose;doorOpen;trapdoorClose;trapdoorOpen;pressurePlateClickOff;pressurePlateClickOn;buttonClickOff;buttonClickOn", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByHand:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canOpenByWindCharge:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->canButtonBeActivatedByArrows:Z", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateSensitivity:Lnet/minecraft/world/level/block/state/properties/BlockSetType$PressurePlateSensitivity;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->soundType:Lnet/minecraft/world/level/block/SoundType;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->doorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorClose:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->trapdoorOpen:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->pressurePlateClickOn:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOff:Lnet/minecraft/sounds/SoundEvent;", "FIELD:Lnet/minecraft/world/level/block/state/properties/BlockSetType;->buttonClickOn:Lnet/minecraft/sounds/SoundEvent;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public boolean canOpenByHand() {
        return this.canOpenByHand;
    }

    public boolean canOpenByWindCharge() {
        return this.canOpenByWindCharge;
    }

    public boolean canButtonBeActivatedByArrows() {
        return this.canButtonBeActivatedByArrows;
    }

    public PressurePlateSensitivity pressurePlateSensitivity() {
        return this.pressurePlateSensitivity;
    }

    public SoundType soundType() {
        return this.soundType;
    }

    public SoundEvent doorClose() {
        return this.doorClose;
    }

    public SoundEvent doorOpen() {
        return this.doorOpen;
    }

    public SoundEvent trapdoorClose() {
        return this.trapdoorClose;
    }

    public SoundEvent trapdoorOpen() {
        return this.trapdoorOpen;
    }

    public SoundEvent pressurePlateClickOff() {
        return this.pressurePlateClickOff;
    }

    public SoundEvent pressurePlateClickOn() {
        return this.pressurePlateClickOn;
    }

    public SoundEvent buttonClickOff() {
        return this.buttonClickOff;
    }

    public SoundEvent buttonClickOn() {
        return this.buttonClickOn;
    }

    static {
        Function function = (v0) -> {
            return v0.name();
        };
        Map<String, BlockSetType> map = TYPES;
        Objects.requireNonNull(map);
        CODEC = Codec.stringResolver(function, (v1) -> {
            return r1.get(v1);
        });
        IRON = register(new BlockSetType("iron", false, false, false, PressurePlateSensitivity.EVERYTHING, SoundType.IRON, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
        COPPER = register(new BlockSetType("copper", true, true, false, PressurePlateSensitivity.EVERYTHING, SoundType.COPPER, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
        GOLD = register(new BlockSetType("gold", false, true, false, PressurePlateSensitivity.EVERYTHING, SoundType.METAL, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
        STONE = register(new BlockSetType("stone", true, true, false, PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
        POLISHED_BLACKSTONE = register(new BlockSetType("polished_blackstone", true, true, false, PressurePlateSensitivity.MOBS, SoundType.STONE, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));
        OAK = register(new BlockSetType("oak"));
        SPRUCE = register(new BlockSetType("spruce"));
        BIRCH = register(new BlockSetType("birch"));
        ACACIA = register(new BlockSetType("acacia"));
        CHERRY = register(new BlockSetType("cherry", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.CHERRY_WOOD, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundEvents.CHERRY_WOOD_DOOR_OPEN, SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE, SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF, SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON));
        JUNGLE = register(new BlockSetType("jungle"));
        DARK_OAK = register(new BlockSetType("dark_oak"));
        PALE_OAK = register(new BlockSetType("pale_oak"));
        CRIMSON = register(new BlockSetType("crimson", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON));
        WARPED = register(new BlockSetType("warped", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.NETHER_WOOD, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON));
        MANGROVE = register(new BlockSetType("mangrove"));
        BAMBOO = register(new BlockSetType("bamboo", true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.BAMBOO_WOOD, SoundEvents.BAMBOO_WOOD_DOOR_CLOSE, SoundEvents.BAMBOO_WOOD_DOOR_OPEN, SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE, SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON));
    }

    public BlockSetType(String $$0) {
        this($$0, true, true, true, PressurePlateSensitivity.EVERYTHING, SoundType.WOOD, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.WOODEN_BUTTON_CLICK_OFF, SoundEvents.WOODEN_BUTTON_CLICK_ON);
    }

    private static BlockSetType register(BlockSetType $$0) {
        TYPES.put($$0.name, $$0);
        return $$0;
    }

    public static Stream<BlockSetType> values() {
        return TYPES.values().stream();
    }
}

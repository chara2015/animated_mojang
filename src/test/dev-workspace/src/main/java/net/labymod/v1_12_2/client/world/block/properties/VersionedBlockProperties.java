package net.labymod.v1_12_2.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public final class VersionedBlockProperties implements BlockProperties {
    private static final axi AGE_1 = axi.a("age", 0, 1);
    private static final axi AGE_2 = axi.a("age", 0, 2);
    private static final axi AGE_3 = axi.a("age", 0, 3);
    private static final axi AGE_4 = axi.a("age", 0, 4);
    private static final axi AGE_5 = axi.a("age", 0, 5);
    private static final axi AGE_7 = axi.a("age", 0, 7);
    private static final axi AGE_15 = axi.a("age", 0, 15);
    private static final axi AGE_25 = axi.a("age", 0, 25);
    private static final axf POWERED = axf.a(ParameterType.POWERED);
    private static final axi HONEY_LEVEL = axi.a("honey_level", 0, 5);
    private static final axi POWER = axi.a("power", 0, 15);
    private static final axi NOTE = axi.a("note", 0, 24);
    private static final axh<UnknownProperty> NOTEBLOCK_INSTRUMENT = axh.a("instrument", UnknownProperty.class);
    private static final axi DELAY = axi.a("delay", 1, 4);
    private static final axh<a> COMPARATOR_MODE = axh.a("mode", a.class);

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge1() {
        return AGE_1;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge2() {
        return AGE_2;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge3() {
        return AGE_3;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge4() {
        return AGE_4;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge5() {
        return AGE_5;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge7() {
        return AGE_7;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge15() {
        return AGE_15;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge25() {
        return AGE_25;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPowered() {
        return POWERED;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getHoneyLevel() {
        return HONEY_LEVEL;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPower() {
        return POWER;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNote() {
        return NOTE;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNoteblockInstrument() {
        return NOTEBLOCK_INSTRUMENT;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getDelay() {
        return DELAY;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getComparatorMode() {
        return COMPARATOR_MODE;
    }
}

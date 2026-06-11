package net.labymod.v26_1_1.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public class VersionedBlockProperties implements BlockProperties {
    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge1() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_1);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge2() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_2);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge3() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_3);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge4() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_4);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge5() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_5);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge7() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_7);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge15() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_15);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge25() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_25);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPowered() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.POWERED);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getHoneyLevel() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.LEVEL_HONEY);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPower() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.POWER);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNote() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.NOTE);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNoteblockInstrument() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.NOTEBLOCK_INSTRUMENT);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getDelay() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.DELAY);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getComparatorMode() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.MODE_COMPARATOR);
    }
}

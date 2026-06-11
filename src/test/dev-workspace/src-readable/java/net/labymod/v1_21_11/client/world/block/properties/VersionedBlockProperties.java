package net.labymod.v1_21_11.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public class VersionedBlockProperties implements BlockProperties {
    public BlockProperty getAge1() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_1);
    }

    public BlockProperty getAge2() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_2);
    }

    public BlockProperty getAge3() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_3);
    }

    public BlockProperty getAge4() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_4);
    }

    public BlockProperty getAge5() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_5);
    }

    public BlockProperty getAge7() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_7);
    }

    public BlockProperty getAge15() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_15);
    }

    public BlockProperty getAge25() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.AGE_25);
    }

    public BlockProperty getPowered() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.POWERED);
    }

    public BlockProperty getHoneyLevel() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.LEVEL_HONEY);
    }

    public BlockProperty getPower() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.POWER);
    }

    public BlockProperty getNote() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.NOTE);
    }

    public BlockProperty getNoteblockInstrument() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.NOTEBLOCK_INSTRUMENT);
    }

    public BlockProperty getDelay() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.DELAY);
    }

    public BlockProperty getComparatorMode() {
        return (BlockProperty) CastUtil.cast(BlockStateProperties.MODE_COMPARATOR);
    }
}

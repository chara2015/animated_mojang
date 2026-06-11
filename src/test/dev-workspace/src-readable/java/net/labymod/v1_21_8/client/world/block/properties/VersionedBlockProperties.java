package net.labymod.v1_21_8.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public class VersionedBlockProperties implements BlockProperties {
    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge1() {
        return (BlockProperty) CastUtil.cast(eer.at);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge2() {
        return (BlockProperty) CastUtil.cast(eer.au);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge3() {
        return (BlockProperty) CastUtil.cast(eer.av);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge4() {
        return (BlockProperty) CastUtil.cast(eer.aw);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge5() {
        return (BlockProperty) CastUtil.cast(eer.ax);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge7() {
        return (BlockProperty) CastUtil.cast(eer.ay);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge15() {
        return (BlockProperty) CastUtil.cast(eer.az);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge25() {
        return (BlockProperty) CastUtil.cast(eer.aA);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPowered() {
        return (BlockProperty) CastUtil.cast(eer.A);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getHoneyLevel() {
        return (BlockProperty) CastUtil.cast(eer.aQ);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPower() {
        return (BlockProperty) CastUtil.cast(eer.aW);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNote() {
        return (BlockProperty) CastUtil.cast(eer.aU);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNoteblockInstrument() {
        return (BlockProperty) CastUtil.cast(eer.bj);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getDelay() {
        return (BlockProperty) CastUtil.cast(eer.aD);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getComparatorMode() {
        return (BlockProperty) CastUtil.cast(eer.bh);
    }
}

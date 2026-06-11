package net.labymod.v1_21_4.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public class VersionedBlockProperties implements BlockProperties {
    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge1() {
        return (BlockProperty) CastUtil.cast(dxo.at);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge2() {
        return (BlockProperty) CastUtil.cast(dxo.au);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge3() {
        return (BlockProperty) CastUtil.cast(dxo.av);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge4() {
        return (BlockProperty) CastUtil.cast(dxo.aw);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge5() {
        return (BlockProperty) CastUtil.cast(dxo.ax);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge7() {
        return (BlockProperty) CastUtil.cast(dxo.ay);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge15() {
        return (BlockProperty) CastUtil.cast(dxo.az);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge25() {
        return (BlockProperty) CastUtil.cast(dxo.aA);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPowered() {
        return (BlockProperty) CastUtil.cast(dxo.B);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getHoneyLevel() {
        return (BlockProperty) CastUtil.cast(dxo.aQ);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPower() {
        return (BlockProperty) CastUtil.cast(dxo.aW);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNote() {
        return (BlockProperty) CastUtil.cast(dxo.aU);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNoteblockInstrument() {
        return (BlockProperty) CastUtil.cast(dxo.bi);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getDelay() {
        return (BlockProperty) CastUtil.cast(dxo.aD);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getComparatorMode() {
        return (BlockProperty) CastUtil.cast(dxo.bg);
    }
}

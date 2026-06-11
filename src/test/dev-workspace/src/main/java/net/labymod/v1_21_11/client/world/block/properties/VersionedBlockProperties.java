package net.labymod.v1_21_11.client.world.block.properties;

import javax.inject.Singleton;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/world/block/properties/VersionedBlockProperties.class */
@Singleton
@Implements(BlockProperties.class)
public class VersionedBlockProperties implements BlockProperties {
    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge1() {
        return (BlockProperty) CastUtil.cast(eox.au);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge2() {
        return (BlockProperty) CastUtil.cast(eox.av);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge3() {
        return (BlockProperty) CastUtil.cast(eox.aw);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge4() {
        return (BlockProperty) CastUtil.cast(eox.ax);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge5() {
        return (BlockProperty) CastUtil.cast(eox.ay);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge7() {
        return (BlockProperty) CastUtil.cast(eox.az);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge15() {
        return (BlockProperty) CastUtil.cast(eox.aA);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getAge25() {
        return (BlockProperty) CastUtil.cast(eox.aB);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPowered() {
        return (BlockProperty) CastUtil.cast(eox.A);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getHoneyLevel() {
        return (BlockProperty) CastUtil.cast(eox.aR);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getPower() {
        return (BlockProperty) CastUtil.cast(eox.aX);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNote() {
        return (BlockProperty) CastUtil.cast(eox.aV);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getNoteblockInstrument() {
        return (BlockProperty) CastUtil.cast(eox.bk);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getDelay() {
        return (BlockProperty) CastUtil.cast(eox.aE);
    }

    @Override // net.labymod.api.client.world.block.properties.BlockProperties
    public BlockProperty getComparatorMode() {
        return (BlockProperty) CastUtil.cast(eox.bi);
    }
}

package net.labymod.v1_19_4.client.world.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder;
import net.labymod.api.client.world.block.properties.BlockProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/world/block/properties/VersionedBlockPropertyHolder.class */
public class VersionedBlockPropertyHolder extends AbstractBlockPropertyHolder {
    private final dbs<?, ?> holder;

    public VersionedBlockPropertyHolder(dbs<?, ?> holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder
    protected <T extends Comparable<T>> T getPropertyValue(BlockProperty blockProperty) {
        dcu dcuVar = (dcu) blockProperty;
        if (!this.holder.b(dcuVar)) {
            return null;
        }
        Enum r0 = (T) this.holder.c(dcuVar);
        if (r0 instanceof Enum) {
            return Laby.references().enumMapperRegistry().from(r0);
        }
        return r0;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    public boolean hasProperty(BlockProperty property) {
        return this.holder.b((dcu) property);
    }
}

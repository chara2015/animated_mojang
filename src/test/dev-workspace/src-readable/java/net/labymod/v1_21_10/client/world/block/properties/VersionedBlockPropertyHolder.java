package net.labymod.v1_21_10.client.world.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder;
import net.labymod.api.client.world.block.properties.BlockProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/world/block/properties/VersionedBlockPropertyHolder.class */
public class VersionedBlockPropertyHolder extends AbstractBlockPropertyHolder {
    private final ejo<?, ?> holder;

    public VersionedBlockPropertyHolder(ejo<?, ?> holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder
    protected <T extends Comparable<T>> T getPropertyValue(BlockProperty blockProperty) {
        ekp ekpVar = (ekp) blockProperty;
        if (!this.holder.b(ekpVar)) {
            return null;
        }
        Enum r0 = (T) this.holder.c(ekpVar);
        if (r0 instanceof Enum) {
            return Laby.references().enumMapperRegistry().from(r0);
        }
        return r0;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    public boolean hasProperty(BlockProperty property) {
        return this.holder.b((ekp) property);
    }
}

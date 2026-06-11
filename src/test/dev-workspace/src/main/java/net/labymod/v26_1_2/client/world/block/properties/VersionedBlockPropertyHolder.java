package net.labymod.v26_1_2.client.world.block.properties;

import net.labymod.api.Laby;
import net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder;
import net.labymod.api.client.world.block.properties.BlockProperty;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/world/block/properties/VersionedBlockPropertyHolder.class */
public class VersionedBlockPropertyHolder extends AbstractBlockPropertyHolder {
    private final StateHolder<?, ?> holder;

    public VersionedBlockPropertyHolder(StateHolder<?, ?> holder) {
        this.holder = holder;
    }

    @Override // net.labymod.api.client.world.block.properties.AbstractBlockPropertyHolder
    protected <T extends Comparable<T>> T getPropertyValue(BlockProperty blockProperty) {
        Property property = (Property) blockProperty;
        if (!this.holder.hasProperty(property)) {
            return null;
        }
        Enum r0 = (T) this.holder.getValue(property);
        if (r0 instanceof Enum) {
            return Laby.references().enumMapperRegistry().from(r0);
        }
        return r0;
    }

    @Override // net.labymod.api.client.world.block.properties.BlockPropertyHolder
    public boolean hasProperty(BlockProperty property) {
        return this.holder.hasProperty((Property) property);
    }
}

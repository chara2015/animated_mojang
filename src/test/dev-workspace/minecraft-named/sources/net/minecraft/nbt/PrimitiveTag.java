package net.minecraft.nbt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/PrimitiveTag.class */
public interface PrimitiveTag extends Tag {
    @Override // net.minecraft.nbt.Tag
    default Tag copy() {
        return this;
    }
}

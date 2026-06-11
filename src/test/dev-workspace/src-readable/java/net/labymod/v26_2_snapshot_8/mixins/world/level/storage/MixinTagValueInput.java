package net.labymod.v26_2_snapshot_8.mixins.world.level.storage;

import net.labymod.v26_2_snapshot_8.client.util.TagValueInputAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.TagValueInput;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/world/level/storage/MixinTagValueInput.class */
@Mixin({TagValueInput.class})
public class MixinTagValueInput implements TagValueInputAccessor {

    @Shadow
    @Final
    private CompoundTag input;

    @Override // net.labymod.v26_2_snapshot_8.client.util.TagValueInputAccessor
    public CompoundTag tag() {
        return this.input;
    }
}

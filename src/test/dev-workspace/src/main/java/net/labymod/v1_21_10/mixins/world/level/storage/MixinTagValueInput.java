package net.labymod.v1_21_10.mixins.world.level.storage;

import net.labymod.v1_21_10.client.util.TagValueInputAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/world/level/storage/MixinTagValueInput.class */
@Mixin({fin.class})
public class MixinTagValueInput implements TagValueInputAccessor {

    @Shadow
    @Final
    private up c;

    @Override // net.labymod.v1_21_10.client.util.TagValueInputAccessor
    public up tag() {
        return this.c;
    }
}

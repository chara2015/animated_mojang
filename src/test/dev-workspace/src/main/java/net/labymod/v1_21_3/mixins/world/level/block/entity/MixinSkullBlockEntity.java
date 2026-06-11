package net.labymod.v1_21_3.mixins.world.level.block.entity;

import net.labymod.api.client.blockentity.SkullBlockEntity;
import net.labymod.api.mojang.GameProfile;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({dwm.class})
public abstract class MixinSkullBlockEntity implements SkullBlockEntity {
    @Shadow
    public abstract dao c();

    @Override // net.labymod.api.client.blockentity.SkullBlockEntity
    @Nullable
    public GameProfile getProfile() {
        dao profile = c();
        if (profile == null) {
            return null;
        }
        return profile.f();
    }
}

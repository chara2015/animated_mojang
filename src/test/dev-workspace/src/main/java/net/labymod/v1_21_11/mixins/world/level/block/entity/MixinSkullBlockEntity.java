package net.labymod.v1_21_11.mixins.world.level.block.entity;

import net.labymod.api.client.blockentity.SkullBlockEntity;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.CastUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({emu.class})
public abstract class MixinSkullBlockEntity implements SkullBlockEntity {
    @Shadow
    public abstract doy a();

    @Override // net.labymod.api.client.blockentity.SkullBlockEntity
    @Nullable
    public GameProfile getProfile() {
        doy profile = a();
        if (profile == null) {
            return null;
        }
        return (GameProfile) CastUtil.cast(profile.b());
    }
}

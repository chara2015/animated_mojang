package net.labymod.v1_8_9.mixins.world.level.block.entity;

import com.mojang.authlib.GameProfile;
import net.labymod.api.client.blockentity.SkullBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({alo.class})
public abstract class MixinSkullBlockEntity implements SkullBlockEntity {
    @Shadow
    public abstract GameProfile b();

    @Override // net.labymod.api.client.blockentity.SkullBlockEntity
    @Nullable
    public net.labymod.api.mojang.GameProfile getProfile() {
        return b();
    }
}

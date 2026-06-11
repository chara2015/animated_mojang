package net.labymod.v1_16_5.mixins.world.level.block.entity;

import com.mojang.authlib.GameProfile;
import javax.annotation.Nullable;
import net.labymod.api.client.blockentity.SkullBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({cdg.class})
public abstract class MixinSkullBlockEntity implements SkullBlockEntity {
    @Shadow
    @Nullable
    public abstract GameProfile d();

    @Override // net.labymod.api.client.blockentity.SkullBlockEntity
    @org.jetbrains.annotations.Nullable
    public net.labymod.api.mojang.GameProfile getProfile() {
        return d();
    }
}

package net.labymod.v26_1_1.mixins.world.level.block.entity;

import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.CastUtil;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({SkullBlockEntity.class})
public abstract class MixinSkullBlockEntity implements net.labymod.api.client.blockentity.SkullBlockEntity {
    @Shadow
    public abstract ResolvableProfile getOwnerProfile();

    @Override // net.labymod.api.client.blockentity.SkullBlockEntity
    @Nullable
    public GameProfile getProfile() {
        ResolvableProfile profile = getOwnerProfile();
        if (profile == null) {
            return null;
        }
        return (GameProfile) CastUtil.cast(profile.partialProfile());
    }
}

package net.labymod.v1_21_11.mixins.world.level.block.entity;

import net.labymod.api.mojang.GameProfile;
import net.labymod.api.util.CastUtil;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/world/level/block/entity/MixinSkullBlockEntity.class */
@Mixin({SkullBlockEntity.class})
public abstract class MixinSkullBlockEntity implements net.labymod.api.client.blockentity.SkullBlockEntity {
    @Shadow
    public abstract ResolvableProfile a();

    @Nullable
    public GameProfile getProfile() {
        ResolvableProfile profile = a();
        if (profile == null) {
            return null;
        }
        return (GameProfile) CastUtil.cast(profile.partialProfile());
    }
}

package net.labymod.v26_2_snapshot_8.mixins.client.gui.components;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.accessor.gui.ImageButtonAccessor;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/gui/components/MixinStatSortButton.class */
@Mixin({StatsScreen.ItemStatisticsList.HeaderEntry.StatSortButton.class})
public abstract class MixinStatSortButton implements ImageButtonAccessor {

    @Unique
    private Identifier labymod4$sprite;

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    public void labyMod$init(StatsScreen.ItemStatisticsList.HeaderEntry headerEntry, int column, Identifier sprite, CallbackInfo ci) {
        this.labymod4$sprite = sprite;
    }

    @Override // net.labymod.core.client.accessor.gui.ImageButtonAccessor
    public ResourceLocation getResourceLocation() {
        return (ResourceLocation) CastUtil.cast(this.labymod4$sprite);
    }
}

package net.labymod.v26_1_2.mixins.client.renderer.feature;

import net.labymod.v26_1_2.client.renderer.ItemStackRenderStateAccessor;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/feature/MixinItemModelResolver_Accessor.class */
@Mixin({ItemModelResolver.class})
public class MixinItemModelResolver_Accessor {
    @Inject(method = {"updateForLiving"}, at = {@At("HEAD")})
    private void labyMod$updateForLiving(ItemStackRenderState $$0, ItemStack $$1, ItemDisplayContext $$2, LivingEntity $$3, CallbackInfo ci) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) $$0;
        accessor.labyMod$setLivingEntity($$3);
        accessor.labyMod$setItemStack($$1);
    }

    @Inject(method = {"appendItemLayers"}, at = {@At("HEAD")})
    private void labyMod$appendItemLayers(ItemStackRenderState $$0, ItemStack $$1, ItemDisplayContext $$2, Level $$3, ItemOwner $$4, int $$5, CallbackInfo ci) {
        ItemStackRenderStateAccessor accessor = (ItemStackRenderStateAccessor) $$0;
        accessor.labyMod$setItemStack($$1);
    }
}

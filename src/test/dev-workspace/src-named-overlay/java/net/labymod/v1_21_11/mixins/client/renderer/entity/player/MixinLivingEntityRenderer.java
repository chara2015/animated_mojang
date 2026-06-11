package net.labymod.v1_21_11.mixins.client.renderer.entity.player;

import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_11.client.render.LivingEntityRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/player/MixinLivingEntityRenderer.class */
@Mixin({LivingEntityRenderer.class})
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements RenderLayerParent<S, M>, LivingEntityRendererAccessor {

    @Shadow
    protected M model;

    @Shadow
    protected abstract boolean addLayer(RenderLayer<S, M> renderLayer);

    protected MixinLivingEntityRenderer(EntityRendererProvider.Context $$0) {
        super($$0);
    }

    @Inject(method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$showOwnName(LivingEntity entity, double $$1, CallbackInfoReturnable<Boolean> cir) {
        if (entity == Minecraft.getInstance().player) {
            cir.setReturnValue((Boolean) LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Override // net.labymod.v1_21_11.client.render.LivingEntityRendererAccessor
    public void addCustomLayer(RenderLayer layer) {
        addLayer(layer);
    }
}



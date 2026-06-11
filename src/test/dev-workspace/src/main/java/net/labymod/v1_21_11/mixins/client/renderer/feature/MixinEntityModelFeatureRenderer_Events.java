package net.labymod.v1_21_11.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import net.labymod.api.event.client.render.entity.EntityRenderPassEvent;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import net.labymod.v1_21_11.laby3d.pipeline.RenderTypeAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/MixinEntityModelFeatureRenderer_Events.class */
@Mixin({igi.class})
public class MixinEntityModelFeatureRenderer_Events {

    @Unique
    private cgk labyMod$entity;

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$beginEntityPass(hpn $$0, a $$1, hoq $$2, a $$3, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$endEntityPass(hpn $$0, a $$1, hoq $$2, a $$3, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }

    @Inject(method = {"renderModel"}, at = {@At("HEAD")}, cancellable = true)
    private <S> void labyMod$preEntityRender(h<S> $$0, ijs $$1, fzp $$2, hoq $$3, a $$4, CallbackInfo ci) {
        Object objC = $$0.c();
        if (!(objC instanceof idf)) {
            return;
        }
        idf entityRenderState = (idf) objC;
        EntityRenderStateAccessor<cgk> accessor = EntityRenderStateAccessor.self(entityRenderState);
        cgk entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        this.labyMod$entity = entity;
        labyMod$fireEntityRenderEvent(entity, Phase.PRE);
    }

    @WrapOperation(method = {"renderModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V")})
    private void labyMod$renderModel(gzp<?> instance, fzm $$0, fzp $$1, int $$2, int $$3, int $$4, Operation<Void> original, @Local(argsOnly = true) ijs renderType) {
        hne hneVar = this.labyMod$entity;
        if (!(hneVar instanceof hne)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        hne player = hneVar;
        if (!(instance instanceof hht)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        hht playerModel = (hht) instance;
        PlayerModelRenderEvent.Type type = PlayerModelRenderEvent.Type.PLAYER;
        if (playerModel instanceof hhr) {
            type = PlayerModelRenderEvent.Type.CAPE;
        } else if (((RenderTypeAccessor) renderType).getName().startsWith("armor")) {
            type = PlayerModelRenderEvent.Type.ARMOR;
        }
        PlayerModelRenderEvent event = (PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent((Player) CastUtil.cast(player), (PlayerModel) CastUtil.cast(playerModel), ((VanillaStackAccessor) $$0).stack(), Phase.PRE, $$2, type));
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
        Laby.fireEvent(new PlayerModelRenderEvent((Player) CastUtil.cast(player), (PlayerModel) CastUtil.cast(playerModel), ((VanillaStackAccessor) $$0).stack(), Phase.POST, $$2, type));
    }

    @Inject(method = {"renderModel"}, at = {@At("TAIL")})
    private <S> void labyMod$postEntityRender(h<S> $$0, ijs $$1, fzp $$2, hoq $$3, a $$4, CallbackInfo ci) {
        Object objC = $$0.c();
        if (!(objC instanceof idf)) {
            return;
        }
        idf entityRenderState = (idf) objC;
        EntityRenderStateAccessor<cgk> accessor = EntityRenderStateAccessor.self(entityRenderState);
        cgk entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        labyMod$fireEntityRenderEvent(entity, Phase.POST);
        this.labyMod$entity = null;
    }

    private void labyMod$fireEntityRenderEvent(cgk entity, Phase phase) {
        Laby.fireEvent(new EntityRenderEvent((Entity) CastUtil.cast(entity), phase));
    }
}

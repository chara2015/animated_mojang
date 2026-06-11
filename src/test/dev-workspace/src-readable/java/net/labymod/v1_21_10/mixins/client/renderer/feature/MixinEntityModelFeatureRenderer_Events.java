package net.labymod.v1_21_10.mixins.client.renderer.feature;

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
import net.labymod.v1_21_10.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/feature/MixinEntityModelFeatureRenderer_Events.class */
@Mixin({hxo.class})
public class MixinEntityModelFeatureRenderer_Events {

    @Unique
    private cdv labyMod$entity;

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$beginEntityPass(hgx $$0, a $$1, hfz $$2, a $$3, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.BEFORE));
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void labyMod$endEntityPass(hgx $$0, a $$1, hfz $$2, a $$3, CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderPassEvent(EntityRenderPassEvent.Phase.AFTER));
    }

    @Inject(method = {"renderModel"}, at = {@At("HEAD")}, cancellable = true)
    private <S> void labyMod$preEntityRender(i<S> $$0, hgk $$1, fud $$2, hfz $$3, a $$4, CallbackInfo ci) {
        Object objC = $$0.c();
        if (!(objC instanceof huk)) {
            return;
        }
        huk entityRenderState = (huk) objC;
        EntityRenderStateAccessor<cdv> accessor = EntityRenderStateAccessor.self(entityRenderState);
        cdv entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        this.labyMod$entity = entity;
        labyMod$fireEntityRenderEvent(entity, Phase.PRE);
    }

    @WrapOperation(method = {"renderModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V")})
    private void labyMod$renderModel(gwg<?> instance, fua $$0, fud $$1, int $$2, int $$3, int $$4, Operation<Void> original, @Local(argsOnly = true) hgk renderType) {
        hem hemVar = this.labyMod$entity;
        if (!(hemVar instanceof hem)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        hem player = hemVar;
        if (!(instance instanceof gwq)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        gwq playerModel = (gwq) instance;
        PlayerModelRenderEvent.Type type = PlayerModelRenderEvent.Type.PLAYER;
        if (playerModel instanceof gwo) {
            type = PlayerModelRenderEvent.Type.CAPE;
        } else if (renderType.c().startsWith("armor")) {
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
    private <S> void labyMod$postEntityRender(i<S> $$0, hgk $$1, fud $$2, hfz $$3, a $$4, CallbackInfo ci) {
        Object objC = $$0.c();
        if (!(objC instanceof huk)) {
            return;
        }
        huk entityRenderState = (huk) objC;
        EntityRenderStateAccessor<cdv> accessor = EntityRenderStateAccessor.self(entityRenderState);
        cdv entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        labyMod$fireEntityRenderEvent(entity, Phase.POST);
        this.labyMod$entity = null;
    }

    private void labyMod$fireEntityRenderEvent(cdv entity, Phase phase) {
        Laby.fireEvent(new EntityRenderEvent((Entity) CastUtil.cast(entity), phase));
    }
}

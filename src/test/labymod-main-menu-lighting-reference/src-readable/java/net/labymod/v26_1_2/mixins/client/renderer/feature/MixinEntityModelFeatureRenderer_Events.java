package net.labymod.v26_1_2.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1_2.client.util.EntityRenderStateAccessor;
import net.labymod.v26_1_2.laby3d.pipeline.RenderTypeAccessor;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.player.PlayerCapeModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/feature/MixinEntityModelFeatureRenderer_Events.class */
@Mixin({ModelFeatureRenderer.class})
public class MixinEntityModelFeatureRenderer_Events {

    @Unique
    private Entity labyMod$entity;

    @Inject(method = {"renderModel"}, at = {@At("HEAD")}, cancellable = true)
    private <S> void labyMod$preEntityRender(SubmitNodeStorage.ModelSubmit<S> $$0, RenderType $$1, VertexConsumer $$2, OutlineBufferSource $$3, MultiBufferSource.BufferSource $$4, CallbackInfo ci) {
        Object objState = $$0.state();
        if (!(objState instanceof EntityRenderState)) {
            return;
        }
        EntityRenderState entityRenderState = (EntityRenderState) objState;
        EntityRenderStateAccessor<Entity> accessor = EntityRenderStateAccessor.self(entityRenderState);
        Entity entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        this.labyMod$entity = entity;
        labyMod$fireEntityRenderEvent(entity, Phase.PRE);
    }

    @WrapOperation(method = {"renderModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V")})
    private void labyMod$renderModel(Model<?> instance, PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, int $$4, Operation<Void> original, @Local(argsOnly = true) RenderType renderType) {
        AbstractClientPlayer abstractClientPlayer = this.labyMod$entity;
        if (!(abstractClientPlayer instanceof AbstractClientPlayer)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        AbstractClientPlayer player = abstractClientPlayer;
        if (!(instance instanceof PlayerModel)) {
            original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
            return;
        }
        PlayerModel playerModel = (PlayerModel) instance;
        PlayerModelRenderEvent.Type type = PlayerModelRenderEvent.Type.PLAYER;
        if (playerModel instanceof PlayerCapeModel) {
            type = PlayerModelRenderEvent.Type.CAPE;
        } else if (((RenderTypeAccessor) renderType).getName().startsWith("armor")) {
            type = PlayerModelRenderEvent.Type.ARMOR;
        }
        PlayerModelRenderEvent event = (PlayerModelRenderEvent) Laby.fireEvent(new PlayerModelRenderEvent((Player) CastUtil.cast(player), (net.labymod.api.client.render.model.entity.player.PlayerModel) CastUtil.cast(playerModel), ((VanillaStackAccessor) $$0).stack(), Phase.PRE, $$2, type));
        if (event.isCancelled()) {
            return;
        }
        original.call(new Object[]{instance, $$0, $$1, Integer.valueOf($$2), Integer.valueOf($$3), Integer.valueOf($$4)});
        Laby.fireEvent(new PlayerModelRenderEvent((Player) CastUtil.cast(player), (net.labymod.api.client.render.model.entity.player.PlayerModel) CastUtil.cast(playerModel), ((VanillaStackAccessor) $$0).stack(), Phase.POST, $$2, type));
    }

    @Inject(method = {"renderModel"}, at = {@At("TAIL")})
    private <S> void labyMod$postEntityRender(SubmitNodeStorage.ModelSubmit<S> $$0, RenderType $$1, VertexConsumer $$2, OutlineBufferSource $$3, MultiBufferSource.BufferSource $$4, CallbackInfo ci) {
        Object objState = $$0.state();
        if (!(objState instanceof EntityRenderState)) {
            return;
        }
        EntityRenderState entityRenderState = (EntityRenderState) objState;
        EntityRenderStateAccessor<Entity> accessor = EntityRenderStateAccessor.self(entityRenderState);
        Entity entity = accessor.labyMod$getEntity();
        if (entity == null) {
            return;
        }
        labyMod$fireEntityRenderEvent(entity, Phase.POST);
        this.labyMod$entity = null;
    }

    private void labyMod$fireEntityRenderEvent(Entity entity, Phase phase) {
        Laby.fireEvent(new EntityRenderEvent((net.labymod.api.client.entity.Entity) CastUtil.cast(entity), phase));
    }
}

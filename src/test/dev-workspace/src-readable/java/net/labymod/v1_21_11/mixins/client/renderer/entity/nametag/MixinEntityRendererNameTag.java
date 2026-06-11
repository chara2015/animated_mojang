package net.labymod.v1_21_11.mixins.client.renderer.entity.nametag;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import net.labymod.v1_21_11.client.renderer.entity.NameTagTransformer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/nametag/MixinEntityRendererNameTag.class */
@Mixin({EntityRenderer.class})
public class MixinEntityRendererNameTag {

    @Shadow
    @Final
    private Font font;

    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer = new NameTagRenderer();

    @WrapOperation(method = {"submitNameTag"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;ILnet/minecraft/network/chat/Component;ZIDLnet/minecraft/client/renderer/state/CameraRenderState;)V")})
    private void labyMod$transformNameTag(SubmitNodeCollector instance, PoseStack poseStack, Vec3 vec3, int i0, Component component, boolean b, int i1, double v, CameraRenderState cameraRenderState, Operation<Void> original, @Local(argsOnly = true) LocalRef<EntityRenderState> entityRenderStateRef) {
        NameTagTransformer.transform(instance, poseStack, vec3, i0, component, b, i1, v, cameraRenderState, original, (EntityRenderState) entityRenderStateRef.get());
    }
}


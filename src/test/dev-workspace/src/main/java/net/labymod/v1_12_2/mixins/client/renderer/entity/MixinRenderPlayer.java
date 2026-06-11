package net.labymod.v1_12_2.mixins.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.util.RenderUtil;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.renderer.entity.VersionedShopItemLayer;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/MixinRenderPlayer.class */
@Mixin({cct.class})
public abstract class MixinRenderPlayer extends caa<bua> {
    @Shadow
    /* JADX INFO: renamed from: h, reason: merged with bridge method [inline-methods] */
    public abstract bqj b();

    protected /* bridge */ /* synthetic */ boolean b(vg vgVar) {
        return super.a((vp) vgVar);
    }

    public MixinRenderPlayer(bzf manager, bqf base, float scale) {
        super(manager, base, scale);
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V"}, at = {@At("RETURN")})
    private void labyMod$addCustomLayers(bzf rendermanager, boolean flag, CallbackInfo ci) {
        super.a(new VersionedShopItemLayer((cct) this));
    }

    @Inject(method = {"renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0)})
    public void labyMod$setScoreboardNameTagType(bua lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, String lvt_8_1_, double lvt_9_1_, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.SCOREBOARD);
    }

    @Inject(method = {"renderEntityName(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;D)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderLivingBase;renderEntityName(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;D)V", shift = At.Shift.BEFORE)})
    public void labyMod$setMainNameTagType(bua lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, String lvt_8_1_, double lvt_9_1_, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }

    @Inject(method = {"renderLeftArm"}, at = {@At("HEAD")})
    private void labyMod$preLeftHandRender(bua clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.PRE);
    }

    @Inject(method = {"renderLeftArm"}, at = {@At("TAIL")})
    private void labyMod$postLeftHandRender(bua clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.POST);
    }

    @Inject(method = {"renderRightArm"}, at = {@At("HEAD")})
    private void labyMod$preRightHandRender(bua clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.PRE);
    }

    @Inject(method = {"renderRightArm"}, at = {@At("TAIL")})
    private void labyMod$postRightHandRender(bua clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.POST);
    }

    private void labyMod$firePlayerModelRenderEvent(bua clientPlayer, MainHand hand, Phase phase) {
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        int packedLight = MinecraftUtil.getPackedLight(clientPlayer);
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player) clientPlayer, b(), stack, phase, hand, packedLight));
    }
}

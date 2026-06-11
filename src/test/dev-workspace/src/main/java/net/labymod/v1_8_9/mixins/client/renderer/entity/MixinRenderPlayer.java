package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.util.RenderUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.renderer.entity.VersionedShopItemLayer;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinRenderPlayer.class */
@Mixin({bln.class})
public abstract class MixinRenderPlayer extends bjl<bet> {
    @Shadow
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public abstract bbr b();

    protected /* bridge */ /* synthetic */ boolean b(pk pkVar) {
        return super.a((pr) pkVar);
    }

    public /* bridge */ /* synthetic */ void a(pk pkVar, double d, double d2, double d3) {
        super.b((pr) pkVar, d, d2, d3);
    }

    public MixinRenderPlayer(biu manager, bbo base, float scale) {
        super(manager, base, scale);
    }

    @Inject(method = {"<init>(Lnet/minecraft/client/renderer/entity/RenderManager;Z)V"}, at = {@At("RETURN")})
    private void labyMod$addCustomLayers(biu rendermanager, boolean flag, CallbackInfo ci) {
        super.a(new VersionedShopItemLayer((bln) this));
    }

    @Inject(method = {"renderOffsetLivingLabel(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;FD)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderPlayer;renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V", shift = At.Shift.BEFORE, ordinal = 0)})
    public void labyMod$setScoreboardNameTagType(bet entity, double x, double y, double z, String displayName, float f, double d, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.SCOREBOARD);
    }

    @Inject(method = {"renderOffsetLivingLabel(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDLjava/lang/String;FD)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;renderOffsetLivingLabel(Lnet/minecraft/entity/Entity;DDDLjava/lang/String;FD)V", shift = At.Shift.BEFORE, ordinal = 0)})
    public void labyMod$setMainNameTagType(bet entity, double x, double y, double z, String displayName, float f, double d, CallbackInfo ci) {
        RenderUtil.setNameTagType(TagType.MAIN_TAG);
    }

    @Inject(method = {"renderLeftArm"}, at = {@At("HEAD")})
    private void labyMod$preLeftHandRender(bet clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.PRE);
    }

    @Inject(method = {"renderLeftArm"}, at = {@At("TAIL")})
    private void labyMod$postLeftHandRender(bet clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.LEFT, Phase.POST);
    }

    @Inject(method = {"renderRightArm"}, at = {@At("HEAD")})
    private void labyMod$preRightHandRender(bet clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.PRE);
    }

    @Inject(method = {"renderRightArm"}, at = {@At("TAIL")})
    private void labyMod$postRightHandRender(bet clientPlayer, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, MainHand.RIGHT, Phase.POST);
    }

    private void labyMod$firePlayerModelRenderEvent(bet clientPlayer, MainHand hand, Phase phase) {
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        int packedLight = MinecraftUtil.getPackedLight(clientPlayer);
        Laby.references().renderEnvironmentContext().setPackedLight(packedLight);
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player) clientPlayer, b(), stack, phase, hand, packedLight));
    }
}

package net.labymod.v1_18_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.ChatInputOverlay;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.core.event.client.render.overlay.CameraOverlayRenderEventCaller;
import net.labymod.core.event.client.render.overlay.HudHealthFoodEventDispatcher;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/gui/MixinGui.class */
@Mixin({dzq.class})
public abstract class MixinGui extends dzr {
    private dtm labyMod$stack;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private dyr v;

    @Shadow
    private int U;

    @Shadow
    private int T;

    @Shadow
    private int y;

    @Shadow
    private long S;

    @Shadow
    private int P;

    @Shadow
    private long R;

    @Shadow
    private int Q;

    @Shadow
    @Final
    private Random u;

    @Shadow
    @Nullable
    public qk K;

    @Shadow
    private qk z;

    @Shadow
    private int A;

    @Shadow
    private boolean B;

    @Shadow
    protected abstract int a(int i);

    @Shadow
    protected abstract boj l();

    @Shadow
    protected abstract axy m();

    @Shadow
    protected abstract int a(@Nullable axy axyVar);

    @Shadow
    protected abstract void a(int i, int i2, float f, boj bojVar, buw buwVar, int i3);

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(dtm stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, false));
        });
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(dtm stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, false));
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 0)})
    private void labyMod$renderCrosshairPre(dzq instance, dtm poseStack, int i1, int i2, int i3, int i4, int i5, int i6, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{instance, poseStack, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Options;attackIndicator:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private dya labyMod$renderAttackIndicatorPre(dyv instance, Operation<dya> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        return cancelled.get() ? dya.a : (dya) original.call(new Object[]{instance});
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(dtm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 2)})
    private void labyMod$renderAttackIndicatorChargingPost(dtm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderHearts"}, at = {@At("HEAD")})
    private void labyMod$renderHeartsPre(dtm graphics, boj vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.s.n_().n();
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderHearts"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V")})
    private boolean labyMod$skipHeartRender(dzq self, dtm graphics, a type, int x, int y, int hardcoreFlag, boolean blink, boolean halfHeart) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderHearts"}, at = {@At("TAIL")})
    private void labyMod$renderHeartsPost(dtm graphics, boj vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.s.n_().n();
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1)})
    private void labyMod$renderFoodPre(dtm graphics, CallbackInfo ci) {
        Player playerL = l();
        if (playerL == null) {
            return;
        }
        Player labyPlayer = playerL;
        int rightAnchor = (this.T / 2) + 91;
        int bottomY = this.U - 39;
        int tick = this.y;
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, rightAnchor, bottomY, tick);
        });
    }

    @WrapWithCondition(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2))})
    private boolean labyMod$skipFoodBlit(dzq self, dtm stack, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2)})
    private void labyMod$renderFoodPost(dtm graphics, CallbackInfo ci) {
        Player playerL = l();
        if (playerL == null) {
            return;
        }
        Player labyPlayer = playerL;
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer);
        });
    }

    @ModifyExpressionValue(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I")})
    private int labyMod$adjustVehicleHeartRowsForSaturationWidget(int original) {
        return HudHealthFoodEventDispatcher.adjustVehicleHeartRows(original);
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(dtm stack, dqj objective, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(dtm stack, dqj objective, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(dtm stack, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("TAIL"))
    public void labymod$renderEffectsPost(dtm stack, InsertInfo insertInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$storePoseStackInstance(dtm poseStack, float $$1, CallbackInfo ci) {
        this.labyMod$stack = poseStack;
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(dzq gui, dtm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(dzq gui, dtm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }

    private void labyMod$renderOffhandHotbarSlot(dzq gui, dtm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        gui.b(stack, x, y, u, v, uWidth, vHeight);
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callOffhandTexturePost);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(dzq gui, int x, int y, float v, boj player, buw itemStack, int i) {
        labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack, i);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(dzq gui, int x, int y, float v, boj player, buw itemStack, int i) {
        labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack, i);
    }

    private void labyMod$renderOffhandHotbarItem(int x, int y, float v, boj player, buw itemStack, int i) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        a(x, y, v, player, itemStack, i);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, IngameOverlayElementRenderEventCaller::callOffhandItemPost);
    }

    @Overwrite
    public void a(qk message, boolean animatedMessage) {
        qk qkVar;
        ComponentMapper mapper = Laby.references().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            qkVar = message;
        } else {
            qkVar = (qk) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.z = qkVar;
        this.A = 60;
        this.B = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(dyv options) {
        if (Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get().booleanValue()) {
            return options.aW && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
        }
        return options.aW;
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private qk labyMod$fireIngameOverlayElementRenderEventTitle(dzq instance) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return null;
        }
        return this.K;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE)})
    private void labyMod$fireIngameOverlayElementRenderEventTitle(dtm matrixStackIn, float partialTicks, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderTextureOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderTextureOverlayPre(yt resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            CameraOverlayRenderEvent event = CameraOverlayRenderEventCaller.callPre(screenContext, resourceLocation.a(), opacity);
            if (event.isCancelled()) {
                ci.cancel();
            } else {
                this.labyMod$modifiedOverlayOpacity = event.getOpacity();
            }
        });
    }

    @ModifyVariable(method = {"renderTextureOverlay"}, at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float labyMod$modifyTextureOverlayOpacity(float opacity) {
        float modified = this.labyMod$modifiedOverlayOpacity;
        this.labyMod$modifiedOverlayOpacity = -1.0f;
        return modified >= 0.0f ? modified : opacity;
    }

    @Inject(method = {"renderTextureOverlay"}, at = {@At("TAIL")})
    private void labyMod$renderTextureOverlayPost(yt resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, resourceLocation.a(), opacity);
        });
    }
}

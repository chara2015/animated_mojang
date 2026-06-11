package net.labymod.v1_19_4.mixins.client.gui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import net.labymod.v1_19_4.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/MixinGui.class */
@Mixin({enq.class})
public abstract class MixinGui extends enr {
    private ehe labyMod$stack;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private emh w;

    @Shadow
    private int W;

    @Shadow
    private int V;

    @Shadow
    private int z;

    @Shadow
    private long U;

    @Shadow
    private int R;

    @Shadow
    private long T;

    @Shadow
    private int S;

    @Shadow
    @Final
    private apo v;

    @Shadow
    @Nullable
    public tj M;

    @Shadow
    private tj A;

    @Shadow
    private int B;

    @Shadow
    private boolean C;

    @Shadow
    protected abstract bym m();

    @Shadow
    protected abstract bfx n();

    @Shadow
    protected abstract int a(@Nullable bfx bfxVar);

    @Shadow
    protected abstract int a(int i);

    @Shadow
    protected abstract void a(ehe eheVar, int i, int i2, float f, bym bymVar, cfv cfvVar, int i3);

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(ehe stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, false));
        });
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(ehe stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, false));
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 0)})
    private void labyMod$renderCrosshair(ehe poseStack, int i1, int i2, int i3, int i4, int i5, int i6, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{poseStack, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/AttackIndicatorStatus;CROSSHAIR:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private els labyMod$renderAttackIndicatorPre(Operation<els> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return null;
        }
        return (els) original.call(new Object[0]);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(ehe poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 2)})
    private void labyMod$renderAttackIndicatorChargingPost(ehe poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderHearts"}, at = {@At("HEAD")})
    private void labyMod$renderHeartsPre(ehe graphics, bym vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.H.n_().n();
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderHearts"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V")})
    private boolean labyMod$skipHeartRender(enq self, ehe graphics, a type, int x, int y, int hardcoreFlag, boolean blink, boolean halfHeart) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderHearts"}, at = {@At("TAIL")})
    private void labyMod$renderHeartsPost(ehe graphics, bym vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.H.n_().n();
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1)})
    private void labyMod$renderFoodPre(ehe graphics, CallbackInfo ci) {
        Player playerM = m();
        if (playerM == null) {
            return;
        }
        Player labyPlayer = playerM;
        int rightAnchor = (this.V / 2) + 91;
        int bottomY = this.W - 39;
        int tick = this.z;
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, rightAnchor, bottomY, tick);
        });
    }

    @WrapWithCondition(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2))})
    private boolean labyMod$skipFoodBlit(ehe stack, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2)})
    private void labyMod$renderFoodPost(ehe graphics, CallbackInfo ci) {
        Player playerM = m();
        if (playerM == null) {
            return;
        }
        Player labyPlayer = playerM;
        MinecraftUtil.obtainScreenContextFromPoseStack(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer);
        });
    }

    @ModifyExpressionValue(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I")})
    private int labyMod$adjustVehicleHeartRowsForSaturationWidget(int original) {
        return HudHealthFoodEventDispatcher.adjustVehicleHeartRows(original);
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(ehe stack, edz objective, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(ehe stack, edz objective, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(ehe stack, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("TAIL"))
    public void labymod$renderEffectsPost(ehe stack, InsertInfo insertInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$storePoseStackInstance(ehe poseStack, float $$1, CallbackInfo ci) {
        this.labyMod$stack = poseStack;
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(ehe stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(stack, x, y, u, v, uWidth, vHeight);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(ehe stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(stack, x, y, u, v, uWidth, vHeight);
    }

    private void labyMod$renderOffhandHotbarSlot(ehe stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        enr.c(stack, x, y, u, v, uWidth, vHeight);
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callOffhandTexturePost);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lcom/mojang/blaze3d/vertex/PoseStack;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(enq gui, ehe poseStack, int x, int y, float v, bym player, cfv itemStack, int i) {
        labyMod$renderOffhandHotbarItem(poseStack, x, y, v, player, itemStack, i);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lcom/mojang/blaze3d/vertex/PoseStack;IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(enq gui, ehe poseStack, int x, int y, float v, bym player, cfv itemStack, int i) {
        labyMod$renderOffhandHotbarItem(poseStack, x, y, v, player, itemStack, i);
    }

    private void labyMod$renderOffhandHotbarItem(ehe poseStack, int x, int y, float v, bym player, cfv itemStack, int i) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        a(poseStack, x, y, v, player, itemStack, i);
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callOffhandItemPost);
    }

    @Overwrite
    public void a(tj message, boolean animatedMessage) {
        tj tjVar;
        ComponentMapper mapper = Laby.references().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            tjVar = message;
        } else {
            tjVar = (tj) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.A = tjVar;
        this.B = 60;
        this.C = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(eml options) {
        if (Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get().booleanValue()) {
            return options.Z && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
        }
        return options.Z;
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private tj labyMod$fireIngameOverlayElementRenderEventTitle(enq instance) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return null;
        }
        return this.M;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE)})
    private void labyMod$fireIngameOverlayElementRenderEventTitle(ehe matrixStackIn, float partialTicks, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderTextureOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderTextureOverlayPre(ehe poseStack, add resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
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
    private void labyMod$renderTextureOverlayPost(ehe poseStack, add resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, resourceLocation.a(), opacity);
        });
    }
}

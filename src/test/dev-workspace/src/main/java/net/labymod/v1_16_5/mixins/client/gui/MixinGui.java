package net.labymod.v1_16_5.mixins.client.gui;

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
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/MixinGui.class */
@Mixin({dkv.class})
public abstract class MixinGui extends dkw {
    private dfm labyMod$stack;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private djz j;

    @Shadow
    private int I;

    @Shadow
    private int H;

    @Shadow
    private int m;

    @Shadow
    private long G;

    @Shadow
    private int D;

    @Shadow
    private long F;

    @Shadow
    private int E;

    @Shadow
    @Final
    private Random i;

    @Shadow
    @Nullable
    public nr y;

    @Shadow
    private nr n;

    @Shadow
    private int o;

    @Shadow
    private boolean p;

    @Shadow
    protected abstract int a(int i);

    @Shadow
    protected abstract bfw k();

    @Shadow
    protected abstract aqm l();

    @Shadow
    protected abstract int a(@Nullable aqm aqmVar);

    @Shadow
    protected abstract void a(int i, int i2, float f, bfw bfwVar, bmb bmbVar);

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(dfm stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, false));
        });
    }

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;F)V"}, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(dfm stack, float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(((VanillaStackAccessor) stack).stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, false));
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 0)})
    private void labyMod$renderCrosshair(dkv instance, dfm poseStack, int i1, int i2, int i3, int i4, int i5, int i6, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{instance, poseStack, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Options;attackIndicator:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private dji labyMod$renderAttackIndicatorPre(dkd instance, Operation<dji> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        return cancelled.get() ? dji.a : (dji) original.call(new Object[]{instance});
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(dfm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.AFTER, ordinal = 2)})
    private void labyMod$renderAttackIndicatorChargingPost(dfm poseStack, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 0)})
    private void labyMod$renderHeartsPre(dfm poseStack, CallbackInfo ci) {
        Player playerK = k();
        if (playerK == null) {
            return;
        }
        Player labyPlayer = playerK;
        int leftAnchor = (this.H / 2) - 91;
        int bottomY = this.I - 39;
        int health = afm.f(playerK.dk());
        int absorption = afm.f(playerK.dT());
        float maxHealth = Math.max((float) playerK.b(arl.a), Math.max(this.E, health));
        int heartRows = afm.f(((maxHealth + absorption) / 2.0f) / 10.0f);
        int rowHeight = Math.max(10 - (heartRows - 2), 3);
        int offsetHeartIndex = -1;
        if (playerK.a(apw.j)) {
            offsetHeartIndex = this.m % afm.f(maxHealth + 5.0f);
        }
        boolean renderHighlight = this.G > ((long) this.m) && ((this.G - ((long) this.m)) / 3) % 2 == 1;
        boolean hardcore = ((bfw) playerK).l.h().n();
        int finalOffsetHeartIndex = offsetHeartIndex;
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, leftAnchor, bottomY, rowHeight, finalOffsetHeartIndex, maxHealth, health, this.E, absorption, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getPlayerVehicleWithHealth()Lnet/minecraft/world/entity/LivingEntity;"))})
    private boolean labyMod$skipHeartBlit(dkv self, dfm stack, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getPlayerVehicleWithHealth()Lnet/minecraft/world/entity/LivingEntity;")})
    private void labyMod$renderHeartsPost(dfm poseStack, CallbackInfo ci) {
        Player playerK = k();
        if (playerK == null) {
            return;
        }
        Player labyPlayer = playerK;
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer);
        });
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1)})
    private void labyMod$renderFoodPre(dfm poseStack, CallbackInfo ci) {
        Player playerK = k();
        if (playerK == null) {
            return;
        }
        Player labyPlayer = playerK;
        int rightAnchor = (this.H / 2) + 91;
        int bottomY = this.I - 39;
        int tick = this.m;
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, rightAnchor, bottomY, tick);
        });
    }

    @WrapWithCondition(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2))})
    private boolean labyMod$skipFoodBlit(dkv self, dfm stack, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal = 2)})
    private void labyMod$renderFoodPost(dfm poseStack, CallbackInfo ci) {
        Player playerK = k();
        if (playerK == null) {
            return;
        }
        Player labyPlayer = playerK;
        MinecraftUtil.obtainScreenContextFromPoseStack(poseStack, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer);
        });
    }

    @ModifyExpressionValue(method = {"renderPlayerHealth"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I")})
    private int labyMod$adjustVehicleHeartRowsForSaturationWidget(int original) {
        return HudHealthFoodEventDispatcher.adjustVehicleHeartRows(original);
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(dfm stack, ddk objective, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Insert(method = {"displayScoreboardSidebar(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/scores/Objective;)V"}, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(dfm stack, ddk objective, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("HEAD"), cancellable = true)
    public void labymod$renderEffectsPre(dfm stack, InsertInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Insert(method = {"renderEffects(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("TAIL"))
    public void labymod$renderEffectsPost(dfm stack, InsertInfo insertInfo) {
        MinecraftUtil.obtainScreenContextFromPoseStack(stack, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$storePoseStackInstance(dfm poseStack, float $$1, CallbackInfo ci) {
        this.labyMod$stack = poseStack;
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(dkv gui, dfm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(dkv gui, dfm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, stack, x, y, u, v, uWidth, vHeight);
    }

    private void labyMod$renderOffhandHotbarSlot(dkv gui, dfm stack, int x, int y, int u, int v, int uWidth, int vHeight) {
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

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(dkv gui, int x, int y, float v, bfw player, bmb itemStack) {
        labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(IIFLnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(dkv instance, int x, int y, float v, bfw player, bmb itemStack) {
        labyMod$renderOffhandHotbarItem(x, y, v, player, itemStack);
    }

    private void labyMod$renderOffhandHotbarItem(int x, int y, float v, bfw player, bmb itemStack) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        a(x, y, v, player, itemStack);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, IngameOverlayElementRenderEventCaller::callOffhandItemPost);
    }

    @Overwrite
    public void a(nr message, boolean animatedMessage) {
        nr nrVar;
        ComponentMapper mapper = Laby.references().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            nrVar = message;
        } else {
            nrVar = (nr) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.n = nrVar;
        this.o = 60;
        this.p = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z", ordinal = 2))
    private boolean isGuiHidden(dkd options) {
        if (Laby.labyAPI().config().ingame().advancedChat().showChatOnHiddenGui().get().booleanValue()) {
            return options.aI && !(Laby.labyAPI().minecraft().minecraftWindow().currentLabyScreen() instanceof ChatInputOverlay);
        }
        return options.aI;
    }

    @Redirect(method = {"render"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;title:Lnet/minecraft/network/chat/Component;", ordinal = 0))
    private nr labyMod$fireIngameOverlayElementRenderEventTitle(dkv instance) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return null;
        }
        return this.y;
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SubtitleOverlay;render(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE)})
    private void labyMod$fireIngameOverlayElementRenderEventTitle(dfm matrixStackIn, float partialTicks, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderPumpkin"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderPumpkinPre(CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            CameraOverlayRenderEvent event = CameraOverlayRenderEventCaller.callPre(screenContext, CameraOverlayRenderEvent.OverlayType.PUMPKIN, 1.0f);
            if (event.isCancelled()) {
                ci.cancel();
            } else {
                this.labyMod$modifiedOverlayOpacity = event.getOpacity();
            }
        });
    }

    @WrapOperation(method = {"renderPumpkin"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", ordinal = 0)})
    private void labyMod$modifyPumpkinAlpha(float r, float g, float b, float a, Operation<Void> original) {
        float modified = this.labyMod$modifiedOverlayOpacity;
        this.labyMod$modifiedOverlayOpacity = -1.0f;
        Object[] objArr = new Object[4];
        objArr[0] = Float.valueOf(r);
        objArr[1] = Float.valueOf(g);
        objArr[2] = Float.valueOf(b);
        objArr[3] = Float.valueOf(modified >= 0.0f ? modified : a);
        original.call(objArr);
    }

    @Inject(method = {"renderPumpkin"}, at = {@At("TAIL")})
    private void labyMod$renderPumpkinPost(CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromPoseStack(this.labyMod$stack, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, CameraOverlayRenderEvent.OverlayType.PUMPKIN, 1.0f);
        });
    }
}

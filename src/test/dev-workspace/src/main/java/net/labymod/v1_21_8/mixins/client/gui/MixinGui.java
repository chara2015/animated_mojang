package net.labymod.v1_21_8.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.core.event.client.render.overlay.CameraOverlayRenderEventCaller;
import net.labymod.core.event.client.render.overlay.HudHealthFoodEventDispatcher;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_21_8.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/gui/MixinGui.class */
@Mixin({fxa.class})
public abstract class MixinGui {
    private fxb labyMod$graphics;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private fue ag;

    @Shadow
    private int ai;

    @Shadow
    @Nullable
    public xo av;

    @Shadow
    private xo aj;

    @Shadow
    private int ak;

    @Shadow
    private boolean al;

    @Shadow
    protected abstract int a(int i);

    @Shadow
    protected abstract void a(fxb fxbVar, int i, int i2, ftu ftuVar, cut cutVar, dcv dcvVar, int i3);

    @Inject(method = {"render"}, at = {@At("HEAD")})
    public void labyMod$renderGameOverlayPre(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, this.ag.n.Y));
        });
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    public void labyMod$renderGameOverlayPost(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, this.ag.n.Y));
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0)})
    private void labyMod$renderCrosshair(fxb graphics, RenderPipeline renderPipeline, ame resourceLocation, int i1, int i2, int i3, int i4, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{graphics, renderPipeline, resourceLocation, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/AttackIndicatorStatus;CROSSHAIR:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private ftl labyMod$renderAttackIndicatorPre(Operation<ftl> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(this.labyMod$graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return null;
        }
        return (ftl) original.call(new Object[0]);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(fxb graphics, ftu $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V", shift = At.Shift.AFTER)})
    private void labyMod$renderAttackIndicatorChargingPost(fxb graphics, ftu $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Redirect(method = {"getAirBubbleYLine"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(fxa gui, int health) {
        HudWidgetDropzone attachedDropzone;
        HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        int visibleVehicleHeartRows = a(health);
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame() && (attachedDropzone = saturation.getAttachedDropzone()) != null && attachedDropzone.getId().equals("saturation")) {
            return visibleVehicleHeartRows + 1;
        }
        return visibleVehicleHeartRows;
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$renderScoreboardPre(fxb graphics, fjp objective, CallbackInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("TAIL")})
    public void labyMod$renderScoreboardPost(fxb graphics, fjp objective, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Inject(method = {"renderEffects"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$renderEffectsPre(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"renderEffects"}, at = {@At("TAIL")})
    public void labymod$renderEffectsPost(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$storeGuiGraphicsInstance(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(fxb graphics, RenderPipeline renderPipeline, ame location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderPipeline, location, $$1, $$2, $$3, $$4);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(fxb graphics, RenderPipeline renderPipeline, ame location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderPipeline, location, $$1, $$2, $$3, $$4);
    }

    private void labyMod$renderOffhandHotbarSlot(fxb graphics, RenderPipeline renderPipeline, ame location, int x, int y, int uWidth, int vHeight) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        graphics.a(renderPipeline, location, x, y, uWidth, vHeight);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callOffhandTexturePost);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(fxa instance, fxb graphics, int x, int y, ftu deltaTracker, cut player, dcv itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(fxa instance, fxb graphics, int x, int y, ftu deltaTracker, cut player, dcv itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    private void labyMod$renderOffhandHotbarItem(fxb graphics, int x, int y, ftu v, cut player, dcv itemStack, int i) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        a(graphics, x, y, v, player, itemStack, i);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callOffhandItemPost);
    }

    @Overwrite
    public void a(xo message, boolean animatedMessage) {
        xo xoVar;
        ComponentMapper mapper = Laby.references().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            xoVar = message;
        } else {
            xoVar = (xo) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.aj = xoVar;
        this.ak = 60;
        this.al = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Inject(method = {"renderTitle"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"renderTitle"}, at = {@At("TAIL")})
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(fxb graphics, ftu deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderTextureOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderTextureOverlayPre(fxb graphics, ame resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
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
    private void labyMod$renderTextureOverlayPost(fxb graphics, ame resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, resourceLocation.a(), opacity);
        });
    }

    @Inject(method = {"renderFood"}, at = {@At("HEAD")})
    private void labyMod$renderFoodPre(fxb graphics, cut vanillaPlayer, int y, int x, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        int tick = this.ai;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, x, y, tick);
        });
    }

    @WrapWithCondition(method = {"renderFood"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V")})
    private boolean labyMod$skipFoodBlit(fxb self, RenderPipeline pipeline, ame sprite, int x, int y, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderFood"}, at = {@At("TAIL")})
    private void labyMod$renderFoodPost(fxb graphics, cut vanillaPlayer, int y, int x, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer, x, y);
        });
    }

    @Inject(method = {"renderHearts"}, at = {@At("HEAD")})
    private void labyMod$renderHeartsPre(fxb graphics, cut vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.ai().D_().l();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderHearts"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V")})
    private boolean labyMod$skipHeartRender(fxa self, fxb graphics, c type, int x, int y, boolean hardcore, boolean blink, boolean halfHeart) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderHearts"}, at = {@At("TAIL")})
    private void labyMod$renderHeartsPost(fxb graphics, cut vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.ai().D_().l();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }
}

package net.labymod.v1_21_4.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.core.event.client.render.overlay.CameraOverlayRenderEventCaller;
import net.labymod.core.event.client.render.overlay.HudHealthFoodEventDispatcher;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/MixinGui.class */
@Mixin({foe.class})
public abstract class MixinGui {
    private fof labyMod$graphics;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private flk ak;

    @Shadow
    private int am;

    @Shadow
    @Nullable
    public wp az;

    @Shadow
    private wp an;

    @Shadow
    private int ao;

    @Shadow
    private boolean ap;

    @Shadow
    protected abstract int a(int i);

    @Shadow
    protected abstract void a(fof fofVar, int i, int i2, fla flaVar, coy coyVar, cwq cwqVar, int i3);

    @Inject(method = {"render"}, at = {@At("HEAD")})
    public void labyMod$renderGameOverlayPre(fof graphics, fla deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(graphics.c().stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, this.ak.n.X));
        });
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    public void labyMod$renderGameOverlayPost(fof graphics, fla deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(graphics.c().stack(), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, this.ak.n.X));
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0)})
    private void labyMod$renderCrosshair(fof graphics, Function function, akv resourceLocation, int i1, int i2, int i3, int i4, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{graphics, function, resourceLocation, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"renderCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/AttackIndicatorStatus;CROSSHAIR:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private fkr labyMod$renderAttackIndicatorPre(Operation<fkr> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(this.labyMod$graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return null;
        }
        return (fkr) original.call(new Object[0]);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(fof graphics, fla $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"renderCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIIIIIII)V", shift = At.Shift.AFTER)})
    private void labyMod$renderAttackIndicatorChargingPost(fof graphics, fla $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Redirect(method = {"getAirBubbleYLine"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(foe gui, int health) {
        HudWidgetDropzone attachedDropzone;
        HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        int visibleVehicleHeartRows = a(health);
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame() && (attachedDropzone = saturation.getAttachedDropzone()) != null && attachedDropzone.getId().equals("saturation")) {
            return visibleVehicleHeartRows + 1;
        }
        return visibleVehicleHeartRows;
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$renderScoreboardPre(fof graphics, fby objective, CallbackInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("TAIL")})
    public void labyMod$renderScoreboardPost(fof graphics, fby objective, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Inject(method = {"renderEffects"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$renderEffectsPre(fof graphics, fla deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"renderEffects"}, at = {@At("TAIL")})
    public void labymod$renderEffectsPost(fof graphics, fla deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void labyMod$storeGuiGraphicsInstance(fof graphics, fla deltaTracker, CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(fof graphics, Function<akv, gmj> renderTypeMapper, akv location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderTypeMapper, location, $$1, $$2, $$3, $$4);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(fof graphics, Function<akv, gmj> renderTypeMapper, akv location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderTypeMapper, location, $$1, $$2, $$3, $$4);
    }

    private void labyMod$renderOffhandHotbarSlot(fof graphics, Function<akv, gmj> renderTypeMapper, akv location, int x, int y, int uWidth, int vHeight) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        graphics.a(renderTypeMapper, location, x, y, uWidth, vHeight);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callOffhandTexturePost);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(foe instance, fof graphics, int x, int y, fla deltaTracker, coy player, cwq itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    @Redirect(method = {"renderItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(foe instance, fof graphics, int x, int y, fla deltaTracker, coy player, cwq itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    private void labyMod$renderOffhandHotbarItem(fof graphics, int x, int y, fla v, coy player, cwq itemStack, int i) {
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
    public void a(wp message, boolean animatedMessage) {
        wp wpVar;
        ComponentMapper mapper = Laby.references().componentMapper();
        Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            wpVar = message;
        } else {
            wpVar = (wp) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.an = wpVar;
        this.ao = 60;
        this.ap = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Inject(method = {"renderTitle"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(fof graphics, fla deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"renderTitle"}, at = {@At("TAIL")})
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(fof graphics, fla deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderTextureOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderTextureOverlayPre(fof graphics, akv resourceLocation, float opacity, CallbackInfo ci) {
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
    private void labyMod$renderTextureOverlayPost(fof graphics, akv resourceLocation, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, resourceLocation.a(), opacity);
        });
    }

    @Inject(method = {"renderFood"}, at = {@At("HEAD")})
    private void labyMod$renderFoodPre(fof graphics, coy vanillaPlayer, int y, int x, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        int tick = this.am;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, x, y, tick);
        });
    }

    @WrapWithCondition(method = {"renderFood"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIII)V")})
    private boolean labyMod$skipFoodBlit(fof self, Function<akv, gmj> renderTypeMapper, akv sprite, int x, int y, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderFood"}, at = {@At("TAIL")})
    private void labyMod$renderFoodPost(fof graphics, coy vanillaPlayer, int y, int x, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer, x, y);
        });
    }

    @Inject(method = {"renderHearts"}, at = {@At("HEAD")})
    private void labyMod$renderHeartsPre(fof graphics, coy vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.dV().D_().l();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderHearts"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V")})
    private boolean labyMod$skipHeartRender(foe self, fof graphics, b type, int x, int y, boolean hardcore, boolean blink, boolean halfHeart) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderHearts"}, at = {@At("TAIL")})
    private void labyMod$renderHeartsPost(fof graphics, coy vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        Player labyPlayer = (Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.dV().D_().l();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }
}

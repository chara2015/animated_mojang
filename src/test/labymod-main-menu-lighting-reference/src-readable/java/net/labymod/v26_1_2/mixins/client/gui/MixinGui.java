package net.labymod.v26_1_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
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
import net.labymod.v26_1_2.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Objective;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/gui/MixinGui.class */
@Mixin({Gui.class})
public abstract class MixinGui {
    private GuiGraphicsExtractor labyMod$graphics;

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private int tickCount;

    @Shadow
    @Nullable
    public Component title;

    @Shadow
    private Component overlayMessageString;

    @Shadow
    private int overlayMessageTime;

    @Shadow
    private boolean animateOverlayMessageColor;

    @Shadow
    protected abstract int getVisibleVehicleHeartRows(int i);

    @Shadow
    protected abstract void extractSlot(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i2, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int i3);

    @Inject(method = {"extractRenderState"}, at = {@At("HEAD")})
    public void labyMod$renderGameOverlayPre(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, this.minecraft.options.hideGui));
        });
    }

    @Inject(method = {"extractRenderState"}, at = {@At("TAIL")})
    public void labyMod$renderGameOverlayPost(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), Laby.labyAPI().minecraft().mouse(), Laby.labyAPI().minecraft().getTickDelta(), context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, this.minecraft.options.hideGui));
        });
    }

    @WrapOperation(method = {"extractCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0)})
    private void labyMod$renderCrosshair(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier resourceLocation, int i1, int i2, int i3, int i4, Operation<Void> original) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                original.call(new Object[]{graphics, renderPipeline, resourceLocation, Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
                IngameOverlayElementRenderEventCaller.callCrossHairPost(screenContext);
            }
        });
    }

    @WrapOperation(method = {"extractCrosshair"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/AttackIndicatorStatus;CROSSHAIR:Lnet/minecraft/client/AttackIndicatorStatus;")})
    private AttackIndicatorStatus labyMod$renderAttackIndicatorPre(Operation<AttackIndicatorStatus> original) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(this.labyMod$graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return null;
        }
        return (AttackIndicatorStatus) original.call(new Object[0]);
    }

    @Inject(method = {"extractCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", shift = At.Shift.AFTER, ordinal = 1)})
    private void labyMod$renderAttackIndicatorFullyChargedPost(GuiGraphicsExtractor graphics, DeltaTracker $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Inject(method = {"extractCrosshair"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIIIIIII)V", shift = At.Shift.AFTER)})
    private void labyMod$renderAttackIndicatorChargingPost(GuiGraphicsExtractor graphics, DeltaTracker $$1, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
    }

    @Redirect(method = {"getAirBubbleYLine"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;getVisibleVehicleHeartRows(I)I"))
    public int labyMod$getVisibleVehicleHeartRows(Gui gui, int health) {
        HudWidgetDropzone attachedDropzone;
        HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        int visibleVehicleHeartRows = getVisibleVehicleHeartRows(health);
        if (saturation != null && saturation.isEnabled() && saturation.isVisibleInGame() && (attachedDropzone = saturation.getAttachedDropzone()) != null && attachedDropzone.getId().equals("saturation")) {
            return visibleVehicleHeartRows + 1;
        }
        return visibleVehicleHeartRows;
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$renderScoreboardPre(GuiGraphicsExtractor graphics, Objective objective, CallbackInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Inject(method = {"displayScoreboardSidebar"}, at = {@At("TAIL")})
    public void labyMod$renderScoreboardPost(GuiGraphicsExtractor graphics, Objective objective, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @Inject(method = {"extractEffects"}, at = {@At("HEAD")}, cancellable = true)
    public void labymod$renderEffectsPre(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callPotionEffectsPre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"extractEffects"}, at = {@At("TAIL")})
    public void labymod$renderEffectsPost(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callPotionEffectsPost);
    }

    @Inject(method = {"extractRenderState"}, at = {@At("HEAD")})
    private void labyMod$storeGuiGraphicsInstance(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        this.labyMod$graphics = graphics;
    }

    @Redirect(method = {"extractItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderPipeline, location, $$1, $$2, $$3, $$4);
    }

    @Redirect(method = {"extractItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int $$1, int $$2, int $$3, int $$4) {
        labyMod$renderOffhandHotbarSlot(graphics, renderPipeline, location, $$1, $$2, $$3, $$4);
    }

    private void labyMod$renderOffhandHotbarSlot(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int x, int y, int uWidth, int vHeight) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        graphics.blitSprite(renderPipeline, location, x, y, uWidth, vHeight);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callOffhandTexturePost);
    }

    @Redirect(method = {"extractItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(Gui instance, GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    @Redirect(method = {"extractItemHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractSlot(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(Gui instance, GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int i) {
        labyMod$renderOffhandHotbarItem(graphics, x, y, deltaTracker, player, itemStack, i);
    }

    private void labyMod$renderOffhandHotbarItem(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker v, Player player, ItemStack itemStack, int i) {
        AtomicBoolean cancelled = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                cancelled.set(true);
            }
        });
        if (cancelled.get()) {
            return;
        }
        extractSlot(graphics, x, y, v, player, itemStack, i);
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callOffhandItemPost);
    }

    @Overwrite
    public void setOverlayMessage(Component message, boolean animatedMessage) {
        Component component;
        ComponentMapper mapper = Laby.references().componentMapper();
        net.labymod.api.client.component.Component mapped = mapper.fromMinecraftComponent(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        net.labymod.api.client.component.Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            component = message;
        } else {
            component = (Component) mapper.toMinecraftComponent(modifiedMessage);
        }
        this.overlayMessageString = component;
        this.overlayMessageTime = 60;
        this.animateOverlayMessageColor = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Inject(method = {"extractTitle"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$fireIngameOverlayElementRenderEventTitlePre(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                ci.cancel();
            }
        });
    }

    @Inject(method = {"extractTitle"}, at = {@At("TAIL")})
    private void labyMod$fireIngameOverlayElementRenderEventTitlePost(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"extractTextureOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$extractTextureOverlayPre(GuiGraphicsExtractor graphics, Identifier identifier, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            CameraOverlayRenderEvent event = CameraOverlayRenderEventCaller.callPre(screenContext, identifier.getPath(), opacity);
            if (event.isCancelled()) {
                ci.cancel();
            } else {
                this.labyMod$modifiedOverlayOpacity = event.getOpacity();
            }
        });
    }

    @ModifyVariable(method = {"extractTextureOverlay"}, at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float labyMod$modifyTextureOverlayOpacity(float opacity) {
        float modified = this.labyMod$modifiedOverlayOpacity;
        this.labyMod$modifiedOverlayOpacity = -1.0f;
        return modified >= 0.0f ? modified : opacity;
    }

    @Inject(method = {"extractTextureOverlay"}, at = {@At("TAIL")})
    private void labyMod$extractTextureOverlayPost(GuiGraphicsExtractor graphics, Identifier identifier, float opacity, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, identifier.getPath(), opacity);
        });
    }

    @Inject(method = {"extractFood"}, at = {@At("HEAD")})
    private void labyMod$renderFoodPre(GuiGraphicsExtractor graphics, Player vanillaPlayer, int y, int x, CallbackInfo ci) {
        net.labymod.api.client.entity.player.Player labyPlayer = (net.labymod.api.client.entity.player.Player) vanillaPlayer;
        int tick = this.tickCount;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, x, y, tick);
        });
    }

    @WrapWithCondition(method = {"extractFood"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V")})
    private boolean labyMod$skipFoodBlit(GuiGraphicsExtractor self, RenderPipeline pipeline, Identifier sprite, int x, int y, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"extractFood"}, at = {@At("TAIL")})
    private void labyMod$renderFoodPost(GuiGraphicsExtractor graphics, Player vanillaPlayer, int y, int x, CallbackInfo ci) {
        net.labymod.api.client.entity.player.Player labyPlayer = (net.labymod.api.client.entity.player.Player) vanillaPlayer;
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer, x, y);
        });
    }

    @Inject(method = {"extractHearts"}, at = {@At("HEAD")})
    private void labyMod$renderHeartsPre(GuiGraphicsExtractor graphics, Player vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        net.labymod.api.client.entity.player.Player labyPlayer = (net.labymod.api.client.entity.player.Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.level().getLevelData().isHardcore();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"extractHearts"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;extractHeart(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V")})
    private boolean labyMod$skipHeartRender(Gui self, GuiGraphicsExtractor graphics, Gui.HeartType type, int x, int y, boolean hardcore, boolean blink, boolean halfHeart) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"extractHearts"}, at = {@At("TAIL")})
    private void labyMod$renderHeartsPost(GuiGraphicsExtractor graphics, Player vanillaPlayer, int x, int y, int rowHeight, int offsetHeartIndex, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, CallbackInfo ci) {
        net.labymod.api.client.entity.player.Player labyPlayer = (net.labymod.api.client.entity.player.Player) vanillaPlayer;
        boolean hardcore = vanillaPlayer.level().getLevelData().isHardcore();
        MinecraftUtil.obtainScreenContextFromGraphics(graphics, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer, x, y, rowHeight, offsetHeartIndex, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore);
        });
    }
}

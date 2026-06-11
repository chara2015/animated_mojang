package net.labymod.v1_12_2.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.core.event.client.render.overlay.CameraOverlayRenderEventCaller;
import net.labymod.core.event.client.render.overlay.HudHealthFoodEventDispatcher;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiIngame.class */
@Mixin({biq.class})
public abstract class MixinGuiIngame extends bir {

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;
    private static final int LABYMOD$CROSSHAIR = 1;
    private static final int LABYMOD$HOTBAR = 2;

    @Shadow
    @Final
    private bib j;

    @Shadow
    private int m;

    @Shadow
    private long F;

    @Shadow
    private long G;

    @Shadow
    private int D;

    @Shadow
    private int E;

    @Shadow
    @Final
    private Random i;

    @Shadow
    public int x;

    @Shadow
    private String n;

    @Shadow
    private int o;

    @Shadow
    private boolean p;

    @Shadow
    protected abstract void a(int i, int i2, float f, aed aedVar, aip aipVar);

    @Inject(method = {"renderAttackIndicator"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$renderCrosshairPre(float partialTicks, bit resolution, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (!IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                return;
            }
            ci.cancel();
            if (this.j.t.N != 1 || IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                return;
            }
            Laby3D laby3D = Laby.references().laby3D();
            laby3D.storeStates();
            bus.a(r.i, l.k, r.e, l.n);
            float strengthScale = this.j.h.n(0.0f);
            boolean lvt_5_1_ = false;
            if ((this.j.i instanceof vp) && strengthScale >= 1.0f) {
                lvt_5_1_ = (this.j.h.dr() > 5.0f) & this.j.i.aC();
            }
            int x = (resolution.a() / 2) - 8;
            int y = ((resolution.b() / 2) - 7) + 16;
            if (lvt_5_1_) {
                b(x, y, 68, 94, 16, 16);
            } else if (strengthScale < 1.0f) {
                b(x, y, 36, 94, 16, 4);
                b(x, y, 52, 94, (int) (strengthScale * 17.0f), 4);
            }
            IngameOverlayElementRenderEventCaller.callAttackIndicatorPost(screenContext);
            laby3D.restoreStates();
        });
    }

    @Insert(method = {"renderAttackIndicator"}, at = @At("TAIL"))
    public void labyMod$renderCrosshairAndAttackIndicatorPost(float partialTicks, bit resolution, InsertInfo callbackInfo) {
        if (this.j.t.N == 1) {
            MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
        }
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callCrossHairPost);
    }

    @Redirect(method = {"renderAttackIndicator"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;getCooledAttackStrength(F)F"))
    public float labyMod$renderCrosshairAttackIndicatorPre(bud instance, float value) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return 1.0f;
        }
        return instance.n(value);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;getCooledAttackStrength(F)F"))
    public float labyMod$renderHotbarAttackIndicatorPre(bud instance, float value) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callAttackIndicatorPre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return 1.0f;
        }
        return instance.n(value);
    }

    @Inject(method = {"renderHotbar"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderHelper;disableStandardItemLighting()V", shift = At.Shift.BEFORE)})
    public void labyMod$renderHotbarAttackIndicatorPost(bit scaledResolution, float partialTicks, CallbackInfo ci) {
        if (this.j.t.N == 2) {
            MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callAttackIndicatorPost);
        }
    }

    @Insert(method = {"renderGameOverlay(F)V"}, at = @At("HEAD"))
    public void labyMod$renderGameOverlayPre(float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(VersionedStackProvider.DEFAULT_STACK, Laby.labyAPI().minecraft().mouse(), partialTicks, context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.PRE, false));
        });
    }

    @Insert(method = {"renderGameOverlay(F)V"}, at = @At("TAIL"))
    public void labyMod$renderGameOverlayPost(float partialTicks, InsertInfo callback) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(VersionedStackProvider.DEFAULT_STACK, Laby.labyAPI().minecraft().mouse(), partialTicks, context -> {
            Laby.fireEvent(new IngameOverlayRenderEvent(context, Phase.POST, false));
        });
    }

    @Insert(method = {"renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(bhg objective, bit scaledResolution, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Insert(method = {"renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"}, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(bhg objective, bit scaledResolution, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @ModifyConstant(method = {"renderScoreboard"}, constant = {@Constant(intValue = 553648127)})
    private int labyMod$addOpacity(int color) {
        return color | (-16777216);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventTextureLeft(biq gui, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, x, y, u, v, uWidth, vHeight);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 3))
    private void labyMod$callIngameOverlayElementRenderEventTextureRight(biq gui, int x, int y, int u, int v, int uWidth, int vHeight) {
        labyMod$renderOffhandHotbarSlot(gui, x, y, u, v, uWidth, vHeight);
    }

    private void labyMod$renderOffhandHotbarSlot(bir gui, int x, int y, int u, int v, int uWidth, int vHeight) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandTexturePre(screenContext)) {
                return;
            }
            gui.b(x, y, u, v, uWidth, vHeight);
            IngameOverlayElementRenderEventCaller.callOffhandTexturePost(screenContext);
        });
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIFLnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V", ordinal = 1))
    private void labyMod$callIngameOverlayElementRenderEventItemLeft(biq gui, int x, int y, float partialTicks, aed player, aip itemStack) {
        labyMod$renderOffhandHotbarItem(x, y, partialTicks, player, itemStack);
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderHotbarItem(IIFLnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V", ordinal = 2))
    private void labyMod$callIngameOverlayElementRenderEventItemRight(biq gui, int x, int y, float partialTicks, aed player, aip itemStack) {
        labyMod$renderOffhandHotbarItem(x, y, partialTicks, player, itemStack);
    }

    private void labyMod$renderOffhandHotbarItem(int x, int y, float partialTicks, aed player, aip itemStack) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callOffhandItemPre(screenContext)) {
                return;
            }
            a(x, y, partialTicks, player, itemStack);
            IngameOverlayElementRenderEventCaller.callOffhandItemPost(screenContext);
        });
    }

    @Overwrite
    public void a(String message, boolean animatedMessage) {
        String strD;
        LegacyComponentSerializer serializer = Laby.references().componentRenderer().legacySectionSerializer();
        Component mapped = serializer.deserialize(message);
        ActionBarReceiveEvent event = ActionBarReceiveEventCaller.callPre(mapped, animatedMessage);
        if (event.isCancelled()) {
            return;
        }
        Component modifiedMessage = event.getMessage();
        ComponentMapper componentMapper = Laby.references().componentMapper();
        if (modifiedMessage == mapped) {
            strD = message;
        } else {
            strD = ((hh) componentMapper.toMinecraftComponent(modifiedMessage)).d();
        }
        this.n = strD;
        this.o = 60;
        this.p = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Redirect(method = {"renderPlayerStats"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 13))
    private void labyMod$renderAirBubble(biq instance, int i1, int i2, int i3, int i4, int i5, int i6) {
        if (labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        b(i1, i2, i3, i4, i5, i6);
    }

    @Redirect(method = {"renderPlayerStats"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 14))
    private void labyMod$renderPoppingAirBubble(biq instance, int i1, int i2, int i3, int i4, int i5, int i6) {
        if (labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        b(i1, i2, i3, i4, i5, i6);
    }

    private boolean labyMod$isSaturationHudWidgetDisplayed() {
        HudWidgetDropzone attachedDropzone;
        HudWidget<?> saturation = Laby.labyAPI().hudWidgetRegistry().getById("saturation");
        return saturation != null && saturation.isEnabled() && saturation.isVisibleInGame() && (attachedDropzone = saturation.getAttachedDropzone()) != null && attachedDropzone.getId().equals("saturation");
    }

    @Redirect(method = {"renderGameOverlay"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiIngame;titlesTimer:I"))
    private int labyMod$fireIngameOverlayElementRenderEventTitle(biq instance) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return 0;
        }
        return this.x;
    }

    @Inject(method = {"renderGameOverlay"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", shift = At.Shift.BEFORE)})
    private void labyMod$fireIngameOverlayElementRenderEventTitle(float partialTicks, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callTitlePost);
    }

    @Inject(method = {"renderPumpkinOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderPumpkinOverlayPre(bit scaledResolution, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            CameraOverlayRenderEvent event = CameraOverlayRenderEventCaller.callPre(screenContext, CameraOverlayRenderEvent.OverlayType.PUMPKIN, 1.0f);
            if (event.isCancelled()) {
                ci.cancel();
            } else {
                this.labyMod$modifiedOverlayOpacity = event.getOpacity();
            }
        });
    }

    @WrapOperation(method = {"renderPumpkinOverlay"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", ordinal = 0)})
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

    @Inject(method = {"renderPumpkinOverlay"}, at = {@At("TAIL")})
    private void labyMod$renderPumpkinOverlayPost(bit scaledResolution, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, CameraOverlayRenderEvent.OverlayType.PUMPKIN, 1.0f);
        });
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0)})
    private void labyMod$renderHeartsPre(bit resolution, CallbackInfo ci) {
        Player playerAa = this.j.aa();
        if (!(playerAa instanceof aed)) {
            return;
        }
        Player player = (aed) playerAa;
        Player labyPlayer = player;
        int leftAnchor = (resolution.a() / 2) - 91;
        int bottomY = resolution.b() - 39;
        int health = rk.f(player.cd());
        int displayHealth = this.E;
        float maxHealth = (float) player.a(adh.a).e();
        int absorption = rk.f(player.cD());
        int heartRows = rk.f(((maxHealth + absorption) / 2.0f) / 10.0f);
        int rowHeight = Math.max(10 - (heartRows - 2), 3);
        int offsetHeartIndex = -1;
        if (player.a(vb.j)) {
            offsetHeartIndex = this.m % rk.f(maxHealth + 5.0f);
        }
        boolean renderHighlight = this.G > ((long) this.m) && ((this.G - ((long) this.m)) / 3) % 2 == 1;
        boolean hardcore = ((aed) player).l.V().s();
        int finalOffsetHeartIndex = offsetHeartIndex;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, leftAnchor, bottomY, rowHeight, finalOffsetHeartIndex, maxHealth, health, displayHealth, absorption, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1))})
    private boolean labyMod$skipHeartBlit(biq self, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1)})
    private void labyMod$renderFoodPre(bit resolution, CallbackInfo ci) {
        Player playerAa = this.j.aa();
        if (!(playerAa instanceof aed)) {
            return;
        }
        Player labyPlayer = (aed) playerAa;
        int rightAnchor = (resolution.a() / 2) + 91;
        int bottomY = resolution.b() - 39;
        int tick = this.m;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, rightAnchor, bottomY, tick);
        });
    }

    @WrapWithCondition(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2))})
    private boolean labyMod$skipFoodBlit(biq self, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2)})
    private void labyMod$renderHealthFoodPost(bit resolution, CallbackInfo ci) {
        Player playerAa = this.j.aa();
        if (!(playerAa instanceof aed)) {
            return;
        }
        Player labyPlayer = (aed) playerAa;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer);
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer);
        });
    }
}

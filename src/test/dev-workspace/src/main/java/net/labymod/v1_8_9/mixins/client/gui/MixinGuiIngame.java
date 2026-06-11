package net.labymod.v1_8_9.mixins.client.gui;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.event.client.chat.ActionBarReceiveEventCaller;
import net.labymod.core.event.client.render.overlay.CameraOverlayRenderEventCaller;
import net.labymod.core.event.client.render.overlay.HudHealthFoodEventDispatcher;
import net.labymod.core.event.client.render.overlay.IngameOverlayElementRenderEventCaller;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinGuiIngame.class */
@Mixin({avo.class})
public abstract class MixinGuiIngame extends avp {

    @Unique
    private float labyMod$modifiedOverlayOpacity = -1.0f;

    @Shadow
    public int w;

    @Shadow
    private String o;

    @Shadow
    private int p;

    @Shadow
    private boolean q;

    @Shadow
    @Final
    private ave j;

    @Shadow
    private int n;

    @Shadow
    private long E;

    @Shadow
    private long F;

    @Shadow
    private int C;

    @Shadow
    private int D;

    @Shadow
    @Final
    private Random i;

    @Shadow
    public abstract avn shadow$f();

    @Overwrite
    private void j() {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (bfc.c == null || bfc.b <= 0) {
                Laby.references().bossBarRegistry().unregisterBossBar(null);
                return;
            }
            if (IngameOverlayElementRenderEventCaller.callBossBarPre(screenContext)) {
                return;
            }
            ave mc = ave.A();
            bfc.b--;
            avn fontRenderer = mc.k;
            avr scaledResolution = new avr(mc);
            int scaledWidth = scaledResolution.a();
            int bossBarPositionX = (scaledWidth / 2) - (182 / 2);
            int bossBarHealth = (int) (bfc.a * (182 + 1));
            b(bossBarPositionX, 12, 0, 74, 182, 5);
            b(bossBarPositionX, 12, 0, 74, 182, 5);
            if (bossBarHealth > 0) {
                b(bossBarPositionX, 12, 0, 79, bossBarHealth, 5);
            }
            String bossName = bfc.c;
            fontRenderer.a(bossName, (scaledWidth / 2) - (shadow$f().a(bossName) / 2), 12 - 10, 16777215);
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            mc.P().a(d);
            IngameOverlayElementRenderEventCaller.callBossBarPost(screenContext);
        });
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

    @WrapOperation(method = {"renderGameOverlay(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;showCrosshair()Z")})
    public boolean labyMod$renderCrosshairPre(avo instance, Operation<Boolean> original) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callCrossHairPre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return false;
        }
        return ((Boolean) original.call(new Object[]{instance})).booleanValue();
    }

    @Insert(method = {"renderGameOverlay(F)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", shift = At.Shift.AFTER))
    public void labyMod$renderCrosshairPost(float partialTicks, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callCrossHairPost);
    }

    @Insert(method = {"renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"}, at = @At("HEAD"), cancellable = true)
    public void labyMod$renderScoreboardPre(auk objective, avr scaledResolution, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callScoreboardPre(screenContext)) {
                callbackInfo.cancel();
            }
        });
    }

    @Insert(method = {"renderScoreboard(Lnet/minecraft/scoreboard/ScoreObjective;Lnet/minecraft/client/gui/ScaledResolution;)V"}, at = @At("TAIL"))
    public void labyMod$renderScoreboardPost(auk objective, avr scaledResolution, InsertInfo callbackInfo) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callScoreboardPost);
    }

    @ModifyConstant(method = {"renderScoreboard"}, constant = {@Constant(intValue = 553648127)})
    private int labyMod$addOpacity(int color) {
        return color | (-16777216);
    }

    @Redirect(method = {"renderPlayerStats"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = 18))
    private void labyMod$renderAirBubble(avo instance, int i1, int i2, int i3, int i4, int i5, int i6) {
        if (labyMod$isSaturationHudWidgetDisplayed()) {
            i2 -= 10;
        }
        b(i1, i2, i3, i4, i5, i6);
    }

    @Redirect(method = {"renderPlayerStats"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V", ordinal = ImGuiFlags.StyleColors.SliderGrab))
    private void labyMod$renderPoppingAirBubble(avo instance, int i1, int i2, int i3, int i4, int i5, int i6) {
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
    private int labyMod$fireIngameOverlayElementRenderEventTitle(avo instance) {
        AtomicBoolean result = new AtomicBoolean(false);
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            if (IngameOverlayElementRenderEventCaller.callTitlePre(screenContext)) {
                result.set(true);
            }
        });
        if (result.get()) {
            return 0;
        }
        return this.w;
    }

    @Inject(method = {"renderGameOverlay"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getScoreboard()Lnet/minecraft/scoreboard/Scoreboard;", shift = At.Shift.BEFORE)})
    private void labyMod$fireIngameOverlayElementRenderEventTitle(float partialTicks, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, IngameOverlayElementRenderEventCaller::callTitlePost);
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
        ComponentMapper componentMapper = Laby.references().componentMapper();
        Component modifiedMessage = event.getMessage();
        if (modifiedMessage == mapped) {
            strD = message;
        } else {
            strD = ((eu) componentMapper.toMinecraftComponent(modifiedMessage)).d();
        }
        this.o = strD;
        this.p = 60;
        this.q = animatedMessage;
        ActionBarReceiveEventCaller.callPost(modifiedMessage, animatedMessage);
    }

    @Inject(method = {"resetPlayersOverlayFooterHeader"}, at = {@At("TAIL")})
    private void labyMod$reset(CallbackInfo ci) {
        Laby.references().bossBarRegistry().unregisterBossBar(null);
    }

    @Inject(method = {"renderPumpkinOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderPumpkinOverlayPre(avr scaledResolution, CallbackInfo ci) {
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
    private void labyMod$renderPumpkinOverlayPost(avr scaledResolution, CallbackInfo ci) {
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, screenContext -> {
            CameraOverlayRenderEventCaller.callPost(screenContext, CameraOverlayRenderEvent.OverlayType.PUMPKIN, 1.0f);
        });
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0)})
    private void labyMod$renderHeartsPre(avr resolution, CallbackInfo ci) {
        Player playerAc = this.j.ac();
        if (!(playerAc instanceof wn)) {
            return;
        }
        Player player = (wn) playerAc;
        Player labyPlayer = player;
        int leftAnchor = (resolution.a() / 2) - 91;
        int bottomY = resolution.b() - 39;
        int health = ns.f(player.bn());
        int displayHealth = this.D;
        float maxHealth = (float) player.a(vy.a).e();
        int absorption = ns.f(player.bN());
        int heartRows = ns.f(((maxHealth + absorption) / 2.0f) / 10.0f);
        int rowHeight = Math.max(10 - (heartRows - 2), 3);
        int offsetHeartIndex = -1;
        if (player.a(pe.l)) {
            offsetHeartIndex = this.n % ns.f(maxHealth + 5.0f);
        }
        boolean renderHighlight = this.F > ((long) this.n) && ((this.F - ((long) this.n)) / 3) % 2 == 1;
        boolean hardcore = ((wn) player).o.P().t();
        int finalOffsetHeartIndex = offsetHeartIndex;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPre(ctx, labyPlayer, leftAnchor, bottomY, rowHeight, finalOffsetHeartIndex, maxHealth, health, displayHealth, absorption, renderHighlight, hardcore);
        });
    }

    @WrapWithCondition(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1))})
    private boolean labyMod$skipHeartBlit(avo self, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipHeartRender();
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1)})
    private void labyMod$renderFoodPre(avr resolution, CallbackInfo ci) {
        Player playerAc = this.j.ac();
        if (!(playerAc instanceof wn)) {
            return;
        }
        Player labyPlayer = (wn) playerAc;
        int rightAnchor = (resolution.a() / 2) + 91;
        int bottomY = resolution.b() - 39;
        int tick = this.n;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchFoodPre(ctx, labyPlayer, rightAnchor, bottomY, tick);
        });
    }

    @WrapWithCondition(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;drawTexturedModalRect(IIIIII)V")}, slice = {@Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2))})
    private boolean labyMod$skipFoodBlit(avo self, int x, int y, int u, int v, int w, int h) {
        return !HudHealthFoodEventDispatcher.shouldSkipFoodRender();
    }

    @Inject(method = {"renderPlayerStats"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 3)})
    private void labyMod$renderHealthFoodPost(avr resolution, CallbackInfo ci) {
        Player playerAc = this.j.ac();
        if (!(playerAc instanceof wn)) {
            return;
        }
        Player labyPlayer = (wn) playerAc;
        MinecraftUtil.obtainScreenContextFromStack(VersionedStackProvider.DEFAULT_STACK, ctx -> {
            HudHealthFoodEventDispatcher.dispatchHeartsPost(ctx, labyPlayer);
            HudHealthFoodEventDispatcher.dispatchFoodPost(ctx, labyPlayer);
        });
    }
}

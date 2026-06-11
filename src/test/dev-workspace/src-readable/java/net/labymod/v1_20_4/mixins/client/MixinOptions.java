package net.labymod.v1_20_4.mixins.client;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.VanillaOptionsSaveEvent;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.core.client.options.OptionsTranslator;
import net.labymod.core.client.options.OptionsUtil;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/MixinOptions.class */
@Mixin({evm.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private euu bL;

    @Shadow
    public String ad;

    @Shadow
    public boolean ab;

    @Shadow
    public String aa;

    @Shadow
    @Final
    public evg I;

    @Shadow
    @Final
    public evg H;

    @Shadow
    @Final
    private Set<cfj> aP;

    @Shadow
    @Final
    private evl<Double> aU;

    @Shadow
    @Final
    private evl<Double> aT;

    @Shadow
    @Final
    private evl<Boolean> am;

    @Shadow
    @Final
    private evl<Integer> aw;

    @Shadow
    @Final
    private evl<cfg> aH;

    @Shadow
    @Final
    private evl<bmf> aQ;

    @Shadow
    @Final
    private evl<Double> aS;

    @Shadow
    @Final
    private evl<Double> aR;

    @Shadow
    @Final
    private evl<Double> aI;

    @Shadow
    @Final
    private evl<Double> aK;

    @Shadow
    @Final
    private evl<Double> aJ;

    @Shadow
    @Final
    private evl<Boolean> bg;

    @Shadow
    @Final
    private evl<Boolean> bh;

    @Shadow
    @Final
    private evl<Boolean> bi;

    @Shadow
    @Final
    private evl<Boolean> bz;

    @Shadow
    @Final
    private evl<Boolean> bA;

    @Shadow
    @Final
    private evl<Integer> bM;

    @Shadow
    public boolean Z;

    @Shadow
    @Final
    public evg D;

    @Shadow
    @Final
    public evg C;

    @Shadow
    @Final
    private File bK;

    @Shadow
    @Final
    private evl<eus> aZ;

    @Shadow
    public boolean ae;

    @Shadow
    public gkj r;

    @Shadow
    @Final
    private evl<Integer> as;

    @Shadow
    @Final
    private evl<Double> bS;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void au();

    @Shadow
    public abstract evl<Boolean> Y();

    @Shadow
    public abstract void shadow$as();

    @Shadow
    public abstract evl<Boolean> V();

    @Shadow
    public abstract evl<Boolean> c();

    @Shadow
    public abstract evl<Integer> e();

    @Shadow
    public abstract evl<Integer> f();

    @Shadow
    public abstract int aA();

    @Shadow
    public abstract evl<Boolean> L();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bK);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.ae = false;
        this.r = gkj.f;
        this.as.a(Integer.valueOf(OptionsUtil.getRenderDistance(((Integer) this.as.c()).intValue())));
    }

    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = (VanillaOptionsSaveEvent) Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bK));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"save"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bK));
    }

    @Redirect(method = {"save"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getFrameLimit() {
        return ((Integer) this.aw.c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getChatBackgroundColor() {
        return getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getBackgroundColorWithOpacity(int baseColor) {
        return a(baseColor);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public Perspective perspective() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$CameraType[this.bL.ordinal()]) {
            case 1:
                return Perspective.FIRST_PERSON;
            case 2:
                return Perspective.THIRD_PERSON_BACK;
            case 3:
                return Perspective.THIRD_PERSON_FRONT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setModelParts(int value) {
        for (cfj part : cfj.values()) {
            if ((value & part.a()) == part.a()) {
                this.aP.add(part);
            } else {
                this.aP.remove(part);
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getModelParts() {
        int mask = 0;
        for (cfj part : this.aP) {
            mask |= part.a();
        }
        return mask;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void sendOptionsToServer() {
        au();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getCurrentLanguage() {
        return this.ad;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[((cfg) this.aH.c()).ordinal()]) {
            case 1:
                return ChatVisibility.SHOWN;
            case 2:
                return ChatVisibility.HIDDEN;
            case 3:
                return ChatVisibility.COMMANDS_ONLY;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MainHand mainHand() {
        return this.aQ.c() == bmf.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.aQ.a(hand == MainHand.LEFT ? bmf.a : bmf.b);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightOpen() {
        return (float) Math.floor((((Double) this.aU.c()).doubleValue() * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightClosed() {
        return (float) Math.floor((((Double) this.aT.c()).doubleValue() * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatWidth() {
        return (float) Math.floor((((Double) this.aS.c()).doubleValue() * 280.0d) + 40.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatScale() {
        return ((Double) this.aR.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatTextOpacity() {
        return (((Double) this.aI.c()).doubleValue() * 0.8999999761581421d) + 0.10000000149011612d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return ((Double) this.aK.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return ((Double) this.aJ.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatColorsEnabled() {
        return ((Boolean) this.bg.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinksEnabled() {
        return ((Boolean) this.bh.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinkConfirmationEnabled() {
        return ((Boolean) this.bi.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getLastKnownServer() {
        return this.aa;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.ab;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.ab = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.aa = address;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return ((Boolean) this.am.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
        this.am.a(Boolean.valueOf(darkMode));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping useItemInput() {
        return this.H;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping attackInput() {
        return this.I;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sprintInput() {
        return this.D;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sneakInput() {
        return this.C;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isFullscreen() {
        return ((Boolean) this.bz.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setFullscreen(boolean fullscreen) {
        this.bz.a(Boolean.valueOf(fullscreen));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setPerspective(Perspective perspective) {
        this.bL = euu.values()[perspective.ordinal()];
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return ((Boolean) Y().c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setBobbing(boolean bobbing) {
        this.bA.a(Boolean.valueOf(bobbing));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFov() {
        return ((Integer) this.bM.c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFovEffectScale() {
        return ((Double) this.bS.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideGUI() {
        return this.Z;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isDebugEnabled() {
        return evi.O().aN().d();
    }

    @Intrinsic
    public void options$save() {
        shadow$as();
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_4.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[eus.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[eus.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[eus.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[eus.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[cfg.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cfg.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cfg.c.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cfg.b.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[euu.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[euu.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[euu.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[euu.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[((eus) this.aZ.c()).ordinal()]) {
            case 1:
                return AttackIndicatorPosition.CROSSHAIR;
            case 2:
                return AttackIndicatorPosition.HOTBAR;
            case 3:
                return AttackIndicatorPosition.OFF;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBackgroundForChatOnly() {
        return ((Boolean) V().c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideSplashTexts() {
        return ((Boolean) c().c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getRenderDistance() {
        return ((Integer) e().c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getActualRenderDistance() {
        return aA();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getSimulationDistance() {
        return ((Integer) f().c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return ((Boolean) L().c()).booleanValue();
    }
}

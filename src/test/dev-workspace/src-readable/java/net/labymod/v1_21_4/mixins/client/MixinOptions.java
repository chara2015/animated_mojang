package net.labymod.v1_21_4.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinOptions.class */
@Mixin({flo.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private fkt bU;

    @Shadow
    public String ab;

    @Shadow
    public boolean Z;

    @Shadow
    public String Y;

    @Shadow
    @Final
    public fli G;

    @Shadow
    @Final
    public fli F;

    @Shadow
    @Final
    private Set<coz> aU;

    @Shadow
    @Final
    private fln<Double> aZ;

    @Shadow
    @Final
    private fln<Double> aY;

    @Shadow
    @Final
    private fln<Boolean> aj;

    @Shadow
    @Final
    private fln<Integer> at;

    @Shadow
    @Final
    private fln<cov> aH;

    @Shadow
    @Final
    private fln<bvc> aV;

    @Shadow
    @Final
    private fln<Double> aX;

    @Shadow
    @Final
    private fln<Double> aW;

    @Shadow
    @Final
    private fln<Double> aI;

    @Shadow
    @Final
    private fln<Double> aN;

    @Shadow
    @Final
    private fln<Double> aJ;

    @Shadow
    @Final
    private fln<Boolean> bn;

    @Shadow
    @Final
    private fln<Boolean> bo;

    @Shadow
    @Final
    private fln<Boolean> bp;

    @Shadow
    @Final
    private fln<Boolean> bI;

    @Shadow
    @Final
    private fln<Boolean> bJ;

    @Shadow
    @Final
    private fln<Integer> bV;

    @Shadow
    public boolean X;

    @Shadow
    @Final
    public fli B;

    @Shadow
    @Final
    public fli A;

    @Shadow
    @Final
    private File bT;

    @Shadow
    @Final
    private fln<fkr> be;

    @Shadow
    public boolean ac;

    @Shadow
    public hla r;

    @Shadow
    @Final
    private fln<Integer> ap;

    @Shadow
    @Final
    private fln<Double> cb;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void aB();

    @Shadow
    public abstract fln<Boolean> ae();

    @Shadow
    public abstract void shadow$az();

    @Shadow
    public abstract fln<Boolean> ab();

    @Shadow
    public abstract fln<Boolean> c();

    @Shadow
    public abstract fln<Integer> e();

    @Shadow
    public abstract fln<Integer> f();

    @Shadow
    public abstract int aH();

    @Shadow
    public abstract fln<Boolean> Q();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bT);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.ac = false;
        this.r = hla.f;
        this.ap.a(Integer.valueOf(OptionsUtil.getRenderDistance(((Integer) this.ap.c()).intValue())));
    }

    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = (VanillaOptionsSaveEvent) Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bT));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"save"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bT));
    }

    @Redirect(method = {"save"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getFrameLimit() {
        return ((Integer) this.at.c()).intValue();
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
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$CameraType[this.bU.ordinal()]) {
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
        for (coz part : coz.values()) {
            if ((value & part.a()) == part.a()) {
                this.aU.add(part);
            } else {
                this.aU.remove(part);
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getModelParts() {
        int mask = 0;
        for (coz part : this.aU) {
            mask |= part.a();
        }
        return mask;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void sendOptionsToServer() {
        aB();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getCurrentLanguage() {
        return this.ab;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[((cov) this.aH.c()).ordinal()]) {
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
        return this.aV.c() == bvc.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.aV.a(hand == MainHand.LEFT ? bvc.a : bvc.b);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightOpen() {
        return (float) Math.floor((((Double) this.aZ.c()).doubleValue() * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightClosed() {
        return (float) Math.floor((((Double) this.aY.c()).doubleValue() * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatWidth() {
        return (float) Math.floor((((Double) this.aX.c()).doubleValue() * 280.0d) + 40.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatScale() {
        return ((Double) this.aW.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatTextOpacity() {
        return (((Double) this.aI.c()).doubleValue() * 0.8999999761581421d) + 0.10000000149011612d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return ((Double) this.aN.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return ((Double) this.aJ.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatColorsEnabled() {
        return ((Boolean) this.bn.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinksEnabled() {
        return ((Boolean) this.bo.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinkConfirmationEnabled() {
        return ((Boolean) this.bp.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getLastKnownServer() {
        return this.Y;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.Z;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.Z = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.Y = address;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return ((Boolean) this.aj.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
        this.aj.a(Boolean.valueOf(darkMode));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping useItemInput() {
        return this.F;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping attackInput() {
        return this.G;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sprintInput() {
        return this.B;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sneakInput() {
        return this.A;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isFullscreen() {
        return ((Boolean) this.bI.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setFullscreen(boolean fullscreen) {
        this.bI.a(Boolean.valueOf(fullscreen));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setPerspective(Perspective perspective) {
        this.bU = fkt.values()[perspective.ordinal()];
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return ((Boolean) ae().c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setBobbing(boolean bobbing) {
        this.bJ.a(Boolean.valueOf(bobbing));
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFov() {
        return ((Integer) this.bV.c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFovEffectScale() {
        return ((Double) this.cb.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideGUI() {
        return this.X;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isDebugEnabled() {
        return flk.Q().aQ().d();
    }

    @Intrinsic
    public void options$save() {
        shadow$az();
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[fkr.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[fkr.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[fkr.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[fkr.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[cov.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cov.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cov.c.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[cov.b.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[fkt.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[fkt.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[fkt.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[fkt.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[((fkr) this.be.c()).ordinal()]) {
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
        return ((Boolean) ab().c()).booleanValue();
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
        return aH();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getSimulationDistance() {
        return ((Integer) f().c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return ((Boolean) Q().c()).booleanValue();
    }
}

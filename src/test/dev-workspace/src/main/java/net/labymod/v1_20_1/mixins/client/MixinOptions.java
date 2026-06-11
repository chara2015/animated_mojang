package net.labymod.v1_20_1.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/MixinOptions.class */
@Mixin({enr.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private ena bL;

    @Shadow
    public String ag;

    @Shadow
    public boolean ae;

    @Shadow
    public String ad;

    @Shadow
    @Final
    public enl I;

    @Shadow
    @Final
    public enl H;

    @Shadow
    @Final
    private Set<byp> aP;

    @Shadow
    @Final
    private enq<Double> aU;

    @Shadow
    @Final
    private enq<Double> aT;

    @Shadow
    @Final
    private enq<Boolean> ap;

    @Shadow
    @Final
    private enq<Integer> ax;

    @Shadow
    @Final
    private enq<bym> aI;

    @Shadow
    @Final
    private enq<bft> aQ;

    @Shadow
    @Final
    private enq<Double> aS;

    @Shadow
    @Final
    private enq<Double> aR;

    @Shadow
    @Final
    private enq<Double> aJ;

    @Shadow
    @Final
    private enq<Double> aL;

    @Shadow
    @Final
    private enq<Double> aK;

    @Shadow
    @Final
    private enq<Boolean> bg;

    @Shadow
    @Final
    private enq<Boolean> bh;

    @Shadow
    @Final
    private enq<Boolean> bi;

    @Shadow
    @Final
    private enq<Boolean> bz;

    @Shadow
    @Final
    private enq<Boolean> bA;

    @Shadow
    @Final
    private enq<Integer> bM;

    @Shadow
    public boolean Z;

    @Shadow
    @Final
    public enl D;

    @Shadow
    @Final
    public enl C;

    @Shadow
    @Final
    private File bK;

    @Shadow
    public boolean aa;

    @Shadow
    @Final
    private enq<emy> aZ;

    @Shadow
    public boolean ah;

    @Shadow
    public gah r;

    @Shadow
    @Final
    private enq<Integer> at;

    @Shadow
    @Final
    private enq<Integer> au;

    @Shadow
    @Final
    private enq<Double> bS;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void ar();

    @Shadow
    public abstract enq<Boolean> W();

    @Shadow
    public abstract void shadow$aq();

    @Shadow
    public abstract enq<Boolean> T();

    @Shadow
    public abstract int ax();

    @Shadow
    public abstract enq<Boolean> J();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bK);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.ah = false;
        this.r = gah.f;
        this.at.a(Integer.valueOf(OptionsUtil.getRenderDistance(((Integer) this.at.c()).intValue())));
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
        return ((Integer) this.ax.c()).intValue();
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
        for (byp part : byp.values()) {
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
        for (byp part : this.aP) {
            mask |= part.a();
        }
        return mask;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void sendOptionsToServer() {
        ar();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getCurrentLanguage() {
        return this.ag;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[((bym) this.aI.c()).ordinal()]) {
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
        return this.aQ.c() == bft.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.aQ.a(hand == MainHand.LEFT ? bft.a : bft.b);
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
        return (((Double) this.aJ.c()).doubleValue() * 0.8999999761581421d) + 0.10000000149011612d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return ((Double) this.aL.c()).doubleValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return ((Double) this.aK.c()).doubleValue();
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
        return this.ad;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.ae;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.ae = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.ad = address;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return ((Boolean) this.ap.c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
        this.ap.a(Boolean.valueOf(darkMode));
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
        this.bL = ena.values()[perspective.ordinal()];
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return ((Boolean) W().c()).booleanValue();
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
        return this.aa;
    }

    @Intrinsic
    public void options$save() {
        shadow$aq();
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_1.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[emy.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[emy.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[emy.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[emy.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[bym.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bym.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bym.c.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bym.b.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[ena.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[ena.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[ena.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[ena.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[((emy) this.aZ.c()).ordinal()]) {
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
        return ((Boolean) T().c()).booleanValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getRenderDistance() {
        return ((Integer) this.at.c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getActualRenderDistance() {
        return ax();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getSimulationDistance() {
        return ((Integer) this.au.c()).intValue();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return ((Boolean) J().c()).booleanValue();
    }
}

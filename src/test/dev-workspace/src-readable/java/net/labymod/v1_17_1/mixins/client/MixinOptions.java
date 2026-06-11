package net.labymod.v1_17_1.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/MixinOptions.class */
@Mixin({dvt.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private dvb bn;

    @Shadow
    public String bd;

    @Shadow
    public bkc q;

    @Shadow
    public double C;

    @Shadow
    public double D;

    @Shadow
    public double E;

    @Shadow
    public double F;

    @Shadow
    public double r;

    @Shadow
    public double t;

    @Shadow
    public double s;

    @Shadow
    public boolean T;

    @Shadow
    public boolean U;

    @Shadow
    public boolean V;

    @Shadow
    public boolean aV;

    @Shadow
    public double aW;

    @Shadow
    public String aU;

    @Shadow
    public boolean g;

    @Shadow
    public int k;

    @Shadow
    public Set<bkf> bk;

    @Shadow
    public atp y;

    @Shadow
    public dvu bb;

    @Shadow
    @Final
    public dvm ay;

    @Shadow
    @Final
    public dvm ax;

    @Shadow
    public boolean ah;

    @Shadow
    public boolean ai;

    @Shadow
    public boolean aQ;

    @Shadow
    @Final
    public dvm at;

    @Shadow
    @Final
    public dvm as;

    @Shadow
    @Final
    private File bm;

    @Shadow
    public boolean aR;

    @Shadow
    public duz J;

    @Shadow
    public fbq K;

    @Shadow
    public int i;

    @Shadow
    public boolean af;

    @Shadow
    public float aY;

    @Shadow
    public boolean W;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void c();

    @Shadow
    public abstract void shadow$b();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bm);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.K = fbq.f;
        this.i = OptionsUtil.getRenderDistance(this.i);
    }

    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = (VanillaOptionsSaveEvent) Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bm));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"save"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bm));
    }

    @Redirect(method = {"save"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getFrameLimit() {
        return this.k;
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
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$CameraType[this.bn.ordinal()]) {
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
        for (bkf part : bkf.values()) {
            if ((value & part.a()) == part.a()) {
                this.bk.add(part);
            } else {
                this.bk.remove(part);
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getModelParts() {
        int mask = 0;
        for (bkf part : this.bk) {
            mask |= part.a();
        }
        return mask;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void sendOptionsToServer() {
        c();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getCurrentLanguage() {
        return this.bd;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[this.q.ordinal()]) {
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
        return this.y == atp.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.y = hand == MainHand.LEFT ? atp.a : atp.b;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightOpen() {
        return (float) Math.floor((this.F * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightClosed() {
        return (float) Math.floor((this.E * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatWidth() {
        return (float) Math.floor((this.D * 280.0d) + 40.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatScale() {
        return this.C;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatTextOpacity() {
        return (this.r * 0.8999999761581421d) + 0.10000000149011612d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return this.t;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return this.s;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatColorsEnabled() {
        return this.T;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinksEnabled() {
        return this.U;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinkConfirmationEnabled() {
        return this.V;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getLastKnownServer() {
        return this.aU;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.aU = address;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.aV;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.aV = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return this.g;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
        this.g = darkMode;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping useItemInput() {
        return this.ax;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping attackInput() {
        return this.ay;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sprintInput() {
        return this.at;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sneakInput() {
        return this.as;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isFullscreen() {
        return this.ah;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setFullscreen(boolean fullscreen) {
        this.ah = fullscreen;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setPerspective(Perspective perspective) {
        this.bn = dvb.values()[perspective.ordinal()];
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return this.ai;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setBobbing(boolean bobbing) {
        this.ai = bobbing;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFov() {
        return this.aW;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFovEffectScale() {
        return this.aY;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideGUI() {
        return this.aQ;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isDebugEnabled() {
        return this.aR;
    }

    @Intrinsic
    public void options$save() {
        shadow$b();
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[duz.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[duz.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[duz.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[duz.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[bkc.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bkc.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bkc.c.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bkc.b.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[dvb.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[dvb.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[dvb.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[dvb.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[this.J.ordinal()]) {
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
        return this.af;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getRenderDistance() {
        return this.i;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getActualRenderDistance() {
        return getRenderDistance();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return this.W;
    }
}

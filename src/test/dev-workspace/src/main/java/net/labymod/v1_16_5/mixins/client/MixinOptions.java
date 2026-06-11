package net.labymod.v1_16_5.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinOptions.class */
@Mixin({dkd.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private djl be;

    @Shadow
    public String aV;

    @Shadow
    public bfu j;

    @Shadow
    public double v;

    @Shadow
    public double w;

    @Shadow
    public double x;

    @Shadow
    public double y;

    @Shadow
    public double k;

    @Shadow
    public double m;

    @Shadow
    public double l;

    @Shadow
    public boolean L;

    @Shadow
    public boolean M;

    @Shadow
    public boolean N;

    @Shadow
    public boolean aN;

    @Shadow
    public double aO;

    @Shadow
    public String aM;

    @Shadow
    public int d;

    @Shadow
    public Set<bfx> bb;

    @Shadow
    public aqi r;

    @Shadow
    public dke aT;

    @Shadow
    @Final
    public djw aq;

    @Shadow
    @Final
    public djw ap;

    @Shadow
    public boolean Z;

    @Shadow
    public boolean aa;

    @Shadow
    public boolean aI;

    @Shadow
    @Final
    public djw al;

    @Shadow
    @Final
    public djw ak;

    @Shadow
    @Final
    private File bd;

    @Shadow
    public boolean aJ;

    @Shadow
    public dji C;

    @Shadow
    public eog D;

    @Shadow
    public int b;

    @Shadow
    public boolean X;

    @Shadow
    public float aQ;

    @Shadow
    public boolean O;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void c();

    @Shadow
    public abstract void shadow$b();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.bd);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.D = eog.f;
        this.b = OptionsUtil.getRenderDistance(this.b);
    }

    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = (VanillaOptionsSaveEvent) Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.bd));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"save"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.bd));
    }

    @Redirect(method = {"save"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getFrameLimit() {
        return this.d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getChatBackgroundColor() {
        return getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getBackgroundColorWithOpacity(int baseColor) {
        return a(baseColor);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public Perspective perspective() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$CameraType[this.be.ordinal()]) {
            case 1:
                return Perspective.FIRST_PERSON;
            case 2:
                return Perspective.THIRD_PERSON_BACK;
            case 3:
                return Perspective.THIRD_PERSON_FRONT;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.be));
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setModelParts(int value) {
        for (bfx part : bfx.values()) {
            if ((value & part.a()) == part.a()) {
                this.bb.add(part);
            } else {
                this.bb.remove(part);
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getModelParts() {
        int mask = 0;
        for (bfx part : this.bb) {
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
        return this.aV;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[this.j.ordinal()]) {
            case 1:
                return ChatVisibility.SHOWN;
            case 2:
                return ChatVisibility.COMMANDS_ONLY;
            case 3:
                return ChatVisibility.HIDDEN;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.j));
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MainHand mainHand() {
        return this.r == aqi.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.r = hand == MainHand.LEFT ? aqi.a : aqi.b;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightOpen() {
        return (float) Math.floor((this.y * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightClosed() {
        return (float) Math.floor((this.x * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatWidth() {
        return (float) Math.floor((this.w * 280.0d) + 40.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatScale() {
        return this.v;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatTextOpacity() {
        return (this.k * 0.8999999761581421d) + 0.10000000149011612d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return this.m;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return this.l;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatColorsEnabled() {
        return this.L;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinksEnabled() {
        return this.M;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinkConfirmationEnabled() {
        return this.N;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getLastKnownServer() {
        return this.aM;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.aM = address;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.aN;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.aN = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return false;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping useItemInput() {
        return this.ap;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping attackInput() {
        return this.aq;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sprintInput() {
        return this.al;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sneakInput() {
        return this.ak;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isFullscreen() {
        return this.Z;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setFullscreen(boolean fullscreen) {
        this.Z = fullscreen;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setPerspective(Perspective perspective) {
        this.be = djl.values()[perspective.ordinal()];
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return this.aa;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setBobbing(boolean bobbing) {
        this.aa = bobbing;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFov() {
        return this.aO;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFovEffectScale() {
        return this.aQ;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideGUI() {
        return this.aI;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isDebugEnabled() {
        return this.aJ;
    }

    @Intrinsic
    public void options$save() {
        shadow$b();
    }

    /* JADX INFO: renamed from: net.labymod.v1_16_5.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[dji.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[dji.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[dji.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[dji.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[bfu.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bfu.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bfu.b.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[bfu.c.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[djl.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[djl.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[djl.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[djl.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[this.C.ordinal()]) {
            case 1:
                return AttackIndicatorPosition.OFF;
            case 2:
                return AttackIndicatorPosition.CROSSHAIR;
            case 3:
                return AttackIndicatorPosition.HOTBAR;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.C));
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBackgroundForChatOnly() {
        return this.X;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getRenderDistance() {
        return this.b;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getActualRenderDistance() {
        return getRenderDistance();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return this.O;
    }
}

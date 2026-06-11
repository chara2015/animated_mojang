package net.labymod.v1_21_11.mixins.client;

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
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;
import net.minecraft.world.entity.player.PlayerModelPart;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinOptions.class */
@Mixin({Options.class})
@Implements({@Interface(iface = MinecraftOptions.class, prefix = "options$", remap = Interface.Remap.NONE)})
public abstract class MixinOptions implements MinecraftOptions {

    @Shadow
    private CameraType cS;

    @Shadow
    public String ay;

    @Shadow
    public boolean aw;

    @Shadow
    public String av;

    @Shadow
    @Final
    public KeyMapping D;

    @Shadow
    @Final
    public KeyMapping C;

    @Shadow
    @Final
    private Set<PlayerModelPart> bB;

    @Shadow
    @Final
    private OptionInstance<Double> bG;

    @Shadow
    @Final
    private OptionInstance<Double> bF;

    @Shadow
    @Final
    private OptionInstance<Boolean> aH;

    @Shadow
    @Final
    private OptionInstance<Integer> aR;

    @Shadow
    @Final
    private OptionInstance<ChatVisiblity> bo;

    @Shadow
    @Final
    private OptionInstance<HumanoidArm> bC;

    @Shadow
    @Final
    private OptionInstance<Double> bE;

    @Shadow
    @Final
    private OptionInstance<Double> bD;

    @Shadow
    @Final
    private OptionInstance<Double> bp;

    @Shadow
    @Final
    private OptionInstance<Double> bu;

    @Shadow
    @Final
    private OptionInstance<Double> bq;

    @Shadow
    @Final
    private OptionInstance<Boolean> cd;

    @Shadow
    @Final
    private OptionInstance<Boolean> ce;

    @Shadow
    @Final
    private OptionInstance<Boolean> cf;

    @Shadow
    @Final
    private OptionInstance<Boolean> cA;

    @Shadow
    @Final
    private OptionInstance<Boolean> cB;

    @Shadow
    @Final
    private OptionInstance<Integer> cT;

    @Shadow
    public boolean au;

    @Shadow
    @Final
    public KeyMapping y;

    @Shadow
    @Final
    public KeyMapping x;

    @Shadow
    @Final
    private File cR;

    @Shadow
    @Final
    private OptionInstance<AttackIndicatorStatus> bS;

    @Shadow
    public boolean az;

    @Shadow
    public TutorialSteps o;

    @Shadow
    @Final
    private OptionInstance<Integer> aN;

    @Shadow
    @Final
    private OptionInstance<Double> cZ;

    @Shadow
    public abstract int a(int i);

    @Shadow
    public abstract void aS();

    @Shadow
    public abstract OptionInstance<Boolean> ap();

    @Shadow
    public abstract void shadow$aQ();

    @Shadow
    public abstract OptionInstance<Boolean> am();

    @Shadow
    public abstract OptionInstance<Boolean> c();

    @Shadow
    public abstract OptionInstance<Integer> e();

    @Shadow
    public abstract OptionInstance<Integer> f();

    @Shadow
    public abstract int aY();

    @Shadow
    public abstract OptionInstance<Boolean> aa();

    @Inject(method = {"load"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.cR);
    }

    @Inject(method = {"load"}, at = {@At("RETURN")})
    private void labyMod$forceOptions(CallbackInfo ci) {
        this.az = false;
        this.o = TutorialSteps.NONE;
        this.aN.set(Integer.valueOf(OptionsUtil.getRenderDistance(((Integer) this.aN.get()).intValue())));
    }

    @Inject(method = {"save"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.cR));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"save"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.cR));
    }

    @Redirect(method = {"save"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    public int getFrameLimit() {
        return ((Integer) this.aR.get()).intValue();
    }

    public int getChatBackgroundColor() {
        return getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }

    public int getBackgroundColorWithOpacity(int baseColor) {
        return a(baseColor);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Perspective perspective() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$CameraType[this.cS.ordinal()]) {
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

    public void setModelParts(int value) {
        for (PlayerModelPart part : PlayerModelPart.values()) {
            if ((value & part.getMask()) == part.getMask()) {
                this.bB.add(part);
            } else {
                this.bB.remove(part);
            }
        }
    }

    public int getModelParts() {
        int mask = 0;
        for (PlayerModelPart part : this.bB) {
            mask |= part.getMask();
        }
        return mask;
    }

    public void sendOptionsToServer() {
        aS();
    }

    public String getCurrentLanguage() {
        return this.ay;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ChatVisibility chatVisibility() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[((ChatVisiblity) this.bo.get()).ordinal()]) {
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

    public MainHand mainHand() {
        return this.bC.get() == HumanoidArm.LEFT ? MainHand.LEFT : MainHand.RIGHT;
    }

    public void setMainHand(MainHand hand) {
        this.bC.set(hand == MainHand.LEFT ? HumanoidArm.LEFT : HumanoidArm.RIGHT);
    }

    public float getChatHeightOpen() {
        return (float) Math.floor((((Double) this.bG.get()).doubleValue() * 160.0d) + 20.0d);
    }

    public float getChatHeightClosed() {
        return (float) Math.floor((((Double) this.bF.get()).doubleValue() * 160.0d) + 20.0d);
    }

    public float getChatWidth() {
        return (float) Math.floor((((Double) this.bE.get()).doubleValue() * 280.0d) + 40.0d);
    }

    public double getChatScale() {
        return ((Double) this.bD.get()).doubleValue();
    }

    public double getChatTextOpacity() {
        return (((Double) this.bp.get()).doubleValue() * 0.8999999761581421d) + 0.10000000149011612d;
    }

    public double getTextBackgroundOpacity() {
        return ((Double) this.bu.get()).doubleValue();
    }

    public double getChatLineSpacing() {
        return ((Double) this.bq.get()).doubleValue();
    }

    public boolean isChatColorsEnabled() {
        return ((Boolean) this.cd.get()).booleanValue();
    }

    public boolean isChatLinksEnabled() {
        return ((Boolean) this.ce.get()).booleanValue();
    }

    public boolean isChatLinkConfirmationEnabled() {
        return ((Boolean) this.cf.get()).booleanValue();
    }

    public String getLastKnownServer() {
        return this.av;
    }

    public boolean isSmoothCamera() {
        return this.aw;
    }

    public void setSmoothCamera(boolean smooth) {
        this.aw = smooth;
    }

    public void setLastKnownServer(String address) {
        this.av = address;
    }

    public boolean isEyeProtectionActive() {
        return ((Boolean) this.aH.get()).booleanValue();
    }

    public void setEyeProtectionActive(boolean darkMode) {
        this.aH.set(Boolean.valueOf(darkMode));
    }

    public MinecraftInputMapping useItemInput() {
        return this.C;
    }

    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    public MinecraftInputMapping attackInput() {
        return this.D;
    }

    public MinecraftInputMapping sprintInput() {
        return this.y;
    }

    public MinecraftInputMapping sneakInput() {
        return this.x;
    }

    public boolean isFullscreen() {
        return ((Boolean) this.cA.get()).booleanValue();
    }

    public void setFullscreen(boolean fullscreen) {
        this.cA.set(Boolean.valueOf(fullscreen));
    }

    public void setPerspective(Perspective perspective) {
        this.cS = CameraType.values()[perspective.ordinal()];
    }

    public boolean isBobbing() {
        return ((Boolean) ap().get()).booleanValue();
    }

    public void setBobbing(boolean bobbing) {
        this.cB.set(Boolean.valueOf(bobbing));
    }

    public double getFov() {
        return ((Integer) this.cT.get()).intValue();
    }

    public double getFovEffectScale() {
        return ((Double) this.cZ.get()).doubleValue();
    }

    public boolean isHideGUI() {
        return this.au;
    }

    public boolean isDebugEnabled() {
        Minecraft minecraft = Minecraft.getInstance();
        DebugScreenEntryList list = minecraft.debugEntries;
        return list.isOverlayVisible() && !(minecraft.options.hideGui && minecraft.screen == null);
    }

    @Intrinsic
    public void options$save() {
        shadow$aQ();
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.MixinOptions$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinOptions$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$CameraType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$AttackIndicatorStatus = new int[AttackIndicatorStatus.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[AttackIndicatorStatus.CROSSHAIR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[AttackIndicatorStatus.HOTBAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$client$AttackIndicatorStatus[AttackIndicatorStatus.OFF.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity = new int[ChatVisiblity.values().length];
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[ChatVisiblity.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[ChatVisiblity.HIDDEN.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$world$entity$player$ChatVisiblity[ChatVisiblity.SYSTEM.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$CameraType = new int[CameraType.values().length];
            try {
                $SwitchMap$net$minecraft$client$CameraType[CameraType.FIRST_PERSON.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[CameraType.THIRD_PERSON_BACK.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$CameraType[CameraType.THIRD_PERSON_FRONT.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public AttackIndicatorPosition attackIndicatorPosition() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$AttackIndicatorStatus[((AttackIndicatorStatus) this.bS.get()).ordinal()]) {
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

    public boolean isBackgroundForChatOnly() {
        return ((Boolean) am().get()).booleanValue();
    }

    public boolean isHideSplashTexts() {
        return ((Boolean) c().get()).booleanValue();
    }

    public int getRenderDistance() {
        return ((Integer) e().get()).intValue();
    }

    public int getActualRenderDistance() {
        return aY();
    }

    public int getSimulationDistance() {
        return ((Integer) f().get()).intValue();
    }

    public boolean isVSyncEnabled() {
        return ((Boolean) aa().get()).booleanValue();
    }
}

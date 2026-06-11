package net.labymod.v1_12_2.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Set;
import net.labymod.api.Laby;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.api.client.options.ChatVisibility;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.event.client.misc.VanillaOptionsSaveEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.core.client.options.OptionsTranslator;
import net.labymod.core.client.options.OptionsUtil;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinGameSettings.class */
@Mixin({bid.class})
public abstract class MixinGameSettings implements MinecraftOptions {
    private static final ChatScreenUpdateEvent VISIBILITY_CHAT_SCREEN_UPDATE_EVENT = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.VISIBILITY);
    private static final ChatScreenUpdateEvent COLORS_CHAT_SCREEN_UPDATE_EVENT = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.COLORS);

    @Shadow
    public int aw;

    @Shadow
    public String aJ;

    @Shadow
    public b o;

    @Shadow
    public float G;

    @Shadow
    public float H;

    @Shadow
    public float I;

    @Shadow
    public float J;

    @Shadow
    public float s;

    @Shadow
    public boolean p;

    @Shadow
    public boolean q;

    @Shadow
    public boolean r;

    @Shadow
    public boolean aB;

    @Shadow
    public Set<aee> aT;

    @Shadow
    public String aA;

    @Shadow
    public int i;

    @Shadow
    public bhy ae;

    @Shadow
    public bhy ad;

    @Shadow
    public boolean u;

    @Shadow
    public boolean f;

    @Shadow
    public float aD;

    @Shadow
    public boolean av;

    @Shadow
    public bhy Y;

    @Shadow
    public bhy Z;

    @Shadow
    @Final
    private File aV;

    @Shadow
    public boolean ax;

    @Shadow
    public vo C;

    @Shadow
    public int e;

    @Shadow
    public int aG;

    @Shadow
    public boolean v;

    @Shadow
    public abstract void c();

    @Shadow
    public abstract void b();

    @Inject(method = {"loadOptions"}, at = {@At("HEAD")})
    private void labyMod$loadOptions(CallbackInfo ci) {
        LabyMod.references().optionsTranslator().translateOptions(this.aV);
    }

    @Inject(method = {"loadOptions"}, at = {@At("RETURN")})
    private void labyMod$loadOptionsReturn(CallbackInfo ci) {
        this.e = OptionsUtil.getRenderDistance(this.e);
    }

    @Inject(method = {"saveOptions"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$saveOptionsPre(CallbackInfo ci) {
        VanillaOptionsSaveEvent event = (VanillaOptionsSaveEvent) Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.PRE, this.aV));
        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = {"saveOptions"}, at = {@At("TAIL")})
    private void labyMod$saveOptionsPost(CallbackInfo ci) {
        Laby.fireEvent(new VanillaOptionsSaveEvent(Phase.POST, this.aV));
    }

    @Redirect(method = {"saveOptions"}, at = @At(value = "NEW", target = "java/io/PrintWriter"))
    public PrintWriter labyMod$replaceWriter(Writer out) {
        return new OptionsTranslator.OptionWriter(out);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getFrameLimit() {
        return this.i;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getChatBackgroundColor() {
        return getBackgroundColorWithOpacity(Integer.MIN_VALUE);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getBackgroundColorWithOpacity(int baseColor) {
        return baseColor;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public Perspective perspective() {
        if (this.aw == 0) {
            return Perspective.FIRST_PERSON;
        }
        if (this.aw == 1) {
            return Perspective.THIRD_PERSON_BACK;
        }
        if (this.aw == 2) {
            return Perspective.THIRD_PERSON_FRONT;
        }
        throw new RuntimeException("No perspective set");
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setModelParts(int value) {
        for (aee part : aee.values()) {
            if ((value & part.a()) == part.a()) {
                this.aT.add(part);
            } else {
                this.aT.remove(part);
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getModelParts() {
        int mask = 0;
        for (aee part : this.aT) {
            mask |= part.a();
        }
        return mask;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void sendOptionsToServer() {
        c();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isEyeProtectionActive() {
        return true;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setEyeProtectionActive(boolean darkMode) {
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getCurrentLanguage() {
        return this.aJ.toLowerCase(Locale.US);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public String getLastKnownServer() {
        return this.aA;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setLastKnownServer(String address) {
        this.aA = address;
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.mixins.client.MixinGameSettings$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinGameSettings$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$entity$player$EntityPlayer$EnumChatVisibility = new int[b.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$entity$player$EntityPlayer$EnumChatVisibility[b.b.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$entity$player$EntityPlayer$EnumChatVisibility[b.c.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$entity$player$EntityPlayer$EnumChatVisibility[b.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public ChatVisibility chatVisibility() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$entity$player$EntityPlayer$EnumChatVisibility[this.o.ordinal()]) {
            case 1:
                return ChatVisibility.COMMANDS_ONLY;
            case 2:
                return ChatVisibility.HIDDEN;
            case 3:
            default:
                return ChatVisibility.SHOWN;
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MainHand mainHand() {
        return this.C == vo.a ? MainHand.LEFT : MainHand.RIGHT;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setMainHand(MainHand hand) {
        this.C = hand == MainHand.LEFT ? vo.a : vo.b;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightOpen() {
        return (float) Math.floor((((double) this.J) * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatHeightClosed() {
        return (float) Math.floor((((double) this.I) * 160.0d) + 20.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public float getChatWidth() {
        return (float) Math.floor((((double) this.H) * 280.0d) + 40.0d);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatScale() {
        return this.G;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatTextOpacity() {
        return (this.s * 0.9f) + 0.1f;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getTextBackgroundOpacity() {
        return getChatTextOpacity() * 0.5d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getChatLineSpacing() {
        return 0.0d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatColorsEnabled() {
        return this.p;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinksEnabled() {
        return this.q;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isChatLinkConfirmationEnabled() {
        return this.r;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isSmoothCamera() {
        return this.aB;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setSmoothCamera(boolean smooth) {
        this.aB = smooth;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping getInputMapping(String name) {
        return InputMappingResolver.resolve(name);
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping useItemInput() {
        return this.ad;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping attackInput() {
        return this.ae;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sneakInput() {
        return this.Y;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public MinecraftInputMapping sprintInput() {
        return this.Z;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isFullscreen() {
        return this.u;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setFullscreen(boolean fullscreen) {
        this.u = fullscreen;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setPerspective(Perspective perspective) {
        this.aw = perspective.ordinal();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBobbing() {
        return this.f;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void setBobbing(boolean bobbing) {
        this.f = bobbing;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFov() {
        return this.aD;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public double getFovEffectScale() {
        return 1.0d;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isHideGUI() {
        return this.av;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isDebugEnabled() {
        return this.ax;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public void save() {
        b();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public AttackIndicatorPosition attackIndicatorPosition() {
        return AttackIndicatorPosition.OFF;
    }

    @Insert(method = {"setOptionValue"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatVisibility:Lnet/minecraft/entity/player/EntityPlayer$EnumChatVisibility;", shift = At.Shift.AFTER))
    private void labyMod$refreshChatForChatVisibility(a option, int $$, InsertInfo ci) {
        Laby.fireEvent(VISIBILITY_CHAT_SCREEN_UPDATE_EVENT);
    }

    @Insert(method = {"setOptionValue"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;chatColours:Z", shift = At.Shift.AFTER))
    private void labyMod$refreshChatForChatColors(a option, int $$, InsertInfo ci) {
        Laby.fireEvent(COLORS_CHAT_SCREEN_UPDATE_EVENT);
    }

    @Inject(method = {"isKeyDown"}, at = {@At("HEAD")}, cancellable = true)
    private static void labyMod$checkForHiddenKeys(bhy key, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) key)) {
            cir.setReturnValue(false);
        }
    }

    @WrapOperation(method = {"setOptionValue"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;guiScale:I", ordinal = 1)})
    private void labyMod$setGuiScale(bid instance, int newValue, Operation<Void> original, @Local(argsOnly = true) int value) {
        int guiScale = this.aG + value;
        original.call(new Object[]{instance, 0});
        bit scaledResolution = new bit(bib.z());
        if (guiScale > scaledResolution.e()) {
            return;
        }
        original.call(new Object[]{instance, Integer.valueOf(guiScale)});
    }

    @Inject(method = {"getKeyBinding"}, at = {@At("HEAD")}, cancellable = true)
    public void labyMod$changeKeyBinding(a option, CallbackInfoReturnable<String> cir) {
        if (option == a.n && this.aG != 0) {
            cir.setReturnValue(cey.a(option.d(), new Object[0]) + ": " + this.aG);
        }
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isBackgroundForChatOnly() {
        return false;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getRenderDistance() {
        return this.e;
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public int getActualRenderDistance() {
        return getRenderDistance();
    }

    @Override // net.labymod.api.client.options.MinecraftOptions
    public boolean isVSyncEnabled() {
        return this.v;
    }
}

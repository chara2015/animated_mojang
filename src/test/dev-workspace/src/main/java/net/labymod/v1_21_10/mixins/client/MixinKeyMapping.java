package net.labymod.v1_21_10.mixins.client;

import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.v1_21_10.client.gui.screen.key.mapper.VersionedKeyMapper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/MixinKeyMapping.class */
@Mixin({fzx.class})
@Implements({@Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE)})
public abstract class MixinKeyMapping implements MinecraftInputMapping {

    @Shadow
    private a a;

    @Shadow
    @Final
    private static Map<String, fzx> b;

    @Shadow
    private boolean g;

    @Shadow
    private int h;
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;

    @Shadow
    public abstract boolean f();

    @Shadow
    public abstract String k();

    @Shadow
    protected abstract void i();

    @Shadow
    public abstract void a(boolean z);

    @Inject(method = {"<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILnet/minecraft/client/KeyMapping$Category;)V"}, at = {@At("RETURN")})
    private void labyMod$fireRegisterKeybindingEvent(String $$0, b $$1, int $$2, a $$3, CallbackInfo ci) {
        RegisterKeybindingEvent event = (RegisterKeybindingEvent) Laby.fireEvent(new RegisterKeybindingEvent($$0, $$3.b().toString()));
        if (event.isCancelled()) {
            this.labyMod$hidden = true;
        } else if (event.getReplacement() != null) {
            this.labyMod$replacement = event.getReplacement();
        }
    }

    @Inject(method = {"isDown"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$disableKeybind(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced(this)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = {"same"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$same(fzx mapping, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) mapping)) {
            cir.setReturnValue(false);
        }
    }

    @Intrinsic
    public boolean input$isDown() {
        return f();
    }

    @Intrinsic
    public String input$getName() {
        return k();
    }

    @Intrinsic
    public String input$getCategory() {
        return getCategory();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public int getKeyCode() {
        return this.a.b();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    @NotNull
    public Key key() {
        Key key;
        b type = this.a.a();
        if (type == b.c) {
            key = KeyMapper.getMouseButton(getKeyCode());
        } else {
            key = KeyMapper.getKey(getKeyCode());
        }
        return key == null ? Key.NONE : key;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isMouse() {
        return this.a.a() == b.c;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void unpress() {
        i();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void press() {
        a(true);
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isActuallyDown() {
        b type = this.a.a();
        if (type == b.c) {
            return VersionedKeyMapper.isMousePressed(getKeyCode());
        }
        return VersionedKeyMapper.isKeyPressed(getKeyCode());
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isHidden() {
        return this.labyMod$hidden;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public Widget getReplacement() {
        if (this.labyMod$replacement != null) {
            return this.labyMod$replacement.get();
        }
        return null;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean hasReplacement() {
        return this.labyMod$replacement != null;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void addCategory(String category) {
    }

    public void updateDown(boolean down) {
        this.g = down;
    }

    public void resetClickCount() {
        this.h = 0;
    }
}

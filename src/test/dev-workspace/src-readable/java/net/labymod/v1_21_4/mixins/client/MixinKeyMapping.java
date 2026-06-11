package net.labymod.v1_21_4.mixins.client;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.v1_21_4.client.gui.screen.key.mapper.VersionedKeyMapper;
import org.jetbrains.annotations.NotNull;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinKeyMapping.class */
@Mixin({fli.class})
@Implements({@Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE)})
public abstract class MixinKeyMapping implements MinecraftInputMapping {

    @Shadow
    private a o;

    @Shadow
    @Final
    private static Map<String, fli> h;

    @Shadow
    private boolean p;

    @Shadow
    private int q;

    @Shadow
    @Final
    private static Set<String> j;

    @Shadow
    @Final
    private static Map<String, Integer> k;
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;

    @Shadow
    public abstract boolean e();

    @Shadow
    public abstract String h();

    @Shadow
    public abstract String f();

    @Shadow
    protected abstract void n();

    @Shadow
    public abstract void a(boolean z);

    @Inject(method = {"<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V"}, at = {@At("RETURN")})
    private void labyMod$fireRegisterKeybindingEvent(String name, b type, int key, String category, CallbackInfo ci) {
        RegisterKeybindingEvent event = (RegisterKeybindingEvent) Laby.fireEvent(new RegisterKeybindingEvent(name, category));
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

    @Redirect(method = {"resetMapping"}, at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static Object labyMod$disableKeybind(Map instance, Object k2, Object v) {
        if (!MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) v)) {
            return instance.put(k2, v);
        }
        return null;
    }

    @Inject(method = {"same"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$same(fli mapping, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) mapping)) {
            cir.setReturnValue(false);
        }
    }

    @Intrinsic
    public boolean input$isDown() {
        return e();
    }

    @Intrinsic
    public String input$getName() {
        return h();
    }

    @Intrinsic
    public String input$getCategory() {
        return f();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public int getKeyCode() {
        return this.o.b();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    @NotNull
    public Key key() {
        Key key;
        b type = this.o.a();
        if (type == b.c) {
            key = KeyMapper.getMouseButton(getKeyCode());
        } else {
            key = KeyMapper.getKey(getKeyCode());
        }
        return key == null ? Key.NONE : key;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isMouse() {
        return this.o.a() == b.c;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void unpress() {
        n();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void press() {
        a(true);
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isActuallyDown() {
        b type = this.o.a();
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
        if (k.containsKey(category)) {
            return;
        }
        int lastSortOrder = 0;
        for (Integer sortOrder : k.values()) {
            if (lastSortOrder < sortOrder.intValue()) {
                lastSortOrder = sortOrder.intValue();
            }
        }
        k.put(category, Integer.valueOf(lastSortOrder + 1));
    }

    public void updateDown(boolean down) {
        this.p = down;
    }

    public void resetClickCount() {
        this.q = 0;
    }
}

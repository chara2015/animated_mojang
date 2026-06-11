package net.labymod.v1_16_5.mixins.client;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.v1_16_5.client.gui.screen.key.mapper.VersionedKeyMapper;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/MixinKeyMapping.class */
@Mixin({djw.class})
@Implements({@Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE)})
public abstract class MixinKeyMapping implements MinecraftInputMapping {

    @Shadow
    private a h;

    @Shadow
    @Final
    private static Map<String, djw> a;

    @Shadow
    private boolean i;

    @Shadow
    private int j;

    @Shadow
    @Final
    private static Set<String> c;

    @Shadow
    @Final
    private static Map<String, Integer> d;
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;

    @Shadow
    public abstract boolean d();

    @Shadow
    public abstract String g();

    @Shadow
    public abstract String e();

    @Shadow
    protected abstract void m();

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
    private static Object labyMod$disableKeybind(Map instance, Object k, Object v) {
        if (!MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) v)) {
            return instance.put(k, v);
        }
        return null;
    }

    @Inject(method = {"same"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$same(djw mapping, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) mapping)) {
            cir.setReturnValue(false);
        }
    }

    @Intrinsic
    public boolean input$isDown() {
        return d();
    }

    @Intrinsic
    public String input$getName() {
        return g();
    }

    @Intrinsic
    public String input$getCategory() {
        return e();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public int getKeyCode() {
        return this.h.b();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    @NotNull
    public Key key() {
        Key key;
        b type = this.h.a();
        if (type == b.c) {
            key = KeyMapper.getMouseButton(getKeyCode());
        } else {
            key = KeyMapper.getKey(getKeyCode());
        }
        return key == null ? Key.NONE : key;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isMouse() {
        return this.h.a() == b.c;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void unpress() {
        m();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void press() {
        a(true);
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isActuallyDown() {
        b type = this.h.a();
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
        if (d.containsKey(category)) {
            return;
        }
        int lastSortOrder = 0;
        for (Integer sortOrder : d.values()) {
            if (lastSortOrder < sortOrder.intValue()) {
                lastSortOrder = sortOrder.intValue();
            }
        }
        d.put(category, Integer.valueOf(lastSortOrder + 1));
    }

    public void updateDown(boolean down) {
        this.i = down;
    }

    public void resetClickCount() {
        this.j = 0;
    }
}

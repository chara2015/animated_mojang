package net.labymod.v1_12_2.mixins.client.settings;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.event.client.input.RegisterKeybindingEvent;
import net.labymod.core.client.options.InputMappingResolver;
import net.labymod.v1_12_2.client.component.VersionedKeybindComponent;
import net.labymod.v1_12_2.client.gui.screen.key.mapper.VersionedKeyMapper;
import net.labymod.v1_12_2.client.settings.KeyBindingIntHashMap;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/settings/MixinKeyBinding.class */
@Mixin({bhy.class})
@Implements({@Interface(iface = MinecraftInputMapping.class, prefix = "input$", remap = Interface.Remap.NONE)})
public abstract class MixinKeyBinding implements MinecraftInputMapping {
    private boolean labyMod$hidden;
    private Supplier<Widget> labyMod$replacement;

    @Shadow
    private boolean i;

    @Shadow
    @Final
    private static Map<String, bhy> a;

    @Shadow
    @Final
    private static Map<String, Integer> d;

    @Shadow
    public abstract boolean e();

    @Shadow
    public abstract String h();

    @Shadow
    public abstract String f();

    @Shadow
    protected abstract void k();

    @Shadow
    public abstract int shadow$j();

    @Shadow
    public abstract int j();

    @Redirect(method = {"<clinit>"}, at = @At(value = "NEW", target = "()Lnet/minecraft/util/IntHashMap;"))
    private static rg labyMod$createIntHashMap() {
        return new KeyBindingIntHashMap();
    }

    @Inject(method = {"<clinit>"}, at = {@At("TAIL")})
    private static void labyMod$createKeySupplier(CallbackInfo ci) {
        InputMappingResolver.setResolver(keyDescription -> {
            Iterator<bhy> it = a.values().iterator();
            while (it.hasNext()) {
                MinecraftInputMapping minecraftInputMapping = (bhy) it.next();
                if (minecraftInputMapping.h().equals(keyDescription)) {
                    return minecraftInputMapping;
                }
            }
            return null;
        });
        VersionedKeybindComponent.setKeyResolver(keyDescription2 -> {
            for (bhy keyBinding : a.values()) {
                if (keyBinding.h().equals(keyDescription2)) {
                    return () -> {
                        Key key = Key.get(keyBinding.j());
                        return Component.translatable(key.getTranslationKey(), new Component[0]);
                    };
                }
            }
            return null;
        });
    }

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void labyMod$fireRegisterKeybindingEvent(String name, int key, String category, CallbackInfo ci) {
        RegisterKeybindingEvent event = (RegisterKeybindingEvent) Laby.fireEvent(new RegisterKeybindingEvent(name, category));
        if (event.isCancelled()) {
            this.labyMod$hidden = true;
        } else if (event.getReplacement() != null) {
            this.labyMod$replacement = event.getReplacement();
        }
    }

    @Inject(method = {"isKeyDown"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$disableKeybind(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftInputMapping.isHiddenOrReplaced(this)) {
            cir.setReturnValue(false);
        }
    }

    @Redirect(method = {"resetKeyBindingArrayAndHash"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/IntHashMap;addKey(ILjava/lang/Object;)V"))
    private static void labyMod$disableKeybind(rg instance, int k, Object v) {
        if (!MinecraftInputMapping.isHiddenOrReplaced((MinecraftInputMapping) v)) {
            instance.a(k, v);
        }
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isDown() {
        return e();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public String getName() {
        return h();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public String getCategory() {
        return f();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void unpress() {
        k();
    }

    @Intrinsic
    public int input$getKeyCode() {
        return shadow$j();
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public void press() {
        this.i = true;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isActuallyDown() {
        int keyCode = j();
        if (keyCode < 0) {
            return VersionedKeyMapper.isMousePressed(keyCode + 100);
        }
        return VersionedKeyMapper.isKeyPressed(keyCode);
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
    @NotNull
    public Key key() {
        Key key;
        int keyCode = j();
        if (keyCode < 0) {
            key = KeyMapper.getMouseButton(keyCode + 100);
        } else {
            key = KeyMapper.getKey(keyCode);
        }
        return key == null ? Key.NONE : key;
    }

    @Override // net.labymod.api.client.options.MinecraftInputMapping
    public boolean isMouse() {
        return j() < 0;
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
}

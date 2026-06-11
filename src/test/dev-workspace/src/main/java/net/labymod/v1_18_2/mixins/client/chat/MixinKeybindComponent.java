package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/chat/MixinKeybindComponent.class */
@Mixin({qp.class})
@Implements({@Interface(iface = KeybindComponent.class, prefix = "keybindComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinKeybindComponent extends MixinBaseComponent<KeybindComponent> implements KeybindComponent {

    @Shadow
    @Mutable
    @Final
    private String e;

    @Shadow
    public abstract qp shadow$h();

    @Shadow
    public abstract String shadow$i();

    @Shadow
    protected abstract qk j();

    @Override // net.labymod.api.client.component.KeybindComponent
    public KeybindComponent keybind(String keybind) {
        this.e = keybind;
        return this;
    }

    @Intrinsic
    public String keybindComponent$getKeybind() {
        return shadow$i();
    }

    @Intrinsic
    public KeybindComponent keybindComponent$plainCopy() {
        return shadow$h();
    }

    @Override // net.labymod.api.client.component.KeybindComponent
    public Component resolveKeybind() {
        return j();
    }

    public int hashCode() {
        return Objects.hash(c(), this.a, this.e);
    }
}

package net.labymod.v1_21_1.mixins.client.chat;

import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/chat/MixinHoverEvent.class */
@Mixin({xf.class})
@Implements({@Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE)})
public abstract class MixinHoverEvent implements HoverEvent {

    @Shadow
    @Final
    private e<?> b;

    @Override // net.labymod.api.client.component.event.HoverEvent
    public Object value() {
        Object value = this.b.b();
        if (value instanceof MutableComponentAccessor) {
            MutableComponentAccessor component = (MutableComponentAccessor) value;
            return component.getLabyComponent();
        }
        throw new IllegalStateException("Unknown value type: " + value.getClass().getName());
    }

    @Override // net.labymod.api.client.component.event.HoverEvent
    public HoverEvent.Action action() {
        if (this.b.a() == a.a) {
            return HoverEvent.Action.SHOW_TEXT;
        }
        return null;
    }
}

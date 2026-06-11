package net.labymod.v1_21_11.mixins.client.chat;

import net.labymod.api.client.component.event.HoverEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/chat/MixinHoverEvent.class */
@Mixin({yo.class})
@Implements({@Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE)})
public interface MixinHoverEvent extends HoverEvent {
    @Shadow
    a shadow$a();

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.event.HoverEvent
    default Object value() throws MatchException {
        if (this instanceof e) {
            try {
                return ((e) this).b().getLabyComponent();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        throw new IllegalStateException("Unknown value type: " + getClass().getName());
    }

    @Override // net.labymod.api.client.component.event.HoverEvent
    default HoverEvent.Action<?> action() {
        if (shadow$a() == a.a) {
            return HoverEvent.Action.SHOW_TEXT;
        }
        return null;
    }
}

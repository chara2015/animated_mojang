package net.labymod.v1_21_11.mixins.client.chat;

import net.labymod.api.client.component.event.HoverEvent;
import net.minecraft.network.chat.HoverEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/chat/MixinHoverEvent.class */
@Mixin({HoverEvent.class})
@Implements({@Interface(iface = net.labymod.api.client.component.event.HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE)})
public interface MixinHoverEvent extends net.labymod.api.client.component.event.HoverEvent {
    @Shadow
    HoverEvent.Action shadow$a();

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    default Object value() throws MatchException {
        if (this instanceof HoverEvent.ShowText) {
            try {
                return ((HoverEvent.ShowText) this).value().getLabyComponent();
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        throw new IllegalStateException("Unknown value type: " + getClass().getName());
    }

    default HoverEvent.Action<?> action() {
        if (shadow$a() == HoverEvent.Action.SHOW_TEXT) {
            return HoverEvent.Action.SHOW_TEXT;
        }
        return null;
    }
}

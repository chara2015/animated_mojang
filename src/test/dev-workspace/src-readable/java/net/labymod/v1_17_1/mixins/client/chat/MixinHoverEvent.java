package net.labymod.v1_17_1.mixins.client.chat;

import net.labymod.api.client.component.event.HoverEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/chat/MixinHoverEvent.class */
@Mixin({ow.class})
@Implements({@Interface(iface = HoverEvent.class, prefix = "hoverEvent$", remap = Interface.Remap.NONE)})
public abstract class MixinHoverEvent implements HoverEvent {
    @Shadow
    public abstract a<?> a();

    @Shadow
    @Nullable
    public abstract <T> T a(a<T> aVar);

    @Override // net.labymod.api.client.component.event.HoverEvent
    public Object value() {
        return a(a());
    }

    @Override // net.labymod.api.client.component.event.HoverEvent
    public HoverEvent.Action action() {
        a<?> action = a();
        if (action == a.a) {
            return HoverEvent.Action.SHOW_TEXT;
        }
        return null;
    }
}

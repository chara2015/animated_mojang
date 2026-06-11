package net.labymod.v1_8_9.mixins.client.component;

import net.labymod.api.client.component.event.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinHoverEvent.class */
@Mixin({ew.class})
public abstract class MixinHoverEvent implements HoverEvent {
    @Shadow
    public abstract a a();

    @Shadow
    public abstract eu b();

    /* JADX INFO: renamed from: net.labymod.v1_8_9.mixins.client.component.MixinHoverEvent$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinHoverEvent$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$event$HoverEvent$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$event$HoverEvent$Action[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    @Override // net.labymod.api.client.component.event.HoverEvent
    public HoverEvent.Action action() {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$event$HoverEvent$Action[a().ordinal()]) {
            case 1:
                return HoverEvent.Action.SHOW_TEXT;
            default:
                return null;
        }
    }

    @Override // net.labymod.api.client.component.event.HoverEvent
    public Object value() {
        return b();
    }
}

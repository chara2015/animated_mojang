package net.labymod.v1_21_4.mixins.client.chat;

import net.labymod.api.client.component.event.ClickEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/chat/MixinClickEvent.class */
@Mixin({wn.class})
@Implements({@Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE)})
public abstract class MixinClickEvent implements ClickEvent {
    @Shadow
    public abstract a a();

    @Shadow
    public abstract String shadow$b();

    /* JADX INFO: renamed from: net.labymod.v1_21_4.mixins.client.chat.MixinClickEvent$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/chat/MixinClickEvent$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$chat$ClickEvent$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.event.ClickEvent
    public ClickEvent.Action action() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a().ordinal()]) {
            case 1:
                return ClickEvent.Action.OPEN_URL;
            case 2:
                return ClickEvent.Action.OPEN_FILE;
            case 3:
                return ClickEvent.Action.RUN_COMMAND;
            case 4:
                return ClickEvent.Action.SUGGEST_COMMAND;
            case 5:
                return ClickEvent.Action.CHANGE_PAGE;
            case 6:
                return ClickEvent.Action.COPY_TO_CLIPBOARD;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Intrinsic
    public String clickEvent$getValue() {
        return shadow$b();
    }
}

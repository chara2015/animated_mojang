package net.labymod.v1_21_10.mixins.client.chat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.net.URI;
import java.util.Objects;
import net.labymod.api.client.component.event.ClickEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/MixinClickEvent.class */
@Mixin({xv.class})
@Implements({@Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE)})
public interface MixinClickEvent extends ClickEvent {
    @Shadow
    a shadow$a();

    /* JADX INFO: renamed from: net.labymod.v1_21_10.mixins.client.chat.MixinClickEvent$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/MixinClickEvent$1.class */
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
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.g.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[a.h.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.event.ClickEvent
    default ClickEvent.Action action() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$network$chat$ClickEvent$Action[shadow$a().ordinal()]) {
            case 1:
                return ClickEvent.Action.OPEN_URL;
            case 2:
                return ClickEvent.Action.OPEN_FILE;
            case 3:
                return ClickEvent.Action.RUN_COMMAND;
            case 4:
                return ClickEvent.Action.SUGGEST_COMMAND;
            case 5:
                return ClickEvent.Action.SHOW_DIALOG;
            case 6:
                return ClickEvent.Action.CHANGE_PAGE;
            case 7:
                return ClickEvent.Action.COPY_TO_CLIPBOARD;
            case 8:
                return ClickEvent.Action.CUSTOM;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Intrinsic
    default String clickEvent$getValue() throws MatchException {
        String string;
        b bVar = (xv) this;
        Objects.requireNonNull(bVar);
        int i = 0;
        while (true) {
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), b.class, c.class, e.class, f.class, g.class, i.class, h.class, d.class).dynamicInvoker().invoke(bVar, i) /* invoke-custom */) {
                case 0:
                    try {
                        int page = bVar.b();
                        if (1 == 0) {
                            i = 1;
                        } else {
                            string = String.valueOf(page);
                        }
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                    break;
                case 1:
                    String value = ((c) bVar).b();
                    string = value;
                    break;
                case 2:
                    String path = ((e) bVar).c();
                    string = path;
                    break;
                case 3:
                    URI uri = ((f) bVar).b();
                    string = uri.toString();
                    break;
                case 4:
                    String command = ((g) bVar).b();
                    string = command;
                    break;
                case 5:
                    String command2 = ((i) bVar).b();
                    string = command2;
                    break;
                case 6:
                    ((h) bVar).b();
                    string = "<dialog>";
                    break;
                case 7:
                    d dVar = (d) bVar;
                    amj resourceLocation = dVar.b();
                    dVar.c();
                    string = resourceLocation.b() + ":" + resourceLocation.a();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(bVar));
            }
        }
        return string;
    }
}

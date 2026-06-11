package net.labymod.v26_1_1.mixins.client.chat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.net.URI;
import java.util.Objects;
import net.labymod.api.client.component.event.ClickEvent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/chat/MixinClickEvent.class */
@Mixin({ClickEvent.class})
@Implements({@Interface(iface = net.labymod.api.client.component.event.ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE)})
public interface MixinClickEvent extends net.labymod.api.client.component.event.ClickEvent {
    @Shadow
    ClickEvent.Action shadow$action();

    /* JADX INFO: renamed from: net.labymod.v26_1_1.mixins.client.chat.MixinClickEvent$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/chat/MixinClickEvent$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$network$chat$ClickEvent$Action = new int[ClickEvent.Action.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.OPEN_URL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.OPEN_FILE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.RUN_COMMAND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.SUGGEST_COMMAND.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.SHOW_DIALOG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.CHANGE_PAGE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.COPY_TO_CLIPBOARD.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$network$chat$ClickEvent$Action[ClickEvent.Action.CUSTOM.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.component.event.ClickEvent
    default ClickEvent.Action action() throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$network$chat$ClickEvent$Action[shadow$action().ordinal()]) {
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
        ClickEvent.ChangePage changePage = (net.minecraft.network.chat.ClickEvent) this;
        Objects.requireNonNull(changePage);
        int i = 0;
        while (true) {
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, net.minecraft.network.chat.ClickEvent.class, Integer.TYPE), ClickEvent.ChangePage.class, ClickEvent.CopyToClipboard.class, ClickEvent.OpenFile.class, ClickEvent.OpenUrl.class, ClickEvent.RunCommand.class, ClickEvent.SuggestCommand.class, ClickEvent.ShowDialog.class, ClickEvent.Custom.class).dynamicInvoker().invoke(changePage, i) /* invoke-custom */) {
                case 0:
                    try {
                        int page = changePage.page();
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
                    String value = ((ClickEvent.CopyToClipboard) changePage).value();
                    string = value;
                    break;
                case 2:
                    String path = ((ClickEvent.OpenFile) changePage).path();
                    string = path;
                    break;
                case 3:
                    URI uri = ((ClickEvent.OpenUrl) changePage).uri();
                    string = uri.toString();
                    break;
                case 4:
                    String command = ((ClickEvent.RunCommand) changePage).command();
                    string = command;
                    break;
                case 5:
                    String command2 = ((ClickEvent.SuggestCommand) changePage).command();
                    string = command2;
                    break;
                case 6:
                    ((ClickEvent.ShowDialog) changePage).dialog();
                    string = "<dialog>";
                    break;
                case 7:
                    ClickEvent.Custom custom = (ClickEvent.Custom) changePage;
                    Identifier identifier = custom.id();
                    custom.payload();
                    string = identifier.getNamespace() + ":" + identifier.getPath();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(changePage));
            }
        }
        return string;
    }
}

package net.labymod.v1_8_9.mixins.client.component;

import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.v1_8_9.client.component.VersionedClickEvent;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinClickEvent.class */
@Mixin({et.class})
@Implements({@Interface(iface = ClickEvent.class, prefix = "clickEvent$", remap = Interface.Remap.NONE)})
public abstract class MixinClickEvent implements VersionedClickEvent, ClickEvent {
    private ClickEvent.Action labyMod$action;

    @Shadow
    public abstract a a();

    @Shadow
    public abstract String shadow$b();

    @Override // net.labymod.v1_8_9.client.component.VersionedClickEvent
    public void labyMod$setAction(ClickEvent.Action action) {
        this.labyMod$action = action;
    }

    @Override // net.labymod.api.client.component.event.ClickEvent
    public ClickEvent.Action action() {
        if (this.labyMod$action != null) {
            return this.labyMod$action;
        }
        switch (AnonymousClass1.$SwitchMap$net$minecraft$event$ClickEvent$Action[a().ordinal()]) {
            case 1:
                return ClickEvent.Action.OPEN_URL;
            case 2:
                return ClickEvent.Action.OPEN_FILE;
            case 3:
                return ClickEvent.Action.RUN_COMMAND;
            case 4:
                return ClickEvent.Action.CHANGE_PAGE;
            default:
                return ClickEvent.Action.SUGGEST_COMMAND;
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_8_9.mixins.client.component.MixinClickEvent$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinClickEvent$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$event$ClickEvent$Action = new int[a.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$event$ClickEvent$Action[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$event$ClickEvent$Action[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$event$ClickEvent$Action[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$event$ClickEvent$Action[a.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @Intrinsic
    public String clickEvent$getValue() {
        return shadow$b();
    }
}

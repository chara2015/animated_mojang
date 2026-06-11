package net.labymod.v1_21_1.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_1.client.component.VersionedIconContents;
import net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_1.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_1.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_1.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/chat/MixinMutableComponent.class */
@Mixin({xn.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, wz {
    private final List<Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<wz> d;

    @Shadow
    @Mutable
    @Final
    private xa c;

    @Shadow
    private tw g;

    @Override // net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v1_21_1.client.network.chat.MutableComponentAccessor
    public void setContents(xa contents) {
        this.c = contents;
        this.g = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(xa contents, List<wz> $$1, xw $$2, CallbackInfo ci) {
        this.d = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.d.addAll($$1);
        xn holder = (xn) this;
        if (contents instanceof yk) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof a) {
            a literalContents = (a) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
            return;
        }
        if (contents instanceof yd) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof yh) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        } else if (contents == yg.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof yf));
        }
    }

    public xn f() {
        a ykVar = this.c;
        if (ykVar instanceof a) {
            a literalContents = ykVar;
            ykVar = new a(literalContents.b());
        } else if (ykVar instanceof yk) {
            yk translatableContents = (yk) ykVar;
            Object[] args = translatableContents.d();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            ykVar = new yk(translatableContents.b(), translatableContents.c(), arguments);
        }
        List<wz> siblings = new ArrayList<>();
        for (wz sibling : this.d) {
            siblings.add(sibling.f());
        }
        return new xn(ykVar, siblings, a());
    }
}

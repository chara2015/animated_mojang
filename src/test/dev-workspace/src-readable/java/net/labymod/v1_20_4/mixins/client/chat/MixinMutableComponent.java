package net.labymod.v1_20_4.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_20_4.client.component.VersionedIconContents;
import net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_20_4.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_20_4.client.network.chat.VersionedIconComponent;
import net.labymod.v1_20_4.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_20_4.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_20_4.client.network.chat.VersionedTextComponent;
import net.labymod.v1_20_4.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_20_4.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/chat/MixinMutableComponent.class */
@Mixin({vt.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, vf {
    private final List<Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<vf> d;

    @Shadow
    @Mutable
    @Final
    private vg c;

    @Shadow
    private si g;

    @Override // net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor
    public void setContents(vg contents) {
        this.c = contents;
        this.g = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(vg contents, List<vf> $$1, wc $$2, CallbackInfo ci) {
        this.d = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.d.addAll($$1);
        vt holder = (vt) this;
        if (contents instanceof wq) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof a) {
            a literalContents = (a) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
            return;
        }
        if (contents instanceof wj) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof wn) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        } else if (contents == wm.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof wl));
        }
    }

    public vt f() {
        a wqVar = this.c;
        if (wqVar instanceof a) {
            a literalContents = wqVar;
            wqVar = new a(literalContents.b());
        } else if (wqVar instanceof wq) {
            wq translatableContents = (wq) wqVar;
            Object[] args = translatableContents.d();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            wqVar = new wq(translatableContents.b(), translatableContents.c(), arguments);
        }
        List<vf> siblings = new ArrayList<>();
        for (vf sibling : this.d) {
            siblings.add(sibling.f());
        }
        return new vt(wqVar, siblings, a());
    }
}

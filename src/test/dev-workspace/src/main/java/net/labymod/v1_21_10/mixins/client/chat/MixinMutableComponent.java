package net.labymod.v1_21_10.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_10.client.component.VersionedIconContents;
import net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_10.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedObjectComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_10.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_10.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/MixinMutableComponent.class */
@Mixin({ym.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, xx {
    private final List<Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<xx> d;

    @Shadow
    @Mutable
    @Final
    private xy c;

    @Shadow
    private uk g;

    @Override // net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor
    public void setContents(xy contents) {
        this.c = contents;
        this.g = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(xy contents, List<xx> $$1, yv $$2, CallbackInfo ci) {
        this.d = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.d.addAll($$1);
        ym holder = (ym) this;
        if (contents instanceof zg) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof a) {
            a literalContents = (a) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
            return;
        }
        if (contents instanceof yz) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof ze) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
            return;
        }
        if (contents == zd.b) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else if (contents instanceof zc) {
            this.labyMod$component = new VersionedObjectComponent(holder);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof zb));
        }
    }

    public ym f() {
        a zgVar = this.c;
        if (zgVar instanceof a) {
            a literalContents = zgVar;
            zgVar = new a(literalContents.b());
        } else if (zgVar instanceof zg) {
            zg translatableContents = (zg) zgVar;
            Object[] args = translatableContents.d();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            zgVar = new zg(translatableContents.b(), translatableContents.c(), arguments);
        }
        List<xx> siblings = new ArrayList<>();
        for (xx sibling : this.d) {
            siblings.add(sibling.f());
        }
        return new ym(zgVar, siblings, a());
    }
}

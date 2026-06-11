package net.labymod.v1_21_4.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_4.client.component.VersionedIconContents;
import net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_4.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedIconComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedTextComponent;
import net.labymod.v1_21_4.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_21_4.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/chat/MixinMutableComponent.class */
@Mixin({xd.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, wp {
    private final List<Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<wp> d;

    @Shadow
    @Mutable
    @Final
    private wq c;

    @Shadow
    private tl g;

    @Override // net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v1_21_4.client.network.chat.MutableComponentAccessor
    public void setContents(wq contents) {
        this.c = contents;
        this.g = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(wq contents, List<wp> $$1, xm $$2, CallbackInfo ci) {
        this.d = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.d.addAll($$1);
        xd holder = (xd) this;
        if (contents instanceof ya) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof a) {
            a literalContents = (a) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.b().isEmpty());
            return;
        }
        if (contents instanceof xt) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof xx) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        } else if (contents == xw.c) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof xv));
        }
    }

    public xd f() {
        a yaVar = this.c;
        if (yaVar instanceof a) {
            a literalContents = yaVar;
            yaVar = new a(literalContents.b());
        } else if (yaVar instanceof ya) {
            ya translatableContents = (ya) yaVar;
            Object[] args = translatableContents.d();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            yaVar = new ya(translatableContents.b(), translatableContents.c(), arguments);
        }
        List<wp> siblings = new ArrayList<>();
        for (wp sibling : this.d) {
            siblings.add(sibling.f());
        }
        return new xd(yaVar, siblings, a());
    }
}

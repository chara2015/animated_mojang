package net.labymod.v1_20_1.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_20_1.client.component.VersionedIconContents;
import net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_20_1.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedIconComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedKeybindComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedScoreComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedTextComponent;
import net.labymod.v1_20_1.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v1_20_1.client.util.WatchableComponentSiblingList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/chat/MixinMutableComponent.class */
@Mixin({tj.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, sw {
    private final List<Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<sw> d;

    @Shadow
    @Mutable
    @Final
    private sx c;

    @Shadow
    private qm g;

    @Override // net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor
    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v1_20_1.client.network.chat.MutableComponentAccessor
    public void setContents(sx contents) {
        this.c = contents;
        this.g = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(sx contents, List<sw> $$1, ts $$2, CallbackInfo ci) {
        this.d = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.d.addAll($$1);
        tj holder = (tj) this;
        if (contents instanceof ug) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof ub) {
            ub literalContents = (ub) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.a().isEmpty());
            return;
        }
        if (contents instanceof tz) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof ud) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
        } else if (contents == sx.a) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof uc));
        }
    }

    public tj e() {
        ub ugVar = this.c;
        if (ugVar instanceof ub) {
            ub literalContents = ugVar;
            ugVar = new ub(literalContents.a());
        } else if (ugVar instanceof ug) {
            ug translatableContents = (ug) ugVar;
            Object[] args = translatableContents.c();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            ugVar = new ug(translatableContents.a(), translatableContents.b(), arguments);
        }
        List<sw> siblings = new ArrayList<>();
        for (sw sibling : this.d) {
            siblings.add(sibling.e());
        }
        return new tj(ugVar, siblings, a());
    }
}

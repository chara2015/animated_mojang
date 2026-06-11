package net.labymod.v26_2_snapshot_8.mixins.client.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v26_2_snapshot_8.client.component.VersionedIconContents;
import net.labymod.v26_2_snapshot_8.client.network.chat.MutableComponentAccessor;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedBaseComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedIconComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedKeybindComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedObjectComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedScoreComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedTextComponent;
import net.labymod.v26_2_snapshot_8.client.network.chat.VersionedTranslatableComponent;
import net.labymod.v26_2_snapshot_8.client.util.WatchableComponentSiblingList;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.ObjectContents;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/chat/MixinMutableComponent.class */
@Mixin({MutableComponent.class})
@Implements({@Interface(iface = MutableComponentAccessor.class, prefix = "component$", remap = Interface.Remap.NONE)})
public abstract class MixinMutableComponent implements MutableComponentAccessor, Component {
    private final List<net.labymod.api.client.component.Component> labyMod$children = new ArrayList();
    private VersionedBaseComponent<? extends net.labymod.api.client.component.Component, ?> labyMod$component;

    @Shadow
    @Mutable
    @Final
    private List<Component> siblings;

    @Shadow
    @Mutable
    @Final
    private ComponentContents contents;

    @Shadow
    private Language decomposedWith;

    @Override // net.labymod.v26_2_snapshot_8.client.network.chat.MutableComponentAccessor
    public VersionedBaseComponent<? extends net.labymod.api.client.component.Component, ?> getLabyComponent() {
        return this.labyMod$component;
    }

    @Override // net.labymod.v26_2_snapshot_8.client.network.chat.MutableComponentAccessor
    public List<net.labymod.api.client.component.Component> getChildren() {
        return Collections.unmodifiableList(this.labyMod$children);
    }

    @Override // net.labymod.v26_2_snapshot_8.client.network.chat.MutableComponentAccessor
    public void setContents(ComponentContents contents) {
        this.contents = contents;
        this.decomposedWith = null;
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    public void labyMod$createLabyComponent(ComponentContents contents, List<Component> $$1, Style $$2, CallbackInfo ci) {
        this.siblings = new WatchableArrayList(new WatchableComponentSiblingList(this.labyMod$children));
        this.siblings.addAll($$1);
        MutableComponent holder = (MutableComponent) this;
        if (contents instanceof TranslatableContents) {
            this.labyMod$component = new VersionedTranslatableComponent(holder);
            return;
        }
        if (contents instanceof PlainTextContents.LiteralContents) {
            PlainTextContents.LiteralContents literalContents = (PlainTextContents.LiteralContents) contents;
            this.labyMod$component = new VersionedTextComponent(holder, literalContents.text().isEmpty());
            return;
        }
        if (contents instanceof KeybindContents) {
            this.labyMod$component = new VersionedKeybindComponent(holder);
            return;
        }
        if (contents instanceof ScoreContents) {
            this.labyMod$component = new VersionedScoreComponent(holder);
            return;
        }
        if (contents instanceof VersionedIconContents) {
            this.labyMod$component = new VersionedIconComponent(holder);
            return;
        }
        if (contents == PlainTextContents.EMPTY) {
            this.labyMod$component = new VersionedTextComponent(holder, true);
        } else if (contents instanceof ObjectContents) {
            this.labyMod$component = new VersionedObjectComponent(holder);
        } else {
            Laby.references().componentService().reportMissing(contents, !(contents instanceof NbtContents));
        }
    }

    public MutableComponent copy() {
        PlainTextContents.LiteralContents translatableContents = this.contents;
        if (translatableContents instanceof PlainTextContents.LiteralContents) {
            PlainTextContents.LiteralContents literalContents = translatableContents;
            translatableContents = new PlainTextContents.LiteralContents(literalContents.text());
        } else if (translatableContents instanceof TranslatableContents) {
            TranslatableContents translatableContents2 = (TranslatableContents) translatableContents;
            Object[] args = translatableContents2.getArgs();
            Object[] arguments = new Object[args.length];
            System.arraycopy(args, 0, arguments, 0, args.length);
            translatableContents = new TranslatableContents(translatableContents2.getKey(), translatableContents2.getFallback(), arguments);
        }
        List<Component> siblings = new ArrayList<>();
        for (Component sibling : this.siblings) {
            siblings.add(sibling.copy());
        }
        return new MutableComponent(translatableContents, siblings, getStyle());
    }
}

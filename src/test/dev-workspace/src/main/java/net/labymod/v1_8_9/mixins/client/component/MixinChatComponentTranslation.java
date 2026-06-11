package net.labymod.v1_8_9.mixins.client.component;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.util.CollectionHelper;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_8_9.client.util.WatchableTranslatableComponentArgumentsList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/component/MixinChatComponentTranslation.class */
@Mixin({fb.class})
@Implements({@Interface(iface = TranslatableComponent.class, prefix = "translatableComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinChatComponentTranslation extends MixinChatComponentStyle<TranslatableComponent> implements TranslatableComponent {
    private List<Component> labyMod$arguments;

    @Mutable
    @Shadow
    @Final
    private Object[] e;

    @Shadow
    private long g;

    @Shadow
    @Final
    private String d;

    @Shadow
    public abstract String shadow$i();

    @Inject(method = {"<init>(Ljava/lang/String;[Ljava/lang/Object;)V"}, at = {@At("TAIL")})
    public void labyMod$initializeWithArguments(CallbackInfo ci) {
        WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<>(labyMod$watchableArgumentsList());
        for (Object argument : this.e) {
            if (argument instanceof es) {
                watchableArrayList.addUnwatched((Component) argument);
            } else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        this.labyMod$arguments = watchableArrayList;
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public TranslatableComponent plainCopy() {
        return new fb(this.d, this.e);
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public List<Component> getArguments() {
        return this.labyMod$arguments;
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent argument(Component argument) {
        this.labyMod$arguments.add(argument);
        return this;
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(Component... arguments) {
        return arguments(CollectionHelper.asUnmodifiableList(arguments));
    }

    @Override // net.labymod.api.client.component.TranslatableComponent
    public TranslatableComponent arguments(Collection<Component> arguments) {
        this.labyMod$arguments.addAll(arguments);
        return this;
    }

    @Intrinsic
    public String translatableComponent$getKey() {
        return shadow$i();
    }

    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> {
            return this.e;
        }, arguments -> {
            this.e = arguments;
            this.g = 0L;
        });
    }
}

package net.labymod.v1_18_2.mixins.client.chat;

import java.util.Collection;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_18_2.client.util.WatchableTranslatableComponentArgumentsList;
import org.jetbrains.annotations.Nullable;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/chat/MixinTranslatableComponent.class */
@Mixin({qy.class})
@Implements({@Interface(iface = TranslatableComponent.class, prefix = "translatableComponent$", remap = Interface.Remap.NONE)})
public abstract class MixinTranslatableComponent extends MixinBaseComponent<TranslatableComponent> implements TranslatableComponent {
    private List<Component> labyMod$arguments;

    @Shadow
    @Mutable
    @Final
    private Object[] h;

    @Shadow
    @Nullable
    private of i;

    @Shadow
    public abstract String shadow$i();

    @Shadow
    public abstract qy shadow$h();

    @Shadow
    public abstract String i();

    @Inject(method = {"<init>(Ljava/lang/String;[Ljava/lang/Object;)V"}, at = {@At("TAIL")})
    public void labyMod$initializeWithArguments(CallbackInfo ci) {
        labyMod$mapArguments();
    }

    @Inject(method = {"<init>(Ljava/lang/String;)V"}, at = {@At("TAIL")})
    public void labyMod$initializeWithoutArguments(CallbackInfo ci) {
        labyMod$mapArguments();
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
        return arguments(List.of((Object[]) arguments));
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

    @Intrinsic
    public TranslatableComponent translatableComponent$plainCopy() {
        return shadow$h();
    }

    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> {
            return this.h;
        }, arguments -> {
            this.h = arguments;
            this.i = null;
        });
    }

    private void labyMod$mapArguments() {
        WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<>(labyMod$watchableArgumentsList());
        for (Object argument : this.h) {
            if (argument instanceof qg) {
                watchableArrayList.addUnwatched((Component) argument);
            } else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        this.labyMod$arguments = watchableArrayList;
    }
}

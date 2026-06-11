package net.labymod.v1_21_10.mixins.client.chat.contents;

import java.util.List;
import javax.annotation.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v1_21_10.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_21_10.client.network.chat.VersionedBaseComponent;
import net.labymod.v1_21_10.client.network.chat.contents.TranslatableContentsAccessor;
import net.labymod.v1_21_10.client.util.WatchableTranslatableComponentArgumentsList;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/contents/MixinTranslatableContents.class */
@Mixin({zg.class})
@Implements({@Interface(iface = TranslatableContentsAccessor.class, prefix = "translatableAccessor$", remap = Interface.Remap.NONE)})
public abstract class MixinTranslatableContents implements TranslatableContentsAccessor {
    private List<Component> labyMod$arguments;

    @Mutable
    @Shadow
    @Final
    private Object[] i;

    @Shadow
    @Nullable
    private uk j;

    @Shadow
    public abstract String shadow$b();

    @Shadow
    public abstract String shadow$c();

    @Inject(method = {"<init>(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V"}, at = {@At("TAIL")})
    public void labyMod$initializeWithArguments(CallbackInfo ci) {
        labyMod$mapArguments();
    }

    @Override // net.labymod.v1_21_10.client.network.chat.contents.TranslatableContentsAccessor
    public List<Component> getLabyArguments() {
        return this.labyMod$arguments;
    }

    @Intrinsic
    public String translatableAccessor$getKey() {
        return shadow$b();
    }

    @Intrinsic
    public String translatableAccessor$getFallback() {
        return shadow$c();
    }

    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> {
            return this.i;
        }, arguments -> {
            this.i = arguments;
            labyMod$cleanupArguments();
            this.j = null;
        });
    }

    private void labyMod$cleanupArguments() {
        for (int i = 0; i < this.i.length; i++) {
            Object argument = this.i[i];
            if (argument instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) argument;
                this.i[i] = component.getHolder();
            }
        }
    }

    private void labyMod$mapArguments() {
        if (this.i instanceof Component[]) {
            Object[] args = new Object[this.i.length];
            if (this.i.length != 0) {
                System.arraycopy(this.i, 0, args, 0, this.i.length);
            }
            this.i = args;
        }
        WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<>(labyMod$watchableArgumentsList());
        for (Object argument : this.i) {
            if (argument instanceof MutableComponentAccessor) {
                MutableComponentAccessor accessor = (MutableComponentAccessor) argument;
                watchableArrayList.addUnwatched(accessor.getLabyComponent());
            } else if (argument instanceof Component) {
                Component component = (Component) argument;
                watchableArrayList.addUnwatched(component);
            } else if (argument instanceof String) {
                String string = (String) argument;
                watchableArrayList.addUnwatched(Component.text(string));
            } else {
                watchableArrayList.addUnwatched(Component.text(String.valueOf(argument)));
            }
        }
        labyMod$cleanupArguments();
        this.labyMod$arguments = watchableArrayList;
    }
}

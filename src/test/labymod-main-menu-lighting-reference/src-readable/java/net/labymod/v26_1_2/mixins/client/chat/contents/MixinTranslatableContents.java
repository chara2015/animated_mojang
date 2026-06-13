package net.labymod.v26_1_2.mixins.client.chat.contents;

import java.util.List;
import javax.annotation.Nullable;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableArrayList;
import net.labymod.v26_1_2.client.network.chat.MutableComponentAccessor;
import net.labymod.v26_1_2.client.network.chat.VersionedBaseComponent;
import net.labymod.v26_1_2.client.network.chat.contents.TranslatableContentsAccessor;
import net.labymod.v26_1_2.client.util.WatchableTranslatableComponentArgumentsList;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.contents.TranslatableContents;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/chat/contents/MixinTranslatableContents.class */
@Mixin({TranslatableContents.class})
@Implements({@Interface(iface = TranslatableContentsAccessor.class, prefix = "translatableAccessor$", remap = Interface.Remap.NONE)})
public abstract class MixinTranslatableContents implements TranslatableContentsAccessor {
    private List<Component> labyMod$arguments;

    @Mutable
    @Shadow
    @Final
    private Object[] args;

    @Shadow
    @Nullable
    private Language decomposedWith;

    @Shadow
    public abstract String shadow$getKey();

    @Shadow
    public abstract String shadow$getFallback();

    @Inject(method = {"<init>(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V"}, at = {@At("TAIL")})
    public void labyMod$initializeWithArguments(CallbackInfo ci) {
        labyMod$mapArguments();
    }

    @Override // net.labymod.v26_1_2.client.network.chat.contents.TranslatableContentsAccessor
    public List<Component> getLabyArguments() {
        return this.labyMod$arguments;
    }

    @Intrinsic
    public String translatableAccessor$getKey() {
        return shadow$getKey();
    }

    @Intrinsic
    public String translatableAccessor$getFallback() {
        return shadow$getFallback();
    }

    private WatchableTranslatableComponentArgumentsList labyMod$watchableArgumentsList() {
        return new WatchableTranslatableComponentArgumentsList(() -> {
            return this.args;
        }, arguments -> {
            this.args = arguments;
            labyMod$cleanupArguments();
            this.decomposedWith = null;
        });
    }

    private void labyMod$cleanupArguments() {
        for (int i = 0; i < this.args.length; i++) {
            Object argument = this.args[i];
            if (argument instanceof VersionedBaseComponent) {
                VersionedBaseComponent<?, ?> component = (VersionedBaseComponent) argument;
                this.args[i] = component.getHolder();
            }
        }
    }

    private void labyMod$mapArguments() {
        if (this.args instanceof Component[]) {
            Object[] args = new Object[this.args.length];
            if (this.args.length != 0) {
                System.arraycopy(this.args, 0, args, 0, this.args.length);
            }
            this.args = args;
        }
        WatchableArrayList<Component> watchableArrayList = new WatchableArrayList<>(labyMod$watchableArgumentsList());
        for (Object argument : this.args) {
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

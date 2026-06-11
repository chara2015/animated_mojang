package net.labymod.api.util.bounds;

import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/bounds/ModifyReason.class */
public class ModifyReason {
    private final Class<?> source;
    private final String reason;
    private boolean addonLoaded;
    private LoadedAddon sourceAddon;
    private boolean renderOnly;

    private ModifyReason(@NotNull Class<?> source, @NotNull String reason) {
        this.source = source;
        this.reason = reason;
    }

    public static ModifyReason of(@NotNull Class<?> source, @NotNull String reason) {
        return new ModifyReason(source, reason);
    }

    private static ModifyReason ofInternal(@NotNull String reason, Class<?> callerClass) {
        Reflection.validateStaticConstructorInvocation(2, () -> {
            return "ModifyReason.of(reason) may only be used for constants as this is very performance intensive";
        });
        return of(callerClass, reason);
    }

    public static ModifyReason of(@NotNull String reason) {
        return ofInternal(reason, Reflection.getCallerClass());
    }

    public static ModifyReason renderOnly(@NotNull String reason) {
        return ofInternal(reason, Reflection.getCallerClass()).setRenderOnly();
    }

    @Nullable
    public LoadedAddon sourceAddon() {
        if (!this.addonLoaded) {
            Laby.labyAPI().addonService().getAddon(this.source).ifPresent(addon -> {
                this.sourceAddon = addon;
            });
            this.addonLoaded = true;
        }
        return this.sourceAddon;
    }

    @NotNull
    public Class<?> source() {
        return this.source;
    }

    @NotNull
    public String reason() {
        return this.reason;
    }

    public boolean isRenderOnly() {
        return this.renderOnly;
    }

    public ModifyReason setRenderOnly() {
        this.renderOnly = true;
        return this;
    }

    public String toString() {
        return this.source.getName() + " (" + this.reason + ")";
    }
}

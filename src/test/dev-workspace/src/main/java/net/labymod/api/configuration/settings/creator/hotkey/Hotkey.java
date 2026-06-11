package net.labymod.api.configuration.settings.creator.hotkey;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.key.KeyAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/hotkey/Hotkey.class */
public final class Hotkey extends Record {
    private final String category;
    private final String namespace;
    private final String translationKey;
    private final KeyAccessor accessor;
    private final BooleanSupplier visibility;

    public Hotkey(String category, String namespace, String translationKey, KeyAccessor accessor, BooleanSupplier visibility) {
        this.category = category;
        this.namespace = namespace;
        this.translationKey = translationKey;
        this.accessor = accessor;
        this.visibility = visibility;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Hotkey.class), Hotkey.class, "category;namespace;translationKey;accessor;visibility", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->category:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->translationKey:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->accessor:Lnet/labymod/api/client/gui/screen/key/KeyAccessor;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->visibility:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Hotkey.class), Hotkey.class, "category;namespace;translationKey;accessor;visibility", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->category:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->translationKey:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->accessor:Lnet/labymod/api/client/gui/screen/key/KeyAccessor;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->visibility:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Hotkey.class, Object.class), Hotkey.class, "category;namespace;translationKey;accessor;visibility", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->category:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->translationKey:Ljava/lang/String;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->accessor:Lnet/labymod/api/client/gui/screen/key/KeyAccessor;", "FIELD:Lnet/labymod/api/configuration/settings/creator/hotkey/Hotkey;->visibility:Ljava/util/function/BooleanSupplier;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String category() {
        return this.category;
    }

    public String namespace() {
        return this.namespace;
    }

    public String translationKey() {
        return this.translationKey;
    }

    public KeyAccessor accessor() {
        return this.accessor;
    }

    public BooleanSupplier visibility() {
        return this.visibility;
    }
}

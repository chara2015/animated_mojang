package net.labymod.api.util;

import net.labymod.api.Laby;
import net.labymod.api.localization.Internationalization;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/I18n.class */
public class I18n {
    private static final Internationalization INSTANCE = Laby.references().internationalization();

    private I18n() {
    }

    @NotNull
    public static String translate(@NotNull String key, Object... args) {
        return INSTANCE.translate(key, args);
    }

    @Nullable
    public static String getTranslation(@NotNull String key, Object... args) {
        return INSTANCE.getTranslation(key, args);
    }

    public static boolean has(@NotNull String key) {
        return INSTANCE.has(key);
    }
}

package net.labymod.api.localization;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/localization/Internationalization.class */
@Referenceable
public interface Internationalization {
    @NotNull
    String getRawTranslation(@NotNull String str);

    @NotNull
    String translate(@NotNull String str, Object... objArr);

    @Nullable
    String getTranslation(@NotNull String str, Object... objArr);

    @Nullable
    String getSelectedLanguage();

    boolean has(@NotNull String str);

    boolean isAssumedTranslatable(@NotNull String str);
}

package net.labymod.api.client.gui.lss.property;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/property/DirectPropertyValueAccessor.class */
public interface DirectPropertyValueAccessor {
    @Nullable
    PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String str);

    boolean hasPropertyValueAccessor(String str);

    @Nullable
    LssPropertyResetter getPropertyResetter();
}

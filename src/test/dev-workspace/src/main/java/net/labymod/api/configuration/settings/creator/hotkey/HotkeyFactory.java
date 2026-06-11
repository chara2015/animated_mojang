package net.labymod.api.configuration.settings.creator.hotkey;

import net.labymod.api.configuration.settings.creator.MemberInspector;
import net.labymod.api.configuration.settings.creator.SettingEntry;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/hotkey/HotkeyFactory.class */
public interface HotkeyFactory {
    boolean hasSettingAnnotation(MemberInspector memberInspector);

    @Nullable
    Hotkey create(MemberInspector memberInspector, String str, SettingEntry settingEntry, String str2);
}

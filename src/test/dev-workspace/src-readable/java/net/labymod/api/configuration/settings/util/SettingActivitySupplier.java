package net.labymod.api.configuration.settings.util;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.configuration.settings.Setting;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/util/SettingActivitySupplier.class */
public interface SettingActivitySupplier {
    Activity activity(Setting setting);
}

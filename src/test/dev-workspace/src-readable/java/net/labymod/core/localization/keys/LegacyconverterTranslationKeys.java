package net.labymod.core.localization.keys;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/keys/LegacyconverterTranslationKeys.class */
public final class LegacyconverterTranslationKeys {
    private static final TranslatableComponent RESTARTREQUIRED = Component.translatable("labymod.legacyconverter.restartRequired", new Component[0]);
    private static final TranslatableComponent NO = Component.translatable("labymod.legacyconverter.no", new Component[0]);
    private static final TranslatableComponent YES = Component.translatable("labymod.legacyconverter.yes", new Component[0]);

    public static Component getAddonTitle(Component arg0) {
        return Component.translatable("labymod.legacyconverter.addonTitle", arg0);
    }

    public static Component getRestartRequired() {
        return RESTARTREQUIRED;
    }

    public static Component getNo() {
        return NO;
    }

    public static Component getYes() {
        return YES;
    }

    public static Component getConvertWarning(Component arg0) {
        return Component.translatable("labymod.legacyconverter.convertWarning", arg0);
    }

    public static Component getConvertSettings(Component arg0, Component arg1) {
        return Component.translatable("labymod.legacyconverter.convertSettings", arg0, arg1);
    }

    public static Component getConvertAddons(Component arg0) {
        return Component.translatable("labymod.legacyconverter.convertAddons", arg0);
    }
}

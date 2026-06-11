package net.labymod.core.localization.keys;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.core.main.lagdetector.GarbageCollectorLagDetectionModule;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/keys/NotificationTranslationKeys.class */
public final class NotificationTranslationKeys {
    private static final TranslatableComponent INCOMPATIBLEGRAPHICSFILES_TITLE = Component.translatable("labymod.notification.incompatibleGraphicsFiles.title", new Component[0]);
    private static final TranslatableComponent COPIED = Component.translatable("labymod.notification.copied", new Component[0]);
    private static final TranslatableComponent INCOMPATIBLEGRAPHICSFILES_DESCRIPTION = Component.translatable("labymod.notification.incompatibleGraphicsFiles.description", new Component[0]);
    private static final TranslatableComponent FANCYFONTSERVERRESOURCEPACK_DESCRIPTION = Component.translatable("labymod.notification.fancyFontServerResourcePack.description", new Component[0]);
    private static final TranslatableComponent INSUFFICIENTSTORAGESPACE_CONFIGURATION_TITLE = Component.translatable("labymod.notification.insufficientStorageSpace.configuration.title", new Component[0]);
    private static final TranslatableComponent OUTOFMEMORYWARNING_TITLE = Component.translatable(GarbageCollectorLagDetectionModule.OUT_OF_MEMORY_TITLE_KEY, new Component[0]);
    private static final TranslatableComponent COSMETIC_CRASH_TITLE = Component.translatable("labymod.notification.cosmetic_crash.title", new Component[0]);
    private static final TranslatableComponent COSMETIC_DEFAULT_DATA_DESCRIPTION = Component.translatable("labymod.notification.cosmetic_default_data.description", new Component[0]);
    private static final TranslatableComponent FANCYFONTSERVERRESOURCEPACK_TITLE = Component.translatable("labymod.notification.fancyFontServerResourcePack.title", new Component[0]);

    public static Component getOutOfMemoryWarningDescription(Component arg0, Component arg1) {
        return Component.translatable("labymod.notification.outOfMemoryWarning.description", arg0, arg1);
    }

    public static Component getAddonsMultiple(Component arg0, Component arg1) {
        return Component.translatable("labymod.notification.addons.multiple", arg0, arg1);
    }

    public static Component getPermissions(Component arg0, Component arg1) {
        return Component.translatable("labymod.notification.permissions", arg0, arg1);
    }

    public static Component getAddonsOne(Component arg0, Component arg1) {
        return Component.translatable("labymod.notification.addons.one", arg0, arg1);
    }

    public static Component getIncompatibleGraphicsFilesTitle() {
        return INCOMPATIBLEGRAPHICSFILES_TITLE;
    }

    public static Component getCosmeticCrashDescription(Component arg0) {
        return Component.translatable("labymod.notification.cosmetic_crash.description", arg0);
    }

    public static Component getCopied() {
        return COPIED;
    }

    public static Component getInsufficientStorageSpaceConfigurationDescription(Component arg0) {
        return Component.translatable("labymod.notification.insufficientStorageSpace.configuration.description", arg0);
    }

    public static Component getIncompatibleGraphicsFilesDescription() {
        return INCOMPATIBLEGRAPHICSFILES_DESCRIPTION;
    }

    public static Component getFancyFontServerResourcePackDescription() {
        return FANCYFONTSERVERRESOURCEPACK_DESCRIPTION;
    }

    public static Component getInsufficientStorageSpaceConfigurationTitle() {
        return INSUFFICIENTSTORAGESPACE_CONFIGURATION_TITLE;
    }

    public static Component getPermission(Component arg0, Component arg1) {
        return Component.translatable("labymod.notification.permission", arg0, arg1);
    }

    public static Component getOutOfMemoryWarningTitle() {
        return OUTOFMEMORYWARNING_TITLE;
    }

    public static Component getCosmeticCrashTitle() {
        return COSMETIC_CRASH_TITLE;
    }

    public static Component getCosmeticDefaultDataDescription() {
        return COSMETIC_DEFAULT_DATA_DESCRIPTION;
    }

    public static Component getFancyFontServerResourcePackTitle() {
        return FANCYFONTSERVERRESOURCEPACK_TITLE;
    }
}

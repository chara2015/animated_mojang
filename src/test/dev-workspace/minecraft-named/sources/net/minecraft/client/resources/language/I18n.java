package net.minecraft.client.resources.language;

import java.util.IllegalFormatException;
import java.util.Locale;
import net.minecraft.locale.Language;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/language/I18n.class */
public class I18n {
    private static volatile Language language = Language.getInstance();

    private I18n() {
    }

    static void setLanguage(Language $$0) {
        language = $$0;
    }

    public static String get(String $$0, Object... $$1) {
        String $$2 = language.getOrDefault($$0);
        try {
            return String.format(Locale.ROOT, $$2, $$1);
        } catch (IllegalFormatException e) {
            return "Format error: " + $$2;
        }
    }

    public static boolean exists(String $$0) {
        return language.has($$0);
    }
}

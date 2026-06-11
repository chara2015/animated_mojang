package net.labymod.core.localization.keys;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/localization/keys/SurveyTranslationKeys.class */
public final class SurveyTranslationKeys {
    private static final TranslatableComponent SUCCESS_TEXT = Component.translatable("labymod.survey.success.text", new Component[0]);
    private static final TranslatableComponent SUCCESS_TITLE = Component.translatable("labymod.survey.success.title", new Component[0]);
    private static final TranslatableComponent ERROR_TEXT = Component.translatable("labymod.survey.error.text", new Component[0]);
    private static final TranslatableComponent TITLE = Component.translatable("labymod.survey.title", new Component[0]);
    private static final TranslatableComponent ERROR_TITLE = Component.translatable("labymod.survey.error.title", new Component[0]);

    public static Component getSuccessText() {
        return SUCCESS_TEXT;
    }

    public static Component getSuccessTitle() {
        return SUCCESS_TITLE;
    }

    public static Component getErrorText() {
        return ERROR_TEXT;
    }

    public static Component getTitle() {
        return TITLE;
    }

    public static Component getErrorTitle() {
        return ERROR_TITLE;
    }
}

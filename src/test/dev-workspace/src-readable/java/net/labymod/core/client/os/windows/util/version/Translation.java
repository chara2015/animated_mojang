package net.labymod.core.client.os.windows.util.version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/version/Translation.class */
public class Translation {
    public static final int SIZE = 32;
    private static final int ENGLISH_LANGUAGE_ID = 1033;
    private static final int CHARSET_UNICODE_ID = 1200;
    private final int languageId;
    private final int charsetId;

    private Translation(int languageId, int charsetId) {
        this.languageId = languageId;
        this.charsetId = charsetId;
    }

    static Translation decode(int value) {
        int languageId = value & 65535;
        int codePage = (value & (-65536)) >> 16;
        return new Translation(languageId, codePage);
    }

    public int getLanguageId() {
        return this.languageId;
    }

    public int getCharsetId() {
        return this.charsetId;
    }

    public boolean isEnglish() {
        return this.languageId == 1033 && this.charsetId == CHARSET_UNICODE_ID;
    }
}

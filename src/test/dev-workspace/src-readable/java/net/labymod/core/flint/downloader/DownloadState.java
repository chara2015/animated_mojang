package net.labymod.core.flint.downloader;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/downloader/DownloadState.class */
public enum DownloadState {
    PREPARE,
    DOWNLOADING,
    FAILED,
    LOADING,
    REQUIRES_RESTART,
    FINISHED;

    @Override // java.lang.Enum
    public String toString() {
        String str;
        if (name().contains("_")) {
            String[] words = name().split("_");
            String camelCase = "";
            for (int i = 0; i < words.length; i++) {
                String word = words[i];
                if (i == 0) {
                    str = camelCase + word.toLowerCase(Locale.ROOT);
                } else {
                    str = camelCase + word.charAt(0) + word.substring(1).toLowerCase(Locale.ROOT);
                }
                camelCase = str;
            }
            return camelCase;
        }
        return name().toLowerCase(Locale.ROOT);
    }
}

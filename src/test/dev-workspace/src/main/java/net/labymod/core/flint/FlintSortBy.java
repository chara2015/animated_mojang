package net.labymod.core.flint;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/FlintSortBy.class */
public enum FlintSortBy {
    TRENDING,
    NAME_AZ,
    NAME_ZA,
    DOWNLOADS,
    LATEST,
    OLDEST,
    RATING;

    private static final FlintSortBy[] VALUES = values();

    public static FlintSortBy[] getValues() {
        return VALUES;
    }

    @Override // java.lang.Enum
    public String toString() {
        return name().toUpperCase(Locale.ENGLISH);
    }
}

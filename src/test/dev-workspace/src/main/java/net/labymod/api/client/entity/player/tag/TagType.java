package net.labymod.api.client.entity.player.tag;

import java.util.Locale;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/TagType.class */
public enum TagType {
    MAIN_TAG,
    SCOREBOARD,
    CUSTOM,
    INVALID;

    private static final TagType[] VALUES = values();
    private final String lowerCase = name().toLowerCase(Locale.ENGLISH);

    TagType() {
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.lowerCase;
    }

    public static TagType fromString(String string) {
        TagType tagType = CUSTOM;
        TagType[] tagTypeArr = VALUES;
        int length = tagTypeArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            TagType value = tagTypeArr[i];
            if (!value.lowerCase.equals(string)) {
                i++;
            } else {
                tagType = value;
                break;
            }
        }
        return tagType;
    }
}

package net.labymod.api.client.component.format;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/TextDecoration.class */
public enum TextDecoration {
    OBFUSCATED,
    BOLD,
    STRIKETHROUGH,
    UNDERLINED,
    ITALIC;

    private static final List<TextDecoration> VALUES = List.of((Object[]) values());

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/TextDecoration$State.class */
    @Deprecated
    public enum State {
        NOT_SET,
        TRUE,
        FALSE
    }

    public static List<TextDecoration> getValues() {
        return VALUES;
    }
}

package net.labymod.api.client.session;

import java.util.Locale;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/Session.class */
public interface Session {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/Session$Factory.class */
    public interface Factory {
        Session create(String str, UUID uuid, String str2, Type type);
    }

    @NotNull
    String getUsername();

    UUID getUniqueId();

    boolean hasUniqueId();

    @Nullable
    String getAccessToken();

    Type getType();

    boolean isPremium();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/session/Session$Type.class */
    public enum Type {
        MOJANG,
        MICROSOFT,
        LEGACY;

        public static Type fromName(String name) {
            String name2 = name.toUpperCase(Locale.ENGLISH);
            for (Type value : values()) {
                if (value.name().equals(name2)) {
                    return value;
                }
            }
            return LEGACY;
        }

        @NotNull
        public String lowercase() {
            return name().toLowerCase(Locale.ENGLISH);
        }
    }
}

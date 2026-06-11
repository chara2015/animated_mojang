package net.labymod.api.util;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/TextFormat.class */
public enum TextFormat {
    CAMEL_CASE { // from class: net.labymod.api.util.TextFormat.1
        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toCamelCase(@NotNull String text, boolean lower) {
            if (text.isEmpty()) {
                return text;
            }
            char c = text.charAt(0);
            if (Character.isLowerCase(c) == lower) {
                return text;
            }
            return (lower ? Character.toLowerCase(c) : Character.toUpperCase(c)) + text.substring(1);
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toDashCase(@NotNull String text) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (Character.isUpperCase(c)) {
                    if (i != 0) {
                        char prev = text.charAt(i - 1);
                        if (Character.isAlphabetic(prev) || Character.isDigit(prev)) {
                            builder.append('-');
                        }
                    }
                    builder.append(Character.toLowerCase(c));
                } else {
                    builder.append(c);
                }
            }
            return builder.toString();
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toSnakeCase(@NotNull String text) {
            return toDashCase(text).replace('-', '_').toUpperCase(Locale.ENGLISH);
        }
    },
    DASH_CASE { // from class: net.labymod.api.util.TextFormat.2
        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toCamelCase(@NotNull String text, boolean lower) {
            char character;
            StringBuilder builder = new StringBuilder();
            boolean newWord = false;
            for (int i = 0; i < text.length(); i++) {
                char character2 = text.charAt(i);
                if (character2 == '-') {
                    newWord = true;
                } else {
                    if (newWord || (i == 0 && !lower)) {
                        character = Character.toUpperCase(character2);
                    } else {
                        character = Character.toLowerCase(character2);
                    }
                    newWord = false;
                    builder.append(character);
                }
            }
            return builder.toString();
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toDashCase(@NotNull String text) {
            return text;
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toSnakeCase(@NotNull String text) {
            return text.replace('-', '_').toUpperCase(Locale.ENGLISH);
        }
    },
    SNAKE_CASE { // from class: net.labymod.api.util.TextFormat.3
        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toCamelCase(@NotNull String text, boolean lower) {
            char character;
            StringBuilder builder = new StringBuilder();
            boolean newWord = false;
            for (int i = 0; i < text.length(); i++) {
                char character2 = text.charAt(i);
                if (character2 == '_') {
                    newWord = true;
                } else {
                    if (newWord || (i == 0 && !lower)) {
                        character = Character.toUpperCase(character2);
                    } else {
                        character = Character.toLowerCase(character2);
                    }
                    newWord = false;
                    builder.append(character);
                }
            }
            return builder.toString();
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toDashCase(@NotNull String text) {
            return text.replace('_', '-').toLowerCase(Locale.ENGLISH);
        }

        @Override // net.labymod.api.util.TextFormat
        @NotNull
        public String toSnakeCase(@NotNull String text) {
            return text;
        }
    };

    @Deprecated
    @NotNull
    public abstract String toCamelCase(@NotNull String str, boolean z);

    @NotNull
    public abstract String toDashCase(@NotNull String str);

    @NotNull
    public abstract String toSnakeCase(@NotNull String str);

    @NotNull
    public String toUpperCamelCase(String text) {
        return toCamelCase(text, false);
    }

    @NotNull
    public String toLowerCamelCase(String text) {
        return toCamelCase(text, true);
    }
}

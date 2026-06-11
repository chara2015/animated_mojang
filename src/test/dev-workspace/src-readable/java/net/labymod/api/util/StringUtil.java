package net.labymod.api.util;

import it.unimi.dsi.fastutil.chars.CharPredicate;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/StringUtil.class */
public final class StringUtil {
    private static final Predicate<Character> MINECRAFT_ILLEGAL_CHARACTERS = character -> {
        return character.charValue() == 167 || character.charValue() < ' ' || character.charValue() == 127;
    };

    private StringUtil() {
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }

    @NotNull
    public static String removeIllegalCharacters(String value) {
        return removeIllegalCharacters(value, MINECRAFT_ILLEGAL_CHARACTERS);
    }

    @NotNull
    public static String removeIllegalCharacters(String value, @NotNull Predicate<Character> filter) {
        if (value == null) {
            return "";
        }
        StringBuilder builder = null;
        int length = value.length();
        for (int index = 0; index < length; index++) {
            char character = value.charAt(index);
            if (filter.test(Character.valueOf(character))) {
                if (builder == null) {
                    builder = new StringBuilder(length);
                    builder.append((CharSequence) value, 0, index);
                }
            } else if (builder != null) {
                builder.append(character);
            }
        }
        return builder == null ? value : builder.toString();
    }

    @NotNull
    public static String toUppercase(@NotNull String value) {
        StringBuilder upperTheBuilder = new StringBuilder();
        for (char c : value.toCharArray()) {
            upperTheBuilder.append(Character.toUpperCase(c));
        }
        return upperTheBuilder.toString();
    }

    @NotNull
    public static String toLowercase(@NotNull String value) {
        StringBuilder lowerTheBuilder = new StringBuilder();
        for (char c : value.toCharArray()) {
            lowerTheBuilder.append(Character.toLowerCase(c));
        }
        return lowerTheBuilder.toString();
    }

    @NotNull
    public static String join(@NotNull Collection<?> entries) {
        return join(entries, (CharSequence) ", ");
    }

    @NotNull
    public static String join(@NotNull Iterable<?> entries, @NotNull CharSequence delimiter) {
        Objects.requireNonNull(entries, "entries must not be null");
        Objects.requireNonNull(delimiter, "delimiter must not be null");
        StringJoiner joiner = new StringJoiner(delimiter);
        Iterator<?> it = entries.iterator();
        while (it.hasNext()) {
            Object argument = it.next();
            joiner.add(argument == null ? null : argument.toString());
        }
        return joiner.toString();
    }

    @NotNull
    public static String join(@NotNull Collection<?> entries, @NotNull CharSequence delimiter) {
        return join((Iterable<?>) entries, delimiter);
    }

    @NotNull
    public static String join(@NotNull Map<?, ?> entries) {
        return join(entries, ", ");
    }

    public static String join(@NotNull Map<?, ?> entries, @NotNull CharSequence delimiter) {
        String string;
        Objects.requireNonNull(entries, "Map must not be null");
        Objects.requireNonNull(delimiter, "Delimiter must not be null");
        StringJoiner joiner = new StringJoiner(delimiter);
        for (Map.Entry<?, ?> entry : entries.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Collection) {
                string = join((Collection<?>) value, (CharSequence) ", ");
            } else {
                string = value == null ? null : value.toString();
            }
            String valueString = string;
            Object key = entry.getKey();
            joiner.add(String.valueOf(key) + ": " + valueString);
        }
        return joiner.toString();
    }

    public static boolean isNumeric(String value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean containsIgnoreCase(String s, String search) {
        if (s == null || search == null) {
            return false;
        }
        int length = search.length();
        if (length == 0) {
            return true;
        }
        for (int i = s.length() - length; i >= 0; i--) {
            if (s.regionMatches(true, i, search, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWithIgnoreCase(String s, String prefix) {
        return s.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static boolean endsWithIgnoreCase(String s, String suffix) {
        int suffixLength = suffix.length();
        return s.regionMatches(true, s.length() - suffixLength, suffix, 0, suffixLength);
    }

    public static String capitalizeWords(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toUpperCase(c);
                nextTitleCase = false;
            } else if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            }
            builder.append(c);
        }
        return builder.toString();
    }

    public static String parseEscapedUnicode(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int length = input.getBytes(StandardCharsets.UTF_8).length;
        int i = 0;
        while (i <= length - 1) {
            char c = input.charAt(i);
            if (c == '\\' && i + 5 < length && input.charAt(i + 1) == 'u') {
                try {
                    int code = Integer.parseInt(input.substring(i + 2, i + 6), 16);
                    builder.append((char) code);
                    i += 5;
                } catch (NumberFormatException e) {
                    builder.append(c);
                }
            } else {
                builder.append(c);
            }
            i++;
        }
        return builder.toString();
    }

    public static String sanitizePath(String name) {
        return sanitizeName(name, StringUtil::validPathChar);
    }

    public static String sanitizeName(String name, CharPredicate tester) {
        String lowerCase = name.toLowerCase(Locale.ROOT);
        char[] chars = lowerCase.toCharArray();
        StringBuilder bobTheBuilder = new StringBuilder();
        for (char c : chars) {
            if (tester.test(c)) {
                bobTheBuilder.append(c);
            } else {
                bobTheBuilder.append('_');
            }
        }
        return bobTheBuilder.toString();
    }

    public static boolean validPathChar(char value) {
        return value == '_' || value == '-' || (value >= 'a' && value <= 'z') || ((value >= '0' && value <= '9') || value == '/' || value == '.');
    }
}

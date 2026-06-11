package net.labymod.api.util;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/CharSequences.class */
public final class CharSequences {
    private CharSequences() {
    }

    public static boolean isEmpty(CharSequence sequence) {
        return sequence.length() == 0;
    }

    public static CharSequence[] split(CharSequence sequence, String regex) {
        Pattern compile = Pattern.compile(regex);
        String[] splitStrings = compile.split(sequence);
        CharSequence[] sequences = new CharSequence[splitStrings.length];
        System.arraycopy(splitStrings, 0, sequences, 0, sequences.length);
        return sequences;
    }

    public static boolean containsLowercase(CharSequence sequence, CharSequence s) {
        return contains(toLowerCase(sequence), toLowerCase(s));
    }

    public static boolean contains(CharSequence sequence, CharSequence s) {
        if (sequence instanceof String) {
            String text = (String) sequence;
            return text.contains(s);
        }
        return sequence.toString().contains(s);
    }

    public static int indexOf(CharSequence sequence, char c) {
        if (sequence instanceof String) {
            String text = (String) sequence;
            return text.indexOf(c);
        }
        if (sequence instanceof StringBuilder) {
            StringBuilder builder = (StringBuilder) sequence;
            return builder.indexOf(String.valueOf(c));
        }
        return sequence.toString().indexOf(c);
    }

    public static int lastIndexOf(CharSequence sequence, char c) {
        if (sequence instanceof String) {
            String text = (String) sequence;
            return text.lastIndexOf(c);
        }
        if (sequence instanceof StringBuilder) {
            StringBuilder builder = (StringBuilder) sequence;
            return builder.lastIndexOf(String.valueOf(c));
        }
        return sequence.toString().lastIndexOf(c);
    }

    public static CharSequence toLowerCase(CharSequence sequence) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); index++) {
            char character = Character.toLowerCase(sequence.charAt(index));
            builder.append(character);
        }
        return builder.toString();
    }

    public static CharSequence toUpperCase(CharSequence sequence) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < sequence.length(); index++) {
            char character = Character.toUpperCase(sequence.charAt(index));
            builder.append(character);
        }
        return builder.toString();
    }

    public static CharSequence capitalize(CharSequence sequence) {
        return capitalize(sequence, false);
    }

    public static CharSequence capitalize(CharSequence sequence, boolean everyWord) {
        StringBuilder builder = new StringBuilder();
        int length = sequence.length();
        boolean shouldNextCapitalize = false;
        for (int index = 0; index < length; index++) {
            char c = sequence.charAt(index);
            if (index == 0 || shouldNextCapitalize) {
                builder.append(Character.toUpperCase(c));
                shouldNextCapitalize = false;
            } else {
                if (everyWord && c == ' ') {
                    shouldNextCapitalize = true;
                }
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static String toString(CharSequence sequence) {
        return String.valueOf(sequence);
    }

    public static byte[] toByteArray(CharSequence sequence, Charset charset) {
        if (sequence == null) {
            return new byte[0];
        }
        return sequence.toString().getBytes(charset);
    }
}

package net.labymod.api.util.function;

import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/function/NumberPredicate.class */
public final class NumberPredicate implements Predicate<String> {
    private static final String NUMBER_PATTERN = "[0-9]";
    private final int maximum;
    private final IntConsumer numberConsumer;

    public NumberPredicate(int maximum, IntConsumer numberConsumer) {
        this.maximum = maximum;
        this.numberConsumer = numberConsumer;
    }

    @Override // java.util.function.Predicate
    public boolean test(String content) {
        if (content == null) {
            acceptNumber(0);
            return true;
        }
        StringBuilder builder = new StringBuilder();
        char[] chars = content.toCharArray();
        boolean isZero = false;
        for (int i = 0; i < chars.length; i++) {
            char character = chars[i];
            if (!Pattern.matches(NUMBER_PATTERN, String.valueOf(character))) {
                return false;
            }
            if (isZero && character == '0') {
                return false;
            }
            if (i == 0 && character == '0') {
                isZero = true;
            }
            builder.append(character);
        }
        String content2 = builder.toString();
        int number = content2.isEmpty() ? 0 : Integer.parseInt(content2);
        if (number < 0 || number > this.maximum) {
            return false;
        }
        acceptNumber(number);
        return true;
    }

    private void acceptNumber(int number) {
        if (this.numberConsumer == null) {
            return;
        }
        this.numberConsumer.accept(number);
    }
}

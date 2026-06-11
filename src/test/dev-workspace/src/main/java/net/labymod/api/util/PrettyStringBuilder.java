package net.labymod.api.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/PrettyStringBuilder.class */
public class PrettyStringBuilder {
    private final StringBuilder builder;

    public PrettyStringBuilder() {
        this(new StringBuilder());
    }

    public PrettyStringBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public PrettyStringBuilder append(Object obj) {
        this.builder.append(obj);
        return this;
    }

    public PrettyStringBuilder append(String str) {
        this.builder.append(str);
        return this;
    }

    public PrettyStringBuilder append(StringBuffer sb) {
        this.builder.append(sb);
        return this;
    }

    public PrettyStringBuilder append(CharSequence s) {
        this.builder.append(s);
        return this;
    }

    public PrettyStringBuilder append(CharSequence s, int start, int end) {
        this.builder.append(s, start, end);
        return this;
    }

    public PrettyStringBuilder append(char[] str) {
        this.builder.append(str);
        return this;
    }

    public PrettyStringBuilder append(char[] str, int offset, int len) {
        this.builder.append(str, offset, len);
        return this;
    }

    public PrettyStringBuilder append(boolean b) {
        this.builder.append(b);
        return this;
    }

    public PrettyStringBuilder append(char c) {
        this.builder.append(c);
        return this;
    }

    public PrettyStringBuilder append(int i) {
        this.builder.append(i);
        return this;
    }

    public PrettyStringBuilder append(long lng) {
        this.builder.append(lng);
        return this;
    }

    public PrettyStringBuilder append(float f) {
        this.builder.append(f);
        return this;
    }

    public PrettyStringBuilder append(double d) {
        this.builder.append(d);
        return this;
    }

    public PrettyStringBuilder append(CharSequence s, int indent) {
        for (int i = 0; i < indent; i++) {
            append("\t");
        }
        append(s);
        return this;
    }

    public PrettyStringBuilder appendKeyValue(CharSequence key, Object value) {
        return appendKeyValue(key, value, 0);
    }

    public PrettyStringBuilder appendKeyValue(CharSequence key, Object value, int indent) {
        for (int i = 0; i < indent; i++) {
            append("\t");
        }
        append(key).append(": ").append(value).newLine();
        return this;
    }

    public PrettyStringBuilder newLine() {
        this.builder.append(System.lineSeparator());
        return this;
    }

    public String toString() {
        return this.builder.toString();
    }
}

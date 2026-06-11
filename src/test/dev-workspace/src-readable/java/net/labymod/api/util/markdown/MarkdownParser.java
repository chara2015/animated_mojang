package net.labymod.api.util.markdown;

import java.util.Objects;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownParser.class */
@Referenceable
public interface MarkdownParser {
    MarkdownDocument parse(String str);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownParser$MarkdownLine.class */
    public static class MarkdownLine {
        private final String text;
        private String trimmedText;
        private char[] trimmedChars;
        private char[] chars;

        public MarkdownLine(@NotNull String text) {
            Objects.requireNonNull(text);
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public String getTrimmedText() {
            if (this.trimmedText == null) {
                this.trimmedText = this.text.trim();
            }
            return this.trimmedText;
        }

        public char[] getChars() {
            if (this.chars == null) {
                this.chars = this.text.toCharArray();
            }
            return this.chars;
        }

        public char[] getTrimmedChars() {
            if (this.trimmedChars == null) {
                this.trimmedChars = getTrimmedText().toCharArray();
            }
            return this.trimmedChars;
        }
    }
}

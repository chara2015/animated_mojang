package net.labymod.api.util.markdown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.labymod.api.util.CollectionHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownRawComponent.class */
public class MarkdownRawComponent extends MarkdownComponent {
    private static final List<Formatting> NO_FORMATTING = Collections.unmodifiableList(new ArrayList(0));
    private final String originalText;
    private final String text;
    private final List<Formatting> formatting;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownRawComponent$Formatting.class */
    public enum Formatting {
        BOLD,
        ITALIC;

        public static final Set<Formatting> VALUES = CollectionHelper.asUnmodifiableSet(values());
    }

    public MarkdownRawComponent(String text) {
        this(text, (Formatting[]) null);
    }

    public MarkdownRawComponent(String text, Formatting... formatting) {
        this(text, text, formatting);
    }

    public MarkdownRawComponent(String originalText, String text, Formatting... formatting) {
        super("rawtext");
        this.originalText = originalText;
        this.text = text;
        if (formatting == null) {
            this.formatting = NO_FORMATTING;
        } else {
            this.formatting = List.of((Object[]) formatting);
        }
    }

    public String getText() {
        return this.text;
    }

    public List<Formatting> getFormatting() {
        return this.formatting;
    }

    public boolean hasFormatting(Formatting formatting) {
        return this.formatting.contains(formatting);
    }

    public boolean hasFormatting() {
        return !this.formatting.isEmpty();
    }

    public boolean fromSameLine(MarkdownRawComponent other) {
        return this.originalText.equals(other.originalText);
    }

    public String toString() {
        return "MarkdownRawComponent{text='" + this.text + "', formatting=" + String.valueOf(this.formatting) + "}";
    }
}

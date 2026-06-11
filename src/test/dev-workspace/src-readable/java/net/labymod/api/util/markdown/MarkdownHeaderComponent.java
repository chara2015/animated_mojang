package net.labymod.api.util.markdown;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownHeaderComponent.class */
public class MarkdownHeaderComponent extends MarkdownComponent {
    private final int level;
    private final String text;

    protected MarkdownHeaderComponent(String identifier, int level, String text) {
        super(identifier);
        this.level = validateLevel(level);
        this.text = text;
    }

    public MarkdownHeaderComponent(int level, String text) {
        this("header", level, text);
    }

    public String getText() {
        return this.text;
    }

    public int getLevel() {
        return this.level;
    }

    protected String getNiceName() {
        return "MarkdownHeaderComponent";
    }

    protected int validateLevel(int level) {
        if (level < 1 || level > 6) {
            throw new IllegalStateException("Header level has to be between 1-6");
        }
        return level;
    }

    public String toString() {
        return getNiceName() + "{level=" + this.level + ", text='" + this.text + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarkdownHeaderComponent)) {
            return false;
        }
        MarkdownHeaderComponent that = (MarkdownHeaderComponent) o;
        return this.level == that.level && this.text.equals(that.text);
    }

    public int hashCode() {
        int result = this.level;
        return (31 * result) + (this.text != null ? this.text.hashCode() : 0);
    }
}

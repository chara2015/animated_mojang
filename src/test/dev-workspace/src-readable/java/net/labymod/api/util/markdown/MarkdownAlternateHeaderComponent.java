package net.labymod.api.util.markdown;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownAlternateHeaderComponent.class */
public class MarkdownAlternateHeaderComponent extends MarkdownHeaderComponent {
    public MarkdownAlternateHeaderComponent(int level, String text) {
        super("alternateheader", level, text);
    }

    @Override // net.labymod.api.util.markdown.MarkdownHeaderComponent
    protected int validateLevel(int level) {
        if (level < 1 || level > 2) {
            throw new IllegalStateException("Header level has to be between 1-2");
        }
        return level;
    }

    @Override // net.labymod.api.util.markdown.MarkdownHeaderComponent
    protected String getNiceName() {
        return "MarkdownAlternateHeaderComponent";
    }
}

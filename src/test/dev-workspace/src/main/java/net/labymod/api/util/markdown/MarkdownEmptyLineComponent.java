package net.labymod.api.util.markdown;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownEmptyLineComponent.class */
public class MarkdownEmptyLineComponent extends MarkdownComponent {
    public static final MarkdownEmptyLineComponent INSTANCE = new MarkdownEmptyLineComponent();

    protected MarkdownEmptyLineComponent() {
        super("emptyline");
    }

    public String toString() {
        return "MarkdownEmptyLineComponent{}";
    }
}

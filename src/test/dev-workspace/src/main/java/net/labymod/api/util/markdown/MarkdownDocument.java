package net.labymod.api.util.markdown;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/markdown/MarkdownDocument.class */
public class MarkdownDocument {
    private final List<MarkdownComponent> components;

    public MarkdownDocument(List<MarkdownComponent> components) {
        this.components = Collections.unmodifiableList(components);
    }

    public List<MarkdownComponent> getComponents() {
        return this.components;
    }
}

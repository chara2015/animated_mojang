package net.labymod.api.client.gui.lss.style.modifier.attribute;

import java.util.Comparator;
import java.util.Objects;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/attribute/StyleInstructions.class */
public class StyleInstructions {
    public static final Comparator<StyleInstructions> COMPARATOR = StyleInstructions::compare;
    private final Selector selector;
    private final AttributePatch patch;
    private final int skipDepth;

    public StyleInstructions(Selector selector, AttributePatch patch, int skipDepth) {
        this.selector = selector;
        this.patch = patch;
        this.skipDepth = skipDepth;
    }

    public static int compare(StyleInstructions a, StyleInstructions b) {
        int c3;
        if (a.equals(b)) {
            return 0;
        }
        StyleSheet styleA = a.patch().instruction().styleSheet();
        StyleSheet styleB = b.patch().instruction().styleSheet();
        if (!styleA.equals(styleB)) {
            if (styleB.getImports().contains(styleA)) {
                return -1;
            }
            if (styleA.getImports().contains(styleB)) {
                return 1;
            }
            int c4 = Integer.compare(styleA.getPriority(), styleB.getPriority());
            if (c4 != 0) {
                return c4;
            }
        }
        int c1 = Boolean.compare(a.patch().instruction().isImportant(), b.patch().instruction().isImportant());
        if (c1 != 0) {
            return c1;
        }
        int c2 = Boolean.compare(a.selector().hasStateAttributes(), b.selector().hasStateAttributes());
        if (c2 != 0) {
            return c2;
        }
        if ((a.patch() instanceof PropertyAttributePatch) && (b.patch() instanceof PropertyAttributePatch) && (c3 = Integer.compare(((PropertyAttributePatch) b.patch()).forwarder().getPriority(), ((PropertyAttributePatch) a.patch()).forwarder().getPriority())) != 0) {
            return c3;
        }
        try {
            PseudoClass pca = a.selector().lastPseudoClass();
            PseudoClass pcb = b.selector().lastPseudoClass();
            int c5 = Integer.compare(pca != null ? pca.getPriority() : Integer.MIN_VALUE, pcb != null ? pcb.getPriority() : Integer.MIN_VALUE);
            if (c5 != 0) {
                return c5;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        if (styleA.equals(styleB)) {
            return Integer.compare(a.getLineNumber(), b.getLineNumber());
        }
        return Integer.compare(styleA.getLoadIndex(), styleB.getLoadIndex());
    }

    public Selector selector() {
        return this.selector;
    }

    public int getSkipDepth() {
        return this.skipDepth;
    }

    public AttributePatch patch() {
        return this.patch;
    }

    public int getLineNumber() {
        return this.patch.getMeta().getBlock().getLineOf(this.patch.getKey());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StyleInstructions that = (StyleInstructions) o;
        return Objects.equals(this.selector, that.selector) && Objects.equals(this.patch, that.patch);
    }

    public int hashCode() {
        int result = this.selector != null ? this.selector.hashCode() : 0;
        return (31 * result) + (this.patch != null ? this.patch.hashCode() : 0);
    }

    public String toString() {
        return "StyleInstructions{" + this.patch.getKey() + ": " + this.patch.rawValue() + "}";
    }
}

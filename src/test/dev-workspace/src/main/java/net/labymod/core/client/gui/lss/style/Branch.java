package net.labymod.core.client.gui.lss.style;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.attribute.state.PseudoClass;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.lss.style.modifier.state.DefaultPseudoClassRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/Branch.class */
public class Branch {
    private static final DefaultPseudoClassRegistry PSEUDO_CLASS_REGISTRY = (DefaultPseudoClassRegistry) Laby.references().pseudoClassRegistry();
    private final BranchEntry[] entries;

    public Branch(String selector) {
        if (selector.isEmpty()) {
            this.entries = new BranchEntry[0];
            return;
        }
        List<BranchEntry> entries = new ArrayList<>();
        String[] segments = (String[]) parseSelectorSegments(selector).toArray(new String[0]);
        for (String segment : segments) {
            String[] parts = segment.split(":", 2);
            String entrySelector = parts[0];
            PseudoClass pseudoClass = null;
            if (parts.length > 1) {
                pseudoClass = PSEUDO_CLASS_REGISTRY.parse(parts[1]);
            }
            entries.add(new BranchEntry(entrySelector, pseudoClass));
        }
        this.entries = (BranchEntry[]) entries.toArray(new BranchEntry[0]);
    }

    private List<String> parseSelectorSegments(String selector) {
        List<String> out = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int stateLevel = 0;
        for (char c : selector.toCharArray()) {
            if (c == '(') {
                stateLevel++;
            }
            if (c == ')') {
                stateLevel--;
            }
            if (c == '.' && stateLevel == 0 && builder.length() != 0) {
                out.add(builder.toString());
                builder.setLength(0);
            }
            builder.append(c);
        }
        if (builder.length() != 0) {
            out.add(builder.toString());
        }
        if (stateLevel != 0) {
            throw new IllegalArgumentException("Invalid selector: \"" + selector + "\"");
        }
        return out;
    }

    public static Branch[] createTree(String[] selectors) {
        Branch[] branches = new Branch[selectors.length];
        for (int i = 0; i < selectors.length; i++) {
            String selector = selectors[i];
            branches[i] = new Branch(selector);
        }
        return branches;
    }

    public BranchEntry[] getEntries() {
        return this.entries;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (BranchEntry entry : this.entries) {
            builder.append(entry.getSelector());
            if (entry.getPseudoClass() != null) {
                builder.append(':').append(entry.getPseudoClass().element().toString());
            }
        }
        return builder.toString();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/Branch$BranchEntry.class */
    public static class BranchEntry {
        private final String selector;
        private final PseudoClass pseudoClass;
        private final Predicate<Widget> predicate;

        public BranchEntry(@NotNull String selector, @Nullable PseudoClass pseudoClass) {
            this.selector = selector;
            this.pseudoClass = pseudoClass;
            boolean isId = selector.startsWith(".");
            boolean isUniqueId = selector.startsWith("#");
            String id = (isId || isUniqueId) ? selector.substring(1) : selector;
            this.predicate = widget -> {
                if (id.length() == 0 || this.selector.equals("*")) {
                    return true;
                }
                if (isUniqueId) {
                    return widget.getUniqueId().equals(id);
                }
                if (isId) {
                    return widget.hasId(id);
                }
                return widget.getTypeName().equals(selector);
            };
        }

        @NotNull
        public String getSelector() {
            return this.selector;
        }

        @Nullable
        public PseudoClass getPseudoClass() {
            return this.pseudoClass;
        }

        public boolean matchesPseudoClass(Widget widget) {
            return this.pseudoClass == null || this.pseudoClass.matchesState(widget);
        }

        public boolean matches(Widget widget) {
            return this.predicate.test(widget);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BranchEntry that = (BranchEntry) o;
            return Objects.equals(this.selector, that.selector) && Objects.equals(this.pseudoClass, that.pseudoClass);
        }

        public int hashCode() {
            int result = this.selector.hashCode();
            return (31 * result) + (this.pseudoClass != null ? this.pseudoClass.hashCode() : 0);
        }
    }
}

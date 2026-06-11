package net.labymod.api.client.gui.lss.meta;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/meta/LinkReference.class */
public class LinkReference implements Comparable<LinkReference> {
    private final String namespace;
    private final String path;
    private final int priority;

    public LinkReference(String namespace, String path, int priority) {
        this.namespace = namespace;
        this.path = path;
        this.priority = priority;
    }

    public LinkReference(String namespace, String path) {
        this(namespace, path, 0);
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getPath() {
        return this.path;
    }

    public int getPriority() {
        return this.priority;
    }

    public ThemeFile toThemeFile(Theme theme) {
        return ThemeFile.create(theme, this.namespace, this.path);
    }

    public StyleSheet loadStyleSheet() {
        ThemeFile file = toThemeFile(Laby.labyAPI().themeService().currentTheme());
        StyleSheet styleSheet = Laby.references().styleSheetLoader().load(file.normalize());
        if (styleSheet == null) {
            return null;
        }
        return styleSheet.withPriority(this.priority);
    }

    public boolean equals(Object obj) {
        return (obj instanceof LinkReference) && ((LinkReference) obj).namespace.equals(this.namespace) && ((LinkReference) obj).path.equals(this.path) && ((LinkReference) obj).priority == this.priority;
    }

    public int hashCode() {
        int result = this.namespace != null ? this.namespace.hashCode() : 0;
        return (31 * ((31 * result) + (this.path != null ? this.path.hashCode() : 0))) + this.priority;
    }

    @Override // java.lang.Comparable
    public int compareTo(@NotNull LinkReference o) {
        return Integer.compare(this.priority, o.priority);
    }
}

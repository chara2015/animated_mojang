package net.labymod.api.client.gui.screen.theme;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.client.gui.lss.meta.LinkReference;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/ThemeFile.class */
public class ThemeFile extends BasicThemeFile {
    private static final Map<String, ThemeFile> CACHE = new HashMap();
    private static final String THEMES_DIR = "themes/";
    private final String[] pathSegments;
    private final String[] absolutePath;
    private final String fullRawPath;

    private ThemeFile(Theme theme, String namespace, String[] pathSegments) {
        super(theme, namespace, String.join("/", pathSegments));
        this.pathSegments = pathSegments;
        this.absolutePath = rebuildPathStructure(pathSegments);
        this.fullRawPath = "themes/" + theme.getId() + "/" + String.join("/", this.absolutePath);
    }

    private ThemeFile(Theme theme, String namespace, String path) {
        super(theme, namespace, path);
        this.pathSegments = path.split("/");
        this.absolutePath = rebuildPathStructure(this.pathSegments);
        this.fullRawPath = "themes/" + theme.getId() + "/" + String.join("/", this.absolutePath);
    }

    private String[] rebuildPathStructure(String[] path) {
        try {
            List<String> nodes = new ArrayList<>();
            for (String node : path) {
                if (!node.equals(".")) {
                    if (node.equals("..") && !nodes.isEmpty() && !nodes.get(nodes.size() - 1).equals("..")) {
                        nodes.remove(nodes.size() - 1);
                    } else {
                        nodes.add(node);
                    }
                }
            }
            path = new String[nodes.size()];
            nodes.toArray(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public String[] getAbsolutePath() {
        return this.absolutePath;
    }

    public String getFullRawPath() {
        return this.fullRawPath;
    }

    public ThemeFile parent() {
        return create(this.theme, this.namespace, (String[]) Arrays.copyOf(this.pathSegments, this.pathSegments.length - 1));
    }

    @Override // net.labymod.api.client.gui.screen.theme.BasicThemeFile
    public String getNamespace() {
        return this.namespace;
    }

    public Theme theme() {
        return this.theme;
    }

    public String getName() {
        return this.pathSegments[this.pathSegments.length - 1];
    }

    public boolean isDirectory() {
        return !getName().contains(".");
    }

    public InputStream openInputStream() throws IOException {
        return toResourceLocation().openStream();
    }

    public ThemeFile forTheme(Theme theme) {
        return create(theme, this.namespace, this.path);
    }

    public ThemeFile normalize() {
        return this.theme.file(this.namespace, this.path);
    }

    public String toString() {
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[2];
        objArr[0] = this.namespace;
        objArr[1] = this.theme != null ? this.theme.getId() + "/" + getPath() : getPath();
        return String.format(locale, "%s:%s", objArr);
    }

    @Override // net.labymod.api.client.gui.screen.theme.BasicThemeFile
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeFile)) {
            return false;
        }
        ThemeFile themeFile = (ThemeFile) o;
        if (!this.namespace.equals(themeFile.namespace) || !this.theme.equals(themeFile.theme)) {
            return false;
        }
        return getPath().equals(themeFile.getPath());
    }

    @Override // net.labymod.api.client.gui.screen.theme.BasicThemeFile
    public int hashCode() {
        int result = this.namespace.hashCode();
        return (31 * ((31 * result) + this.theme.hashCode())) + getPath().hashCode();
    }

    public LinkReference toLink() {
        return new LinkReference(this.namespace, this.path, 0);
    }

    public static ThemeFile create(Theme theme, String namespace, String path) {
        String cacheKey = theme.getId() + ":" + namespace + ":" + path;
        return CACHE.computeIfAbsent(cacheKey, key -> {
            return new ThemeFile(theme, namespace, path);
        });
    }

    public static ThemeFile create(Theme theme, String namespace, String[] pathSegments) {
        String cacheKey = theme.getId() + ":" + namespace + ":" + String.join("/", pathSegments);
        return CACHE.computeIfAbsent(cacheKey, key -> {
            return new ThemeFile(theme, namespace, pathSegments);
        });
    }
}

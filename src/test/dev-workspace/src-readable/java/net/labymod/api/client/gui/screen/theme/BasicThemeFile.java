package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/BasicThemeFile.class */
public class BasicThemeFile {
    protected final Theme theme;
    protected final String namespace;
    protected final String path;
    private ResourceLocation resourceLocation;

    public BasicThemeFile(Theme theme, String namespace, String path) {
        this.theme = theme;
        this.namespace = namespace;
        this.path = path;
    }

    public Theme getTheme() {
        return this.theme;
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public ResourceLocation toResourceLocation() {
        if (this.resourceLocation != null) {
            return this.resourceLocation;
        }
        ResourceLocation resourceLocationCreate = ResourceLocation.create(this.namespace, this.theme.getDirectoryPath() + getPath());
        this.resourceLocation = resourceLocationCreate;
        return resourceLocationCreate;
    }

    public boolean exists() {
        return Laby.references().themeFileFinder().exists(this);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicThemeFile file = (BasicThemeFile) o;
        return this.namespace.equals(file.namespace) && this.theme.equals(file.theme) && this.path.equals(file.path);
    }

    public int hashCode() {
        int result = this.theme != null ? this.theme.hashCode() : 0;
        return (31 * ((31 * result) + (this.namespace != null ? this.namespace.hashCode() : 0))) + (this.path != null ? this.path.hashCode() : 0);
    }
}

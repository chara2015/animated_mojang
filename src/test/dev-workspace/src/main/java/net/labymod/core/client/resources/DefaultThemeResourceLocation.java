package net.labymod.core.client.resources;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultThemeResourceLocation.class */
public class DefaultThemeResourceLocation implements ThemeResourceLocation {
    private final String id;
    private final String namespace;
    private final String path;
    private ResourceLocation location;
    private String themeId;

    protected DefaultThemeResourceLocation(String id, String namespace, String path) {
        this.id = id;
        this.namespace = namespace;
        this.path = path;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @Override // net.labymod.api.client.resources.ThemeResourceLocation
    public ResourceLocation resource() {
        Theme theme = Laby.labyAPI().themeService().currentTheme();
        if (this.location != null && Objects.equals(this.themeId, theme.getId())) {
            return this.location;
        }
        this.themeId = theme.getId();
        ResourceLocation resourceLocationResource = resource(theme);
        this.location = resourceLocationResource;
        return resourceLocationResource;
    }

    public ResourceLocation resource(@NotNull Theme theme) {
        return theme.resource(this.namespace == null ? theme.getNamespace() : this.namespace, this.path);
    }

    public String toString() {
        return resource().toString();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/DefaultThemeResourceLocation$Builder.class */
    public static class Builder {
        protected String id;
        protected String namespace;
        protected String path;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public DefaultThemeResourceLocation build() {
            if (this.namespace == null && this.path.contains(":")) {
                String[] split = this.path.split(":");
                this.path = split[1];
                this.namespace = split[0];
            }
            if (this.id == null) {
                String id = this.path.replace(".", "");
                if (id.contains(":")) {
                    id = id.split(":")[1];
                }
                this.id = id;
            }
            return new DefaultThemeResourceLocation(this.id, this.namespace, this.path);
        }
    }
}

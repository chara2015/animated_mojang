package net.labymod.api.client.resources.pack.rich;

import java.io.File;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/rich/ResourcePackDetails.class */
public interface ResourcePackDetails {
    public static final Logging LOGGER = Logging.create((Class<?>) ResourcePackDetails.class);

    @NotNull
    String getId();

    @NotNull
    Icon getIcon();

    @NotNull
    Component getTitle();

    @NotNull
    Component getDescription();

    boolean isPositionFixed();

    boolean isRequired();

    @NotNull
    DefaultPosition getDefaultPosition();

    @NotNull
    Compatibility getCompatibility();

    @Nullable
    File getFile();

    boolean isUserPack();

    default Component getPrettyTitle() {
        File file = getFile();
        if (file == null || !isUserPack()) {
            return getTitle();
        }
        return Component.text(file.getName().replace("_", " ").replaceAll("\\.zip$", ""));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/rich/ResourcePackDetails$DefaultPosition.class */
    public enum DefaultPosition {
        TOP,
        BOTTOM;

        public DefaultPosition invert() {
            return this == TOP ? BOTTOM : TOP;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/rich/ResourcePackDetails$Compatibility.class */
    public enum Compatibility {
        TOO_OLD(false),
        TOO_NEW(false),
        COMPATIBLE(true),
        UNKNOWN(false);

        private final boolean compatible;

        Compatibility(boolean compatible) {
            this.compatible = compatible;
        }

        public boolean isCompatible() {
            return this.compatible;
        }
    }
}

package net.labymod.api.configuration.labymod.main.laby.other.microsoft;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/microsoft/MicrosoftWindowConfig.class */
public interface MicrosoftWindowConfig extends ConfigAccessor {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/microsoft/MicrosoftWindowConfig$IdProvider.class */
    public interface IdProvider {
        int getId();
    }

    ConfigProperty<Boolean> immersiveDarkMode();

    ConfigProperty<WindowMaterial> windowMaterial();

    ConfigProperty<CornerCurvatures> cornerCurvatures();

    ConfigProperty<Boolean> defaultWindowBorderColor();

    ConfigProperty<Boolean> hideWindowBorder();

    ConfigProperty<Color> windowBorderColor();

    ConfigProperty<Boolean> defaultWindowTitleBarColor();

    ConfigProperty<Color> windowTitleBarColor();

    ConfigProperty<Boolean> defaultTitleTextColor();

    ConfigProperty<Color> titleTextColor();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/microsoft/MicrosoftWindowConfig$WindowMaterial.class */
    public enum WindowMaterial implements IdProvider {
        AUTO(0),
        NONE(1),
        MICA(2),
        ACRYLIC(3),
        TABBED(4);

        private final int id;

        WindowMaterial(int id) {
            this.id = id;
        }

        @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig.IdProvider
        public int getId() {
            return this.id;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/other/microsoft/MicrosoftWindowConfig$CornerCurvatures.class */
    public enum CornerCurvatures implements IdProvider {
        DEFAULT(0),
        ROUND(2),
        DO_NOT_ROUND(1),
        ROUND_SMALL(3);

        private final int id;

        CornerCurvatures(int id) {
            this.id = id;
        }

        @Override // net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig.IdProvider
        public int getId() {
            return this.id;
        }
    }
}

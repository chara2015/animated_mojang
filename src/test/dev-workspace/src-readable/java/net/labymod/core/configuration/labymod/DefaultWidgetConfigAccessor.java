package net.labymod.core.configuration.labymod;

import net.labymod.api.configuration.labymod.WidgetConfigAccessor;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.loader.annotation.ConfigName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/DefaultWidgetConfigAccessor.class */
@ConfigName("widgets")
public class DefaultWidgetConfigAccessor extends Config implements WidgetConfigAccessor {
    private int lastSelectedColor = -16740376;

    @Override // net.labymod.api.configuration.labymod.WidgetConfigAccessor
    public int getLastSelectedColor() {
        return this.lastSelectedColor;
    }

    @Override // net.labymod.api.configuration.labymod.WidgetConfigAccessor
    public void setLastSelectedColor(int lastSelectedColor) {
        this.lastSelectedColor = lastSelectedColor;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/DefaultWidgetConfigAccessor$WidgetConfigProvider.class */
    public static class WidgetConfigProvider extends ConfigProvider<WidgetConfigAccessor> {
        public static final WidgetConfigProvider INSTANCE = new WidgetConfigProvider();

        private WidgetConfigProvider() {
        }

        @Override // net.labymod.api.configuration.loader.ConfigProvider
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultWidgetConfigAccessor.class;
        }
    }
}

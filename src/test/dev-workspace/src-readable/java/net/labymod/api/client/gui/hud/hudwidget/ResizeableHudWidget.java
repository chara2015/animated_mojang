package net.labymod.api.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget.ResizeableHudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/ResizeableHudWidget.class */
public abstract class ResizeableHudWidget<T extends ResizeableHudWidgetConfig> extends HudWidget<T> {
    protected float width;
    protected float height;

    public abstract void render(ScreenContext screenContext, boolean z, float f, float f2);

    protected ResizeableHudWidget(String id, Class<T> configClass) {
        super(id, configClass);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(T config) {
        super.load(config);
        this.width = config.width().get().floatValue();
        this.height = config.height().get().floatValue();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public final void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        size.set(this.width, this.height);
        render(context, isEditorContext, this.width, this.height);
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/ResizeableHudWidget$ResizeableHudWidgetConfig.class */
    public static abstract class ResizeableHudWidgetConfig extends HudWidgetConfig {

        @Exclude
        private final float minWidth;

        @Exclude
        private final float minHeight;

        @Exclude
        private final float maxWidth;

        @Exclude
        private final float maxHeight;
        private final ConfigProperty<Float> width;
        private final ConfigProperty<Float> height;

        public ResizeableHudWidgetConfig(float minWidth, float defaultWidth, float maxWidth, float minHeight, float defaultHeight, float maxHeight) {
            this.minWidth = minWidth;
            this.minHeight = minHeight;
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.width = new ConfigProperty<>(Float.valueOf(defaultWidth));
            this.height = new ConfigProperty<>(Float.valueOf(defaultHeight));
        }

        public ConfigProperty<Float> width() {
            return this.width;
        }

        public ConfigProperty<Float> height() {
            return this.height;
        }

        public float getMinWidth() {
            return this.minWidth;
        }

        public float getMinHeight() {
            return this.minHeight;
        }

        public float getMaxWidth() {
            return this.maxWidth;
        }

        public float getMaxHeight() {
            return this.maxHeight;
        }
    }
}

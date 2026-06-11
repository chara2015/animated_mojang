package net.labymod.api.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/converter/AbstractMinecraftWidgetConverter.class */
public abstract class AbstractMinecraftWidgetConverter<T, K extends AbstractWidget<?>> extends AbstractWidgetConverter<T, K> {
    protected static final ModifyReason COPY_MINECRAFT_BOUNDS = ModifyReason.of("copyMinecraftBounds");
    private final String name;

    public AbstractMinecraftWidgetConverter(MinecraftWidgetType widgetType) {
        this.name = widgetType.toString();
    }

    @Override // net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter
    public String getName() {
        return this.name;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        return true;
    }
}

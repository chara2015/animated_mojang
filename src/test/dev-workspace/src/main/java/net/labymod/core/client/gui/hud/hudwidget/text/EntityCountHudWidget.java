package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/EntityCountHudWidget.class */
@SpriteSlot(x = 6, y = 1)
public class EntityCountHudWidget extends TextHudWidget<EntityCountHudWidgetConfig> {
    private final LabyMod labyMod;
    private TextLine entityCountTextLine;

    public EntityCountHudWidget() {
        super("entity_count", EntityCountHudWidgetConfig.class);
        this.labyMod = LabyMod.getInstance();
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(EntityCountHudWidgetConfig config) {
        super.load(config);
        this.entityCountTextLine = super.createLine("Entity", (Object) 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        Object value;
        int renderedEntities = this.labyMod.minecraft().worldRenderer().getRenderedEntities();
        if (((EntityCountHudWidgetConfig) getConfig()).showMaxEntityCount().get().booleanValue()) {
            ClientWorld world = this.labyMod.minecraft().clientWorld();
            if (world == null) {
                return;
            } else {
                value = renderedEntities + "/" + world.getEntities().size();
            }
        } else {
            value = Integer.valueOf(renderedEntities);
        }
        this.entityCountTextLine.updateAndFlush(value);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/EntityCountHudWidget$EntityCountHudWidgetConfig.class */
    public static class EntityCountHudWidgetConfig extends TextHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showMaxEntityCount = new ConfigProperty<>(true);

        public ConfigProperty<Boolean> showMaxEntityCount() {
            return this.showMaxEntityCount;
        }
    }
}

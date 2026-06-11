package net.labymod.core.client.gui.hud;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategoryRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/DefaultHudWidgetCategoryRegistry.class */
@Singleton
@Implements(HudWidgetCategoryRegistry.class)
public class DefaultHudWidgetCategoryRegistry extends DefaultRegistry<HudWidgetCategory> implements HudWidgetCategoryRegistry {
    @Inject
    public DefaultHudWidgetCategoryRegistry() {
    }
}

package net.labymod.api.client.gui.hud.binding.category;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.hud.binding.HudWidgetBinding;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/category/HudWidgetCategory.class */
public class HudWidgetCategory extends HudWidgetBinding {
    public static final HudWidgetCategory INGAME = new HudWidgetCategory("ingame");
    public static final HudWidgetCategory ITEM = new HudWidgetCategory("item");
    public static final HudWidgetCategory SYSTEM = new HudWidgetCategory("system");
    public static final HudWidgetCategory SERVICE = new HudWidgetCategory("service");
    public static final HudWidgetCategory MISCELLANEOUS = new HudWidgetCategory("miscellaneous");

    public HudWidgetCategory(@NotNull Object holder, @NotNull String id) {
        super(holder, id);
    }

    public HudWidgetCategory(@NotNull String id) {
        super(id);
    }

    @NotNull
    public Component title() {
        return Component.translatable(this.namespace + ".hudWidgetCategory." + this.id + ".name", new Component[0]).decorate(TextDecoration.BOLD);
    }

    @NotNull
    public Component description() {
        return Component.translatable(this.namespace + ".hudWidgetCategory." + this.id + ".description", new Component[0]);
    }
}

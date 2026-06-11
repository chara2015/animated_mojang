package net.labymod.core.test.other;

import java.lang.reflect.Field;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.GridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/other/TexturesTestActivity.class */
@AutoActivity
@Link("test/test-menu.lss")
public class TexturesTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        GridWidget<DivWidget> icons = new GridWidget<>();
        icons.addId("icon-showcase");
        for (Field declaredField : Textures.class.getDeclaredFields()) {
            addIcon(icons, declaredField);
        }
        for (Class<?> declaredClass : Textures.class.getDeclaredClasses()) {
            for (Field declaredField2 : declaredClass.getDeclaredFields()) {
                addIcon(icons, declaredField2);
            }
        }
        ((Document) super.document()).addChild(icons);
    }

    private void addIcon(GridWidget<DivWidget> icons, Field field) {
        if (!field.getType().isAssignableFrom(Icon.class)) {
            return;
        }
        try {
            Icon icon = (Icon) field.get(null);
            DivWidget wrapper = new DivWidget();
            wrapper.addId("wrapper");
            ComponentWidget iconNameWidget = ComponentWidget.text(field.getName());
            iconNameWidget.addId("icon-name");
            wrapper.addChild(iconNameWidget);
            IconWidget iconWidget = new IconWidget(icon);
            iconWidget.addId("test-view-icon");
            wrapper.addChild(iconWidget);
            icons.addChild(wrapper);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

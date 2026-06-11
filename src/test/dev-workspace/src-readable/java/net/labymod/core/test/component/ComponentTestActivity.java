package net.labymod.core.test.component;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/component/ComponentTestActivity.class */
@Link("test/test-menu.lss")
@AutoActivity
public class ComponentTestActivity extends TestActivity {
    private static boolean DEBUG_IDEA_15514 = false;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<ComponentWidget> list = new VerticalListWidget<>();
        list.addId("texts");
        list.addChild(ComponentWidget.component(Component.translatable("%", new Component[0]).arguments(Component.text(""))));
        list.addChild(ComponentWidget.component(Component.translatable("Hello, %s", new Component[0]).arguments(Component.text(this.labyAPI.getName()))));
        list.addChild(ComponentWidget.i18n("Test, %s %s %s", "1", "2", "3"));
        list.addChild(ComponentWidget.component(Component.empty().append(Component.icon(Icon.head("derrop"), Style.builder().hoverEvent(HoverEvent.showText(Component.text("test"))).build())).append(Component.text("test", Style.builder().hoverEvent(HoverEvent.showText(Component.text("asdf"))).build())).append(Component.text("asdf"))));
        list.addChild(ComponentWidget.component(Component.keybind("key.forward")));
        list.addChild(ComponentWidget.text("Test the ScoreComponent with \"/scoreboard objectives add test dummy\", \"/scoreboard players set PLAYER test 10\" and in 1.17+ \"/scoreboard objectives setdisplay sidebar test\""));
        list.addChild(ComponentWidget.component(Component.score(this.labyAPI.getName(), "test")));
        if (DEBUG_IDEA_15514) {
            list.removeChildIf(componentWidget -> {
                return true;
            });
            list.addChild(ComponentWidget.text("(FancyTheme) GommeHD.net MoneyMaker ActionBar Message:"));
            list.addChild(ComponentWidget.text("§bNext level: §8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│§8§l│ §e0§8/10"));
        }
        TextComponent text = Component.text("Click me! :)", NamedTextColor.GOLD, TextDecoration.BOLD);
        text.clickEvent(ClickEvent.openUrl("https://www.laby.net/@Robby_"));
        list.addChild(ComponentWidget.component(text));
        ((Document) this.document).addChild(list);
    }
}

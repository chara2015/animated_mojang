package net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetCreatedEvent;
import net.labymod.api.event.client.gui.hud.HudWidgetDestroyedEvent;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTilesGridWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTypeInfoWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/hudwidget/ListHudWindowActivity.class */
@Link("activity/hudwidget/window/hud-widget-list.lss")
@AutoActivity
public class ListHudWindowActivity extends HudWindowActivity {
    private final HudWidgetRegistry registry;
    private final ListSession<Widget> session;
    private VerticalListWidget<Widget> listWidget;

    public ListHudWindowActivity(HudWidgetWindowWidget window) {
        super(window);
        this.registry = Laby.references().hudWidgetRegistry();
        this.session = new ListSession<>();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        String filterText = this.window.getSearchQuery();
        WidgetsEditorActivity editor = this.window.editor();
        this.listWidget = new VerticalListWidget<>(this.session);
        this.listWidget.addId("list");
        for (HudWidgetCategory category : this.registry.categoryRegistry().values()) {
            HudWidgetTilesGridWidget gridWidget = new HudWidgetTilesGridWidget(editor);
            gridWidget.addId("grid");
            InterpolateWidget categoryWidget = new InterpolateWidget(50.0f);
            categoryWidget.addId("category");
            ComponentWidget categoryTitleWidget = ComponentWidget.component(category.title());
            categoryTitleWidget.addId("title");
            categoryWidget.addChild(categoryTitleWidget);
            ComponentWidget categoryDescriptionWidget = ComponentWidget.component(category.description());
            categoryDescriptionWidget.addId("description");
            categoryWidget.addChild(categoryDescriptionWidget);
            for (HudWidget<?> hudWidget : this.registry.values()) {
                if (hudWidget.category().equals(category) && hudWidget.isHolderEnabled()) {
                    if (filterText != null) {
                        filterText = filterText.toLowerCase(Locale.ROOT);
                        Component title = hudWidget.displayName();
                        String titleCharacters = RenderableComponent.of(title).getText();
                        String titleString = titleCharacters.toLowerCase(Locale.ROOT);
                        if (!titleString.contains(filterText)) {
                        }
                    }
                    HudWidgetTypeInfoWidget widget = new HudWidgetTypeInfoWidget(hudWidget, gridWidget);
                    widget.addId("entry");
                    gridWidget.addChild(widget);
                }
            }
            if (!gridWidget.getChildren().isEmpty()) {
                this.listWidget.addChild(categoryWidget);
                this.listWidget.addChild(gridWidget);
            }
        }
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.listWidget);
        scrollWidget.addId("scroll");
        ((Document) this.document).addChild(scrollWidget);
    }

    @Subscribe
    public void onHudWidgetCreated(HudWidgetCreatedEvent event) {
        updateHudWidgetTypeInfo(event.hudWidget());
    }

    @Subscribe
    public void onHudWidgetDestroyed(HudWidgetDestroyedEvent event) {
        updateHudWidgetTypeInfo(event.hudWidget());
    }

    private void updateHudWidgetTypeInfo(HudWidget<?> hudWidget) {
        for (Widget child : this.listWidget.getChildren()) {
            if (child instanceof HudWidgetTilesGridWidget) {
                HudWidgetTilesGridWidget grid = (HudWidgetTilesGridWidget) child;
                for (HudWidgetTypeInfoWidget typeInfoWidget : grid.getChildren()) {
                    if (typeInfoWidget.hudWidget() == hudWidget) {
                        typeInfoWidget.reInitialize();
                    }
                }
            }
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget.HudWindowActivity
    public boolean canDropHudWidget() {
        for (Widget child : this.listWidget.getChildren()) {
            if (child instanceof HudWidgetTilesGridWidget) {
                HudWidgetTilesGridWidget grid = (HudWidgetTilesGridWidget) child;
                if (grid.draggingWidget() != null) {
                    return false;
                }
            }
        }
        return true;
    }
}

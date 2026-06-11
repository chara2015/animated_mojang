package net.labymod.core.test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.test.animation.AnimationDemoActivity;
import net.labymod.core.test.animation.FluentAnimationDemoActivity;
import net.labymod.core.test.cave.BackgroundWorldTestActivity;
import net.labymod.core.test.cave.ModelMinecraftTextTestActivity;
import net.labymod.core.test.component.ComponentTestActivity;
import net.labymod.core.test.gfx.AnimatedIconTestActivity;
import net.labymod.core.test.gfx.CircleStencilTestActivity;
import net.labymod.core.test.gfx.DynamicTextureTestActivity;
import net.labymod.core.test.gfx.GFXImmediateTestActivity;
import net.labymod.core.test.other.ActivitiesTestActivity;
import net.labymod.core.test.other.ResourcePackDetailsTestActivity;
import net.labymod.core.test.other.TexturesTestActivity;
import net.labymod.core.test.overlay.StreamOverlayTestActivity;
import net.labymod.core.test.performance.VerticalListTestActivity;
import net.labymod.core.test.styleorder.StyleOrderTestActivity;
import net.labymod.core.test.vanilla.VanillaFontRendererTestActivity;
import net.labymod.core.test.widget.LazyGridWidgetTestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/TestMenuActivity.class */
@Link("test/test-menu.lss")
@AutoActivity
public class TestMenuActivity extends SimpleActivity {
    private final Map<String, Supplier<TestActivity>> activities = new HashMap();

    public TestMenuActivity() {
        registerActivity("Performance VerticalList", VerticalListTestActivity::new);
        registerActivity("Formular Test", FormTestActivity::new);
        registerActivity("Components", ComponentTestActivity::new);
        registerActivity("GFX Immediate", GFXImmediateTestActivity::new);
        registerActivity("Rectangle", RectangleTestActivity::new);
        registerActivity("Background World", BackgroundWorldTestActivity::new);
        registerActivity("Fractured Logo", ModelMinecraftTextTestActivity::new);
        registerActivity("StyleSheet Order Test", StyleOrderTestActivity::new);
        registerActivity("Context Menu", ContextMenuTestActivity::new);
        registerActivity("Animated Icons", AnimatedIconTestActivity::new);
        registerActivity("TextInput", TextInputTestActivity::new);
        registerActivity("Stream Overlay", StreamOverlayTestActivity::new);
        registerActivity("Circle Clip", CircleStencilTestActivity::new);
        registerActivity("Activities", ActivitiesTestActivity::new);
        registerActivity("View Textures", TexturesTestActivity::new);
        registerActivity("Dynamic Texture", DynamicTextureTestActivity::new);
        registerActivity("Resource Pack Details", ResourcePackDetailsTestActivity::new);
        registerActivity("Lazy Grid Widget", LazyGridWidgetTestActivity::new);
        registerActivity("Vanilla Font Renderer", VanillaFontRendererTestActivity::new);
        registerActivity("Animation System", AnimationDemoActivity::new);
        registerActivity("Fluent Animation API", FluentAnimationDemoActivity::new);
    }

    public void registerActivity(String name, TestActivity activity) {
        registerActivity(name, () -> {
            return activity;
        });
    }

    public void registerActivity(String name, Supplier<TestActivity> activitySupplier) {
        this.activities.put(name, activitySupplier);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VerticalListWidget<Widget> list = new VerticalListWidget<>();
        TilesGridWidget<Widget> gridWidget = new TilesGridWidget<>();
        for (Map.Entry<String, Supplier<TestActivity>> entry : this.activities.entrySet()) {
            DivWidget entryContainer = new DivWidget();
            entryContainer.addChild(ComponentWidget.text(entry.getKey()));
            entryContainer.addId("entries");
            entryContainer.setPressable(() -> {
                displayScreen((ScreenInstance) ((Supplier) entry.getValue()).get());
            });
            gridWidget.addTile(entryContainer);
        }
        list.addChild(gridWidget);
        DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        ComponentWidget title = ComponentWidget.text("Test Menu");
        title.addId("title");
        containerWidget.addChild(title);
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) list);
        containerWidget.addChild(scrollWidget);
        ((Document) this.document).addChild(containerWidget);
    }
}

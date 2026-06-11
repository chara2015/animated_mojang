package net.labymod.core.client.gui.hud.hudwidget.survival;

import java.util.Locale;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.ItemStackWidget;
import net.labymod.api.client.waila.WailaPosition;
import net.labymod.api.client.waila.tooltip.WailaTooltip;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.service.Registry;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.DefaultWailaRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaWidget.class */
@AutoWidget
@Link("waila-widget.lss")
public class WailaWidget extends FlexibleContentWidget {
    private final DefaultWailaRegistry wailaRegistry;
    private final boolean editorContext;
    private final WailaHudWidget hudWidget;
    private float prevPadding = Float.MIN_VALUE;
    private float prevMargin = Float.MIN_VALUE;
    private final ItemStackFactory itemStackFactory = Laby.references().itemStackFactory();

    /* JADX WARN: Multi-variable type inference failed */
    public WailaWidget(DefaultWailaRegistry wailaRegistry, boolean editorContext, WailaHudWidget hudWidget, HudWidgetWidget widget) {
        ThemeRenderer<WailaWidget> themeRenderer;
        this.wailaRegistry = wailaRegistry;
        this.editorContext = editorContext;
        this.hudWidget = hudWidget;
        updateVariables();
        WailaHudWidget.WailaBackgroundType wailaBackgroundType = ((WailaHudWidget.WailaConfiguration) this.hudWidget.getConfig()).backgroundStyle().get();
        if (wailaBackgroundType.hasRenderer()) {
            themeRenderer = wailaBackgroundType.createRenderer(widget).withConfig((WailaHudWidget.WailaConfiguration) this.hudWidget.getConfig());
        } else {
            themeRenderer = null;
        }
        renderer().updateDefaultValue(themeRenderer);
        renderer().reset();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        updateVariables();
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("container");
        if (this.editorContext) {
            populateWidgetWithDummyContent(container);
        } else {
            populateWidgetWithWailaContent(container);
        }
    }

    public float getOffset() {
        return this.hudWidget.getMargin();
    }

    private void updateVariables() {
        float padding = this.hudWidget.getPadding();
        float margin = this.hudWidget.getMargin();
        if (this.prevPadding == padding && this.prevMargin == margin) {
            return;
        }
        this.prevPadding = padding;
        this.prevMargin = margin;
        updateBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public Float getPadding(OffsetSide side) {
        return Float.valueOf(this.hudWidget.getPadding() + super.getPadding(side).floatValue());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public Float getMargin(OffsetSide side) {
        return Float.valueOf(this.hudWidget.getMargin() + super.getMargin(side).floatValue());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        super.render(context);
        updateVariables();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void populateWidgetWithDummyContent(FlexibleContentWidget container) {
        VerticalListWidget<Widget> leftList;
        addContent(createDummyList(new Widget[0]).addId("top", "custom"));
        ItemStack icon = this.itemStackFactory.create(VanillaItems.STONE);
        if (((WailaHudWidget.WailaConfiguration) this.hudWidget.getConfig()).blockIcon().get().booleanValue() && icon.areComponentsBound()) {
            leftList = createDummyList(new ItemStackWidget(icon));
        } else {
            leftList = createDummyList(new Widget[0]);
        }
        container.addContent(leftList.addId("left", "center"));
        container.addContent(createDummyList(createItemDisplayComponent(icon, true, () -> {
            return Component.text("Stone");
        }, "waila-main-component"), createItemDisplayComponent(this.itemStackFactory.create(VanillaItems.WOODEN_PICKAXE), ((WailaHudWidget.WailaConfiguration) this.hudWidget.getConfig()).blockRequiredTool().get().booleanValue(), () -> {
            return Component.text("Wooden Pickaxe");
        }, "waila-secondary-component")).addId("middle", "center"));
        container.addContent(createDummyList(new Widget[0]).addId("right", "center"));
        addContent(container);
        addContent(createDummyList(new Widget[0]).addId("bottom", "custom"));
    }

    private void populateWidgetWithWailaContent(FlexibleContentWidget container) {
        addContent(createList(WailaPosition.TOP).addId("top", "custom"));
        for (WailaPosition position : WailaPosition.getValues()) {
            if (!position.isCustom()) {
                container.addContent(createList(position)).addId(position.name().toLowerCase(Locale.ROOT), "center");
            }
        }
        addContent(container);
        addContent(createList(WailaPosition.BOTTOM).addId("bottom", "custom"));
    }

    private VerticalListWidget<Widget> createDummyList(Widget... widgets) {
        VerticalListWidget<Widget> list = new VerticalListWidget<>();
        for (Widget widget : widgets) {
            if (widget != null) {
                list.addChild(widget);
            }
        }
        return list;
    }

    private VerticalListWidget<Widget> createList(WailaPosition position) {
        return createList(this.wailaRegistry.getRegistry(position));
    }

    private VerticalListWidget<Widget> createList(Registry<WailaTooltip> registry) {
        VerticalListWidget<Widget> list = new VerticalListWidget<>();
        for (WailaTooltip tooltip : registry.values()) {
            Widget tooltipWidget = tooltip.createWidget();
            if (tooltipWidget != null) {
                if (tooltipWidget instanceof ComponentWidget) {
                    if (tooltip.getId().endsWith("/name")) {
                        tooltipWidget.addId("waila-main-component");
                    } else {
                        tooltipWidget.addId("waila-secondary-component");
                    }
                }
                list.addChild(tooltipWidget.addId(tooltip.getId().replace("/", "-")));
            }
        }
        return list;
    }

    private ComponentWidget createItemDisplayComponent(ItemStack itemStack, boolean showDetails, Supplier<Component> fallbackComponentSupplier, String... ids) {
        Component displayName;
        if (showDetails) {
            if (itemStack.areComponentsBound()) {
                displayName = itemStack.getDisplayName();
            } else {
                displayName = fallbackComponentSupplier.get();
            }
            return (ComponentWidget) ComponentWidget.component(displayName).addId(ids);
        }
        return null;
    }
}

package net.labymod.core.client.gui.hud.hudwidget.survival;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.widget.WidgetHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.world.DimensionChangeEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.util.InjectionNames;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.waila.DefaultWailaRegistry;
import net.labymod.core.client.waila.WailaUpdateEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget.class */
@SpriteSlot(x = 6, y = 5)
@IntroducedIn("4.3.0")
public class WailaHudWidget extends WidgetHudWidget<WailaConfiguration> {
    private static final String WAILA_UPDATE_REASON = "waila_update";
    private final DefaultWailaRegistry wailaRegistry;
    private float padding;
    private float margin;

    public WailaHudWidget() {
        super("waila", WailaConfiguration.class);
        bindCategory(HudWidgetCategory.INGAME);
        this.wailaRegistry = (DefaultWailaRegistry) Laby.references().wailaRegistry();
        this.wailaRegistry.registerDefaults(this);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(WailaConfiguration config) {
        super.load(config);
        updateOffsets();
        config.backgroundStyle.addChangeListener(() -> {
            updateOffsets();
            requestUpdate(WAILA_UPDATE_REASON);
        });
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (!isEditorContext) {
            this.wailaRegistry.tick();
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.widget.WidgetHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        updateOffsets();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.widget.WidgetHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initialize(HudWidgetWidget widget) {
        super.initialize(widget);
        WailaWidget wailaWidget = new WailaWidget(this.wailaRegistry, widget.accessor().isEditor(), this, widget);
        widget.addChild(wailaWidget);
        widget.addId("waila");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.widget.WidgetHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void updateSize(HudWidgetWidget widget, boolean isEditorContext, HudSize size) {
        super.updateSize(widget, isEditorContext, size);
    }

    public void updateOffsets() {
        WailaBackgroundType backgroundStyle = ((WailaConfiguration) this.config).backgroundStyle.get();
        BackgroundConfig background = ((WailaConfiguration) this.config).background();
        boolean backgroundEnabled = background.enabled().get().booleanValue();
        float margin = backgroundEnabled ? background.getMargin() : 0.0f;
        float padding = Float.MIN_VALUE;
        if (backgroundStyle.hasRenderer()) {
            padding = backgroundStyle.defaultRenderer().getPadding();
        }
        if (padding == Float.MIN_VALUE) {
            padding = backgroundEnabled ? background.getPadding() : 0.0f;
        }
        this.padding = padding;
        this.margin = margin;
    }

    public float getMargin() {
        return this.margin;
    }

    public float getPadding() {
        return this.padding;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.wailaRegistry.isVisible();
    }

    @Subscribe
    public void onWailaUpdate(WailaUpdateEvent event) {
        requestUpdate(WAILA_UPDATE_REASON);
    }

    @Subscribe
    public void onBlockEntityPreLoad(BlockEntityPreLoadEvent event) {
        this.wailaRegistry.onBlockEntityPreLoad(event.blockEntity());
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        this.wailaRegistry.clear();
    }

    @Subscribe
    public void onDimensionChange(DimensionChangeEvent event) {
        this.wailaRegistry.clear();
    }

    @Subscribe
    public void onSubServerSwitch(SubServerSwitchEvent event) {
        this.wailaRegistry.clear();
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.wailaRegistry.clear();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaBackgroundType.class */
    public enum WailaBackgroundType {
        NONE,
        CUSTOM(WailaCustomBackgroundRenderer::new),
        TOOLTIP(WailaTooltipBackgroundRenderer::new);

        private final Function<HudWidgetWidget, WailaBackgroundRenderer> renderer;
        private WailaBackgroundRenderer instance;

        WailaBackgroundType(Function function) {
            this.renderer = function;
        }

        WailaBackgroundType() {
            this(null);
        }

        WailaBackgroundRenderer defaultRenderer() {
            if (this.instance == null) {
                this.instance = this.renderer.apply(null);
            }
            return this.instance;
        }

        WailaBackgroundRenderer createRenderer(HudWidgetWidget widget) {
            if (widget == null) {
                return defaultRenderer();
            }
            return this.renderer.apply(widget);
        }

        boolean hasRenderer() {
            return this.renderer != null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaConfiguration.class */
    public static class WailaConfiguration extends BackgroundHudWidget.BackgroundHudWidgetConfig {

        @DropdownWidget.DropdownSetting
        @DropdownWidget.DropdownEntryTranslationPrefix("labymod.hudWidget.waila.backgroundStyle.entries")
        @SettingOrder(10)
        private final ConfigProperty<WailaBackgroundType> backgroundStyle = ConfigProperty.createEnum(WailaBackgroundType.TOOLTIP);

        @SwitchWidget.SwitchSetting
        @SettingSection("block")
        private final ConfigProperty<Boolean> blockDestroyProgress = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> blockIcon = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> blockRequiredTool = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        @SettingRequires("blockRequiredTool")
        private final ConfigProperty<Boolean> blockRequiredToolForDrops = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> blockProperties = ConfigProperty.create(true);

        @DropdownWidget.DropdownSetting
        private final ConfigProperty<NoteFormatting> noteFormatting = ConfigProperty.create(NoteFormatting.PITCH);

        @SwitchWidget.SwitchSetting
        @SettingSection("entity")
        private final ConfigProperty<Boolean> entityIcon = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> entityHealth = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> entityVillagerProfession = ConfigProperty.create(true);

        @SwitchWidget.SwitchSetting
        @SettingSection("labymod")
        private final ConfigProperty<Boolean> labyModFriend = ConfigProperty.create(false);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> labyModGroup = ConfigProperty.create(false);

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaConfiguration$NoteFormatting.class */
        public enum NoteFormatting {
            PITCH,
            NUMBER
        }

        public ConfigProperty<Boolean> blockDestroyProgress() {
            return this.blockDestroyProgress;
        }

        public ConfigProperty<Boolean> blockIcon() {
            return this.blockIcon;
        }

        public ConfigProperty<Boolean> blockRequiredTool() {
            return this.blockRequiredTool;
        }

        public ConfigProperty<Boolean> blockRequiredToolForDrops() {
            return this.blockRequiredToolForDrops;
        }

        public ConfigProperty<Boolean> blockProperties() {
            return this.blockProperties;
        }

        public ConfigProperty<NoteFormatting> noteFormatting() {
            return this.noteFormatting;
        }

        public ConfigProperty<Boolean> entityIcon() {
            return this.entityIcon;
        }

        public ConfigProperty<Boolean> entityHealth() {
            return this.entityHealth;
        }

        public ConfigProperty<Boolean> entityVillagerProfession() {
            return this.entityVillagerProfession;
        }

        public ConfigProperty<Boolean> labyModFriend() {
            return this.labyModFriend;
        }

        public ConfigProperty<Boolean> labyModGroup() {
            return this.labyModGroup;
        }

        public ConfigProperty<WailaBackgroundType> backgroundStyle() {
            return this.backgroundStyle;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaBackgroundRenderer.class */
    static abstract class WailaBackgroundRenderer extends ThemeRenderer<WailaWidget> {
        protected final HudWidgetWidget widget;
        protected WailaConfiguration config;

        abstract void render(ScreenContext screenContext, float f, float f2);

        protected WailaBackgroundRenderer(String name, HudWidgetWidget widget) {
            super(name);
            this.widget = widget;
        }

        @Override // net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer, net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
        public void renderPre(WailaWidget widget, ScreenContext context) {
            super.renderPre(widget, context);
            if (this.widget == null) {
                return;
            }
            float innerPadding = 0.0f;
            if (getPadding() != Float.MIN_VALUE) {
                innerPadding = getPadding();
            }
            float offset = (widget.getOffset() * 2.0f) + (innerPadding * 2.0f);
            float width = this.widget.size().getActualWidth() - offset;
            float height = this.widget.size().getActualHeight() - offset;
            Stack stack = context.stack();
            stack.push();
            ReasonableMutableRectangle rectangle = widget.bounds().rectangle(BoundsType.MIDDLE);
            stack.translate(rectangle.getX() + innerPadding, rectangle.getY() + innerPadding, 0.0f);
            render(context, MathHelper.ceil(width), MathHelper.ceil(height));
            stack.pop();
        }

        float getPadding() {
            return Float.MIN_VALUE;
        }

        WailaBackgroundRenderer withConfig(WailaConfiguration config) {
            this.config = config;
            return this;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaTooltipBackgroundRenderer.class */
    static class WailaTooltipBackgroundRenderer extends WailaBackgroundRenderer {
        private final HoverBackgroundEffect renderer;

        WailaTooltipBackgroundRenderer(HudWidgetWidget widget) {
            super("waila-background-tooltip", widget);
            this.renderer = Laby.references().hoverBackgroundEffect(InjectionNames.VANILLA_HOVER_EFFECT);
        }

        @Override // net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget.WailaBackgroundRenderer
        public void render(ScreenContext context, float width, float height) {
            float alpha = this.renderPipeline.getAlpha();
            this.renderPipeline.setAlpha(0.9f);
            this.renderer.render(context, 1.0f, 1.0f, (width - 1.0f) - 1.0f, height - 1.0f);
            this.renderPipeline.setAlpha(alpha);
        }

        @Override // net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget.WailaBackgroundRenderer
        public float getPadding() {
            return this.renderer.getPadding() - 1.0f;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/survival/WailaHudWidget$WailaCustomBackgroundRenderer.class */
    static class WailaCustomBackgroundRenderer extends WailaBackgroundRenderer {
        protected WailaCustomBackgroundRenderer(HudWidgetWidget widget) {
            super("waila-background-custom", widget);
        }

        @Override // net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget.WailaBackgroundRenderer
        void render(ScreenContext context, float width, float height) {
            float radius;
            int color;
            if (this.config == null) {
                return;
            }
            BackgroundConfig background = this.config.background();
            if (background.enabled().get().booleanValue()) {
                radius = background.roundness().get().intValue();
                color = background.color().get().get();
            } else {
                radius = 0.0f;
                color = 2130706432;
            }
            context.canvas().submitRelativeRoundedRect(0.0f, 0.0f, width, height, color, RoundedData.builder().setRadius(radius).build());
        }
    }
}

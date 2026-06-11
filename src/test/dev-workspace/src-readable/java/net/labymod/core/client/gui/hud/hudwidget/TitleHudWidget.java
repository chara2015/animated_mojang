package net.labymod.core.client.gui.hud.hudwidget;

import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.util.parity.VanillaParityUtil;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/TitleHudWidget.class */
@SpriteSlot(x = 2, y = 5)
@IntroducedIn("4.1.0")
public class TitleHudWidget extends SimpleHudWidget<TitleHudWidgetConfig> {
    private static final Title DUMMY_TITLE = Title.builder().title(Component.text(Constants.Branding.NAME, NamedTextColor.BLUE)).subTitle(Component.translatable("labymod.hudWidget.title.dummy.subTitle", new Component[0])).fadeIn(50).stay(100).fadeOut(50).build();
    private static final TitleHandler DUMMY_TITLE_HANDLER = new TitleHandler(DUMMY_TITLE, true);
    private TitleHandler titleHandler;

    public TitleHudWidget() {
        super("title", TitleHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.TITLE);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (this.titleHandler != null) {
            this.titleHandler.onTick();
        }
        Title title = getTitle();
        if (title == null) {
            this.titleHandler = null;
        } else {
            if (this.titleHandler != null && Objects.equals(this.titleHandler.title(), title)) {
                return;
            }
            this.titleHandler = new TitleHandler(title, false);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        int opacity;
        TitleHandler handler = isEditorContext ? DUMMY_TITLE_HANDLER : this.titleHandler;
        if (handler == null || !handler.hasTitle() || (opacity = handler.calculateOpacity(context.getTickDelta())) <= 8) {
            return;
        }
        ScreenCanvas renderState = context.canvas();
        ComponentRenderer componentRenderer = Laby.references().componentRenderer();
        Title title = handler.title();
        Component titleComponent = title.getTitle();
        Component subTitleComponent = title.getSubTitle();
        float titleScale = ((TitleHudWidgetConfig) this.config).titleScale().get().floatValue();
        float subTitleScale = ((TitleHudWidgetConfig) this.config).subTitleScale().get().floatValue();
        float lineSpacing = ((TitleHudWidgetConfig) this.config).lineSpacing().get().floatValue();
        float titleWidth = getComponentWidth(componentRenderer, titleComponent) * titleScale;
        float subTitleWidth = getComponentWidth(componentRenderer, subTitleComponent) * subTitleScale;
        float maxWidth = Math.max(titleWidth, subTitleWidth) + (2.0f * 2.0f);
        float titleHeight = (componentRenderer.height() + (2.0f * 2.0f)) * titleScale;
        float subTitleHeight = (componentRenderer.height() + (2.0f * 2.0f)) * subTitleScale;
        float titlePadding = 2.0f * titleScale;
        float subTitlePadding = 2.0f * subTitleScale;
        if (phase.canRender()) {
            context.pushStack();
            context.translate(0.0f, 0.0f, VanillaParityUtil.getTitlesOverlayZLevel());
            float titleOffsetX = this.anchor.getGapX(maxWidth, titleWidth + (titlePadding * 2.0f));
            int finalColor = 16777215 | ((opacity << 24) & (-16777216));
            drawBackground(context, titleOffsetX, 0.0f, titleWidth + (titlePadding * 2.0f), titleHeight);
            renderState.submitComponent(titleComponent, titleOffsetX + titlePadding, titlePadding, finalColor, titleScale, 5);
            if (subTitleComponent != null) {
                float subTitleOffsetX = this.anchor.getGapX(maxWidth, subTitleWidth + (subTitlePadding * 2.0f));
                drawBackground(context, subTitleOffsetX, ((titleHeight - titlePadding) + lineSpacing) - subTitlePadding, subTitleWidth + (subTitlePadding * 2.0f), subTitleHeight);
                renderState.submitComponent(subTitleComponent, subTitleOffsetX + subTitlePadding, (titleHeight - titlePadding) + lineSpacing, finalColor, subTitleScale, 5);
            }
            context.popStack();
        }
        size.set(maxWidth, (((titleHeight - titlePadding) + lineSpacing) + subTitleHeight) - subTitlePadding);
    }

    @Subscribe
    public void onIngameOverlayElementRender(IngameOverlayElementRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE || event.elementType() != IngameOverlayElementRenderEvent.OverlayElementType.TITLE) {
            return;
        }
        event.setCancelled(isVisibleInGame());
    }

    private void drawBackground(ScreenContext context, float x, float y, float width, float height) {
        int backgroundColor = this.labyAPI.minecraft().options().getBackgroundColorWithOpacity(0);
        if (backgroundColor == 0) {
            return;
        }
        context.canvas().submitRelativeRect(x, y, width, height, backgroundColor);
    }

    private float getComponentWidth(ComponentRenderer componentRenderer, Component component) {
        if (component == null) {
            return 0.0f;
        }
        return componentRenderer.width(component);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return getTitle() != null;
    }

    @Nullable
    private Title getTitle() {
        return this.labyAPI.minecraft().getTitle();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean renderInDebug() {
        return true;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/TitleHudWidget$TitleHudWidgetConfig.class */
    public static class TitleHudWidgetConfig extends HudWidgetConfig {

        @SliderWidget.SliderSetting(min = 1.0f, max = 8.0f, steps = 0.5f)
        private final ConfigProperty<Float> titleScale = new ConfigProperty<>(Float.valueOf(4.0f));

        @SliderWidget.SliderSetting(min = 1.0f, max = 8.0f, steps = 0.5f)
        private final ConfigProperty<Float> subTitleScale = new ConfigProperty<>(Float.valueOf(2.0f));

        @SliderWidget.SliderSetting(min = 0.0f, max = 40.0f)
        private final ConfigProperty<Float> lineSpacing = new ConfigProperty<>(Float.valueOf(14.0f));

        public ConfigProperty<Float> titleScale() {
            return this.titleScale;
        }

        public ConfigProperty<Float> subTitleScale() {
            return this.subTitleScale;
        }

        public ConfigProperty<Float> lineSpacing() {
            return this.lineSpacing;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/TitleHudWidget$TitleHandler.class */
    private static class TitleHandler {
        private final boolean dummy;
        private Title title;
        private int titleTime;
        private int titleFadeInTime;
        private int titleStayTime;
        private int titleFadeOutTime;

        public TitleHandler(Title title, boolean dummy) {
            this.title = title;
            this.dummy = dummy;
            setTimes(title);
        }

        protected void onTick() {
            decrementTitleTime();
        }

        protected boolean hasTitle() {
            return this.dummy || (this.title != null && this.titleTime > 0);
        }

        protected int calculateOpacity(float partialTicks) {
            if (this.dummy) {
                return 255;
            }
            float timePassed = this.titleTime - partialTicks;
            int opacity = 255;
            if (this.titleTime > this.titleFadeOutTime + this.titleStayTime) {
                float timeTotal = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
                float temp = timeTotal - timePassed;
                opacity = (int) ((temp * 255.0f) / this.titleFadeOutTime);
            }
            if (this.titleTime <= this.titleFadeOutTime) {
                opacity = (int) ((timePassed * 255.0f) / this.titleFadeOutTime);
            }
            return MathHelper.clamp(opacity, 0, 255);
        }

        private void decrementTitleTime() {
            if (this.titleTime <= 0) {
                return;
            }
            this.titleTime--;
            if (this.titleTime == 0) {
                this.title = null;
            }
        }

        private void setTimes(Title title) {
            setTimes(title.getFadeInTicks(), title.getStayTicks(), title.getFadeOutTicks());
        }

        private void setTimes(int fadeInTime, int stayTime, int fadeOutTime) {
            if (fadeInTime >= 0) {
                this.titleFadeInTime = fadeInTime;
            }
            if (stayTime >= 0) {
                this.titleStayTime = stayTime;
            }
            if (fadeOutTime >= 0) {
                this.titleFadeOutTime = fadeOutTime;
            }
            this.titleTime = this.titleFadeInTime + this.titleStayTime + this.titleFadeOutTime;
        }

        public Title title() {
            return this.title;
        }
    }
}

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.util.parity.VanillaParityUtil;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ActionBarReceiveEvent;
import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ActionBarHudWidget.class */
@SpriteSlot(x = 3, y = 5)
@IntroducedIn("4.1.0")
public class ActionBarHudWidget extends SimpleHudWidget<HudWidgetConfig> {
    private static final ActionBarHolder COOKIES_ARE_DELICIOUS = new ActionBarHolder(Component.translatable("labymod.hudWidget.action_bar.dummy", new Component[0]), false);
    private ActionBarHolder actionBarHolder;

    public ActionBarHudWidget() {
        super("action_bar", HudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.ACTION_BAR);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        if (this.actionBarHolder != null) {
            this.actionBarHolder.onTick();
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        ActionBarHolder holder = isEditorContext ? COOKIES_ARE_DELICIOUS : this.actionBarHolder;
        if (holder == null || !holder.hasMessage()) {
            return;
        }
        ScreenCanvas renderState = context.canvas();
        ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
        Component message = holder.getMessage();
        float width = componentRenderer.width(message);
        float height = componentRenderer.height();
        if (phase.canRender()) {
            context.pushStack();
            context.translate(0.0f, 0.0f, VanillaParityUtil.getActionBarZLevel());
            float timePassed = holder.getOverlayMessageTime() - context.getTickDelta();
            int opacity = (int) ((timePassed * 255.0f) / 20.0f);
            if (opacity > 255) {
                opacity = 255;
            }
            if (opacity > 8) {
                int color = -1;
                if (holder.isAnimatedMessage()) {
                    color = ColorUtil.hsvToRgb(timePassed / 50.0f, 0.7f, 0.6f);
                }
                drawBackground(context, 0.0f, 0.0f, width + (2.0f * 2.0f), height + (2.0f * 2.0f));
                renderState.submitComponent(message, 2.0f, 2.0f, color | ((opacity << 24) & (-16777216)), 1);
            }
            context.popStack();
        }
        size.set(width + (2.0f * 2.0f), height + (2.0f * 2.0f));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.actionBarHolder != null && this.actionBarHolder.hasMessage();
    }

    @Subscribe
    public void onActionBarReceive(ActionBarReceiveEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        this.actionBarHolder = new ActionBarHolder(event.getMessage(), event.isAnimatedMessage());
        event.setCancelled(true);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean renderInDebug() {
        return true;
    }

    private void drawBackground(ScreenContext context, float x, float y, float width, float height) {
        int backgroundColor = this.labyAPI.minecraft().options().getBackgroundColorWithOpacity(0);
        if (backgroundColor == 0) {
            return;
        }
        context.canvas().submitRelativeRect(x, y, width, height, backgroundColor);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ActionBarHudWidget$ActionBarHolder.class */
    private static class ActionBarHolder {
        private final Component message;
        private final boolean animatedMessage;
        private int overlayMessageTime = 60;

        public ActionBarHolder(Component message, boolean animatedMessage) {
            this.message = message;
            this.animatedMessage = animatedMessage;
        }

        public void onTick() {
            if (this.overlayMessageTime > 0) {
                this.overlayMessageTime--;
            }
        }

        public boolean hasMessage() {
            return getMessage() != null && this.overlayMessageTime > 0;
        }

        @Nullable
        public Component getMessage() {
            return this.message;
        }

        public boolean isAnimatedMessage() {
            return this.animatedMessage;
        }

        public int getOverlayMessageTime() {
            return this.overlayMessageTime;
        }
    }
}

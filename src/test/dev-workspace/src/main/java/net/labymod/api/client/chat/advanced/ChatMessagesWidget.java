package net.labymod.api.client.chat.advanced;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/advanced/ChatMessagesWidget.class */
@AutoWidget
public class ChatMessagesWidget extends AbstractWidget<AbstractWidget<?>> {
    private static final Logging LOGGER = Logging.getLogger();
    private static final ModifyReason UPDATE_BUTTON = ModifyReason.of("updateButton");
    private static final Lazy<AdvancedChatConfig> SETTINGS = Lazy.of(() -> {
        return Laby.labyAPI().config().ingame().advancedChat();
    });
    private static final int SCROLLBAR_WIDTH = 4;
    private static final int MULTI_LINE_INDENT = 4;
    private final IngameChatTab tab;
    private final WindowAccessor window;
    private final ChatPrefixRegistry prefixRegistry;
    private ButtonWidget menuButtonWidget;
    private ComponentRenderMeta lastHoveredComponentMeta;
    private int lineOffset;
    private int counterBeforeScroll;
    private int lastTotalLines = 0;

    public ChatMessagesWidget(IngameChatTab tab, WindowAccessor window) {
        this.tab = tab;
        this.window = window;
        this.prefixRegistry = window.chat().getProvider().prefixRegistry();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (SETTINGS.get().showMenuButton().get().booleanValue()) {
            this.menuButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER);
            this.menuButtonWidget.addId("context");
            this.menuButtonWidget.setPressable(() -> {
                ((AbstractWidget) this.window).openContextMenu();
            });
            addChild(this.menuButtonWidget);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (this.tab.getMessages().isEmpty() && !this.window.chat().isChatOpen()) {
            return;
        }
        keepScrollPosition();
        this.lastHoveredComponentMeta = null;
        renderMessages(context);
        renderScrollbar(context);
        super.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        super.renderHoverComponent(context);
        if (this.lastHoveredComponentMeta != null) {
            this.lastHoveredComponentMeta.renderHover(context.stack(), context.mouse());
        }
    }

    private void renderMessages(ScreenContext context) {
        int lines = Math.min(getMaxLinesVisible(), getTotalLines());
        Stack stack = context.stack();
        stack.push();
        int lineIndex = 0;
        List<AdvancedChatMessage> messages = this.tab.getMessages();
        for (AdvancedChatMessage message : messages) {
            if (message != null && message.isVisible()) {
                lineIndex += renderMessage(context, message, lineIndex);
            }
        }
        stack.pop();
        int chatHeight = lines * getLineHeight(this.labyAPI.minecraft().options());
        updateMenuButton(chatHeight);
        if (this.window.hasTitleBar() && this.window.chat().isChatOpen()) {
            renderBackgroundGap(context, chatHeight);
        }
    }

    private int renderMessage(ScreenContext context, AdvancedChatMessage message, int lineIndex) {
        float fCurve;
        MinecraftOptions options = this.labyAPI.minecraft().options();
        AdvancedChatConfig advancedChat = this.labyAPI.config().ingame().advancedChat();
        boolean legacyMessageOffset = advancedChat.legacyMessageOffset().get().booleanValue();
        Bounds bounds = bounds();
        double scale = options.getChatScale();
        int prefixWidth = getPrefixWidth(message, scale);
        int textWidth = getLineWidth(prefixWidth, scale);
        int lineHeight = getLineHeight(options);
        boolean hasPrefix = prefixWidth != 4;
        RenderableComponent[] renderableComponents = message.getRenderableComponents(textWidth);
        int totalLines = 0;
        for (RenderableComponent renderableComponent : renderableComponents) {
            totalLines += renderableComponent.getLines();
        }
        int chatBottom = (int) bounds.getBottom();
        int chatHeight = (int) bounds.getHeight();
        int messageX = (int) bounds.getX();
        int messageY = chatBottom + (((this.lineOffset - totalLines) - lineIndex) * lineHeight);
        float textOffset = MathHelper.ceil(((double) (lineHeight / 2.0f)) - (4.0d * scale));
        long duration = TimeUtil.getMillis() - message.timestamp();
        boolean fadeInMessages = advancedChat.fadeInMessages().get().booleanValue() && !isScrolled();
        if (fadeInMessages) {
            fCurve = (float) CubicBezier.EASE_OUT.curve(Math.min(1.0f, duration / 100.0f));
        } else {
            fCurve = 1.0f;
        }
        float fadeInFactor = fCurve;
        context.translate(0.0f, lineHeight * (1.0f - fadeInFactor), 0.0f);
        boolean chatOpen = this.window.chat().isChatOpen();
        int counter = (int) TimeUtil.convertMillisecondsToTicks(duration);
        double opacity = MathHelper.clamp((1.0d - ((double) (counter / 200.0f))) * 10.0d, 0.0d, 1.0d);
        int alpha = (int) ((chatOpen ? 255.0d : Math.floor(255.0d * opacity * opacity)) * ((double) fadeInFactor));
        double backgroundOpacity = getBackgroundOpacity(options);
        int backgroundColor = getBackgroundColor(message, alpha, backgroundOpacity);
        double textOpacity = options.getChatTextOpacity();
        int textAlpha = (int) (((double) alpha) * textOpacity);
        int textColor = ((textAlpha & 255) << 24) | 16777215;
        int handledLines = 0;
        int mainLine = 0;
        while (mainLine < renderableComponents.length) {
            RenderableComponent renderableComponent2 = renderableComponents[mainLine];
            int renderedSubLines = 0;
            int subLines = renderableComponent2.getLines();
            int subLineTextOffset = (int) Math.floor(((subLines * lineHeight) - lineHeight) / 2.0f);
            for (int subLine = 0; subLine < subLines; subLine++) {
                int lineY = messageY + (lineHeight * (mainLine + subLine));
                handledLines++;
                if ((chatOpen || lineY >= chatBottom - options.getChatHeightClosed()) && lineY >= chatBottom - chatHeight && lineY + lineHeight <= chatBottom && (counter < 200 || chatOpen)) {
                    renderLineBackground(context, messageX, lineY, lineHeight, backgroundColor);
                    renderChatPrefixes(context, message, messageX, lineY, renderableComponents, mainLine, subLine, lineHeight, textOffset, subLineTextOffset, scale, alpha);
                    renderedSubLines++;
                }
            }
            boolean allSubLinesVisible = renderedSubLines == subLines;
            if (allSubLinesVisible) {
                boolean firstLine = mainLine == 0;
                renderLineText(context, renderableComponent2, ((messageX + prefixWidth) + (legacyMessageOffset ? 1 : 2)) - ((firstLine || hasPrefix) ? 4 : 0), messageY + (lineHeight * mainLine) + textOffset, scale, textColor);
            }
            mainLine++;
        }
        return handledLines;
    }

    private void renderChatPrefixes(ScreenContext context, AdvancedChatMessage message, int messageX, int lineY, RenderableComponent[] renderableComponents, int mainLine, int subLine, int lineHeight, float textOffset, int subLineTextOffset, double scale, int alpha) {
        List<String> queuedForRemovalIds = null;
        int gapIndex = 0;
        for (KeyValue<ChatPrefix> entry : this.prefixRegistry.getElements()) {
            try {
                ChatPrefix prefix = entry.getValue();
                if (prefix.isVisible(this.tab, message)) {
                    if (gapIndex == 1) {
                        messageX++;
                    }
                    prefix.render(context, messageX, lineY, message, renderableComponents, mainLine, subLine, lineHeight, textOffset + subLineTextOffset, scale, alpha);
                    int width = prefix.getWidth(message, scale);
                    messageX += width;
                    if (width > 0) {
                        gapIndex++;
                    }
                }
            } catch (Throwable th) {
                if (queuedForRemovalIds == null) {
                    queuedForRemovalIds = new ArrayList<>();
                }
                queuedForRemovalIds.add(entry.getKey());
            }
        }
        if (queuedForRemovalIds != null) {
            for (String queuedForRemovalId : queuedForRemovalIds) {
                this.prefixRegistry.unregister(queuedForRemovalId);
                LOGGER.error("ChatPrefix '{}' was removed from registry from the registry due to an error", queuedForRemovalId);
            }
        }
    }

    private void renderLineBackground(ScreenContext context, int x, int y, int lineHeight, int backgroundColor) {
        context.canvas().submitRelativeRect(x, y, ((int) bounds().getRight()) - x, lineHeight, backgroundColor);
    }

    private void renderLineText(ScreenContext context, RenderableComponent renderableComponent, float x, float y, double scale, int textColor) {
        ComponentRenderMeta meta = this.labyAPI.renderPipeline().componentRenderer().builder().pos(x, y).text(renderableComponent).scale((float) scale).shadow(this.tab.config().shadow().get().booleanValue()).useFloatingPointPosition(false).color(textColor, false).render(context);
        MutableMouse mouse = context.mouse();
        if (mouse != null && meta.isInRectangle(mouse)) {
            this.lastHoveredComponentMeta = meta;
        }
    }

    private void renderBackgroundGap(ScreenContext context, int chatHeight) {
        MinecraftOptions options = this.labyAPI.minecraft().options();
        double backgroundOpacity = getBackgroundOpacity(options);
        Bounds bounds = bounds();
        context.canvas().submitAbsoluteRect((int) bounds.getLeft(), (int) bounds.getTop(), (int) bounds.getRight(), ((int) bounds.getBottom()) - chatHeight, ColorFormat.ARGB32.pack(0, (int) (255.0d * backgroundOpacity)));
    }

    private void updateMenuButton(int chatHeight) {
        if (this.menuButtonWidget == null) {
            return;
        }
        boolean hasMessages = chatHeight > 0;
        boolean chatOpen = this.window.chat().isChatOpen();
        boolean visible = chatOpen && !this.window.hasTitleBar() && hasMessages;
        this.menuButtonWidget.setVisible(visible);
        if (hasMessages) {
            Bounds buttonBounds = this.menuButtonWidget.bounds();
            buttonBounds.setBottomY(bounds().getBottom() - chatHeight, BoundsType.OUTER, UPDATE_BUTTON);
        }
    }

    private void renderScrollbar(ScreenContext context) {
        int totalLines = getTotalLines();
        int visibleLines = getMaxLinesVisible();
        if (this.window.chat().isChatOpen() && totalLines > visibleLines) {
            ScreenCanvas renderState = context.canvas();
            MinecraftOptions options = this.labyAPI.minecraft().options();
            int lineHeight = getLineHeight(options);
            int invisibleLines = totalLines - visibleLines;
            int visibleHeight = visibleLines * lineHeight;
            int scrollBarHeight = (int) (((double) visibleHeight) * (((double) visibleLines) / ((double) totalLines)));
            double percentage = ((double) this.lineOffset) / ((double) invisibleLines);
            double scrollBarOffset = percentage * ((double) (visibleHeight - scrollBarHeight));
            int scrollBarX = ((int) bounds().getRight()) - 4;
            int scrollBarY = ((int) (((double) bounds().getBottom()) - scrollBarOffset)) - scrollBarHeight;
            boolean isScrolled = isScrolled();
            boolean newMessagesSinceScroll = this.counterBeforeScroll != this.tab.getCounter() && isScrolled;
            int opacity = isScrolled ? 170 : 96;
            int color = newMessagesSinceScroll ? 13382451 : 3355562;
            boolean legacyScrollbar = this.labyAPI.config().ingame().advancedChat().legacyScrollbar().get().booleanValue();
            if (!legacyScrollbar) {
                renderState.submitRelativeRect(scrollBarX, scrollBarY, 2.0f, scrollBarHeight, color + (opacity << 24));
            }
            renderState.submitRelativeRect(scrollBarX + (legacyScrollbar ? 3 : 1), scrollBarY, 1.0f, scrollBarHeight, legacyScrollbar ? -1 : 13421772 + (opacity << 24));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (this.lastHoveredComponentMeta != null && this.lastHoveredComponentMeta.interact()) {
            return true;
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if (!isScrolled()) {
            this.counterBeforeScroll = this.tab.getCounter();
        }
        boolean holdingShift = this.labyAPI.minecraft().isKeyPressed(Key.L_SHIFT);
        this.lineOffset += (scrollDelta > 0.0d ? 1 : -1) * (holdingShift ? 1 : 7);
        clampLineOffset();
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        MinecraftOptions options = this.labyAPI.minecraft().options();
        return Math.min(getTotalLines(), getMaxLinesVisible()) * getLineHeight(options);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        clampLineOffset();
    }

    private void clampLineOffset() {
        int totalLines = getTotalLines();
        int maxLinesVisible = getMaxLinesVisible();
        this.lineOffset = MathHelper.clamp(this.lineOffset, 0, Math.max(totalLines - maxLinesVisible, 0));
    }

    private void keepScrollPosition() {
        int totalLines = getTotalLines();
        if (this.lastTotalLines != totalLines) {
            if (this.lineOffset != 0) {
                this.lineOffset += totalLines - this.lastTotalLines;
                clampLineOffset();
            }
            this.lastTotalLines = totalLines;
        }
    }

    private int getTotalLines() {
        double scale = this.labyAPI.minecraft().options().getChatScale();
        int totalLines = 0;
        for (AdvancedChatMessage message : this.tab.getMessages()) {
            if (message.isVisible()) {
                int width = getLineWidth(message, scale);
                RenderableComponent[] renderableComponents = message.getRenderableComponents(width);
                for (RenderableComponent renderableComponent : renderableComponents) {
                    totalLines += renderableComponent.getLines();
                }
            }
        }
        return totalLines;
    }

    private int getMaxLinesVisible() {
        MinecraftOptions options = this.labyAPI.minecraft().options();
        int lineHeight = getLineHeight(options);
        if (lineHeight == 0) {
            return 0;
        }
        return ((int) bounds().getHeight()) / lineHeight;
    }

    private int getPrefixWidth(AdvancedChatMessage message, double scale) {
        int gapIndex = 0;
        int width = 0;
        for (KeyValue<ChatPrefix> entry : this.prefixRegistry.getElements()) {
            ChatPrefix prefix = entry.getValue();
            if (prefix.isVisible(this.tab, message)) {
                if (gapIndex == 1) {
                    gapIndex++;
                }
                int lineWidth = prefix.getWidth(message, scale);
                width += lineWidth;
                if (lineWidth > 0) {
                    gapIndex++;
                }
            }
        }
        return width + 4;
    }

    private int getLineWidth(int prefixWidth, double scale) {
        float width = (bounds().getWidth() - prefixWidth) - 4.0f;
        return MathHelper.ceil(((double) width) / scale);
    }

    private int getLineWidth(AdvancedChatMessage message, double scale) {
        return getLineWidth(getPrefixWidth(message, scale), scale);
    }

    private int getLineHeight(MinecraftOptions options) {
        float height = Laby.references().textRendererProvider().getRenderer().getLineHeight();
        int lineHeight = (int) (((double) height) * (options.getChatLineSpacing() + 1.0d));
        return MathHelper.ceil(((double) lineHeight) * options.getChatScale());
    }

    private int getBackgroundColor(AdvancedChatMessage message, int alpha, double backgroundOpacity) {
        Object value = message.metadata().get(IngameChatTab.CUSTOM_BACKGROUND);
        boolean hasBackgroundColor = value instanceof Integer;
        boolean isBackgroundDisabled = backgroundOpacity == 0.0d;
        int color = hasBackgroundColor ? ((Integer) value).intValue() : 0;
        if (hasBackgroundColor && isBackgroundDisabled) {
            backgroundOpacity = 0.5d;
        }
        int backgroundAlpha = (int) (((double) alpha) * backgroundOpacity);
        return ((backgroundAlpha & 255) << 24) | (color & 16777215);
    }

    private double getBackgroundOpacity(MinecraftOptions options) {
        boolean backgroundVisible = this.tab.config().background().get().booleanValue();
        if (backgroundVisible) {
            return options.getTextBackgroundOpacity();
        }
        return 0.0d;
    }

    private boolean isScrolled() {
        return this.lineOffset != 0;
    }
}

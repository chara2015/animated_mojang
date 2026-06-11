package net.labymod.api.client.gui.hud.hudwidget.text;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.render.font.RenderableComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/text/TextHudWidget.class */
public class TextHudWidget<T extends TextHudWidgetConfig> extends BackgroundHudWidget<T> {
    private final String label;
    protected List<TextLine> lines;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/text/TextHudWidget$TextLineSupplier.class */
    protected interface TextLineSupplier {
        TextLine get(TextHudWidget<?> textHudWidget, Component component, Object obj);
    }

    public TextHudWidget(String id) {
        this(id, "label", TextHudWidgetConfig.class);
    }

    public TextHudWidget(String id, Class<T> configClass) {
        this(id, "label", configClass);
    }

    public TextHudWidget(String id, String label) {
        this(id, label, TextHudWidgetConfig.class);
    }

    public TextHudWidget(String id, String label, Class<T> configClass) {
        super(id, configClass);
        this.lines = new ArrayList();
        this.label = label;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(T config) {
        this.lines.clear();
        super.load(config);
        flushAll();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void reloadConfig() {
        super.reloadConfig();
        flushAll();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onUpdate() {
        super.onUpdate();
        flushAll();
        boolean floatingPointPosition = ((Boolean) Laby.references().themeService().currentTheme().metadata().get(DefaultThemeVariables.HUD_WIDGET_FLOATING_POINT_POSITION, false)).booleanValue();
        for (TextLine line : this.lines) {
            line.setFloatingPointPosition(floatingPointPosition);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget, net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        if (phase.canRender()) {
            super.render(phase, context, isEditorContext, size);
            renderLines(context, isEditorContext, size);
        } else {
            updateSize(isEditorContext, size);
            updateTextContent();
        }
    }

    public void updateTextContent() {
    }

    private void renderLines(ScreenContext context, boolean isEditorContext, HudSize size) {
        float space = getTotalSpace();
        float widthWithoutSpace = size.getActualWidth() - (space * 2.0f);
        float lineHeightPercentage = ((TextHudWidgetConfig) this.config).lineHeight().get().intValue() / 100.0f;
        context.pushStack();
        context.translate(space, space, 0.0f);
        float y = 0.0f;
        for (TextLine line : this.lines) {
            RenderableComponent renderableComponent = line.getRenderableComponent();
            if (isLineVisible(line, isEditorContext) && renderableComponent != null) {
                float offsetX = this.anchor.getGapX(widthWithoutSpace, renderableComponent.getWidth());
                float lineHeight = renderableComponent.getHeight() * lineHeightPercentage;
                float lineHeightDelta = lineHeight - renderableComponent.getHeight();
                line.renderLine(context, offsetX, y + (lineHeightDelta / 2.0f), space, size);
                y += renderableComponent.getHeight() * lineHeightPercentage;
            }
        }
        context.popStack();
    }

    private void updateSize(boolean isEditorContext, HudSize size) {
        float maxWidth = 0.0f;
        float height = 0.0f;
        float lineHeightPercentage = ((TextHudWidgetConfig) this.config).lineHeight().get().intValue() / 100.0f;
        for (TextLine line : this.lines) {
            RenderableComponent component = line.getRenderableComponent();
            if (isLineVisible(line, isEditorContext) && component != null) {
                maxWidth = (int) Math.max(maxWidth, line.getWidth());
                height += line.getHeight() * lineHeightPercentage;
            }
        }
        float space = getTotalSpace();
        size.set(maxWidth + (space * 2.0f), height + (space * 2.0f));
    }

    private float getTotalSpace() {
        BackgroundConfig backgroundConfig = ((TextHudWidgetConfig) this.config).background();
        return backgroundConfig.getPadding() + backgroundConfig.getMargin();
    }

    private boolean isLineVisible(TextLine line, boolean isEditorContext) {
        return line.state() != TextLine.State.DISABLED && (line.state() != TextLine.State.HIDDEN || isEditorContext);
    }

    protected TextLine createLine(Object value) {
        return createLine(this.label, value);
    }

    protected TextLine createLine(String key, Object value) {
        return createLine(Component.text(key), value);
    }

    protected <K extends TextLine> K createLine(String str, Object obj, TextLineSupplier textLineSupplier) {
        return (K) createLine(Component.text(str), obj, textLineSupplier);
    }

    protected TextLine createLine(Component key, Object value) {
        return createLine(key, value, TextLine::new);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    protected <K extends TextLine> K createLine(Component component, Object obj, TextLineSupplier textLineSupplier) {
        if (this.config == 0) {
            throw new RuntimeException("You can't create a line before the config is loaded! Use the load method!");
        }
        String str = ((TextHudWidgetConfig) this.config).customLabel().get();
        if (!str.isEmpty()) {
            int size = this.lines.size();
            String[] strArrSplit = str.split(";");
            if (size < strArrSplit.length) {
                component = Component.text(strArrSplit[size]);
            }
        }
        K k = (K) textLineSupplier.get(this, component, obj);
        this.lines.add(k);
        return k;
    }

    @Deprecated
    protected void flushAll() {
        for (TextLine line : this.lines) {
            line.flush();
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        int visibleLines = 0;
        for (TextLine line : this.lines) {
            if (line.state() == TextLine.State.VISIBLE) {
                visibleLines++;
            }
        }
        return this.lines.isEmpty() || visibleLines > 0;
    }

    @Deprecated
    protected boolean displayInvisibleLinesInEditor() {
        throw new RuntimeException("Method is deprecated");
    }
}

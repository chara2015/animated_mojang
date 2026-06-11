package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/widgets/SectionedListWidget.class */
@AutoWidget
public class SectionedListWidget<T extends ListWidget<?>, I> extends SessionedListWidget<Widget> {
    private static final ModifyReason ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    private Runnable onFocusedSectionChanged;
    private I focusedSection;
    private final LssProperty<Float> spaceBetweenEntries = new LssProperty<>(Float.valueOf(1.0f));
    private boolean manualTarget = false;
    private I manualTargetIdentifier = null;
    private long lastManualTargetChanged = 0;

    public SectionedListWidget() {
        translateY().addChangeListener(newValue -> {
            updateVisibility(this, this.parent);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        super.updateContentBounds();
        updateChildren(true);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return updateChildren(false) + bounds().getVerticalOffset(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return bounds().getWidth(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget
    public void updateVisibility(ListWidget<?> widget, Parent parent) {
        super.updateVisibility(widget, parent);
        long timePassedSinceManualTargetChanged = TimeUtil.getMillis() - this.lastManualTargetChanged;
        if (this.manualTarget || timePassedSinceManualTargetChanged < 100) {
            this.manualTarget = false;
            return;
        }
        float offset = -widget.getTranslateY();
        SectionWidget<I> section = null;
        List<?> widgetChildren = widget.getChildren();
        int i = widgetChildren.size() - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            Widget childWidget = (Widget) widgetChildren.get(i);
            if (childWidget instanceof SectionWidget) {
                SectionWidget<?> sectionWidget = (SectionWidget) childWidget;
                Bounds bounds = sectionWidget.bounds();
                if (offset > bounds.getY() - 20.0f) {
                    section = (SectionWidget) childWidget;
                    break;
                }
            }
            i--;
        }
        if (section == null) {
            this.focusedSection = null;
        } else {
            updateSection(section.getIdentifier());
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.manualTargetIdentifier != null) {
            setFocusedSection(this.manualTargetIdentifier, false);
        }
    }

    private void updateSection(I newSection) {
        if (Objects.equals(this.focusedSection, newSection)) {
            return;
        }
        this.focusedSection = newSection;
        if (this.onFocusedSectionChanged != null) {
            this.onFocusedSectionChanged.run();
        }
        if (!this.children.isEmpty()) {
            Widget lastWidget = (Widget) this.children.get(this.children.size() - 1);
            float requiredOverflow = (this.parent.bounds().getHeight() - lastWidget.bounds().getHeight()) - 40.0f;
            marginBottom().set(Float.valueOf(Math.max(requiredOverflow, 0.0f)));
        }
    }

    public SectionedListWidget<T, I> onFocusedSectionChanged(Runnable runnable) {
        this.onFocusedSectionChanged = runnable;
        return this;
    }

    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }

    public void addSection(@NotNull Component component, @NotNull I identifier, @NotNull T widget) {
        if (widget.getChildren().isEmpty()) {
            return;
        }
        addChild(new SectionWidget(component, identifier));
        addChild(widget);
    }

    public void forEachSection(BiConsumer<Component, T> biConsumer) {
        for (T child : this.children) {
            if (child instanceof SectionWidget) {
                SectionWidget<T> section = (SectionWidget) child;
                biConsumer.accept(section.getComponent(), section.getIdentifier());
            }
        }
    }

    public I getFocusedSection() {
        return this.focusedSection;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void setFocusedSection(I identifier, boolean retry) {
        this.manualTarget = true;
        this.lastManualTargetChanged = TimeUtil.getMillis();
        SectionWidget<I> section = null;
        Iterator it = this.children.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Widget child = (Widget) it.next();
            if (child instanceof SectionWidget) {
                SectionWidget<I> currentSection = (SectionWidget) child;
                if (Objects.equals(currentSection.getIdentifier(), identifier)) {
                    section = currentSection;
                    break;
                }
            }
        }
        if (section == null) {
            if (retry) {
                this.manualTargetIdentifier = identifier;
            }
        } else {
            getParent().bounds().rectangle(BoundsType.MIDDLE);
            boolean lowerHalf = this.session.getScrollPositionY() > this.session.maxScrollPositionY() / 2.0f;
            float target = lowerHalf ? ((SectionWidget) section).translationY : ((SectionWidget) section).translationY;
            this.manualTargetIdentifier = null;
            this.session.setScrollPositionY(target);
            updateSection(section.getIdentifier());
        }
    }

    protected float updateChildren(boolean setBounds) {
        float spaceBetweenEntries = this.spaceBetweenEntries.get().floatValue();
        Bounds bounds = bounds();
        float width = bounds.getWidth();
        float x = bounds.getX();
        float y = bounds.getY();
        float height = 0.0f;
        SectionWidget<?> currentSection = null;
        for (T child : this.children) {
            if (child.isVisible()) {
                Bounds childBounds = child.bounds();
                if (setBounds) {
                    childBounds.setOuterPosition(x + childBounds.getHorizontalOffset(BoundsType.OUTER), y + height + childBounds.getVerticalOffset(BoundsType.OUTER), ENTRY_BOUNDS);
                    childBounds.setOuterWidth(width, ENTRY_BOUNDS);
                }
                if (child instanceof SectionWidget) {
                    SectionWidget<?> sectionWidget = (SectionWidget) child;
                    if (currentSection != null && ((SectionWidget) currentSection).sectionHeight != 0.0f) {
                        ((SectionWidget) currentSection).sectionHeight -= spaceBetweenEntries;
                    }
                    ((SectionWidget) sectionWidget).translationY = height;
                    ((SectionWidget) sectionWidget).sectionHeight = 0.0f;
                    currentSection = sectionWidget;
                }
                height += childBounds.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
                if (currentSection != null) {
                    ((SectionWidget) currentSection).sectionHeight += childBounds.getHeight(BoundsType.OUTER) + spaceBetweenEntries;
                }
            }
        }
        if (height != 0.0f) {
            height -= spaceBetweenEntries;
        }
        return height;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/widgets/SectionedListWidget$SectionWidget.class */
    @AutoWidget
    public static class SectionWidget<T> extends AbstractWidget<Widget> {
        private final Component component;
        private final LssProperty<Float> lineHeight = new LssProperty<>(Float.valueOf(1.0f));
        private final LssProperty<Integer> lineColor = new LssProperty<>(-1);
        private final LssProperty<Integer> textColor = new LssProperty<>(-1);
        private final T identifier;
        private float translationY;
        private float sectionHeight;

        private SectionWidget(@NotNull Component component, @NotNull T identifier) {
            Objects.requireNonNull(identifier);
            this.component = component;
            this.identifier = identifier;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public void renderWidget(ScreenContext context) {
            super.renderWidget(context);
            ScreenCanvas renderState = context.canvas();
            RenderableComponent renderableComponent = RenderableComponent.of(this.component);
            Bounds bounds = bounds();
            float lineWidth = (bounds.getWidth() / 2.0f) - (renderableComponent.getWidth() / 2.0f);
            float lineHeight = this.lineHeight.get().floatValue();
            int lineColor = this.lineColor.get().intValue();
            renderState.submitRelativeRect(bounds.getX(), bounds.getCenterY(), lineWidth - 5.0f, lineHeight, lineColor);
            renderState.submitRelativeRect((bounds.getMaxX() - lineWidth) + 3.0f, bounds.getCenterY(), lineWidth - 3.0f, lineHeight, lineColor);
            renderState.submitRenderableComponent(renderableComponent, bounds.getCenterX(), (bounds.getCenterY() - (renderableComponent.getHeight() / 2.0f)) + 1.0f, this.textColor.get().intValue(), 3);
        }

        public Component getComponent() {
            return this.component;
        }

        public T getIdentifier() {
            return this.identifier;
        }

        public LssProperty<Float> lineHeight() {
            return this.lineHeight;
        }

        public LssProperty<Integer> lineColor() {
            return this.lineColor;
        }

        public LssProperty<Integer> textColor() {
            return this.textColor;
        }
    }
}

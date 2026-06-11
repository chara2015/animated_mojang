package net.labymod.api.client.gui.screen.widget.widgets.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/IconSliderWidget.class */
@AutoWidget
@Link("widget/icon-slider.lss")
public class IconSliderWidget extends AbstractWidget<Widget> {
    private static final ModifyReason LIST_CONTENT = ModifyReason.of("sliderContent");
    private final List<IconWidget> icons = new ArrayList();
    private boolean switchedThisFrame;
    private int previousIndex;
    private int index;
    private ButtonWidget previousButton;
    private ButtonWidget nextButton;

    public IconSliderWidget() {
        addId("icon-slider");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.icons.clear();
        for (T child : this.children) {
            if (child instanceof IconWidget) {
                IconWidget iconWidget = (IconWidget) child;
                iconWidget.addId("slider-entry");
                this.icons.add(iconWidget);
            }
        }
        if (this.icons.size() <= 1) {
            return;
        }
        IconSliderNavigationWidget widget = new IconSliderNavigationWidget(this, index -> {
            IconSliderNavigationButtonWidget navigationButton = createNavigationButton(index);
            navigationButton.addId("slider-navigation-entry");
            return navigationButton;
        });
        widget.addId("slider-navigation-container");
        addChild(widget);
        this.previousButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        this.previousButton.addId("slider-navigation-button", "slider-navigation-previous-button");
        this.previousButton.setPressable(() -> {
            updateIndex(-1);
        });
        this.previousButton.setEnabled(this.index != 0);
        addChild(this.previousButton);
        this.nextButton = ButtonWidget.icon(Textures.SpriteCommon.FORWARD_BUTTON);
        this.nextButton.addId("slider-navigation-button", "slider-navigation-next-button");
        this.nextButton.setPressable(() -> {
            updateIndex(1);
        });
        this.nextButton.setEnabled(this.index != this.icons.size() - 1);
        addChild(this.nextButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        if (!isVisible()) {
            return;
        }
        for (int i = 0; i < this.icons.size(); i++) {
            if (i == this.index || i == this.previousIndex) {
                IconWidget icon = this.icons.get(i);
                icon.render(context);
            }
        }
        for (T child : this.children) {
            if (!(child instanceof IconWidget) || !this.icons.contains(child)) {
                child.render(context);
            }
        }
        this.switchedThisFrame = false;
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ARROW_LEFT || key == Key.ARROW_RIGHT) {
            updateIndex(key == Key.ARROW_LEFT ? -1 : 1);
            return true;
        }
        return super.keyPressed(key, type);
    }

    protected IconSliderNavigationButtonWidget createNavigationButton(int index) {
        IconSliderNavigationButtonWidget widget = new IconSliderNavigationButtonWidget(this, index);
        widget.setPressListener(() -> {
            return updateIndexPosition(index);
        });
        return widget;
    }

    protected void updateIndex(int direction) {
        updateIndexPosition(this.index + direction);
    }

    protected boolean updateIndexPosition(int newIndex) {
        if (this.index == newIndex || newIndex < 0 || newIndex >= this.icons.size()) {
            return false;
        }
        this.previousIndex = this.index;
        this.index = newIndex;
        this.switchedThisFrame = true;
        float width = bounds().getWidth();
        float translateX = this.index == 0 ? 0.0f : (-width) * this.index;
        for (IconWidget icon : this.icons) {
            icon.translateX().set(Float.valueOf(translateX));
        }
        this.previousButton.setEnabled(this.index != 0);
        this.nextButton.setEnabled(this.index != this.icons.size() - 1);
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void updateContentBounds() {
        super.updateContentBounds();
        Bounds bounds = bounds();
        float x = bounds.getX();
        float y = bounds.getY();
        float width = bounds.getWidth();
        float height = bounds.getHeight();
        for (IconWidget icon : this.icons) {
            Bounds iconBounds = icon.bounds();
            iconBounds.setOuterPosition(x, y, LIST_CONTENT);
            iconBounds.setOuterSize(width, height, LIST_CONTENT);
            x += width;
        }
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public boolean hasAutoBounds(Widget child, AutoAlignType type) {
        return (child instanceof IconWidget) && this.icons.contains(child);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/IconSliderWidget$IconSliderNavigationWidget.class */
    @AutoWidget
    public static class IconSliderNavigationWidget extends AbstractWidget<IconSliderNavigationButtonWidget> {
        private final IconSliderWidget slider;
        private final LssProperty<Float> spaceBetweenEntries = new LssProperty<>(Float.valueOf(2.0f));
        private final IntFunction<IconSliderNavigationButtonWidget> childSupplier;

        protected IconSliderNavigationWidget(IconSliderWidget slider, IntFunction<IconSliderNavigationButtonWidget> childSupplier) {
            this.slider = slider;
            this.childSupplier = childSupplier;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public void renderWidget(ScreenContext context) {
            if (this.slider.switchedThisFrame) {
                for (int i = 0; i < this.children.size(); i++) {
                    IconSliderNavigationButtonWidget child = (IconSliderNavigationButtonWidget) this.children.get(i);
                    if (i == this.slider.index) {
                        child.addId("selected");
                    } else {
                        child.removeId("selected");
                    }
                }
            }
            super.renderWidget(context);
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
        public void initialize(Parent parent) {
            super.initialize(parent);
            for (int i = 0; i < this.slider.icons.size(); i++) {
                IconSliderNavigationButtonWidget child = this.childSupplier.apply(i);
                if (i == this.slider.index) {
                    child.addId("selected");
                }
                addChild(child);
            }
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
        protected void updateContentBounds() {
            super.updateContentBounds();
            float contentWidth = 0.0f;
            Iterator it = this.children.iterator();
            while (it.hasNext()) {
                contentWidth += ((IconSliderNavigationButtonWidget) it.next()).bounds().getWidth(BoundsType.OUTER);
            }
            Bounds bounds = bounds();
            float x = bounds.getCenterX() - (contentWidth / 2.0f);
            float y = bounds.getY();
            float height = bounds.getHeight();
            float spaceBetweenEntries = this.spaceBetweenEntries.get().floatValue();
            for (T child : this.children) {
                Bounds childBounds = child.bounds();
                float entryY = y;
                WidgetAlignment widgetAlignment = child.alignmentY().get();
                if (widgetAlignment == WidgetAlignment.CENTER) {
                    entryY += (height / 2.0f) - (childBounds.getHeight(BoundsType.OUTER) / 2.0f);
                } else if (widgetAlignment == WidgetAlignment.BOTTOM) {
                    entryY += height - childBounds.getHeight(BoundsType.OUTER);
                }
                childBounds.setOuterPosition(x, entryY, IconSliderWidget.LIST_CONTENT);
                x += childBounds.getWidth(BoundsType.OUTER) + spaceBetweenEntries;
            }
        }

        @Override // net.labymod.api.client.gui.screen.Parent
        public boolean hasAutoBounds(Widget child, AutoAlignType type) {
            return type == AutoAlignType.POSITION;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
        public float getContentHeight(BoundsType type) {
            float height = 0.0f;
            for (T child : this.children) {
                if (child.isVisible()) {
                    height = Math.max(height, child.bounds().getHeight(BoundsType.OUTER));
                }
            }
            return height + bounds().getVerticalOffset(type);
        }

        public LssProperty<Float> spaceBetweenEntries() {
            return this.spaceBetweenEntries;
        }

        public IconSliderWidget slider() {
            return this.slider;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/IconSliderWidget$IconSliderNavigationButtonWidget.class */
    @AutoWidget
    public static class IconSliderNavigationButtonWidget extends AbstractWidget<Widget> {
        protected final IconSliderWidget sliderWidget;
        protected final int index;

        protected IconSliderNavigationButtonWidget(IconSliderWidget sliderWidget, int index) {
            this.sliderWidget = sliderWidget;
            this.index = index;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
        protected SoundType getInteractionSound() {
            return SoundType.BUTTON_CLICK;
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
        protected boolean playInteractionSoundAfterHandling() {
            return true;
        }

        public boolean isCurrentIcon() {
            return this.sliderWidget.index == this.index;
        }
    }
}

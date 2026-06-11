package net.labymod.api.client.gui.screen.widget;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.util.metadata.ActivityMetadataRegistry;
import net.labymod.api.client.gui.screen.util.metadata.WidgetMetadata;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AnimationBuilder;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/WrappedWidget.class */
@AutoWidget
@Deprecated
public class WrappedWidget extends StyledWidget {

    @NotNull
    protected final Widget childWidget;
    private WidgetMetadata widgetMetadata;

    protected WrappedWidget(@NotNull Widget childWidget) {
        Widget widgetChildWidget;
        this.childWidget = childWidget;
        if (childWidget instanceof StyledWidget) {
            Widget current = childWidget;
            do {
                ((StyledWidget) current).actualWidget = this.actualWidget;
                if (!(current instanceof WrappedWidget)) {
                    break;
                }
                widgetChildWidget = ((WrappedWidget) current).childWidget();
                current = widgetChildWidget;
            } while (widgetChildWidget instanceof StyledWidget);
        }
        ActivityMetadataRegistry.collectWidgetMetadata(getClass(), metadata -> {
            this.widgetMetadata = metadata;
        });
    }

    public boolean equals(Object o) {
        return super.equals(o) || this.childWidget == o;
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void unfocus() {
        this.childWidget.unfocus();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void reset() {
        this.childWidget.reset();
    }

    @Override // net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        this.childWidget.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void initializeLazy(Parent parent) {
        this.childWidget.initializeLazy(parent);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void postInitialize() {
        this.childWidget.postInitialize();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void applyStyleSheet(StyleSheet styleSheet) {
        this.childWidget.applyStyleSheet(styleSheet);
        super.applyStyleSheet(styleSheet);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void reInitialize(Parent parent) {
        this.childWidget.reInitialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        this.childWidget.postStyleSheetLoad();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        super.render(context);
        this.childWidget.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        this.childWidget.renderWidget(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderOverlay(ScreenContext context) {
        this.childWidget.renderOverlay(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderHoverComponent(ScreenContext context) {
        this.childWidget.renderHoverComponent(context);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return this.childWidget.getDefaultRendererName();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        this.childWidget.tick();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return this.childWidget.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return this.childWidget.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return this.childWidget.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return this.childWidget.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return this.childWidget.fileDropped(mouse, paths);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement
    public List<? extends Widget> getChildren() {
        return this.childWidget.getChildren();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        return this.childWidget.onPress();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasParentStateAttributes() {
        return this.childWidget.hasParentStateAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public boolean isHovered() {
        return this.childWidget.isHovered();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isTicking() {
        return this.childWidget.isTicking();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getContentSize(BoundsType type, WidgetSide side) {
        return this.childWidget.getContentSize(type, side);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<PriorityLayer> priorityLayer() {
        return this.childWidget.priorityLayer();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<WidgetAlignment> alignmentX() {
        return this.childWidget.alignmentX();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<WidgetAlignment> alignmentY() {
        return this.childWidget.alignmentY();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasTabFocus() {
        return this.childWidget.hasTabFocus();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isFocused() {
        return this.childWidget.isFocused();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setFocused(boolean focused) {
        this.childWidget.setFocused(focused);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setActionListener(Runnable listener) {
        this.childWidget.setActionListener(listener);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setActionListener(String id, Runnable listener) {
        this.childWidget.setActionListener(id, listener);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void removeActionListener(String id) {
        this.childWidget.removeActionListener(id);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setPressable(Pressable pressable) {
        this.childWidget.setPressable(pressable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setPressListener(BooleanSupplier pressable) {
        this.childWidget.setPressListener(pressable);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isVisible() {
        return this.childWidget.isVisible();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setVisible(boolean visible) {
        this.childWidget.setVisible(visible);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W addId(String... strArr) {
        return (W) this.childWidget.addId(strArr);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W addId(String str) {
        return (W) this.childWidget.addId(str);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget
    protected void reloadStyleSheets() {
        if (this.childWidget instanceof StyledWidget) {
            ((StyledWidget) this.childWidget).reloadStyleSheets();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasId(CharSequence id) {
        return this.childWidget.hasId(id);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void removeId(String id) {
        this.childWidget.removeId(id);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <W extends Widget> W replaceId(String str, String str2) {
        return (W) this.childWidget.replaceId(str, str2);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getUniqueId() {
        return this.childWidget.getUniqueId();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public List<CharSequence> getIds() {
        return this.childWidget.getIds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getTypeName() {
        return this.childWidget.getTypeName();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public String getQualifiedName() {
        return this.widgetMetadata.qualifiedName();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Animation animation() {
        return this.childWidget.animation();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public AnimationBuilder animate(String propertyName) {
        return this.childWidget.animate(propertyName);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public AnimationBuilder animate() {
        return this.childWidget.animate();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void playAnimation(String id, Runnable finishHandler) {
        this.childWidget.playAnimation(id, finishHandler);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void playAnimation(String id) {
        playAnimation(id, null);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<ThemeRenderer> renderer() {
        return this.childWidget.renderer();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Bounds bounds() {
        return this.childWidget.bounds();
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        return this.childWidget.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return this.childWidget.keyReleased(key, type);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return this.childWidget.charTyped(key, character);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        return this.childWidget.handlePreeditText(text);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        this.childWidget.doScreenAction(action, parameters);
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.childWidget.metadata(metadata);
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.childWidget.metadata();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isDragging() {
        return this.childWidget.isDragging();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isDraggable() {
        return this.childWidget.isDraggable();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setDragging(boolean dragging) {
        this.childWidget.setDragging(dragging);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isInteractable() {
        return this.childWidget.isInteractable();
    }

    @NotNull
    public Widget childWidget() {
        return this.childWidget;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getParent() {
        return this.childWidget.getParent();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void handleAttributes() {
        this.childWidget.handleAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return this.childWidget.getContentWidth(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return this.childWidget.getContentHeight(type);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public LssProperty<BoxSizing> boxSizing() {
        return this.childWidget.boxSizing();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public WidgetSizeList sizes() {
        return this.childWidget.sizes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setSize(SizeType type, WidgetSide side, WidgetSize size) {
        this.childWidget.setSize(type, side, size);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Float getSize(SizeType type, WidgetSide side) {
        return this.childWidget.getSize(type, side);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasSize(SizeType type, WidgetSide side, WidgetSize.Type sizeType) {
        return this.childWidget.hasSize(type, side, sizeType);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasAnySize(WidgetSide side) {
        return this.childWidget.hasAnySize(side);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isPercentageSize(SizeType type, WidgetSide side) {
        return this.childWidget.isPercentageSize(type, side);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getFontWeight() {
        return this.childWidget.getFontWeight();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isOutOfBounds() {
        return this.childWidget.isOutOfBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setOutOfBounds(boolean outOfBounds) {
        this.childWidget.setOutOfBounds(outOfBounds);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getStyleInstructions() {
        return this.childWidget.getStyleInstructions();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getSortedStyleInstructions() {
        return this.childWidget.getSortedStyleInstructions();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getDynamicStyleInstructions() {
        return this.childWidget.getDynamicStyleInstructions();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public List<StyleInstructions> getDynamicSortedStyleInstructions() {
        return this.childWidget.getDynamicSortedStyleInstructions();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void addAttributePatch(Selector selector, AttributePatch patch, int skipDepth) {
        super.addAttributePatch(selector, patch, skipDepth);
        this.childWidget.addAttributePatch(selector, patch, skipDepth);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void patch(AttributePatch patch) {
        super.patch(patch);
        this.childWidget.patch(patch);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void patchAttributes() {
        super.patchAttributes();
        this.childWidget.patchAttributes();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateState(boolean force) {
        super.updateState(force);
        this.childWidget.updateState(force);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void resetState() {
        this.childWidget.resetState();
        super.resetState();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getRoot() {
        return this.childWidget.getRoot();
    }

    public String toString() {
        return this.childWidget.getTypeName();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isActive() {
        return this.childWidget.isActive();
    }

    @Override // net.labymod.api.client.gui.element.Element
    public void setActive(boolean active) {
        this.childWidget.setActive(active);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean hasHoverComponent() {
        return this.childWidget.hasHoverComponent();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverComponent(Component component) {
        this.childWidget.setHoverComponent(component);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverComponent(Component component, float maxWidth) {
        this.childWidget.setHoverComponent(component, maxWidth);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setHoverRenderableComponent(RenderableComponent renderableComponent) {
        this.childWidget.setHoverRenderableComponent(renderableComponent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getTranslateX() {
        return this.childWidget.getTranslateX();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setTranslateX(float translateX) {
        this.childWidget.setTranslateX(translateX);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getTranslateY() {
        return this.childWidget.getTranslateY();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setTranslateY(float translateY) {
        this.childWidget.setTranslateY(translateY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getScaleX() {
        return this.childWidget.getScaleX();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScaleX(float scaleX) {
        this.childWidget.setScaleX(scaleX);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getScaleY() {
        return this.childWidget.getScaleY();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScaleY(float scaleY) {
        this.childWidget.setScaleY(scaleY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setScale(float scale) {
        this.childWidget.setScale(scale);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getEffectiveWidth() {
        return this.childWidget.getEffectiveWidth();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public float getEffectiveHeight() {
        return this.childWidget.getEffectiveHeight();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isSelected() {
        return this.childWidget.isSelected();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setSelected(boolean selected) {
        this.childWidget.setSelected(selected);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isCancelParentHoverComponent() {
        return this.childWidget.isCancelParentHoverComponent();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Rectangle childrenRegion() {
        return this.childWidget.childrenRegion();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <K extends Widget> void traverse(Collection<K> output, FilterAction filterAction) {
        this.childWidget.traverse(output, filterAction);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <T extends Widget> List<T> getChildrenAt(int x, int y) {
        return this.childWidget.getChildrenAt(x, y);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public <T extends Widget> void onAttachedTo(AbstractWidget<T> parent) {
        this.childWidget.onAttachedTo(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void updateBounds() {
        this.childWidget.updateBounds();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void destroy() {
        this.childWidget.destroy();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.util.Disposable
    public boolean isDisposed() {
        return this.childWidget.isDisposed();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isDestroyed() {
        return this.childWidget.isDestroyed();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isLazy() {
        return this.childWidget.isLazy();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Collection<AttributeState> getAttributeStates() {
        return this.childWidget.getAttributeStates();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public long getAttributeStateToggleTimestamp(AttributeState state) {
        return this.childWidget.getAttributeStateToggleTimestamp(state);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean isAttributeStateEnabled(AttributeState state) {
        return this.childWidget.isAttributeStateEnabled(state);
    }

    @Override // net.labymod.api.client.gui.element.Element
    public boolean isInteractableOutside() {
        return this.childWidget.isInteractableOutside();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void setAttributeState(AttributeState state, boolean enabled) {
        this.childWidget.setAttributeState(state, enabled);
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean transformMouse(MutableMouse mouse, BooleanSupplier handler) {
        return this.childWidget.transformMouse(mouse, handler);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public Widget getHighestParentWidget() {
        return this.childWidget.getHighestParentWidget();
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        this.childWidget.dispose();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public LssVariableHolder getParentVariableHolder() {
        return this.childWidget.getParentVariableHolder();
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public Map<String, LssVariable> getLssVariables() {
        return this.childWidget.getLssVariables();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        super.updateLssVariable(variable);
        this.childWidget.updateLssVariable(variable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        super.updateLssVariable(variable);
        this.childWidget.updateLssVariable(variable);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public LssVariable getVariable(@NotNull String key) {
        return this.childWidget.getVariable(key);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void setVariable(@NotNull String key, @NotNull String value) {
        this.childWidget.setVariable(key, value);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void clearVariable(@NotNull String key) {
        this.childWidget.clearVariable(key);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public boolean shouldHandleEscape() {
        return this.childWidget.shouldHandleEscape();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    @Nullable
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor() {
        return this.widgetMetadata.propertyAccessor();
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public void renderExtraDebugInformation() {
        this.childWidget.renderExtraDebugInformation();
    }
}

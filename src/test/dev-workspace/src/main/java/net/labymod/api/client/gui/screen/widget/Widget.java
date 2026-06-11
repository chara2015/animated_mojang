package net.labymod.api.client.gui.screen.widget;

import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.element.CompositeElement;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import net.labymod.api.client.gui.screen.widget.attributes.animation.AnimationBuilder;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.Disposable;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/Widget.class */
public interface Widget extends CompositeElement<Widget>, Disposable, LssVariableHolder {
    void reset();

    void applyStyleSheet(StyleSheet styleSheet);

    String getDefaultRendererName();

    void initializeLazy(Parent parent);

    void reInitialize(Parent parent);

    void postStyleSheetLoad();

    void renderWidget(ScreenContext screenContext);

    @Override // net.labymod.api.client.gui.element.Element
    void renderOverlay(ScreenContext screenContext);

    @Override // net.labymod.api.client.gui.element.Element
    void renderHoverComponent(ScreenContext screenContext);

    boolean hasTabFocus();

    void setActionListener(String str, Runnable runnable);

    void setActionListener(Runnable runnable);

    void removeActionListener(String str);

    void unfocus();

    boolean hasParentStateAttributes();

    @Override // net.labymod.api.client.gui.element.Element
    boolean isHovered();

    float getContentSize(BoundsType boundsType, WidgetSide widgetSide);

    float getContentWidth(BoundsType boundsType);

    float getContentHeight(BoundsType boundsType);

    LssProperty<BoxSizing> boxSizing();

    WidgetSizeList sizes();

    void setSize(SizeType sizeType, WidgetSide widgetSide, WidgetSize widgetSize);

    Float getSize(SizeType sizeType, WidgetSide widgetSide);

    boolean hasSize(SizeType sizeType, WidgetSide widgetSide, WidgetSize.Type type);

    boolean hasAnySize(WidgetSide widgetSide);

    boolean isPercentageSize(SizeType sizeType, WidgetSide widgetSide);

    float getFontWeight();

    boolean isOutOfBounds();

    void setOutOfBounds(boolean z);

    LssProperty<WidgetAlignment> alignmentX();

    LssProperty<WidgetAlignment> alignmentY();

    boolean isFocused();

    void setFocused(boolean z);

    boolean onPress();

    LssProperty<PriorityLayer> priorityLayer();

    <W extends Widget> W addId(String... strArr);

    <W extends Widget> W addId(String str);

    boolean hasId(CharSequence charSequence);

    void removeId(String str);

    <W extends Widget> W replaceId(String str, String str2);

    String getUniqueId();

    List<CharSequence> getIds();

    String getTypeName();

    String getQualifiedName();

    Animation animation();

    AnimationBuilder animate(String str);

    AnimationBuilder animate();

    void playAnimation(String str, Runnable runnable);

    void playAnimation(String str);

    LssProperty<ThemeRenderer> renderer();

    void handleAttributes();

    void updateBounds();

    List<StyleInstructions> getStyleInstructions();

    List<StyleInstructions> getSortedStyleInstructions();

    List<StyleInstructions> getDynamicStyleInstructions();

    @Deprecated
    List<StyleInstructions> getDynamicSortedStyleInstructions();

    void updateState(boolean z);

    void resetState();

    void patchAttributes();

    void patch(AttributePatch attributePatch);

    void addAttributePatch(Selector selector, AttributePatch attributePatch, int i);

    void setPressable(Pressable pressable);

    void setPressListener(BooleanSupplier booleanSupplier);

    boolean hasHoverComponent();

    void setHoverComponent(Component component);

    void setHoverComponent(Component component, float f);

    void setHoverRenderableComponent(RenderableComponent renderableComponent);

    float getTranslateX();

    void setTranslateX(float f);

    float getTranslateY();

    void setTranslateY(float f);

    float getScaleX();

    void setScaleX(float f);

    float getScaleY();

    void setScaleY(float f);

    void setScale(float f);

    float getEffectiveWidth();

    float getEffectiveHeight();

    boolean isSelected();

    void setSelected(boolean z);

    boolean isCancelParentHoverComponent();

    Rectangle childrenRegion();

    Widget actualWidget();

    <T extends Widget> void traverse(Collection<T> collection, FilterAction filterAction);

    <T extends Widget> List<T> getChildrenAt(int i, int i2);

    <T extends Widget> void onAttachedTo(AbstractWidget<T> abstractWidget);

    void destroy();

    boolean isDisposed();

    boolean isDestroyed();

    boolean isLazy();

    Collection<AttributeState> getAttributeStates();

    long getAttributeStateToggleTimestamp(AttributeState attributeState);

    boolean isAttributeStateEnabled(AttributeState attributeState);

    void setAttributeState(AttributeState attributeState, boolean z);

    void applyMediaRules(boolean z);

    Widget getHighestParentWidget();

    boolean shouldHandleEscape();

    @Nullable
    DirectPropertyValueAccessor getDirectPropertyValueAccessor();

    default void reInitialize() {
        reInitialize(getParent());
    }

    default boolean hasSize(WidgetSide side, WidgetSize.Type sizeType) {
        for (SizeType type : SizeType.values()) {
            if (hasSize(type, side, sizeType)) {
                return true;
            }
        }
        return false;
    }

    default boolean hasSize(WidgetSide side) {
        for (WidgetSize.Type sizeType : WidgetSize.Type.values()) {
            if (hasSize(side, sizeType)) {
                return true;
            }
        }
        return false;
    }

    default float getSizeOr(SizeType type, WidgetSide side, float defaultValue) {
        Float size = getSize(type, side);
        return size != null ? size.floatValue() : defaultValue;
    }

    default <W extends Widget> W addId(CharSequence charSequence) {
        return (W) addId(CharSequences.toString(charSequence));
    }

    default <T extends Widget> List<T> getChildrenAt(Point point) {
        return getChildrenAt(point.getX(), point.getY());
    }

    default int getSortingValue() {
        return 0;
    }

    @ApiStatus.Experimental
    default void renderExtraDebugInformation() {
    }
}

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlwaysFocusedPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationDirectionAlternatePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationIterationCountPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationTimingFunctionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundAlwaysDirtPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundColorTransitionDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtBrightnessPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtShiftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtTypePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBoxSizingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetCancelParentHoverComponentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetClearDepthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDestroyDelayPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDistinctPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDraggablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFilterPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFitOuterPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFontWeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetForceVanillaFontPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetHeightPrecisionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetHoverBoxDelayPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetInteractableOutsidePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetInteractablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMouseRenderDistancePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetOpacityPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPressablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPriorityLayerPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRenderChildrenPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRendererPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetScaleXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetScaleYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilTranslationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTranslateXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTranslateYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetUseFloatingPointPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetVisiblePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetWidthPrecisionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetWriteToStencilBufferPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetZIndexPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/api/lss/properties/direct/AbstractWidgetDirectPropertyValueAccessor.class */
public class AbstractWidgetDirectPropertyValueAccessor extends StyledWidgetDirectPropertyValueAccessor {
    protected PropertyValueAccessor<?, ?, ?> renderer = new AbstractWidgetRendererPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> boxSizing = new AbstractWidgetBoxSizingPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> animationDuration = new AbstractWidgetAnimationDurationPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> draggable = new AbstractWidgetDraggablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> interactable = new AbstractWidgetInteractablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> pressable = new AbstractWidgetPressablePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> visible = new AbstractWidgetVisiblePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> left = new AbstractWidgetLeftPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> top = new AbstractWidgetTopPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> right = new AbstractWidgetRightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> bottom = new AbstractWidgetBottomPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> marginLeft = new AbstractWidgetMarginLeftPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> marginTop = new AbstractWidgetMarginTopPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> marginRight = new AbstractWidgetMarginRightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> marginBottom = new AbstractWidgetMarginBottomPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> paddingLeft = new AbstractWidgetPaddingLeftPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> paddingTop = new AbstractWidgetPaddingTopPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> paddingRight = new AbstractWidgetPaddingRightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> paddingBottom = new AbstractWidgetPaddingBottomPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> widthPrecision = new AbstractWidgetWidthPrecisionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> heightPrecision = new AbstractWidgetHeightPrecisionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> priorityLayer = new AbstractWidgetPriorityLayerPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignmentX = new AbstractWidgetAlignmentXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alignmentY = new AbstractWidgetAlignmentYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> translateX = new AbstractWidgetTranslateXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> translateY = new AbstractWidgetTranslateYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> zIndex = new AbstractWidgetZIndexPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scaleX = new AbstractWidgetScaleXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> scaleY = new AbstractWidgetScaleYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> opacity = new AbstractWidgetOpacityPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> mouseRenderDistance = new AbstractWidgetMouseRenderDistancePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> stencilX = new AbstractWidgetStencilXPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> stencilY = new AbstractWidgetStencilYPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> stencilTranslation = new AbstractWidgetStencilTranslationPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> writeToStencilBuffer = new AbstractWidgetWriteToStencilBufferPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> useFloatingPointPosition = new AbstractWidgetUseFloatingPointPositionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> alwaysFocused = new AbstractWidgetAlwaysFocusedPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> interactableOutside = new AbstractWidgetInteractableOutsidePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> destroyDelay = new AbstractWidgetDestroyDelayPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> fitOuter = new AbstractWidgetFitOuterPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> distinct = new AbstractWidgetDistinctPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> cancelParentHoverComponent = new AbstractWidgetCancelParentHoverComponentPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> renderChildren = new AbstractWidgetRenderChildrenPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundColor = new AbstractWidgetBackgroundColorPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundColorTransitionDuration = new AbstractWidgetBackgroundColorTransitionDurationPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundAlwaysDirt = new AbstractWidgetBackgroundAlwaysDirtPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtShift = new AbstractWidgetBackgroundDirtShiftPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtType = new AbstractWidgetBackgroundDirtTypePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> fontWeight = new AbstractWidgetFontWeightPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> animationTimingFunction = new AbstractWidgetAnimationTimingFunctionPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> animationIterationCount = new AbstractWidgetAnimationIterationCountPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> animationDirectionAlternate = new AbstractWidgetAnimationDirectionAlternatePropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> filter = new AbstractWidgetFilterPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> hoverBoxDelay = new AbstractWidgetHoverBoxDelayPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> clearDepth = new AbstractWidgetClearDepthPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> forceVanillaFont = new AbstractWidgetForceVanillaFontPropertyValueAccessor();
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtBrightness = new AbstractWidgetBackgroundDirtBrightnessPropertyValueAccessor();
    LssPropertyResetter AbstractWidgetResetter = new AbstractWidgetLssPropertyResetter();

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(String key) {
        switch (key) {
            case "renderer":
                return this.renderer;
            case "boxSizing":
                return this.boxSizing;
            case "animationDuration":
                return this.animationDuration;
            case "draggable":
                return this.draggable;
            case "interactable":
                return this.interactable;
            case "pressable":
                return this.pressable;
            case "visible":
                return this.visible;
            case "left":
                return this.left;
            case "top":
                return this.top;
            case "right":
                return this.right;
            case "bottom":
                return this.bottom;
            case "marginLeft":
                return this.marginLeft;
            case "marginTop":
                return this.marginTop;
            case "marginRight":
                return this.marginRight;
            case "marginBottom":
                return this.marginBottom;
            case "paddingLeft":
                return this.paddingLeft;
            case "paddingTop":
                return this.paddingTop;
            case "paddingRight":
                return this.paddingRight;
            case "paddingBottom":
                return this.paddingBottom;
            case "widthPrecision":
                return this.widthPrecision;
            case "heightPrecision":
                return this.heightPrecision;
            case "priorityLayer":
                return this.priorityLayer;
            case "alignmentX":
                return this.alignmentX;
            case "alignmentY":
                return this.alignmentY;
            case "translateX":
                return this.translateX;
            case "translateY":
                return this.translateY;
            case "zIndex":
                return this.zIndex;
            case "scaleX":
                return this.scaleX;
            case "scaleY":
                return this.scaleY;
            case "opacity":
                return this.opacity;
            case "mouseRenderDistance":
                return this.mouseRenderDistance;
            case "stencilX":
                return this.stencilX;
            case "stencilY":
                return this.stencilY;
            case "stencilTranslation":
                return this.stencilTranslation;
            case "writeToStencilBuffer":
                return this.writeToStencilBuffer;
            case "useFloatingPointPosition":
                return this.useFloatingPointPosition;
            case "alwaysFocused":
                return this.alwaysFocused;
            case "interactableOutside":
                return this.interactableOutside;
            case "destroyDelay":
                return this.destroyDelay;
            case "fitOuter":
                return this.fitOuter;
            case "distinct":
                return this.distinct;
            case "cancelParentHoverComponent":
                return this.cancelParentHoverComponent;
            case "renderChildren":
                return this.renderChildren;
            case "backgroundColor":
                return this.backgroundColor;
            case "backgroundColorTransitionDuration":
                return this.backgroundColorTransitionDuration;
            case "backgroundAlwaysDirt":
                return this.backgroundAlwaysDirt;
            case "backgroundDirtShift":
                return this.backgroundDirtShift;
            case "backgroundDirtType":
                return this.backgroundDirtType;
            case "fontWeight":
                return this.fontWeight;
            case "animationTimingFunction":
                return this.animationTimingFunction;
            case "animationIterationCount":
                return this.animationIterationCount;
            case "animationDirectionAlternate":
                return this.animationDirectionAlternate;
            case "filter":
                return this.filter;
            case "hoverBoxDelay":
                return this.hoverBoxDelay;
            case "clearDepth":
                return this.clearDepth;
            case "forceVanillaFont":
                return this.forceVanillaFont;
            case "backgroundDirtBrightness":
                return this.backgroundDirtBrightness;
            default:
                return super.getPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public boolean hasPropertyValueAccessor(String key) {
        switch (key) {
            case "renderer":
                return true;
            case "boxSizing":
                return true;
            case "animationDuration":
                return true;
            case "draggable":
                return true;
            case "interactable":
                return true;
            case "pressable":
                return true;
            case "visible":
                return true;
            case "left":
                return true;
            case "top":
                return true;
            case "right":
                return true;
            case "bottom":
                return true;
            case "marginLeft":
                return true;
            case "marginTop":
                return true;
            case "marginRight":
                return true;
            case "marginBottom":
                return true;
            case "paddingLeft":
                return true;
            case "paddingTop":
                return true;
            case "paddingRight":
                return true;
            case "paddingBottom":
                return true;
            case "widthPrecision":
                return true;
            case "heightPrecision":
                return true;
            case "priorityLayer":
                return true;
            case "alignmentX":
                return true;
            case "alignmentY":
                return true;
            case "translateX":
                return true;
            case "translateY":
                return true;
            case "zIndex":
                return true;
            case "scaleX":
                return true;
            case "scaleY":
                return true;
            case "opacity":
                return true;
            case "mouseRenderDistance":
                return true;
            case "stencilX":
                return true;
            case "stencilY":
                return true;
            case "stencilTranslation":
                return true;
            case "writeToStencilBuffer":
                return true;
            case "useFloatingPointPosition":
                return true;
            case "alwaysFocused":
                return true;
            case "interactableOutside":
                return true;
            case "destroyDelay":
                return true;
            case "fitOuter":
                return true;
            case "distinct":
                return true;
            case "cancelParentHoverComponent":
                return true;
            case "renderChildren":
                return true;
            case "backgroundColor":
                return true;
            case "backgroundColorTransitionDuration":
                return true;
            case "backgroundAlwaysDirt":
                return true;
            case "backgroundDirtShift":
                return true;
            case "backgroundDirtType":
                return true;
            case "fontWeight":
                return true;
            case "animationTimingFunction":
                return true;
            case "animationIterationCount":
                return true;
            case "animationDirectionAlternate":
                return true;
            case "filter":
                return true;
            case "hoverBoxDelay":
                return true;
            case "clearDepth":
                return true;
            case "forceVanillaFont":
                return true;
            case "backgroundDirtBrightness":
                return true;
            default:
                return super.hasPropertyValueAccessor(key);
        }
    }

    @Override // net.labymod.autogen.api.lss.properties.direct.StyledWidgetDirectPropertyValueAccessor, net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor
    public LssPropertyResetter getPropertyResetter() {
        return this.AbstractWidgetResetter;
    }
}

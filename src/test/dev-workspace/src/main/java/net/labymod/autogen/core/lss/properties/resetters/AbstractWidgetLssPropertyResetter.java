package net.labymod.autogen.core.lss.properties.resetters;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/autogen/core/lss/properties/resetters/AbstractWidgetLssPropertyResetter.class */
public class AbstractWidgetLssPropertyResetter extends StyledWidgetLssPropertyResetter {
    @Override // net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter, net.labymod.api.client.gui.lss.property.LssPropertyResetter
    public void reset(Widget widget) {
        if (widget instanceof AbstractWidget) {
            if (((AbstractWidget) widget).renderer() != null) {
                ((AbstractWidget) widget).renderer().reset();
            }
            if (((AbstractWidget) widget).boxSizing() != null) {
                ((AbstractWidget) widget).boxSizing().reset();
            }
            if (((AbstractWidget) widget).animationDuration() != null) {
                ((AbstractWidget) widget).animationDuration().reset();
            }
            if (((AbstractWidget) widget).draggable() != null) {
                ((AbstractWidget) widget).draggable().reset();
            }
            if (((AbstractWidget) widget).interactable() != null) {
                ((AbstractWidget) widget).interactable().reset();
            }
            if (((AbstractWidget) widget).pressable() != null) {
                ((AbstractWidget) widget).pressable().reset();
            }
            if (((AbstractWidget) widget).visible() != null) {
                ((AbstractWidget) widget).visible().reset();
            }
            if (((AbstractWidget) widget).left() != null) {
                ((AbstractWidget) widget).left().reset();
            }
            if (((AbstractWidget) widget).top() != null) {
                ((AbstractWidget) widget).top().reset();
            }
            if (((AbstractWidget) widget).right() != null) {
                ((AbstractWidget) widget).right().reset();
            }
            if (((AbstractWidget) widget).bottom() != null) {
                ((AbstractWidget) widget).bottom().reset();
            }
            if (((AbstractWidget) widget).marginLeft() != null) {
                ((AbstractWidget) widget).marginLeft().reset();
            }
            if (((AbstractWidget) widget).marginTop() != null) {
                ((AbstractWidget) widget).marginTop().reset();
            }
            if (((AbstractWidget) widget).marginRight() != null) {
                ((AbstractWidget) widget).marginRight().reset();
            }
            if (((AbstractWidget) widget).marginBottom() != null) {
                ((AbstractWidget) widget).marginBottom().reset();
            }
            if (((AbstractWidget) widget).paddingLeft() != null) {
                ((AbstractWidget) widget).paddingLeft().reset();
            }
            if (((AbstractWidget) widget).paddingTop() != null) {
                ((AbstractWidget) widget).paddingTop().reset();
            }
            if (((AbstractWidget) widget).paddingRight() != null) {
                ((AbstractWidget) widget).paddingRight().reset();
            }
            if (((AbstractWidget) widget).paddingBottom() != null) {
                ((AbstractWidget) widget).paddingBottom().reset();
            }
            if (((AbstractWidget) widget).widthPrecision() != null) {
                ((AbstractWidget) widget).widthPrecision().reset();
            }
            if (((AbstractWidget) widget).heightPrecision() != null) {
                ((AbstractWidget) widget).heightPrecision().reset();
            }
            if (((AbstractWidget) widget).priorityLayer() != null) {
                ((AbstractWidget) widget).priorityLayer().reset();
            }
            if (((AbstractWidget) widget).alignmentX() != null) {
                ((AbstractWidget) widget).alignmentX().reset();
            }
            if (((AbstractWidget) widget).alignmentY() != null) {
                ((AbstractWidget) widget).alignmentY().reset();
            }
            if (((AbstractWidget) widget).translateX() != null) {
                ((AbstractWidget) widget).translateX().reset();
            }
            if (((AbstractWidget) widget).translateY() != null) {
                ((AbstractWidget) widget).translateY().reset();
            }
            if (((AbstractWidget) widget).zIndex() != null) {
                ((AbstractWidget) widget).zIndex().reset();
            }
            if (((AbstractWidget) widget).scaleX() != null) {
                ((AbstractWidget) widget).scaleX().reset();
            }
            if (((AbstractWidget) widget).scaleY() != null) {
                ((AbstractWidget) widget).scaleY().reset();
            }
            if (((AbstractWidget) widget).opacity() != null) {
                ((AbstractWidget) widget).opacity().reset();
            }
            if (((AbstractWidget) widget).mouseRenderDistance() != null) {
                ((AbstractWidget) widget).mouseRenderDistance().reset();
            }
            if (((AbstractWidget) widget).stencilX() != null) {
                ((AbstractWidget) widget).stencilX().reset();
            }
            if (((AbstractWidget) widget).stencilY() != null) {
                ((AbstractWidget) widget).stencilY().reset();
            }
            if (((AbstractWidget) widget).stencilTranslation() != null) {
                ((AbstractWidget) widget).stencilTranslation().reset();
            }
            if (((AbstractWidget) widget).writeToStencilBuffer() != null) {
                ((AbstractWidget) widget).writeToStencilBuffer().reset();
            }
            if (((AbstractWidget) widget).useFloatingPointPosition() != null) {
                ((AbstractWidget) widget).useFloatingPointPosition().reset();
            }
            if (((AbstractWidget) widget).alwaysFocused() != null) {
                ((AbstractWidget) widget).alwaysFocused().reset();
            }
            if (((AbstractWidget) widget).interactableOutside() != null) {
                ((AbstractWidget) widget).interactableOutside().reset();
            }
            if (((AbstractWidget) widget).destroyDelay() != null) {
                ((AbstractWidget) widget).destroyDelay().reset();
            }
            if (((AbstractWidget) widget).fitOuter() != null) {
                ((AbstractWidget) widget).fitOuter().reset();
            }
            if (((AbstractWidget) widget).distinct() != null) {
                ((AbstractWidget) widget).distinct().reset();
            }
            if (((AbstractWidget) widget).cancelParentHoverComponent() != null) {
                ((AbstractWidget) widget).cancelParentHoverComponent().reset();
            }
            if (((AbstractWidget) widget).renderChildren() != null) {
                ((AbstractWidget) widget).renderChildren().reset();
            }
            if (((AbstractWidget) widget).backgroundColor() != null) {
                ((AbstractWidget) widget).backgroundColor().reset();
            }
            if (((AbstractWidget) widget).backgroundColorTransitionDuration() != null) {
                ((AbstractWidget) widget).backgroundColorTransitionDuration().reset();
            }
            if (((AbstractWidget) widget).backgroundAlwaysDirt() != null) {
                ((AbstractWidget) widget).backgroundAlwaysDirt().reset();
            }
            if (((AbstractWidget) widget).backgroundDirtShift() != null) {
                ((AbstractWidget) widget).backgroundDirtShift().reset();
            }
            if (((AbstractWidget) widget).backgroundDirtType() != null) {
                ((AbstractWidget) widget).backgroundDirtType().reset();
            }
            if (((AbstractWidget) widget).fontWeight() != null) {
                ((AbstractWidget) widget).fontWeight().reset();
            }
            if (((AbstractWidget) widget).animationTimingFunction() != null) {
                ((AbstractWidget) widget).animationTimingFunction().reset();
            }
            if (((AbstractWidget) widget).animationIterationCount() != null) {
                ((AbstractWidget) widget).animationIterationCount().reset();
            }
            if (((AbstractWidget) widget).animationDirectionAlternate() != null) {
                ((AbstractWidget) widget).animationDirectionAlternate().reset();
            }
            if (((AbstractWidget) widget).filter() != null) {
                ((AbstractWidget) widget).filter().reset();
            }
            if (((AbstractWidget) widget).hoverBoxDelay() != null) {
                ((AbstractWidget) widget).hoverBoxDelay().reset();
            }
            if (((AbstractWidget) widget).clearDepth() != null) {
                ((AbstractWidget) widget).clearDepth().reset();
            }
            if (((AbstractWidget) widget).forceVanillaFont() != null) {
                ((AbstractWidget) widget).forceVanillaFont().reset();
            }
            if (((AbstractWidget) widget).backgroundDirtBrightness() != null) {
                ((AbstractWidget) widget).backgroundDirtBrightness().reset();
            }
        }
        super.reset(widget);
    }
}

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/Filter.class */
public class Filter implements Interpolatable<Filter>, WidgetRenderer<AbstractWidget<?>> {
    private FilterType type;
    private ProcessedObject[] values;

    public Filter(FilterType type, ProcessedObject... values) {
        this.type = type;
        this.values = values;
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPre(AbstractWidget<?> widget, ScreenContext context) {
        switch (this.type) {
            case BLUR:
                renderBlur(widget, context);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.type));
        }
    }

    @Override // net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer
    public void renderPost(AbstractWidget<?> widget, ScreenContext context) {
    }

    public FilterType getType() {
        return this.type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    @Override // net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable
    public Filter interpolate(CubicBezier interpolator, Filter from, long fromMillis, long toMillis, long currentMillis) {
        ProcessedObject fromProcessedObject = from.values[0];
        float interpolated = interpolator.interpolate(((Float) fromProcessedObject.value()).floatValue(), ((Float) this.values[0].value()).floatValue(), fromMillis, toMillis, currentMillis);
        ProcessedObject newProcessedObject = new ProcessedObject(fromProcessedObject.postProcessor(), String.valueOf(interpolated), Float.valueOf(interpolated));
        return new Filter(this.type, newProcessedObject);
    }

    private void renderBlur(AbstractWidget<?> widget, ScreenContext context) {
        int radius;
        Object value = this.values[0].value();
        if (value == null || (radius = MathHelper.ceil(((Float) value).floatValue())) <= 0) {
            return;
        }
        DefaultFilters.BLUR_RENDERER.renderRectangle(context, widget, radius);
    }
}

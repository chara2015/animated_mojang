package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/AnimationTimingFunctionProcessor.class */
public class AnimationTimingFunctionProcessor implements PostProcessor {
    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        if (!(element instanceof Function)) {
            return CubicBezier.of(element.getRawValue()) != null;
        }
        Function function = (Function) element;
        return function.getName().equals("cubic-bezier");
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        if (element instanceof FixedElement) {
            String raw = element.getRawValue();
            CubicBezier cubicBezier = CubicBezier.of(raw);
            if (cubicBezier != null) {
                return cubicBezier;
            }
            throw new IllegalArgumentException("Unknown cubic-bezier: " + raw);
        }
        if (element instanceof Function) {
            Function function = (Function) element;
            return parse(function, function.firstValues(widget, key));
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public int getPriority() {
        return 2;
    }

    public static CubicBezier parse(Function function, ProcessedObject[] values) {
        if (!function.getName().equals("cubic-bezier")) {
            return null;
        }
        double p0 = ((Double) values[0].value()).doubleValue();
        double p1 = ((Double) values[1].value()).doubleValue();
        double p2 = ((Double) values[2].value()).doubleValue();
        double p3 = ((Double) values[3].value()).doubleValue();
        return new CubicBezier(p0, p1, p2, p3);
    }
}

package net.labymod.api.client.animation.interpolation.builtin;

import net.labymod.api.client.animation.interpolation.TypeInterpolator;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.PropertyAttributePatch;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;
import net.labymod.api.util.PrimitiveHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/builtin/AttributePatchInterpolator.class */
public class AttributePatchInterpolator implements TypeInterpolator<AttributePatch> {
    public static final AttributePatchInterpolator INSTANCE = new AttributePatchInterpolator();

    private AttributePatchInterpolator() {
    }

    @Override // net.labymod.api.client.animation.interpolation.TypeInterpolator
    public AttributePatch interpolate(AttributePatch from, AttributePatch to, double t, AttributePatch result) {
        if (from instanceof PropertyAttributePatch) {
            PropertyAttributePatch fromPatch = (PropertyAttributePatch) from;
            if (to instanceof PropertyAttributePatch) {
                PropertyAttributePatch toPatch = (PropertyAttributePatch) to;
                int size = Math.min(fromPatch.objects().length, toPatch.objects().length);
                ProcessedObject[] interpolated = new ProcessedObject[size];
                for (int i = 0; i < size; i++) {
                    ProcessedObject fromObj = fromPatch.objects()[i];
                    Object fromVal = fromObj.value();
                    if (fromVal instanceof Number) {
                        Number fromNum = (Number) fromVal;
                        Number toNum = (Number) toPatch.objects()[i].value();
                        double val = fromNum.doubleValue() + ((toNum.doubleValue() - fromNum.doubleValue()) * t);
                        interpolated[i] = new ProcessedObject(fromObj.postProcessor(), String.valueOf(val), PrimitiveHelper.convertToTarget(Double.valueOf(val), fromVal.getClass()));
                    } else if (fromVal instanceof Interpolatable) {
                        ProcessedObject toObj = toPatch.objects()[i];
                        interpolated[i] = new ProcessedObject(fromObj.postProcessor(), toObj.rawValue(), ((Interpolatable) toObj.value()).interpolate(CubicBezier.LINEAR, (Interpolatable) fromVal, 0L, 1000L, (long) (t * 1000.0d)));
                    } else {
                        interpolated[i] = fromPatch.objects()[i];
                    }
                }
                try {
                    return new PropertyAttributePatch(fromPatch.forwarder(), fromPatch.getType(), fromPatch.instruction(), fromPatch.element(), () -> {
                        return interpolated;
                    });
                } catch (Exception exception) {
                    throw new RuntimeException(exception);
                }
            }
        }
        return to;
    }
}

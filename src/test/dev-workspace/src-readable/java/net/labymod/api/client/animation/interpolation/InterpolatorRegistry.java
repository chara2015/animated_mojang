package net.labymod.api.client.animation.interpolation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.client.animation.interpolation.builtin.AttributePatchInterpolator;
import net.labymod.api.client.animation.interpolation.builtin.ColorInterpolator;
import net.labymod.api.client.animation.interpolation.builtin.DoubleInterpolator;
import net.labymod.api.client.animation.interpolation.builtin.FloatInterpolator;
import net.labymod.api.client.animation.interpolation.builtin.FloatVector2Interpolator;
import net.labymod.api.client.animation.interpolation.builtin.FloatVector3Interpolator;
import net.labymod.api.client.animation.interpolation.builtin.IntInterpolator;
import net.labymod.api.client.animation.interpolation.builtin.QuaternionInterpolator;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/interpolation/InterpolatorRegistry.class */
public final class InterpolatorRegistry {
    private static final InterpolatorRegistry INSTANCE = new InterpolatorRegistry();
    private final Map<Class<?>, TypeInterpolator<?>> interpolators = new ConcurrentHashMap();

    private InterpolatorRegistry() {
        register(Float.class, FloatInterpolator.INSTANCE);
        register(Integer.class, IntInterpolator.INSTANCE);
        register(Double.class, DoubleInterpolator.INSTANCE);
        register(FloatVector2.class, FloatVector2Interpolator.INSTANCE);
        register(FloatVector3.class, FloatVector3Interpolator.INSTANCE);
        register(Quaternion.class, QuaternionInterpolator.INSTANCE);
        register(Color.class, ColorInterpolator.INSTANCE);
        register(BorderRadius.class, new InterpolatableTypeInterpolator());
        register(Filter.class, new InterpolatableTypeInterpolator());
        register(AttributePatch.class, AttributePatchInterpolator.INSTANCE);
    }

    public static InterpolatorRegistry get() {
        return INSTANCE;
    }

    public <T> void register(Class<T> type, TypeInterpolator<T> interpolator) {
        this.interpolators.put(type, interpolator);
    }

    public <T> TypeInterpolator<T> find(Class<T> type) {
        return (TypeInterpolator) this.interpolators.get(type);
    }
}

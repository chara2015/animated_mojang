package net.labymod.api.client.animation.keyframe;

import net.labymod.api.client.animation.easing.EasingFunction;
import net.labymod.api.client.animation.easing.Easings;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/keyframe/Keyframe.class */
public class Keyframe<T> {
    private final long timeMillis;
    private final T value;
    private final EasingFunction easing;

    public Keyframe(long timeMillis, T value, EasingFunction easing) {
        this.timeMillis = timeMillis;
        this.value = value;
        this.easing = easing;
    }

    public Keyframe(long timeMillis, T value) {
        this(timeMillis, value, Easings.LINEAR);
    }

    public long timeMillis() {
        return this.timeMillis;
    }

    public T value() {
        return this.value;
    }

    public EasingFunction easing() {
        return this.easing;
    }
}

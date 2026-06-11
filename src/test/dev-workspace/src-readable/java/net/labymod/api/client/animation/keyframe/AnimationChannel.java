package net.labymod.api.client.animation.keyframe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.labymod.api.client.animation.interpolation.CatmullRomInterpolator;
import net.labymod.api.client.animation.interpolation.InterpolatorRegistry;
import net.labymod.api.client.animation.interpolation.TypeInterpolator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/animation/keyframe/AnimationChannel.class */
public class AnimationChannel<T> {
    private final Class<T> type;
    private final List<Keyframe<T>> keyframes;
    private final InterpolationMode mode;
    private final TypeInterpolator<T> interpolator;
    private T resultBuffer;
    private boolean sorted;

    public AnimationChannel(Class<T> type, InterpolationMode mode) {
        this.sorted = true;
        this.type = type;
        this.keyframes = new ArrayList();
        this.mode = mode;
        this.interpolator = InterpolatorRegistry.get().find(type);
    }

    public AnimationChannel(Class<T> type) {
        this(type, InterpolationMode.LINEAR);
    }

    public AnimationChannel<T> resultBuffer(T buffer) {
        this.resultBuffer = buffer;
        return this;
    }

    public AnimationChannel<T> addKeyframe(Keyframe<T> keyframe) {
        this.keyframes.add(keyframe);
        this.sorted = false;
        return this;
    }

    public AnimationChannel<T> addKeyframe(long timeMillis, T value) {
        return addKeyframe(new Keyframe<>(timeMillis, value));
    }

    public long duration() {
        if (this.keyframes.isEmpty()) {
            return 0L;
        }
        if (!this.sorted) {
            this.keyframes.sort(Comparator.comparingLong((v0) -> {
                return v0.timeMillis();
            }));
            this.sorted = true;
        }
        return this.keyframes.get(this.keyframes.size() - 1).timeMillis();
    }

    public T evaluate(long timestampMillis) {
        if (!this.sorted) {
            this.keyframes.sort(Comparator.comparingLong((v0) -> {
                return v0.timeMillis();
            }));
            this.sorted = true;
        }
        int size = this.keyframes.size();
        if (size == 0) {
            return null;
        }
        Keyframe<T> first = this.keyframes.get(0);
        if (timestampMillis <= first.timeMillis()) {
            return first.value();
        }
        Keyframe<T> last = this.keyframes.get(size - 1);
        if (timestampMillis >= last.timeMillis()) {
            return last.value();
        }
        int nextIndex = 0;
        int i = 1;
        while (true) {
            if (i >= size) {
                break;
            }
            if (this.keyframes.get(i).timeMillis() < timestampMillis) {
                i++;
            } else {
                nextIndex = i;
                break;
            }
        }
        Keyframe<T> from = this.keyframes.get(nextIndex - 1);
        Keyframe<T> to = this.keyframes.get(nextIndex);
        long segmentDuration = to.timeMillis() - from.timeMillis();
        double rawT = (timestampMillis - from.timeMillis()) / segmentDuration;
        double easedT = to.easing().apply(rawT);
        if (this.mode == InterpolationMode.STEP) {
            return rawT < 1.0d ? from.value() : to.value();
        }
        if (this.interpolator == null) {
            return from.value();
        }
        if (this.mode == InterpolationMode.CATMULL_ROM) {
            TypeInterpolator<T> typeInterpolator = this.interpolator;
            if (typeInterpolator instanceof CatmullRomInterpolator) {
                CatmullRomInterpolator<T> catmull = (CatmullRomInterpolator) typeInterpolator;
                T p0 = nextIndex >= 2 ? this.keyframes.get(nextIndex - 2).value() : from.value();
                T p3 = nextIndex + 1 < size ? this.keyframes.get(nextIndex + 1).value() : to.value();
                return catmull.catmullRom(p0, from.value(), to.value(), p3, easedT, this.resultBuffer);
            }
        }
        return this.interpolator.interpolate(from.value(), to.value(), easedT, this.resultBuffer);
    }

    public Class<T> type() {
        return this.type;
    }

    public List<Keyframe<T>> keyframes() {
        return this.keyframes;
    }

    public InterpolationMode mode() {
        return this.mode;
    }
}

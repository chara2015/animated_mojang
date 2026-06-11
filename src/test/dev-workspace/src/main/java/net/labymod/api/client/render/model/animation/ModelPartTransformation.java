package net.labymod.api.client.render.model.animation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelPartTransformation.class */
public class ModelPartTransformation implements MetadataExtension {
    private final TransformationType type;
    private final Keyframe defaultKeyframe;
    private Keyframe first;
    private Keyframe last;
    private boolean hasDynamicKeyframes;
    private final FloatVector3 interpolationResult = new FloatVector3();
    private final FloatVector3 resolveResult = new FloatVector3();
    private final List<Keyframe> keyframes = new ArrayList();
    private long length = 0;
    private Metadata metadata = Metadata.create();

    public ModelPartTransformation(TransformationType type, float defaultX, float defaultY, float defaultZ) {
        this.type = type;
        this.defaultKeyframe = new Keyframe(0L, false, defaultX, defaultY, defaultZ);
        this.first = this.defaultKeyframe;
        this.last = this.defaultKeyframe;
    }

    private static float calculateTransformation(float before, float from, float to, float after, float progress, float duration, boolean smooth) {
        if (from == to || duration == 0.0f || progress > duration) {
            return to;
        }
        if (smooth) {
            float progressPercentage = progress / duration;
            return MathHelper.catmullrom(progressPercentage, before, from, to, after);
        }
        return from - (((from - to) / duration) * progress);
    }

    public FloatVector3 get(long timestamp) {
        return get(timestamp, null);
    }

    public FloatVector3 get(long timestamp, @Nullable EvaluationContext context) {
        float prevX;
        float prevY;
        float prevZ;
        float curX;
        float curY;
        float curZ;
        float beforeX;
        float beforeY;
        float beforeZ;
        float afterX;
        float afterY;
        float afterZ;
        Keyframe previous = this.defaultKeyframe;
        for (int index = 0; index < this.keyframes.size(); index++) {
            Keyframe current = this.keyframes.get(index);
            if (current.getTimestamp() == timestamp) {
                return resolveKeyframe(current, context);
            }
            if (current.getTimestamp() >= timestamp) {
                boolean smooth = current.isSmooth();
                long progress = timestamp - previous.getTimestamp();
                long duration = current.getTimestamp() - previous.getTimestamp();
                if (context != null && previous.isDynamic()) {
                    FloatVector3 rp = resolveKeyframe(previous, context);
                    prevX = rp.getX();
                    prevY = rp.getY();
                    prevZ = rp.getZ();
                } else {
                    prevX = previous.getX();
                    prevY = previous.getY();
                    prevZ = previous.getZ();
                }
                if (context != null && current.isDynamic()) {
                    FloatVector3 rc = resolveKeyframe(current, context);
                    curX = rc.getX();
                    curY = rc.getY();
                    curZ = rc.getZ();
                } else {
                    curX = current.getX();
                    curY = current.getY();
                    curZ = current.getZ();
                }
                Keyframe beforeKf = this.keyframes.get(Math.max(index - 2, 0));
                if (context != null && beforeKf.isDynamic()) {
                    FloatVector3 rb = resolveKeyframe(beforeKf, context);
                    beforeX = rb.getX();
                    beforeY = rb.getY();
                    beforeZ = rb.getZ();
                } else {
                    beforeX = beforeKf.getX();
                    beforeY = beforeKf.getY();
                    beforeZ = beforeKf.getZ();
                }
                Keyframe afterKf = this.keyframes.get(Math.min(index + 1, this.keyframes.size() - 1));
                if (context != null && afterKf.isDynamic()) {
                    FloatVector3 ra = resolveKeyframe(afterKf, context);
                    afterX = ra.getX();
                    afterY = ra.getY();
                    afterZ = ra.getZ();
                } else {
                    afterX = afterKf.getX();
                    afterY = afterKf.getY();
                    afterZ = afterKf.getZ();
                }
                this.interpolationResult.set(calculateTransformation(beforeX, prevX, curX, afterX, progress, duration, smooth), calculateTransformation(beforeY, prevY, curY, afterY, progress, duration, smooth), calculateTransformation(beforeZ, prevZ, curZ, afterZ, progress, duration, smooth));
                return this.interpolationResult;
            }
            previous = current;
        }
        return resolveKeyframe(previous, context);
    }

    private FloatVector3 resolveKeyframe(Keyframe keyframe, @Nullable EvaluationContext context) {
        if (!keyframe.isDynamic()) {
            return keyframe;
        }
        this.resolveResult.set(keyframe.dynamicX != null ? (float) keyframe.dynamicX.resolve(context) : keyframe.getX(), keyframe.dynamicY != null ? (float) keyframe.dynamicY.resolve(context) : keyframe.getY(), keyframe.dynamicZ != null ? (float) keyframe.dynamicZ.resolve(context) : keyframe.getZ());
        return this.resolveResult;
    }

    public boolean hasKeyframe(long timestamp) {
        return !this.keyframes.isEmpty() && timestamp >= 0 && timestamp <= getLength();
    }

    public TransformationType type() {
        return this.type;
    }

    public Keyframe first() {
        return this.first;
    }

    public Keyframe last() {
        return this.last;
    }

    public Keyframe previous(Keyframe keyframe) {
        int index = this.keyframes.indexOf(keyframe) - 1;
        return index < 0 ? this.defaultKeyframe : this.keyframes.get(index);
    }

    public void addKeyframe(Keyframe keyframe) {
        this.keyframes.add(keyframe);
        this.keyframes.sort(Comparator.comparingLong((v0) -> {
            return v0.getTimestamp();
        }));
        this.first = this.keyframes.get(0);
        this.last = this.keyframes.get(this.keyframes.size() - 1);
        this.length = this.last.getTimestamp();
        if (keyframe.isDynamic()) {
            this.hasDynamicKeyframes = true;
        }
    }

    public boolean hasDynamicKeyframes() {
        return this.hasDynamicKeyframes;
    }

    public void filterKeyframes(Predicate<Keyframe> predicate) {
        Keyframe keyframe;
        this.keyframes.removeIf(keyframe2 -> {
            return !predicate.test(keyframe2);
        });
        this.first = this.keyframes.isEmpty() ? this.defaultKeyframe : this.keyframes.get(0);
        if (this.keyframes.isEmpty()) {
            keyframe = this.defaultKeyframe;
        } else {
            keyframe = this.keyframes.get(this.keyframes.size() - 1);
        }
        this.last = keyframe;
        this.length = this.last.getTimestamp();
    }

    public long getLength() {
        return this.length;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.metadata;
    }

    public ModelPartTransformation copy() {
        ModelPartTransformation copy = new ModelPartTransformation(this.type, this.defaultKeyframe.getX(), this.defaultKeyframe.getY(), this.defaultKeyframe.getZ());
        for (Keyframe keyframe : this.keyframes) {
            copy.addKeyframe(new Keyframe(keyframe.getTimestamp(), keyframe.isSmooth(), keyframe));
        }
        return copy;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/ModelPartTransformation$Keyframe.class */
    public static class Keyframe extends FloatVector3 {

        @Nullable
        final DynamicValue dynamicX;

        @Nullable
        final DynamicValue dynamicY;

        @Nullable
        final DynamicValue dynamicZ;
        private final long timestamp;
        private final boolean smooth;

        public Keyframe(long timestamp, boolean smooth, float x, float y, float z) {
            super(x, y, z);
            this.timestamp = timestamp;
            this.smooth = smooth;
            this.dynamicX = null;
            this.dynamicY = null;
            this.dynamicZ = null;
        }

        public Keyframe(long timestamp, boolean smooth, float staticX, float staticY, float staticZ, @Nullable DynamicValue dynamicX, @Nullable DynamicValue dynamicY, @Nullable DynamicValue dynamicZ) {
            super(staticX, staticY, staticZ);
            this.timestamp = timestamp;
            this.smooth = smooth;
            this.dynamicX = dynamicX;
            this.dynamicY = dynamicY;
            this.dynamicZ = dynamicZ;
        }

        public Keyframe(long timestamp, boolean smooth, FloatVector3 transformation) {
            super(transformation);
            this.timestamp = timestamp;
            this.smooth = smooth;
            this.dynamicX = null;
            this.dynamicY = null;
            this.dynamicZ = null;
        }

        public Keyframe(Keyframe keyframe) {
            this(keyframe.getTimestamp(), keyframe.isSmooth(), keyframe);
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public boolean isSmooth() {
            return this.smooth;
        }

        public boolean isDynamic() {
            return (this.dynamicX == null && this.dynamicY == null && this.dynamicZ == null) ? false : true;
        }
    }
}

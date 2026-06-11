package net.minecraft.util;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.attribute.LerpFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/KeyframeTrackSampler.class */
public class KeyframeTrackSampler<T> {
    private final Optional<Integer> periodTicks;
    private final LerpFunction<T> lerp;
    private final List<Segment<T>> segments;

    KeyframeTrackSampler(KeyframeTrack<T> $$0, Optional<Integer> $$1, LerpFunction<T> $$2) {
        this.periodTicks = $$1;
        this.lerp = $$2;
        this.segments = bakeSegments($$0, $$1);
    }

    private static <T> List<Segment<T>> bakeSegments(KeyframeTrack<T> $$0, Optional<Integer> $$1) {
        List<Keyframe<T>> $$2 = $$0.keyframes();
        if ($$2.size() == 1) {
            Object objValue = ((Keyframe) $$2.getFirst()).value();
            return List.of(new Segment(EasingType.CONSTANT, objValue, 0, objValue, 0));
        }
        List<Segment<T>> $$4 = new ArrayList<>();
        if ($$1.isPresent()) {
            Keyframe<T> $$5 = (Keyframe) $$2.getFirst();
            Keyframe<T> $$6 = (Keyframe) $$2.getLast();
            $$4.add(new Segment<>($$0, $$6, $$6.ticks() - $$1.get().intValue(), $$5, $$5.ticks()));
            addSegmentsFromKeyframes($$0, $$2, $$4);
            $$4.add(new Segment<>($$0, $$6, $$6.ticks(), $$5, $$5.ticks() + $$1.get().intValue()));
        } else {
            addSegmentsFromKeyframes($$0, $$2, $$4);
        }
        return List.copyOf($$4);
    }

    private static <T> void addSegmentsFromKeyframes(KeyframeTrack<T> $$0, List<Keyframe<T>> $$1, List<Segment<T>> $$2) {
        for (int $$3 = 0; $$3 < $$1.size() - 1; $$3++) {
            Keyframe<T> $$4 = $$1.get($$3);
            Keyframe<T> $$5 = $$1.get($$3 + 1);
            $$2.add(new Segment<>($$0, $$4, $$4.ticks(), $$5, $$5.ticks()));
        }
    }

    public T sample(long $$0) {
        long $$1 = loopTicks($$0);
        Segment<T> $$2 = getSegmentAt($$1);
        if ($$1 <= ((Segment) $$2).fromTicks) {
            return ((Segment) $$2).fromValue;
        }
        if ($$1 >= ((Segment) $$2).toTicks) {
            return ((Segment) $$2).toValue;
        }
        float $$3 = ($$1 - ((long) ((Segment) $$2).fromTicks)) / (((Segment) $$2).toTicks - ((Segment) $$2).fromTicks);
        float $$4 = ((Segment) $$2).easing.apply($$3);
        return this.lerp.apply($$4, ((Segment) $$2).fromValue, ((Segment) $$2).toValue);
    }

    private Segment<T> getSegmentAt(long $$0) {
        for (Segment<T> $$1 : this.segments) {
            if ($$0 < ((Segment) $$1).toTicks) {
                return $$1;
            }
        }
        return (Segment) this.segments.getLast();
    }

    private long loopTicks(long $$0) {
        if (this.periodTicks.isPresent()) {
            return Math.floorMod($$0, this.periodTicks.get().intValue());
        }
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/KeyframeTrackSampler$Segment.class */
    static final class Segment<T> extends Record {
        private final EasingType easing;
        private final T fromValue;
        private final int fromTicks;
        private final T toValue;
        private final int toTicks;

        Segment(EasingType $$0, T $$1, int $$2, T $$3, int $$4) {
            this.easing = $$0;
            this.fromValue = $$1;
            this.fromTicks = $$2;
            this.toValue = $$3;
            this.toTicks = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Segment.class), Segment.class, "easing;fromValue;fromTicks;toValue;toTicks", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->easing:Lnet/minecraft/util/EasingType;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromTicks:I", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toTicks:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Segment.class), Segment.class, "easing;fromValue;fromTicks;toValue;toTicks", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->easing:Lnet/minecraft/util/EasingType;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromTicks:I", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toTicks:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Segment.class, Object.class), Segment.class, "easing;fromValue;fromTicks;toValue;toTicks", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->easing:Lnet/minecraft/util/EasingType;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->fromTicks:I", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toValue:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/KeyframeTrackSampler$Segment;->toTicks:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public EasingType easing() {
            return this.easing;
        }

        public T fromValue() {
            return this.fromValue;
        }

        public int fromTicks() {
            return this.fromTicks;
        }

        public T toValue() {
            return this.toValue;
        }

        public int toTicks() {
            return this.toTicks;
        }

        public Segment(KeyframeTrack<T> $$0, Keyframe<T> $$1, int $$2, Keyframe<T> $$3, int $$4) {
            this($$0.easingType(), $$1.value(), $$2, $$3.value(), $$4);
        }
    }
}

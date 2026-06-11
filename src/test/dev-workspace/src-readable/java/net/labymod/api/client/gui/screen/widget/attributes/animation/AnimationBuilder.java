package net.labymod.api.client.gui.screen.widget.attributes.animation;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.animation.easing.EasingFunction;
import net.labymod.api.client.animation.easing.Easings;
import net.labymod.api.client.animation.playback.LoopMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/AnimationBuilder.class */
public class AnimationBuilder {
    private final Animation animation;
    private final String singleProperty;
    private List<PropertyConfig<?>> propertyConfigs;
    private long duration;
    private LoopMode loopMode;
    private float speed;
    private EasingFunction defaultEasing;
    private Runnable completionListener;
    private Object fromValue;
    private Object toValue;

    public AnimationBuilder(Animation animation) {
        this.duration = 300L;
        this.loopMode = LoopMode.NONE;
        this.speed = 1.0f;
        this.defaultEasing = Easings.EASE_IN_OUT;
        this.animation = animation;
        this.singleProperty = null;
    }

    public AnimationBuilder(Animation animation, String propertyName) {
        this.duration = 300L;
        this.loopMode = LoopMode.NONE;
        this.speed = 1.0f;
        this.defaultEasing = Easings.EASE_IN_OUT;
        this.animation = animation;
        this.singleProperty = propertyName;
    }

    public AnimationBuilder duration(long ms) {
        this.duration = ms;
        return this;
    }

    public AnimationBuilder loopMode(LoopMode mode) {
        this.loopMode = mode;
        return this;
    }

    public AnimationBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    public AnimationBuilder easing(EasingFunction easing) {
        this.defaultEasing = easing;
        return this;
    }

    public AnimationBuilder onComplete(Runnable handler) {
        this.completionListener = handler;
        return this;
    }

    public <T> AnimationBuilder from(T value) {
        this.fromValue = value;
        return this;
    }

    public <T> AnimationBuilder to(T value) {
        this.toValue = value;
        return this;
    }

    public <T> PropertyBuilder<T> property(String name, Class<T> type) {
        return new PropertyBuilder<>(this, name, type);
    }

    public void play() {
        this.animation.reset();
        this.animation.loopMode(this.loopMode);
        this.animation.speed(this.speed);
        if (this.completionListener != null) {
            this.animation.setCompletionListener(this.completionListener);
        }
        if (this.singleProperty != null && this.toValue != null) {
            AttributeAnimation attr = this.animation.getOrCreate(this.singleProperty, this.toValue.getClass());
            attr.clearKeyframes();
            if (this.fromValue != null) {
                attr.addKeyframe(this.fromValue, 0L, this.defaultEasing);
            }
            attr.addKeyframe(this.toValue, this.duration, this.defaultEasing);
        }
        if (this.propertyConfigs != null) {
            for (PropertyConfig<?> propertyConfig : this.propertyConfigs) {
                AttributeAnimation attr2 = this.animation.getOrCreate(propertyConfig.name, propertyConfig.type);
                attr2.clearKeyframes();
                if (propertyConfig.fromValue != 0) {
                    attr2.addKeyframe(propertyConfig.fromValue, 0L, propertyConfig.easing);
                }
                if (propertyConfig.toValue != 0) {
                    attr2.addKeyframe(propertyConfig.toValue, this.duration, propertyConfig.easing);
                }
            }
        }
        this.animation.start();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/AnimationBuilder$PropertyBuilder.class */
    public static class PropertyBuilder<T> {
        private final AnimationBuilder parent;
        private final String name;
        private final Class<T> type;
        private EasingFunction easing;
        private T fromValue;
        private T toValue;

        PropertyBuilder(AnimationBuilder parent, String name, Class<T> type) {
            this.parent = parent;
            this.name = name;
            this.type = type;
        }

        public PropertyBuilder<T> from(T value) {
            this.fromValue = value;
            return this;
        }

        public PropertyBuilder<T> to(T value) {
            this.toValue = value;
            return this;
        }

        public PropertyBuilder<T> easing(EasingFunction easing) {
            this.easing = easing;
            return this;
        }

        public AnimationBuilder and() {
            EasingFunction easingToUse = this.easing != null ? this.easing : this.parent.defaultEasing;
            if (this.parent.propertyConfigs == null) {
                this.parent.propertyConfigs = new ArrayList();
            }
            this.parent.propertyConfigs.add(new PropertyConfig<>(this.name, this.type, this.fromValue, this.toValue, easingToUse));
            return this.parent;
        }

        public void play() {
            and().play();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/AnimationBuilder$PropertyConfig.class */
    private static class PropertyConfig<T> {
        final String name;
        final Class<T> type;
        final T fromValue;
        final T toValue;
        final EasingFunction easing;

        PropertyConfig(String name, Class<T> type, T fromValue, T toValue, EasingFunction easing) {
            this.name = name;
            this.type = type;
            this.fromValue = fromValue;
            this.toValue = toValue;
            this.easing = easing;
        }
    }
}

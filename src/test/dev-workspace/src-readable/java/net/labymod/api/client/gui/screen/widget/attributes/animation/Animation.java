package net.labymod.api.client.gui.screen.widget.attributes.animation;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.Laby;
import net.labymod.api.client.animation.KeyframeAnimation;
import net.labymod.api.client.animation.playback.AnimationListener;
import net.labymod.api.client.animation.playback.LoopMode;
import net.labymod.api.client.animation.playback.PlaybackState;
import net.labymod.api.client.gui.lss.property.PropertyRegistry;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/animation/Animation.class */
public class Animation {
    private final Widget widget;
    private KeyframeAnimation keyframeAnimation;
    private Runnable completionListener;
    private LoopMode pendingLoopMode = LoopMode.NONE;
    private float pendingSpeed = 1.0f;
    private final Map<String, AttributeAnimation<?>> attributeAnimations = new ConcurrentHashMap();
    private final PropertyRegistry propertyRegistry = Laby.references().propertyRegistry();

    public Animation(Widget widget) {
        this.widget = widget;
    }

    public void start() {
        this.keyframeAnimation = new KeyframeAnimation();
        this.keyframeAnimation.loopMode(this.pendingLoopMode);
        this.keyframeAnimation.speed(this.pendingSpeed);
        for (Map.Entry<String, AttributeAnimation<?>> entry : this.attributeAnimations.entrySet()) {
            this.keyframeAnimation.addChannel(entry.getKey(), entry.getValue().getChannel());
        }
        if (this.completionListener != null) {
            final Runnable handler = this.completionListener;
            this.keyframeAnimation.addListener(new AnimationListener(this) { // from class: net.labymod.api.client.gui.screen.widget.attributes.animation.Animation.1
                @Override // net.labymod.api.client.animation.playback.AnimationListener
                public void onComplete(KeyframeAnimation animation) {
                    handler.run();
                }
            });
        }
        this.keyframeAnimation.play();
    }

    public Animation loopMode(LoopMode mode) {
        this.pendingLoopMode = mode;
        return this;
    }

    public Animation speed(float speed) {
        this.pendingSpeed = speed;
        return this;
    }

    public void pause() {
        if (this.keyframeAnimation != null) {
            this.keyframeAnimation.pause();
        }
    }

    public void resume() {
        if (this.keyframeAnimation != null) {
            this.keyframeAnimation.play();
        }
    }

    public boolean isPlaying() {
        return this.keyframeAnimation != null && this.keyframeAnimation.state() == PlaybackState.PLAYING;
    }

    public boolean isPaused() {
        return this.keyframeAnimation != null && this.keyframeAnimation.state() == PlaybackState.PAUSED;
    }

    public PlaybackState state() {
        if (this.keyframeAnimation == null) {
            return PlaybackState.IDLE;
        }
        return this.keyframeAnimation.state();
    }

    public void remove(String attributeName) {
        Object value;
        AttributeAnimation<?> attributeAnimationRemove = this.attributeAnimations.remove(attributeName);
        if (attributeAnimationRemove != null && (value = attributeAnimationRemove.lastValue()) != null) {
            attributeAnimationRemove.getAccessor().set(value);
        }
    }

    public void apply(float partialTicks) {
        if (this.attributeAnimations.isEmpty() || this.keyframeAnimation == null) {
            return;
        }
        PlaybackState stateBefore = this.keyframeAnimation.state();
        if (stateBefore != PlaybackState.PLAYING) {
            return;
        }
        this.keyframeAnimation.update(TimeUtil.convertDeltaToMilliseconds(partialTicks));
        long timestamp = this.keyframeAnimation.resolveTimestamp();
        for (Map.Entry<String, AttributeAnimation<?>> entry : this.attributeAnimations.entrySet()) {
            AttributeAnimation<?> value = entry.getValue();
            Object value2 = value.getChannel().evaluate(timestamp);
            if (value2 != null) {
                value.getAccessor().set(value2);
            }
        }
    }

    public Map<String, AttributeAnimation<?>> getAttributeAnimations() {
        return this.attributeAnimations;
    }

    public void reset() {
        this.attributeAnimations.clear();
        this.completionListener = null;
        this.pendingLoopMode = LoopMode.NONE;
        this.pendingSpeed = 1.0f;
        if (this.keyframeAnimation != null) {
            this.keyframeAnimation.stop();
            this.keyframeAnimation = null;
        }
    }

    public void setCompletionListener(Runnable completionListener) {
        this.completionListener = completionListener;
    }

    public <T> AttributeAnimation<T> getOrCreate(String attributeName, Class<T> attributeType) {
        AttributeAnimation<T> attributeAnimation = (AttributeAnimation) this.attributeAnimations.get(attributeName);
        if (attributeAnimation != null) {
            Class<T> type = attributeAnimation.getAccessor().getType();
            if (!type.equals(attributeType)) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Invalid type supplied (Accessor: %s, Attribute: %s)", type.getName(), attributeType.getName()));
            }
            return attributeAnimation;
        }
        PropertyValueAccessor<?, ?, ?> accessor = this.propertyRegistry.getValueAccessor(this.widget, attributeName);
        if (accessor == null) {
            throw new IllegalArgumentException("Invalid attribute supplied");
        }
        AttributeAnimation<T> attributeAnimation2 = new AttributeAnimation<>(new PropertyValueAccessorAttributeAnimationAccessor(this.widget, accessor, attributeName));
        this.attributeAnimations.put(attributeName, attributeAnimation2);
        return attributeAnimation2;
    }

    public <T> AttributeAnimation<T> createCustom(String attributeName, AttributeAnimationAccessor<T> field) {
        AttributeAnimation<T> attributeAnimation = new AttributeAnimation<>(field);
        this.attributeAnimations.put(attributeName, attributeAnimation);
        return attributeAnimation;
    }
}

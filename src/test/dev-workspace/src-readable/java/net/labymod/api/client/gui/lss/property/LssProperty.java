package net.labymod.api.client.gui.lss.property;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.property.Property;
import net.labymod.api.property.PropertyConvention;
import net.labymod.api.util.function.ChangeListener;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/property/LssProperty.class */
public class LssProperty<T> extends Property<T> {
    private static final String PERCENTAGE_POST_PROCESSOR = "net.labymod.core.client.gui.lss.style.modifier.processors.PercentagePostProcessor";
    private static final FrameTimer FRAME_TIMER = Laby.references().frameTimer();
    private ProcessedObject processedValue;
    private int lastUpdatedFrame;

    @ApiStatus.Experimental
    private T lastLerpedAccessedValue;

    @ApiStatus.Experimental
    private T previousLerpedAccessedValue;

    @ApiStatus.Experimental
    private long millisChanged;

    public LssProperty(T value) {
        super(value);
        this.lastUpdatedFrame = -1;
        this.millisChanged = -1L;
        this.previousLerpedAccessedValue = value;
    }

    public LssProperty(T value, PropertyConvention<T> propertyConvention) {
        super(value, propertyConvention);
        this.lastUpdatedFrame = -1;
        this.millisChanged = -1L;
        this.previousLerpedAccessedValue = value;
    }

    @Override // net.labymod.api.property.Property
    public void set(T value) {
        set(value, null);
    }

    public void set(T value, ProcessedObject processedValue) {
        boolean changed = !Objects.equals(value, this.value);
        if (changed) {
            if (this.propertyConvention != null) {
                value = applyConvention(value);
            }
            T oldValue = this.value;
            if (this.lastLerpedAccessedValue == null) {
                this.millisChanged = 0L;
                this.previousLerpedAccessedValue = value;
            } else {
                this.millisChanged = TimeUtil.getMillis();
                this.previousLerpedAccessedValue = this.lastLerpedAccessedValue;
            }
            this.value = value;
            this.processedValue = processedValue;
            this.lastUpdatedFrame = FRAME_TIMER.getFrame();
            for (ChangeListener<Property<T>, T> listener : this.changeListeners) {
                listener.changed(this, oldValue, value);
            }
        }
    }

    public ProcessedObject getProcessedValue() {
        return this.processedValue;
    }

    public PostProcessor getPostProcessor() {
        if (this.processedValue != null) {
            return this.processedValue.postProcessor();
        }
        return null;
    }

    public boolean isPercentage() {
        PostProcessor postProcessor = getPostProcessor();
        return postProcessor != null && postProcessor.getClass().getName().equals(PERCENTAGE_POST_PROCESSOR);
    }

    public boolean wasUpdatedThisFrame() {
        return this.lastUpdatedFrame == Laby.references().frameTimer().getFrame();
    }

    @ApiStatus.Experimental
    public float getProgress(float partialTicks, long duration) {
        if (this.millisChanged == -1) {
            return 1.0f;
        }
        long millis = TimeUtil.getMillis();
        long millisSinceChanged = millis - this.millisChanged;
        if (millisSinceChanged >= duration) {
            return 1.0f;
        }
        return (millisSinceChanged + partialTicks) / duration;
    }

    @ApiStatus.Experimental
    public void setLastLerpedAccessedValue(T lastLerpedAccessedValue) {
        this.lastLerpedAccessedValue = lastLerpedAccessedValue;
    }

    @ApiStatus.Experimental
    public T getPreviousLerpedAccessedValue() {
        return this.previousLerpedAccessedValue;
    }
}

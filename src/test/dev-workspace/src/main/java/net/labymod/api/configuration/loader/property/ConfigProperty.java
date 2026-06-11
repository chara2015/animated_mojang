package net.labymod.api.configuration.loader.property;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.SettingElement;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.config.SettingResetEvent;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.property.Property;
import net.labymod.api.property.PropertyConvention;
import net.labymod.api.util.function.ChangeListener;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/property/ConfigProperty.class */
public class ConfigProperty<T> extends Property<T> {
    private final Class<T> type;
    private Type genericType;
    private SettingElement setting;
    private SettingHandler handler;
    private boolean withPseudoValue;
    private T pseudoValue;

    @Nullable
    private CustomRequires<T> customRequires;
    private BooleanSupplier visibilitySupplier;

    public ConfigProperty(T t) {
        super(t);
        Objects.requireNonNull(t, "Null provided for ConfigProperty value");
        this.type = (Class<T>) t.getClass();
    }

    public ConfigProperty(T t, PropertyConvention<T> propertyConvention) {
        super(t, propertyConvention);
        Objects.requireNonNull(t, "Null provided for ConfigProperty value");
        this.type = (Class<T>) t.getClass();
    }

    public static <E extends Enum<E>> ConfigProperty<E> createEnum(E value) {
        return new ConfigProperty<>(value, new DefaultEnumPropertyConvention(value));
    }

    public static <T> ConfigProperty<T> create(T value, Consumer<T> consumer) {
        consumer.accept(value);
        return new ConfigProperty<>(value);
    }

    public static <T> ConfigProperty<T> create(T value) {
        return new ConfigProperty<>(value);
    }

    @ApiStatus.Internal
    public final void withSettings(SettingElement setting) {
        this.setting = setting;
        this.setting.setHandler(this.handler);
    }

    public ConfigProperty<T> withHandler(SettingHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override // net.labymod.api.property.Property
    public T get() {
        return this.withPseudoValue ? this.pseudoValue : this.value;
    }

    @Override // net.labymod.api.property.Property
    public T getOrDefault(T defaultValue) {
        T value = this.withPseudoValue ? this.pseudoValue : this.value;
        return value == null ? defaultValue : value;
    }

    @Override // net.labymod.api.property.Property
    public boolean isDefaultValue() {
        return isDefaultValue(this.withPseudoValue ? this.pseudoValue : this.value);
    }

    @Override // net.labymod.api.property.Property
    public void set(T t) {
        if (Objects.equals(this.value, t)) {
            return;
        }
        boolean z = (this.setting == null || !this.setting.isInitialized() || this.withPseudoValue) ? false : true;
        if (z) {
            t = (T) fireEvent(Phase.PRE, t).getValue();
        }
        T t2 = this.value;
        boolean z2 = !Objects.equals(t2, t);
        if (z2) {
            t = applyConvention(t);
        }
        this.value = t;
        if (z2 && !this.withPseudoValue) {
            notifyChangeListeners(this, t, t2);
        }
        if (z) {
            fireEvent(Phase.POST, t);
        }
    }

    @Override // net.labymod.api.property.Property
    public void reset() {
        boolean initialized = this.setting != null && this.setting.isInitialized();
        if (initialized) {
            Laby.fireEvent(new SettingResetEvent(Phase.PRE, this.setting));
        }
        super.reset();
        if (initialized) {
            Laby.fireEvent(new SettingResetEvent(Phase.POST, this.setting));
        }
    }

    public Class<T> getType() {
        return this.type;
    }

    @Nullable
    public CustomRequires<T> getCustomRequires() {
        return this.customRequires;
    }

    public ConfigProperty<T> customRequires(@Nullable CustomRequires<T> customRequires) {
        this.customRequires = customRequires;
        return this;
    }

    @Nullable
    public BooleanSupplier getVisibilitySupplier() {
        return this.visibilitySupplier;
    }

    public ConfigProperty<T> visibilitySupplier(@Nullable BooleanSupplier visibilitySupplier) {
        this.visibilitySupplier = visibilitySupplier;
        return this;
    }

    public ConfigProperty<T> visibilitySupplier(ConfigProperty<Boolean> property) {
        Objects.requireNonNull(property);
        return visibilitySupplier(property::get);
    }

    @Nullable
    public Type getGenericType() {
        return this.genericType;
    }

    @ApiStatus.Internal
    public void setGenericType(Type genericType) {
        this.genericType = genericType;
    }

    @ApiStatus.Internal
    public void withPseudoValue(T pseudoValue) {
        this.withPseudoValue = true;
        setPseudoValue(pseudoValue);
    }

    @ApiStatus.Internal
    public boolean hasPseudoValue() {
        return this.withPseudoValue;
    }

    @ApiStatus.Internal
    public T getActualValue() {
        return (T) super.get();
    }

    @ApiStatus.Internal
    public void withoutPseudoValue() {
        this.withPseudoValue = false;
        setPseudoValue(null);
    }

    private SettingUpdateEvent fireEvent(Phase phase, T value) {
        return (SettingUpdateEvent) Laby.fireEvent(new SettingUpdateEvent(phase, this.setting, value));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setPseudoValue(T pseudoValue) {
        Object value = this.withPseudoValue ? pseudoValue : this.value;
        T prevValue = this.withPseudoValue ? this.value : this.pseudoValue;
        if (Objects.equals(prevValue, value)) {
            this.pseudoValue = pseudoValue;
            return;
        }
        boolean initialized = this.setting != null && this.setting.isInitialized();
        if (initialized) {
            SettingUpdateEvent event = fireEvent(Phase.PRE, value);
            value = event.getValue();
        }
        this.pseudoValue = pseudoValue;
        notifyChangeListeners(this, value, prevValue);
        if (initialized) {
            fireEvent(Phase.POST, value);
        }
    }

    private void notifyChangeListeners(Property<T> property, T newValue, T oldValue) {
        for (ChangeListener<Property<T>, T> listener : this.changeListeners) {
            try {
                listener.changed(property, oldValue, newValue);
            } catch (Throwable throwable) {
                listener.onException(throwable);
            }
        }
    }
}

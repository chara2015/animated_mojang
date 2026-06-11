package net.labymod.util.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/util/property/LongSystemProperty.class */
public class LongSystemProperty extends SystemProperty<Long> {
    private final long defaultValue;

    public LongSystemProperty(String key) {
        this(key, 0L);
    }

    public LongSystemProperty(String key, long defaultValue) {
        super(key);
        this.defaultValue = defaultValue;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.util.property.SystemProperty
    public Long get() {
        String value = System.getProperty(getKey());
        if (value == null) {
            return Long.valueOf(this.defaultValue);
        }
        try {
            return Long.valueOf(Long.parseLong(value));
        } catch (NumberFormatException e) {
            return Long.valueOf(this.defaultValue);
        }
    }

    public long getDefaultValue() {
        return this.defaultValue;
    }
}

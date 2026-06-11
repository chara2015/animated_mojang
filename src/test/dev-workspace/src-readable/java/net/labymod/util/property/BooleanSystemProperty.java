package net.labymod.util.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/util/property/BooleanSystemProperty.class */
public class BooleanSystemProperty extends SystemProperty<Boolean> {
    public BooleanSystemProperty(String key) {
        super(key);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.util.property.SystemProperty
    public Boolean get() {
        return Boolean.valueOf(Boolean.getBoolean(getKey()));
    }
}

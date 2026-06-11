package net.labymod.api.mojang;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/Property.class */
public class Property {
    private final String name;
    private final String value;
    private final String signature;

    public Property(String value, String name) {
        this(value, name, null);
    }

    public Property(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getSignature() {
        return this.signature;
    }

    public boolean hasSignature() {
        return this.signature != null;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        return Objects.equals(this.name, property.name) && Objects.equals(this.value, property.value) && Objects.equals(this.signature, property.signature);
    }

    public int hashCode() {
        int result = this.name != null ? this.name.hashCode() : 0;
        return (31 * ((31 * result) + (this.value != null ? this.value.hashCode() : 0))) + (this.signature != null ? this.signature.hashCode() : 0);
    }
}

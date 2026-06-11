package net.labymod.api.client.component.format.numbers;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/numbers/FixedFormat.class */
public final class FixedFormat extends Record implements NumberFormat {
    private final Component value;

    public FixedFormat(Component value) {
        this.value = value;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FixedFormat.class), FixedFormat.class, "value", "FIELD:Lnet/labymod/api/client/component/format/numbers/FixedFormat;->value:Lnet/labymod/api/client/component/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FixedFormat.class), FixedFormat.class, "value", "FIELD:Lnet/labymod/api/client/component/format/numbers/FixedFormat;->value:Lnet/labymod/api/client/component/Component;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FixedFormat.class, Object.class), FixedFormat.class, "value", "FIELD:Lnet/labymod/api/client/component/format/numbers/FixedFormat;->value:Lnet/labymod/api/client/component/Component;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Component value() {
        return this.value;
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormat
    public Component format(int number) {
        return this.value.copy();
    }
}

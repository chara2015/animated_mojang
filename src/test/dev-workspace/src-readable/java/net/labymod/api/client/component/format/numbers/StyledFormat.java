package net.labymod.api.client.component.format.numbers;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/numbers/StyledFormat.class */
public final class StyledFormat extends Record implements NumberFormat {
    private final Style style;
    public static final StyledFormat NO_STYLE = new StyledFormat(Style.empty());
    public static final StyledFormat SIDEBAR_DEFAULT = new StyledFormat(Style.builder().color(NamedTextColor.RED).build());
    public static final StyledFormat PLAYER_LIST_DEFAULT = new StyledFormat(Style.builder().color(NamedTextColor.YELLOW).build());

    public StyledFormat(Style style) {
        this.style = style;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StyledFormat.class), StyledFormat.class, "style", "FIELD:Lnet/labymod/api/client/component/format/numbers/StyledFormat;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StyledFormat.class), StyledFormat.class, "style", "FIELD:Lnet/labymod/api/client/component/format/numbers/StyledFormat;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StyledFormat.class, Object.class), StyledFormat.class, "style", "FIELD:Lnet/labymod/api/client/component/format/numbers/StyledFormat;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Style style() {
        return this.style;
    }

    @Override // net.labymod.api.client.component.format.numbers.NumberFormat
    public Component format(int number) {
        return Component.text(Integer.toString(number), this.style);
    }
}

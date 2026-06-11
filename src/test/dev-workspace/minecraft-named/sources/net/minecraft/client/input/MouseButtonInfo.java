package net.minecraft.client.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.input.InputWithModifiers;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/MouseButtonInfo.class */
public final class MouseButtonInfo extends Record implements InputWithModifiers {

    @MouseButton
    private final int button;

    @InputWithModifiers.Modifiers
    private final int modifiers;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/MouseButtonInfo$Action.class */
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Action {
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/MouseButtonInfo$MouseButton.class */
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    public @interface MouseButton {
    }

    public MouseButtonInfo(@MouseButton int $$0, @InputWithModifiers.Modifiers int $$1) {
        this.button = $$0;
        this.modifiers = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MouseButtonInfo.class), MouseButtonInfo.class, "button;modifiers", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->button:I", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MouseButtonInfo.class), MouseButtonInfo.class, "button;modifiers", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->button:I", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MouseButtonInfo.class, Object.class), MouseButtonInfo.class, "button;modifiers", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->button:I", "FIELD:Lnet/minecraft/client/input/MouseButtonInfo;->modifiers:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @MouseButton
    public int button() {
        return this.button;
    }

    @Override // net.minecraft.client.input.InputWithModifiers
    @InputWithModifiers.Modifiers
    public int modifiers() {
        return this.modifiers;
    }

    @Override // net.minecraft.client.input.InputWithModifiers
    @MouseButton
    public int input() {
        return this.button;
    }
}

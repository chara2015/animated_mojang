package net.minecraft.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.input.InputWithModifiers;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/KeyEvent.class */
public final class KeyEvent extends Record implements InputWithModifiers {

    @InputConstants.Value
    private final int key;
    private final int scancode;

    @InputWithModifiers.Modifiers
    private final int modifiers;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/KeyEvent$Action.class */
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Action {
    }

    public KeyEvent(@InputConstants.Value int $$0, int $$1, @InputWithModifiers.Modifiers int $$2) {
        this.key = $$0;
        this.scancode = $$1;
        this.modifiers = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, KeyEvent.class), KeyEvent.class, "key;scancode;modifiers", "FIELD:Lnet/minecraft/client/input/KeyEvent;->key:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->scancode:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, KeyEvent.class), KeyEvent.class, "key;scancode;modifiers", "FIELD:Lnet/minecraft/client/input/KeyEvent;->key:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->scancode:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, KeyEvent.class, Object.class), KeyEvent.class, "key;scancode;modifiers", "FIELD:Lnet/minecraft/client/input/KeyEvent;->key:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->scancode:I", "FIELD:Lnet/minecraft/client/input/KeyEvent;->modifiers:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @InputConstants.Value
    public int key() {
        return this.key;
    }

    public int scancode() {
        return this.scancode;
    }

    @Override // net.minecraft.client.input.InputWithModifiers
    @InputWithModifiers.Modifiers
    public int modifiers() {
        return this.modifiers;
    }

    @Override // net.minecraft.client.input.InputWithModifiers
    public int input() {
        return this.key;
    }
}

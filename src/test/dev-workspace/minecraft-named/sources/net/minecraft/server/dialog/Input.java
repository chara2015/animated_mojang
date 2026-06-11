package net.minecraft.server.dialog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.dialog.action.ParsedTemplate;
import net.minecraft.server.dialog.input.InputControl;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/Input.class */
public final class Input extends Record {
    private final String key;
    private final InputControl control;
    public static final Codec<Input> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ParsedTemplate.VARIABLE_CODEC.fieldOf("key").forGetter((v0) -> {
            return v0.key();
        }), InputControl.MAP_CODEC.forGetter((v0) -> {
            return v0.control();
        })).apply($$0, Input::new);
    });

    public Input(String $$0, InputControl $$1) {
        this.key = $$0;
        this.control = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Input.class), Input.class, "key;control", "FIELD:Lnet/minecraft/server/dialog/Input;->key:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/Input;->control:Lnet/minecraft/server/dialog/input/InputControl;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Input.class), Input.class, "key;control", "FIELD:Lnet/minecraft/server/dialog/Input;->key:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/Input;->control:Lnet/minecraft/server/dialog/input/InputControl;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Input.class, Object.class), Input.class, "key;control", "FIELD:Lnet/minecraft/server/dialog/Input;->key:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/Input;->control:Lnet/minecraft/server/dialog/input/InputControl;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String key() {
        return this.key;
    }

    public InputControl control() {
        return this.control;
    }
}

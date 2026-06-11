package net.minecraft.server.dialog.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/input/BooleanInput.class */
public final class BooleanInput extends Record implements InputControl {
    private final Component label;
    private final boolean initial;
    private final String onTrue;
    private final String onFalse;
    public static final MapCodec<BooleanInput> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ComponentSerialization.CODEC.fieldOf("label").forGetter((v0) -> {
            return v0.label();
        }), Codec.BOOL.optionalFieldOf("initial", false).forGetter((v0) -> {
            return v0.initial();
        }), Codec.STRING.optionalFieldOf("on_true", SnbtOperations.BUILTIN_TRUE).forGetter((v0) -> {
            return v0.onTrue();
        }), Codec.STRING.optionalFieldOf("on_false", SnbtOperations.BUILTIN_FALSE).forGetter((v0) -> {
            return v0.onFalse();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new BooleanInput(v1, v2, v3, v4);
        });
    });

    public BooleanInput(Component $$0, boolean $$1, String $$2, String $$3) {
        this.label = $$0;
        this.initial = $$1;
        this.onTrue = $$2;
        this.onFalse = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BooleanInput.class), BooleanInput.class, "label;initial;onTrue;onFalse", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->initial:Z", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onTrue:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onFalse:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BooleanInput.class), BooleanInput.class, "label;initial;onTrue;onFalse", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->initial:Z", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onTrue:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onFalse:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BooleanInput.class, Object.class), BooleanInput.class, "label;initial;onTrue;onFalse", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->initial:Z", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onTrue:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/dialog/input/BooleanInput;->onFalse:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Component label() {
        return this.label;
    }

    public boolean initial() {
        return this.initial;
    }

    public String onTrue() {
        return this.onTrue;
    }

    public String onFalse() {
        return this.onFalse;
    }

    @Override // net.minecraft.server.dialog.input.InputControl
    public MapCodec<BooleanInput> mapCodec() {
        return MAP_CODEC;
    }
}

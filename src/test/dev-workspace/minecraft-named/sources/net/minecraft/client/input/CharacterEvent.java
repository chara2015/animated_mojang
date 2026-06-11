package net.minecraft.client.input;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.util.StringUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/input/CharacterEvent.class */
public final class CharacterEvent extends Record {
    private final int codepoint;

    @InputWithModifiers.Modifiers
    private final int modifiers;

    public CharacterEvent(int $$0, @InputWithModifiers.Modifiers int $$1) {
        this.codepoint = $$0;
        this.modifiers = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CharacterEvent.class), CharacterEvent.class, "codepoint;modifiers", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->codepoint:I", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CharacterEvent.class), CharacterEvent.class, "codepoint;modifiers", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->codepoint:I", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->modifiers:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CharacterEvent.class, Object.class), CharacterEvent.class, "codepoint;modifiers", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->codepoint:I", "FIELD:Lnet/minecraft/client/input/CharacterEvent;->modifiers:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int codepoint() {
        return this.codepoint;
    }

    @InputWithModifiers.Modifiers
    public int modifiers() {
        return this.modifiers;
    }

    public String codepointAsString() {
        return Character.toString(this.codepoint);
    }

    public boolean isAllowedChatCharacter() {
        return StringUtil.isAllowedChatCharacter(this.codepoint);
    }
}

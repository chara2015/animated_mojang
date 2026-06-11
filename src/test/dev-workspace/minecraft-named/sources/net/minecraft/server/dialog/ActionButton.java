package net.minecraft.server.dialog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.server.dialog.action.Action;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/ActionButton.class */
public final class ActionButton extends Record {
    private final CommonButtonData button;
    private final Optional<Action> action;
    public static final Codec<ActionButton> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(CommonButtonData.MAP_CODEC.forGetter((v0) -> {
            return v0.button();
        }), Action.CODEC.optionalFieldOf("action").forGetter((v0) -> {
            return v0.action();
        })).apply($$0, ActionButton::new);
    });

    public ActionButton(CommonButtonData $$0, Optional<Action> $$1) {
        this.button = $$0;
        this.action = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ActionButton.class), ActionButton.class, "button;action", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->button:Lnet/minecraft/server/dialog/CommonButtonData;", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->action:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ActionButton.class), ActionButton.class, "button;action", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->button:Lnet/minecraft/server/dialog/CommonButtonData;", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->action:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ActionButton.class, Object.class), ActionButton.class, "button;action", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->button:Lnet/minecraft/server/dialog/CommonButtonData;", "FIELD:Lnet/minecraft/server/dialog/ActionButton;->action:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public CommonButtonData button() {
        return this.button;
    }

    public Optional<Action> action() {
        return this.action;
    }
}

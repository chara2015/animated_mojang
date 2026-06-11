package net.labymod.core.client.world.canvas;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.signobject.PlacedSignObject;
import net.labymod.api.client.world.signobject.object.SignObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/canvas/DefaultPlacedSignObject.class */
public final class DefaultPlacedSignObject extends Record implements PlacedSignObject {
    private final SignBlockEntity sign;
    private final SignObject<?>[] objects;

    public DefaultPlacedSignObject(SignBlockEntity sign, SignObject<?>[] objects) {
        this.sign = sign;
        this.objects = objects;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DefaultPlacedSignObject.class), DefaultPlacedSignObject.class, "sign;objects", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->sign:Lnet/labymod/api/client/blockentity/SignBlockEntity;", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->objects:[Lnet/labymod/api/client/world/signobject/object/SignObject;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DefaultPlacedSignObject.class), DefaultPlacedSignObject.class, "sign;objects", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->sign:Lnet/labymod/api/client/blockentity/SignBlockEntity;", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->objects:[Lnet/labymod/api/client/world/signobject/object/SignObject;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DefaultPlacedSignObject.class, Object.class), DefaultPlacedSignObject.class, "sign;objects", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->sign:Lnet/labymod/api/client/blockentity/SignBlockEntity;", "FIELD:Lnet/labymod/core/client/world/canvas/DefaultPlacedSignObject;->objects:[Lnet/labymod/api/client/world/signobject/object/SignObject;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Override // net.labymod.api.client.world.signobject.PlacedSignObject
    public SignBlockEntity sign() {
        return this.sign;
    }

    @Override // net.labymod.api.client.world.signobject.PlacedSignObject
    public SignObject<?>[] objects() {
        return this.objects;
    }

    @Override // net.labymod.api.client.world.signobject.PlacedSignObject
    public SignObject<?> object(SignBlockEntity.SignSide side) {
        return this.objects[side.ordinal()];
    }
}

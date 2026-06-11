package net.minecraft.client.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.model.Model;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/AdultAndBabyModelPair.class */
public final class AdultAndBabyModelPair<T extends Model> extends Record {
    private final T adultModel;
    private final T babyModel;

    public AdultAndBabyModelPair(T $$0, T $$1) {
        this.adultModel = $$0;
        this.babyModel = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AdultAndBabyModelPair.class), AdultAndBabyModelPair.class, "adultModel;babyModel", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->adultModel:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->babyModel:Lnet/minecraft/client/model/Model;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AdultAndBabyModelPair.class), AdultAndBabyModelPair.class, "adultModel;babyModel", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->adultModel:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->babyModel:Lnet/minecraft/client/model/Model;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AdultAndBabyModelPair.class, Object.class), AdultAndBabyModelPair.class, "adultModel;babyModel", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->adultModel:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/model/AdultAndBabyModelPair;->babyModel:Lnet/minecraft/client/model/Model;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public T adultModel() {
        return this.adultModel;
    }

    public T babyModel() {
        return this.babyModel;
    }

    public T getModel(boolean $$0) {
        return $$0 ? this.babyModel : this.adultModel;
    }
}

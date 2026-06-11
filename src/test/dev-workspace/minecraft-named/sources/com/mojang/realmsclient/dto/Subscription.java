package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.util.JsonUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import net.minecraft.util.LenientJsonParser;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/Subscription.class */
public final class Subscription extends Record {
    private final Instant startDate;
    private final int daysLeft;
    private final SubscriptionType type;
    private static final Logger LOGGER = LogUtils.getLogger();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/Subscription$SubscriptionType.class */
    public enum SubscriptionType {
        NORMAL,
        RECURRING
    }

    public Subscription(Instant $$0, int $$1, SubscriptionType $$2) {
        this.startDate = $$0;
        this.daysLeft = $$1;
        this.type = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Subscription.class), Subscription.class, "startDate;daysLeft;type", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->startDate:Ljava/time/Instant;", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->daysLeft:I", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->type:Lcom/mojang/realmsclient/dto/Subscription$SubscriptionType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Subscription.class), Subscription.class, "startDate;daysLeft;type", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->startDate:Ljava/time/Instant;", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->daysLeft:I", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->type:Lcom/mojang/realmsclient/dto/Subscription$SubscriptionType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Subscription.class, Object.class), Subscription.class, "startDate;daysLeft;type", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->startDate:Ljava/time/Instant;", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->daysLeft:I", "FIELD:Lcom/mojang/realmsclient/dto/Subscription;->type:Lcom/mojang/realmsclient/dto/Subscription$SubscriptionType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Instant startDate() {
        return this.startDate;
    }

    public int daysLeft() {
        return this.daysLeft;
    }

    public SubscriptionType type() {
        return this.type;
    }

    public static Subscription parse(String $$0) {
        try {
            JsonObject $$1 = LenientJsonParser.parse($$0).getAsJsonObject();
            return new Subscription(JsonUtils.getDateOr("startDate", $$1), JsonUtils.getIntOr("daysLeft", $$1, 0), typeFrom(JsonUtils.getStringOr("subscriptionType", $$1, null)));
        } catch (Exception $$2) {
            LOGGER.error("Could not parse Subscription", $$2);
            return new Subscription(Instant.EPOCH, 0, SubscriptionType.NORMAL);
        }
    }

    private static SubscriptionType typeFrom(String $$0) {
        if ($$0 != null) {
            try {
                return SubscriptionType.valueOf($$0);
            } catch (Exception e) {
            }
        }
        return SubscriptionType.NORMAL;
    }
}

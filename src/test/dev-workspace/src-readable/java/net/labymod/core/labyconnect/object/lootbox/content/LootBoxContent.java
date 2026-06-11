package net.labymod.core.labyconnect.object.lootbox.content;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/content/LootBoxContent.class */
public final class LootBoxContent extends Record {
    private final List<LootBoxShopItem> pool;
    private final int durationInDays;
    public static final int PRICE_INDEX = 29;
    public static final long REVEAL_TIME = 14500;

    public LootBoxContent(List<LootBoxShopItem> pool, int durationInDays) {
        this.pool = pool;
        this.durationInDays = durationInDays;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LootBoxContent.class), LootBoxContent.class, "pool;durationInDays", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->pool:Ljava/util/List;", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->durationInDays:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LootBoxContent.class), LootBoxContent.class, "pool;durationInDays", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->pool:Ljava/util/List;", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->durationInDays:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LootBoxContent.class, Object.class), LootBoxContent.class, "pool;durationInDays", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->pool:Ljava/util/List;", "FIELD:Lnet/labymod/core/labyconnect/object/lootbox/content/LootBoxContent;->durationInDays:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public List<LootBoxShopItem> pool() {
        return this.pool;
    }

    public int durationInDays() {
        return this.durationInDays;
    }

    public LootBoxShopItem getPriceShopItem() {
        return this.pool.get(29);
    }

    public Component getDurationComponent() {
        if (this.durationInDays == -1) {
            return Component.translatable("labymod.activity.lootBox.duration." + "lifetime", new Component[0]);
        }
        if (this.durationInDays == 1) {
            return Component.translatable("labymod.activity.lootBox.duration." + "day", new Component[0]);
        }
        return Component.translatable("labymod.activity.lootBox.duration." + "days", new Component[0]).argument(Component.text(Integer.valueOf(this.durationInDays)));
    }

    public boolean hasDuration() {
        return this.durationInDays != -2;
    }
}

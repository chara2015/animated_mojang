package net.labymod.core.shop.models.config;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.core.shop.models.PriceItem;
import net.labymod.core.shop.models.ShopItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/config/ShopCurrency.class */
public class ShopCurrency {
    private String id;
    private String code;
    private String symbol;
    private String name;
    private float translation;
    private final transient Int2ObjectOpenHashMap<PriceItem> prices = new Int2ObjectOpenHashMap<>();
    private final transient boolean dummy = false;

    public ShopCurrency() {
    }

    public ShopCurrency(String code) {
        this.code = code;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }

    public float getTranslation() {
        return this.translation;
    }

    public Int2ObjectOpenHashMap<PriceItem> getPrices() {
        return this.prices;
    }

    public PriceItem getPriceFor(int itemId) {
        return (PriceItem) this.prices.get(itemId);
    }

    public PriceItem getPriceFor(ShopItem item) {
        return (PriceItem) this.prices.get(item.getId());
    }

    public boolean isDummy() {
        return this.dummy;
    }

    public String toString() {
        if (this.dummy) {
            return "Default (" + this.code + ")";
        }
        return this.name + " (" + this.code + ")";
    }
}

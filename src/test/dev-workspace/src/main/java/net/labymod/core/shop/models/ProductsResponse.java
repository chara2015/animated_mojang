package net.labymod.core.shop.models;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/ProductsResponse.class */
public class ProductsResponse {
    private ShopItem[] items = new ShopItem[0];
    private Map<String, JsonObject> prices = new HashMap();

    public ShopItem[] getItems() {
        return this.items;
    }

    public Map<String, JsonObject> getPrices() {
        return this.prices;
    }
}

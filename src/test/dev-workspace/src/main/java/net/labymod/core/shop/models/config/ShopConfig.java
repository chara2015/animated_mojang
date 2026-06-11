package net.labymod.core.shop.models.config;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/config/ShopConfig.class */
public class ShopConfig {
    private List<ShopSeason> seasons = new ArrayList();
    private List<ShopCurrency> currencies = new ArrayList();

    @SerializedName("default_currency")
    private String defaultCurrency;

    public List<ShopSeason> getSeasons() {
        return this.seasons;
    }

    public List<ShopCurrency> getCurrencies() {
        return this.currencies;
    }

    public String getDefaultCurrency() {
        return this.defaultCurrency;
    }
}

package net.labymod.core.shop.event;

import net.labymod.api.event.Event;
import net.labymod.core.shop.models.config.ShopCurrency;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/event/CurrencyUpdateEvent.class */
public class CurrencyUpdateEvent implements Event {
    private final ShopCurrency currency;

    public CurrencyUpdateEvent(ShopCurrency currency) {
        this.currency = currency;
    }

    public ShopCurrency getCurrency() {
        return this.currency;
    }
}

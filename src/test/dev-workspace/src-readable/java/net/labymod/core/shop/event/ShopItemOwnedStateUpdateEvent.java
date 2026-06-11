package net.labymod.core.shop.event;

import net.labymod.api.event.Event;
import net.labymod.core.shop.models.ShopItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/event/ShopItemOwnedStateUpdateEvent.class */
public class ShopItemOwnedStateUpdateEvent implements Event {
    private final ShopItem shopItem;

    public ShopItemOwnedStateUpdateEvent(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public ShopItem item() {
        return this.shopItem;
    }
}

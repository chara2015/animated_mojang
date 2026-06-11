package net.labymod.core.shop.models.cart;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/CreateCartResponse.class */
public class CreateCartResponse {

    @SerializedName("cart_uuid")
    private String cartId;

    public UUID getCartId() {
        return UUID.fromString(this.cartId);
    }
}

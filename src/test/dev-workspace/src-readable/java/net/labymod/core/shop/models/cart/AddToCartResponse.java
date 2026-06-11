package net.labymod.core.shop.models.cart;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/AddToCartResponse.class */
public class AddToCartResponse {
    private boolean successful;
    private AddToCartItemResponse item;

    public boolean isSuccessful() {
        return this.successful;
    }

    public AddToCartItemResponse getItem() {
        return this.item;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/AddToCartResponse$AddToCartItemResponse.class */
    public static class AddToCartItemResponse {

        @SerializedName("cart_item_id")
        private String cartItemId;

        public String getCartItemId() {
            return this.cartItemId;
        }
    }
}

package net.labymod.core.shop.models.cart;

import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/PromoCodeResponse.class */
public class PromoCodeResponse {
    private boolean successful;
    private String message;

    @SerializedName("promo_code")
    private PromoCode promoCode;

    @SerializedName("ref")
    private RefCode refCode;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/PromoCodeResponse$Code.class */
    public interface Code {
        String getCode();

        String getName();

        float getMultiplier();

        int getShopItemId();
    }

    public boolean isSuccessful() {
        return this.successful;
    }

    public String getMessage() {
        return this.message;
    }

    public Code getCode() {
        if (this.refCode != null) {
            return this.refCode;
        }
        return this.promoCode;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/PromoCodeResponse$PromoCode.class */
    public class PromoCode implements Code {
        private String code;
        private String name;
        private String sale;

        @SerializedName("shop_item_id")
        private String shopItemId;

        public PromoCode(PromoCodeResponse this$0) {
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public String getCode() {
            return this.code;
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public String getName() {
            return this.name;
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public float getMultiplier() {
            try {
                return Float.parseFloat(this.sale);
            } catch (Exception e) {
                return 1.0f;
            }
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public int getShopItemId() {
            if (this.shopItemId == null) {
                return -1;
            }
            try {
                return Integer.parseInt(this.shopItemId);
            } catch (Exception e) {
                return -1;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/models/cart/PromoCodeResponse$RefCode.class */
    public class RefCode implements Code {

        @SerializedName("user_name")
        private String userName;

        @SerializedName("user_discount")
        private String userDiscount;

        @SerializedName("shop_item_id")
        private String shopItemId;
        private String code;

        public RefCode(PromoCodeResponse this$0) {
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public String getName() {
            return this.userName;
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public float getMultiplier() {
            try {
                return Float.parseFloat(this.userDiscount);
            } catch (Exception e) {
                return 1.0f;
            }
        }

        @Override // net.labymod.core.shop.models.cart.PromoCodeResponse.Code
        public int getShopItemId() {
            if (this.shopItemId == null) {
                return -1;
            }
            try {
                return Integer.parseInt(this.shopItemId);
            } catch (Exception e) {
                return -1;
            }
        }
    }
}

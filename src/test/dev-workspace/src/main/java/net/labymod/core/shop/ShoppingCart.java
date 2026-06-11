package net.labymod.core.shop;

import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.io.web.request.FormData;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.shop.models.cart.AddToCartResponse;
import net.labymod.core.shop.models.cart.CreateCartResponse;
import net.labymod.core.shop.models.cart.PromoCodeResponse;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart.class */
public class ShoppingCart {
    private final CartStorage storage = new CartStorage();
    private final ShopController shopController;
    private PromoCodeResponse.Code promoCode;
    private UUID cartId;
    private UUID userId;

    public ShoppingCart(ShopController shopController) {
        this.shopController = shopController;
    }

    public int size() {
        return this.storage.size();
    }

    public boolean isEmpty() {
        return this.storage.isEmpty();
    }

    public boolean clear() {
        this.cartId = null;
        this.userId = null;
        this.promoCode = null;
        if (isEmpty()) {
            return false;
        }
        this.storage.clear();
        Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
        return true;
    }

    public void forEachShopItem(Consumer<ShopItem> consumer) {
        for (CartItem item : this.storage) {
            consumer.accept(item.item);
        }
    }

    public void forEachGameItem(Consumer<GameUserItem> consumer) {
        forEachShopItem(shopItem -> {
            GameUserItem gameUserItem = shopItem.asGameUserItem();
            if (gameUserItem != null) {
                consumer.accept(gameUserItem);
            }
        });
    }

    public boolean has(ShopItem item) {
        int id = item.getId();
        for (CartItem cartItem : this.storage) {
            if (cartItem.item.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public PromoCodeResponse.Code getPromoCode() {
        return this.promoCode;
    }

    public void getInfo(ResultCallback<JsonObject> callback) {
    }

    public void applyPromoCode(String code, ResultCallback<PromoCodeResponse.Code> callback) {
        cartRequest(PromoCodeResponse.class, "add-promocode", callback, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                return;
            }
            PromoCodeResponse promoCodeResponse = (PromoCodeResponse) response.get();
            PromoCodeResponse.Code promoCode = promoCodeResponse.getCode();
            if (promoCode == null) {
                callback.acceptException(new ShoppingCartKeywordException(promoCodeResponse.getMessage()));
                return;
            }
            if (promoCode instanceof PromoCodeResponse.RefCode) {
                PromoCodeResponse.RefCode refCode = (PromoCodeResponse.RefCode) promoCode;
                refCode.setCode(code);
            }
            this.promoCode = promoCode;
            Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
            callback.acceptRaw(promoCode);
        }, FormData.of("code", code));
    }

    public void removePromoCode(ResultCallback<JsonObject> callback) {
        cartRequest(JsonObject.class, "remove-promocode", (ResultCallback<?>) callback, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
                return;
            }
            this.promoCode = null;
            Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
            callback.acceptRaw((JsonObject) response.get());
        }, new FormData[0]);
    }

    public void removeItem(ShopItem item, ResultCallback<CartItem> callback) {
        int id = item.getId();
        for (CartItem cartItem : this.storage) {
            if (cartItem.item.getId() == id) {
                removeItem(cartItem, callback);
                return;
            }
        }
    }

    public void removeItem(CartItem item, ResultCallback<CartItem> callback) {
        cartRequest(JsonObject.class, "remove-item", callback, response -> {
            this.storage.removeItem(item);
            Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
            callback.acceptRaw(item);
        }, FormData.of("cart_item_id", Integer.valueOf(item.cartId)));
    }

    public void addItem(ShopItem item, ResultCallback<CartItem> callback) {
        addItem(new CartItem(item, CartDuration.LIFETIME), callback);
    }

    public void addItem(CartItem item, ResultCallback<CartItem> callback) {
        cartRequest(AddToCartResponse.class, "add-item", callback, response -> {
            AddToCartResponse addToCartResponse = (AddToCartResponse) response.get();
            if (!addToCartResponse.isSuccessful() || addToCartResponse.getItem() == null) {
                callback.acceptException(new IllegalStateException("Failed to add item to cart"));
                return;
            }
            String cartItemId = addToCartResponse.getItem().getCartItemId();
            if (cartItemId == null) {
                callback.acceptException(new IllegalStateException("Failed to add item to cart"));
                return;
            }
            item.cartId = Integer.parseInt(cartItemId);
            this.storage.addItem(item);
            Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
            callback.acceptRaw(item);
        }, () -> {
            if (item.receiver == null) {
                item.receiver = this.userId;
            }
            return new FormData[]{FormData.of("item_receiver", item.receiver), FormData.of("shop_item_id", Integer.valueOf(item.item.getId())), FormData.of("item_value", Integer.valueOf(item.duration.identifier))};
        });
    }

    public void createCart(ResultCallback<UUID> callback) {
        if (this.cartId != null) {
            callback.acceptRaw(this.cartId);
            return;
        }
        Request<CreateCartResponse> request = createRequest(CreateCartResponse.class, callback);
        if (request == null) {
            return;
        }
        request.url(ShopUrls.CREATE_CARD, new Object[0]).connectTimeout(5000).readTimeout(5000).execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            } else {
                this.cartId = ((CreateCartResponse) response.get()).getCartId();
                callback.acceptRaw(this.cartId);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <T> Request<T> createRequest(Class<T> clazz, ResultCallback<?> callback) {
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session == null) {
            callback.acceptException(new IllegalStateException("LabyConnect is not connected"));
            return null;
        }
        UUID uniqueId = this.userId == null ? session.self().getUniqueId() : this.userId;
        TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            callback.acceptException(new IllegalAccessException("No valid token"));
            return null;
        }
        if (this.userId == null) {
            this.userId = uniqueId;
        }
        String token = tokenStorage.getToken(TokenStorage.Purpose.CLIENT, uniqueId).getToken();
        GsonRequest<T> request = (GsonRequest) ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(clazz).method(Request.Method.POST)).connectTimeout(5000)).readTimeout(5000)).authorization("Client", token);
        if (ThreadSafe.isRenderThread()) {
            request.async();
        }
        return request;
    }

    private <T> void cartRequest(Class<T> responseClass, String action, ResultCallback<?> callback, Consumer<Response<T>> response, Supplier<FormData[]> formDataSupplier) {
        createCart(result -> {
            if (result.hasException()) {
                callback.acceptException(result.exception());
                return;
            }
            Request requestCreateRequest = createRequest(responseClass, callback);
            if (requestCreateRequest == null) {
                return;
            }
            FormData[] formData = (FormData[]) formDataSupplier.get();
            FormData[] actualFormData = new FormData[formData.length + 1];
            actualFormData[0] = FormData.builder().name("action").value(action).build();
            System.arraycopy(formData, 0, actualFormData, 1, formData.length);
            requestCreateRequest.url(String.format(Locale.ROOT, ShopUrls.CART, result.get()), new Object[0]).form(actualFormData).connectTimeout(5000).readTimeout(5000).execute(requestResponse -> {
                if (requestResponse.hasException()) {
                    callback.acceptException(requestResponse.exception());
                } else {
                    response.accept(requestResponse);
                }
            });
        });
    }

    private <T> void cartRequest(Class<T> responseClass, String action, ResultCallback<?> callback, Consumer<Response<T>> response, FormData... formData) {
        cartRequest(responseClass, action, callback, response, () -> {
            return formData;
        });
    }

    public CartStorage storage() {
        return this.storage;
    }

    public UUID getCartId() {
        return this.cartId;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$CartDuration.class */
    public enum CartDuration {
        LIFETIME(0),
        ONE_MONTH(1),
        THREE_MONTHS(3),
        SIX_MONTHS(6),
        TWELVE_MONTHS(12);

        private final int identifier;

        CartDuration(int identifier) {
            this.identifier = identifier;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$CartItem.class */
    public static class CartItem {
        private final ShopItem item;
        private final CartDuration duration;
        private UUID receiver;
        private int cartId;

        public CartItem(ShopItem item, CartDuration duration, UUID receiver) {
            this.item = item;
            this.duration = duration;
            this.receiver = receiver;
        }

        public CartItem(ShopItem item, CartDuration duration) {
            this.item = item;
            this.duration = duration;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$CartStorage.class */
    public static class CartStorage implements Iterable<CartItem> {
        private final Set<CartItem> items = new HashSet();
        private ChangeListener listener;

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$CartStorage$ChangeListener.class */
        public interface ChangeListener {

            /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$CartStorage$ChangeListener$ChangeType.class */
            public enum ChangeType {
                ADD,
                REMOVE,
                CLEAR
            }

            void onChange(ChangeType changeType, int i);
        }

        public void addItem(CartItem item) {
            if (item == null) {
                return;
            }
            this.items.add(item);
            onChange(ChangeListener.ChangeType.ADD, item.item.getItemId());
        }

        public void removeItem(CartItem item) {
            if (item == null) {
                return;
            }
            this.items.remove(item);
            onChange(ChangeListener.ChangeType.REMOVE, item.item.getItemId());
        }

        public void clear() {
            this.items.clear();
            onChange(ChangeListener.ChangeType.CLEAR, -1);
        }

        public int size() {
            return this.items.size();
        }

        public boolean isEmpty() {
            return this.items.isEmpty();
        }

        @Override // java.lang.Iterable
        @NotNull
        public Iterator<CartItem> iterator() {
            return this.items.iterator();
        }

        public void setChangeListener(ChangeListener listener) {
            this.listener = listener;
        }

        private void onChange(ChangeListener.ChangeType type, int cosmeticId) {
            if (this.listener == null) {
                return;
            }
            this.listener.onChange(type, cosmeticId);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShoppingCart$ShoppingCartKeywordException.class */
    public static class ShoppingCartKeywordException extends RuntimeException {
        public ShoppingCartKeywordException(String message) {
            super(message);
        }
    }
}

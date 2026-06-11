package net.labymod.core.shop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.property.Property;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.models.CosmeticOptionEntry;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.widgetoptions.OptionProvider;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.core.shop.event.CurrencyUpdateEvent;
import net.labymod.core.shop.event.ShopLabyPlusToggleEvent;
import net.labymod.core.shop.event.ShopOutfitUpdateEvent;
import net.labymod.core.shop.models.ItemCategory;
import net.labymod.core.shop.models.ItemType;
import net.labymod.core.shop.models.PriceItem;
import net.labymod.core.shop.models.ProductsResponse;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.shop.models.config.ShopConfig;
import net.labymod.core.shop.models.config.ShopCurrency;
import net.labymod.core.shop.models.config.ShopSeason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShopController.class */
@Singleton
@Referenceable
public class ShopController implements OptionProvider {
    private static final Logging LOGGER = Logging.create((Class<?>) ShopController.class);
    private ShopConfig config;
    private ShopCurrency selectedCurrency;
    private CosmeticOptions cosmeticOptions;
    private boolean isLabyPlus;
    private final Int2ObjectOpenHashMap<ShopItem> shopItems = new Int2ObjectOpenHashMap<>();
    private final List<ItemCategory> categories = new ArrayList();
    private final ShopListener shopListener = new ShopListener(this);
    private final Property<Boolean> connectedToLabyConnect = new Property<>(false);
    private final ShoppingCart shoppingCart = new ShoppingCart(this);
    private final ShopItemStorage currentOutfit = new ShopItemStorage(() -> {
        Laby.fireEvent(ShopOutfitUpdateEvent.INSTANCE);
    });
    private final Property<ShopItem> previewedItem = new Property<>(null);
    private final WidgetOptionService widgetOptionService = new WidgetOptionService(this);
    private final Gson gson = new GsonBuilder().registerTypeAdapter(ItemType.class, new ItemType.ItemTypeTypeAdapter()).create();

    public ShopController() {
        Laby.references().eventBus().registerListener(this.shopListener);
        Laby.labyAPI().config().other().preferredCurrency().addChangeListener(newValue -> {
            if (this.config == null) {
                return;
            }
            String identifier = newValue.equals("null") ? this.config.getDefaultCurrency() : newValue;
            for (ShopCurrency currency : this.config.getCurrencies()) {
                if (currency.getCode().equals(identifier)) {
                    this.selectedCurrency = currency;
                    Laby.fireEvent(new CurrencyUpdateEvent(this.selectedCurrency));
                    return;
                }
            }
        });
    }

    public void reload() {
        reloadConfig();
        reloadProducts();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void reloadConfig() {
        Result resultExecuteSync = ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(ShopConfig.class).url(ShopUrls.CONFIG, new Object[0])).readTimeout(5000)).connectTimeout(5000)).executeSync();
        if (resultExecuteSync.hasException()) {
            LOGGER.error("Failed to load shop config", resultExecuteSync.exception());
        } else if (!resultExecuteSync.isPresent()) {
            LOGGER.error("Failed to load shop config (no exception)", new Object[0]);
        } else {
            this.config = (ShopConfig) resultExecuteSync.get();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void reloadProducts() {
        this.shopItems.clear();
        this.categories.clear();
        Result resultExecuteSync = ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(ProductsResponse.class, this.gson).url(ShopUrls.PRODUCTS, new Object[0])).connectTimeout(5000)).readTimeout(5000)).executeSync();
        if (resultExecuteSync.hasException()) {
            LOGGER.error("Failed to load shop products", resultExecuteSync.exception());
            return;
        }
        if (!resultExecuteSync.isPresent()) {
            LOGGER.error("Failed to load shop products (no exception)", new Object[0]);
            return;
        }
        this.shopListener.refreshOwnedShopItems();
        Map<String, ItemCategory> categories = new TreeMap<>();
        ProductsResponse productsResponse = (ProductsResponse) resultExecuteSync.get();
        for (ShopItem item : productsResponse.getItems()) {
            ItemType type = item.getType();
            item.setOwned(this.shopListener.isOwned(item));
            if ((item.isOnSale() || item.isOnPreview()) && type != null) {
                this.shopItems.put(item.getId(), item);
                String category = modifyCategory(item);
                categories.computeIfAbsent(type.getType() + "#" + category, identifier -> {
                    return new ItemCategory(type, category);
                }).addItem(item.getId());
                String seasonName = item.getSeasonName();
                if (seasonName != null && (type == ItemType.EMOTE || type == ItemType.COSMETIC)) {
                    categories.computeIfAbsent(String.valueOf(ItemType.FEATURED) + "#" + seasonName, identifier2 -> {
                        ItemCategory seasonCategory = new ItemCategory(ItemType.FEATURED, ItemType.SEASON, seasonName);
                        String niceName = seasonName;
                        if (this.config != null) {
                            Iterator<ShopSeason> it = this.config.getSeasons().iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                ShopSeason season = it.next();
                                if (seasonName.equals(season.getName())) {
                                    niceName = season.getNiceName();
                                    break;
                                }
                            }
                        }
                        if (niceName != null && niceName.length() > 0) {
                            seasonCategory.setLocalizedIdentifier(StringUtil.capitalizeWords(niceName));
                        }
                        return seasonCategory;
                    }).addItem(item.getId());
                }
            }
        }
        this.categories.addAll(categories.values());
        this.categories.sort((o1, o2) -> {
            ItemType firstType = o1.getType();
            ItemType secondType = o2.getType();
            if (firstType != secondType) {
                return 0;
            }
            String firstIdentifier = o1.getIdentifier();
            String secondIdentifier = o2.getIdentifier();
            if (firstIdentifier.equals("null")) {
                if (firstType == ItemType.COSMETIC) {
                    return 1;
                }
                if (firstType == ItemType.EMOTE) {
                    return -1;
                }
            }
            if (firstType == ItemType.COSMETIC) {
                if (firstIdentifier.equals("partner")) {
                    return -1;
                }
                if (secondIdentifier.equals("partner")) {
                    return 1;
                }
            }
            return firstIdentifier.compareTo(secondIdentifier);
        });
        LOGGER.info("Loaded " + this.shopItems.size() + " shop items", new Object[0]);
        if (this.config == null) {
            return;
        }
        String preferredCurrency = Laby.labyAPI().config().other().preferredCurrency().get();
        for (ShopCurrency currency : this.config.getCurrencies()) {
            String code = currency.getCode();
            if ((this.selectedCurrency == null && code.equals(this.config.getDefaultCurrency())) || (this.selectedCurrency != null && this.selectedCurrency.getCode().equals(code))) {
                this.selectedCurrency = currency;
            }
            if (code.equals(preferredCurrency)) {
                this.selectedCurrency = currency;
            }
            currency.getPrices().clear();
            JsonObject jsonObject = productsResponse.getPrices().get(code);
            if (jsonObject != null) {
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    JsonElement value = entry.getValue();
                    if (value.isJsonObject()) {
                        try {
                            int i = Integer.parseInt(entry.getKey());
                            currency.getPrices().put(i, (PriceItem) this.gson.fromJson(value, PriceItem.class));
                        } catch (Exception e) {
                            LOGGER.error("Failed to parse price for " + code + " " + entry.getKey(), e);
                        }
                    }
                }
            }
        }
        LOGGER.info("Loaded " + this.config.getCurrencies().size() + " shop currencies", new Object[0]);
        Result resultExecuteSync2 = ((GsonRequest) ((GsonRequest) ((GsonRequest) Request.ofGson(JsonObject.class, this.gson).url(ShopUrls.COSMETICS_OPTIONS, new Object[0])).connectTimeout(5000)).readTimeout(5000)).executeSync();
        if (resultExecuteSync2.hasException()) {
            LOGGER.error("Failed to load shop cosmetics options", resultExecuteSync2.exception());
            return;
        }
        if (!resultExecuteSync2.isPresent()) {
            LOGGER.error("Failed to load shop cosmetics options (no exception)", new Object[0]);
            return;
        }
        List<CosmeticOptionEntry> list = new ArrayList<>();
        Set<Map.Entry<String, JsonElement>> options = ((JsonObject) resultExecuteSync2.get()).getAsJsonObject("options").entrySet();
        for (Map.Entry<String, JsonElement> entry2 : options) {
            String key = entry2.getKey();
            for (JsonElement element : entry2.getValue().getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                JsonElement name = object.get("name");
                JsonElement data = object.get("data");
                list.add(new CosmeticOptionEntry(key, name.isJsonNull() ? null : name.getAsString(), data.isJsonNull() ? null : data.getAsString()));
            }
        }
        CosmeticOptions cosmeticOptions = new CosmeticOptions();
        Map<String, CosmeticOption> map = cosmeticOptions.getMap();
        for (CosmeticOptionEntry option : list) {
            map.computeIfAbsent(option.getCustomKey(), k -> {
                return new CosmeticOption();
            }).push(option);
        }
        this.cosmeticOptions = cosmeticOptions;
        LOGGER.info("Loaded " + list.size() + " shop cosmetics options", new Object[0]);
    }

    public ObjectCollection<ShopItem> getShopItems() {
        return this.shopItems.values();
    }

    public ShopItem getItem(int id) {
        return (ShopItem) this.shopItems.get(id);
    }

    public ItemCategory findCategoryByType(ItemType type) {
        for (ItemCategory category : this.categories) {
            if (category.getType() == type) {
                return category;
            }
        }
        return null;
    }

    public List<ItemCategory> getCategories() {
        return this.categories;
    }

    public ShopCurrency getSelectedCurrency() {
        return this.selectedCurrency;
    }

    public Property<ShopItem> previewedItem() {
        return this.previewedItem;
    }

    public WidgetOptionService getWidgetOptionService() {
        return this.widgetOptionService;
    }

    public void pushNotification(String translationKey) {
        Laby.references().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.shop.name", new Component[0])).text(Component.translatable(translationKey, new Component[0])).build());
    }

    private String modifyCategory(ShopItem shopItem) {
        String category;
        String str;
        String str2;
        if (shopItem.getPartner() != null) {
            return "PARTNER";
        }
        String actualCategory = shopItem.getCategory();
        if (actualCategory == null) {
            return null;
        }
        if (shopItem.getType() == ItemType.COSMETIC) {
            switch (actualCategory) {
                case "HATS":
                    str = "HAT";
                    break;
                case "WINGS":
                    str = "WING";
                    break;
                default:
                    str = actualCategory;
                    break;
            }
            actualCategory = str;
            switch (actualCategory) {
                case "HAT":
                case "HEADGEAR":
                case "FACE":
                    str2 = "HEAD";
                    break;
                case "BACK":
                case "LANYARD":
                case "CLOAK":
                case "ARMS":
                    str2 = "BODY";
                    break;
                default:
                    str2 = null;
                    break;
            }
            category = str2;
        } else {
            category = null;
        }
        if (category != null) {
            shopItem.setCategory(category);
            return category;
        }
        return actualCategory;
    }

    public ShopItemStorage currentOutfit() {
        return this.currentOutfit;
    }

    public ShoppingCart shoppingCart() {
        return this.shoppingCart;
    }

    public ShopConfig getConfig() {
        return this.config;
    }

    public Property<Boolean> connectedToLabyConnect() {
        return this.connectedToLabyConnect;
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.OptionProvider
    public CosmeticOptions getCosmeticOptions() {
        return this.cosmeticOptions;
    }

    public boolean isSelectedCurrencyDefault() {
        if (this.selectedCurrency == null || this.config == null || this.config.getDefaultCurrency() == null) {
            return true;
        }
        return this.selectedCurrency.getCode().equals(this.config.getDefaultCurrency());
    }

    public boolean isLabyPlus() {
        return this.isLabyPlus;
    }

    public void setLabyPlus(boolean isLabyPlus) {
        if (this.isLabyPlus == isLabyPlus) {
            return;
        }
        this.isLabyPlus = isLabyPlus;
        Laby.fireEvent(ShopLabyPlusToggleEvent.INSTANCE);
    }
}

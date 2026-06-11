package net.labymod.core.client.gui.screen.activity.activities.labymod.child;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.I18n;
import net.labymod.api.util.StringUtil;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.background.CameraTransitionUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.LabyModActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.CosmeticsActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.CartActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.ShopOverviewActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.ItemPreviewWidget;
import net.labymod.core.client.gui.screen.widget.widgets.customization.PlayerModelWidget;
import net.labymod.core.client.render.model.CosmeticModelFocus;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.core.shop.ShopController;
import net.labymod.core.shop.ShoppingCart;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import net.labymod.core.shop.models.ItemCategory;
import net.labymod.core.shop.models.ItemType;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.util.camera.spline.position.Location;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity.class */
@AutoActivity
@Link("activity/shop/shop.lss")
public class ShopActivity extends AbstractSidebarActivity {
    public static final String ROOT_TRANSLATION_KEY = "labymod.activity.shop.";
    private static final String TRANSLATION_KEY = "labymod.activity.shop.overview.";
    private final ShopController shopController;
    private final ShopOverviewActivity shopOverviewActivity;
    private final CartActivity cartActivity;
    private final PlayerModelWidget modelWidget;
    private final CosmeticModelFocus modelFocus;
    private ItemPreviewWidget itemPreviewWidget;
    private ComponentWidget cartCountWidget;
    private HorizontalListWidget header;
    private DivWidget modelWrapper;
    private ComponentWidget titleWidget;
    private String searchQuery;

    public ShopActivity() {
        super(false);
        this.modelFocus = new CosmeticModelFocus();
        this.shopController = LabyMod.references().shopController();
        this.shopController.connectedToLabyConnect().addChangeListener(connected -> {
            if (currentLabyScreen() == this && isOpen()) {
                reload();
            }
        });
        this.shopOverviewActivity = new ShopOverviewActivity(this.shopController, () -> {
            return this.searchQuery;
        }, () -> {
            this.recheckSidebarButtons();
        });
        this.cartActivity = new CartActivity(this.shopController);
        this.shopOverviewActivity.focusType(ItemType.FEATURED);
        this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
        this.screenRendererWidget.addPreDisplayListener(screenInstance -> {
            ScreenRendererWidget screenRendererWidget = this.screenRendererWidget;
            boolean previousIsCart = screenRendererWidget.currentLabyScreen() instanceof CartActivity;
            boolean newIsCart = screenInstance instanceof CartActivity;
            if (!previousIsCart && !newIsCart) {
                return;
            }
            if (newIsCart) {
                previewCartCosmetics();
            } else {
                previewCosmetic(this.shopController.previewedItem().get());
            }
            refreshActivityType(screenInstance);
        });
        this.modelWidget = new PlayerModelWidget();
        ShoppingCart.CartStorage cartStorageStorage = this.shopController.shoppingCart().storage();
        Supplier supplier = () -> {
            return this.screenRendererWidget;
        };
        PlayerModelWidget playerModelWidget = this.modelWidget;
        Objects.requireNonNull(playerModelWidget);
        cartStorageStorage.setChangeListener(new ShopActivityChangeListener(supplier, playerModelWidget::gameUser, this::previewCartCosmetics));
        this.shopController.previewedItem().addChangeListener(this::updatePreviewedItem);
        Task.builder(() -> {
            LabyMod.references().shopController().reload();
            Task.builder(this::reload).build().executeOnRenderThread();
        }).build().execute();
    }

    private void refreshActivityType() {
        refreshActivityType(this.screenRendererWidget.currentLabyScreen());
    }

    private void refreshActivityType(ScreenInstance screenInstance) {
        Component titleComponent;
        if (screenInstance instanceof CartActivity) {
            this.itemPreviewWidget.setVisible(false);
            titleComponent = Component.translatable("labymod.activity.shop.overview.cart", new Component[0]);
        } else {
            this.itemPreviewWidget.setVisible(true);
            titleComponent = Component.translatable("labymod.activity.shop.name", new Component[0]);
        }
        if (this.titleWidget.component() == titleComponent) {
            return;
        }
        this.titleWidget.setComponent(titleComponent);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("container-outer-wrapper");
        this.modelWrapper = new DivWidget();
        this.modelWrapper.addId("model-wrapper");
        ShopController shopController = this.shopController;
        PlayerModelWidget playerModelWidget = this.modelWidget;
        Objects.requireNonNull(playerModelWidget);
        this.itemPreviewWidget = new ItemPreviewWidget(shopController, playerModelWidget::gameUser, (shopItem, cosmetic) -> {
            if (shopItem == null || shopItem.getType() == ItemType.EMOTE) {
                return;
            }
            this.modelFocus.updateModelFocus(this.modelWidget, shopItem.asGameUserItem().definition().details(), cosmetic);
        });
        this.modelWrapper.addChild(this.itemPreviewWidget);
        UUID uniqueId = this.labyAPI.getUniqueId();
        this.modelWidget.updateSkinTextureFrom(uniqueId);
        updatePreviewedItem(this.shopController.previewedItem().get());
        this.modelWidget.update();
        this.modelWidget.addId("model");
        this.modelWidget.draggable().set(true);
        this.modelWrapper.addChild(this.modelWidget);
        FlexibleContentWidget flexibleContentWidget2 = new FlexibleContentWidget();
        flexibleContentWidget2.addId("container");
        this.header = new HorizontalListWidget();
        this.header.addId("header");
        this.header.addEntry(this.searchWidget);
        this.titleWidget = ComponentWidget.empty();
        refreshActivityType();
        this.titleWidget.addId("title");
        this.header.addEntry(this.titleWidget);
        AbstractSidebarActivity.SidebarButtonWidget cartButton = AbstractSidebarActivity.SidebarButtonWidget.i18n("labymod.activity.shop.overview.cart", Textures.SpriteCommon.CART, (Predicate<ScreenInstance>) screen -> {
            if (this.searchQuery != null) {
                return false;
            }
            return screen instanceof CartActivity;
        });
        cartButton.addId("accent-button");
        cartButton.addId("cart-button");
        cartButton.removeId("primary-button");
        cartButton.setPressable(() -> {
            if (this.searchQuery != null) {
                this.searchWidget.setText("");
            }
            this.shopController.previewedItem().set(null);
            this.screenRendererWidget.displayScreen(this.cartActivity);
            this.titleWidget.setComponent(Component.translatable("labymod.activity.shop.overview.cart", new Component[0]));
        });
        this.cartCountWidget = ComponentWidget.text("");
        this.cartCountWidget.addId("cart-count");
        updateCartCount();
        cartButton.addEntry(this.cartCountWidget);
        if (this.shopController.connectedToLabyConnect().get().booleanValue()) {
            this.header.addEntry(cartButton);
        }
        ButtonWidget reload = ButtonWidget.icon(Textures.SpriteCommon.REFRESH);
        reload.addId("reload-button");
        reload.setPressable(() -> {
            reload.setEnabled(false);
            Task.builder(() -> {
                this.shopController.reload();
                reload.setEnabled(true);
                Task.builder(this::reload).build().executeOnRenderThread();
            }).build().execute();
        });
        this.header.addEntry(reload);
        flexibleContentWidget2.addContent(this.header);
        FlexibleContentWidget innerContainer = (FlexibleContentWidget) ((Document) this.document).getChild("container");
        innerContainer.removeId("container");
        innerContainer.addId("container-inner-wrapper");
        ((Document) this.document).removeChild(innerContainer);
        if (this.shopController.getShopItems().isEmpty()) {
            innerContainer.removeChildIf(entry -> {
                return entry.childWidget() == this.screenRendererWidget;
            });
            DivWidget infoWrapper = new DivWidget();
            infoWrapper.addId("info-wrapper");
            ComponentWidget infoWidget = ComponentWidget.i18n("labymod.activity.shop.notLoaded");
            infoWidget.addId("info");
            infoWrapper.addChild(infoWidget);
            innerContainer.addFlexibleContent(infoWrapper);
        }
        flexibleContentWidget2.addFlexibleContent(innerContainer);
        flexibleContentWidget.addContent(this.modelWrapper);
        flexibleContentWidget.addFlexibleContent(flexibleContentWidget2);
        ((Document) this.document).addChild(flexibleContentWidget);
        recheckSidebarButtons();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        this.modelFocus.applyFocus(this.modelWidget);
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        UUID uniqueId = event.newSession().getUniqueId();
        this.modelWidget.updateSkinTextureFrom(uniqueId);
        this.modelWidget.update();
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    public void onCategoryListInitialize(VerticalListWidget<Widget> categoryList) {
        boolean hasFeatured = this.shopController.findCategoryByType(ItemType.FEATURED) != null;
        if (hasFeatured) {
            addTypeButton(categoryList, ItemType.FEATURED);
        }
        boolean dynamicFocusType = !hasFeatured;
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.shop.overview.cosmetics").addId("sub-category"));
        boolean addedCosmeticCategories = false;
        for (ItemCategory category : this.shopController.getCategories()) {
            if (category.getType() == ItemType.COSMETIC) {
                if (dynamicFocusType) {
                    this.shopOverviewActivity.focusType(category.getType());
                    dynamicFocusType = false;
                }
                addedCosmeticCategories = true;
                addCategoryButton(categoryList, category);
            }
        }
        if (!addedCosmeticCategories) {
            ButtonWidget buttonWidget = ButtonWidget.i18n("labymod.activity.shop.overview.manage");
            buttonWidget.setPressListener(() -> {
                return openPlayerActivity(CosmeticsActivity.class);
            });
            categoryList.addChild(buttonWidget);
        }
        categoryList.addChild(ComponentWidget.i18n("labymod.activity.shop.overview.emotes").addId("sub-category"));
        boolean addedEmoteCategories = false;
        for (ItemCategory category2 : this.shopController.getCategories()) {
            if (category2.getType() == ItemType.EMOTE) {
                addedEmoteCategories = true;
                addCategoryButton(categoryList, category2);
            }
        }
        if (!addedEmoteCategories) {
            ButtonWidget buttonWidget2 = ButtonWidget.i18n("labymod.activity.shop.overview.manage");
            buttonWidget2.setPressListener(() -> {
                return openPlayerActivity(EmotesActivity.class);
            });
            categoryList.addChild(buttonWidget2);
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    public void onSearchUpdateListener(String searchContent) {
        if (searchContent.isEmpty()) {
            this.searchQuery = null;
        } else {
            this.searchQuery = searchContent;
        }
        if (!(this.screenRendererWidget.currentLabyScreen() instanceof ShopOverviewActivity)) {
            this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
        } else {
            this.shopOverviewActivity.reload();
        }
        this.shopOverviewActivity.getSectionedListWidget().listSession().setScrollPositionY(0.0f);
        recheckSidebarButtons();
    }

    private boolean openPlayerActivity(Class<? extends PlayerActivity.Child> childClass) {
        PlayerActivity playerActivity;
        LabyModActivity labyModActivity = LabyModActivity.getFromNavigationRegistry();
        if (labyModActivity == null || (playerActivity = (PlayerActivity) labyModActivity.switchTab(PlayerActivity.class)) == null) {
            return false;
        }
        playerActivity.displayChild(childClass);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(labyModActivity);
        return true;
    }

    private void addTypeButton(VerticalListWidget<Widget> categoryList, ItemType type) {
        String key = I18n.getTranslation("labymod.activity.shop.overview." + String.valueOf(type), new Object[0]);
        if (key == null) {
            key = type.toString();
        }
        for (ItemCategory category : this.shopController.getCategories()) {
            if (category.getType() == type && category.getSubType() == null) {
                category.setLocalizedIdentifier(key);
            }
        }
        AbstractSidebarActivity.SidebarButtonWidget sidebarButtonWidgetText = AbstractSidebarActivity.SidebarButtonWidget.text(key, (Predicate<ScreenInstance>) screenInstance -> {
            if (this.searchQuery != null || !(screenInstance instanceof ShopOverviewActivity)) {
                return false;
            }
            ShopOverviewActivity activity = (ShopOverviewActivity) screenInstance;
            return activity.getItemType() == type;
        });
        sidebarButtonWidgetText.icon().set(type.getIcon());
        sidebarButtonWidgetText.setPressable(() -> {
            if (this.searchQuery != null) {
                this.searchWidget.setText("");
            }
            ScreenInstance screen = this.screenRendererWidget.getScreen();
            if (screen == null || screen != this.shopOverviewActivity) {
                this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
            }
            this.shopOverviewActivity.focusType(type);
            recheckSidebarButtons();
            this.titleWidget.setComponent(Component.translatable("labymod.activity.shop.name", new Component[0]));
        });
        categoryList.addChild(sidebarButtonWidgetText);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.AbstractSidebarActivity
    protected void recheckSidebarButtons(ScreenInstance screen) {
        super.recheckSidebarButtons(screen);
        recheckButtonsFor(this.header, screen);
    }

    private void addCategoryButton(VerticalListWidget<Widget> categoryList, ItemCategory category) {
        String key = I18n.getTranslation("labymod.activity.shop.overview." + String.valueOf(category.getType()) + "." + category.getIdentifier(), new Object[0]);
        if (key == null) {
            key = StringUtil.capitalizeWords(category.getIdentifier());
        }
        category.setLocalizedIdentifier(key);
        AbstractSidebarActivity.SidebarButtonWidget sidebarButtonWidgetText = AbstractSidebarActivity.SidebarButtonWidget.text(key, (Predicate<ScreenInstance>) screenInstance -> {
            if (this.searchQuery != null || !(screenInstance instanceof ShopOverviewActivity)) {
                return false;
            }
            ShopOverviewActivity activity = (ShopOverviewActivity) screenInstance;
            return activity.getFocusedCategory() == category;
        });
        sidebarButtonWidgetText.icon().set(category.getIcon());
        sidebarButtonWidgetText.setPressable(() -> {
            if (this.searchQuery != null) {
                this.searchWidget.setText("");
            }
            ScreenInstance screen = this.screenRendererWidget.getScreen();
            if (screen == null || screen != this.shopOverviewActivity) {
                this.screenRendererWidget.displayScreen(this.shopOverviewActivity);
            }
            this.shopOverviewActivity.focusCategory(category, () -> {
                this.recheckSidebarButtons();
            });
        });
        categoryList.addChild(sidebarButtonWidgetText);
    }

    @Subscribe
    public void onCartUpdate(ShopCartUpdateEvent event) {
        updateCartCount();
        this.itemPreviewWidget.refreshPreviewedItem();
        this.cartActivity.reload();
    }

    private void updatePreviewedItem(ShopItem shopItem) {
        if (this.screenRendererWidget.currentLabyScreen() instanceof CartActivity) {
            previewCartCosmetics();
            return;
        }
        try {
            previewCosmetic(shopItem);
        } catch (Exception e) {
            LOGGER.error("An error occurred while previewing a cosmetic", e);
        }
    }

    private void previewCosmetic(ShopItem shopItem) {
        if (shopItem == null) {
            this.modelWidget.stopEmote(null);
            this.modelFocus.reset(this.modelWidget);
        } else {
            overwriteModelCosmetics(userData -> {
                this.modelWidget.stopEmote(null);
                if (shopItem.getType() == ItemType.EMOTE) {
                    this.modelFocus.reset(this.modelWidget);
                    EmoteItem emote = LabyMod.references().emoteService().getEmote(shopItem.getItemId());
                    if (emote == null) {
                        return;
                    }
                    this.modelWidget.playEmote(emote);
                    return;
                }
                GameUserItem gameUserItem = shopItem.asGameUserItem();
                if (gameUserItem == null) {
                    return;
                }
                userData.getItems().add(gameUserItem);
                this.modelFocus.updateModelFocus(this.modelWidget, gameUserItem.definition().details(), shopItem.asCosmetic());
                Location location = DynamicBackgroundController.SHOP_PLAYER_CAMERA;
                double pitch = (location.getPitch() + getPitch(location.getY(), this.modelFocus.translation().getY())) / 180.0d;
                CameraTransitionUtil.execute(location.getX(), location.getY(), location.getZ(), location.getYaw(), pitch, location.getRoll());
            });
        }
    }

    private double getPitch(double y1, double y2) {
        double diff = y1 - y2;
        return Math.atan2(diff, 1.0d) * 57.29577951308232d;
    }

    private void previewCartCosmetics() {
        overwriteModelCosmetics(userData -> {
            this.shopController.shoppingCart().forEachGameItem(gameItem -> {
                userData.getItems().add(gameItem);
            });
        });
    }

    public void overwriteModelCosmetics(Consumer<GameUserData> userDataConsumer) {
        DefaultGameUser user = (DefaultGameUser) this.modelWidget.gameUser();
        GameUserData userData = user.getUserData();
        userData.getItems().clear();
        userDataConsumer.accept(userData);
        user.getUserItemStorage().prepare(user, userData);
        LabyMod.references().shopItemLayer().resetAnimations(user, true);
    }

    private void updateCartCount() {
        if (this.cartCountWidget == null) {
            return;
        }
        int size = this.shopController.shoppingCart().size();
        this.cartCountWidget.setComponent(Component.text(Integer.valueOf(size)));
        if (size > 0) {
            this.cartCountWidget.addId("cart-count-active");
        } else {
            this.cartCountWidget.removeId("cart-count-active");
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener.class */
    private static final class ShopActivityChangeListener extends Record implements ShoppingCart.CartStorage.ChangeListener {
        private final Supplier<ScreenRendererWidget> screenRendererWidget;
        private final Supplier<GameUser> gameUser;
        private final Runnable previewCartCosmetics;

        private ShopActivityChangeListener(Supplier<ScreenRendererWidget> screenRendererWidget, Supplier<GameUser> gameUser, Runnable previewCartCosmetics) {
            this.screenRendererWidget = screenRendererWidget;
            this.gameUser = gameUser;
            this.previewCartCosmetics = previewCartCosmetics;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ShopActivityChangeListener.class), ShopActivityChangeListener.class, "screenRendererWidget;gameUser;previewCartCosmetics", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->screenRendererWidget:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->gameUser:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->previewCartCosmetics:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShopActivityChangeListener.class), ShopActivityChangeListener.class, "screenRendererWidget;gameUser;previewCartCosmetics", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->screenRendererWidget:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->gameUser:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->previewCartCosmetics:Ljava/lang/Runnable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShopActivityChangeListener.class, Object.class), ShopActivityChangeListener.class, "screenRendererWidget;gameUser;previewCartCosmetics", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->screenRendererWidget:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->gameUser:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/core/client/gui/screen/activity/activities/labymod/child/ShopActivity$ShopActivityChangeListener;->previewCartCosmetics:Ljava/lang/Runnable;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Supplier<ScreenRendererWidget> screenRendererWidget() {
            return this.screenRendererWidget;
        }

        public Supplier<GameUser> gameUser() {
            return this.gameUser;
        }

        public Runnable previewCartCosmetics() {
            return this.previewCartCosmetics;
        }

        @Override // net.labymod.core.shop.ShoppingCart.CartStorage.ChangeListener
        public void onChange(ShoppingCart.CartStorage.ChangeListener.ChangeType type, int cosmeticId) {
            ScreenRendererWidget screenRendererWidget = this.screenRendererWidget.get();
            if (screenRendererWidget == null || !(screenRendererWidget.currentLabyScreen() instanceof CartActivity)) {
                return;
            }
            switch (type) {
                case ADD:
                    return;
                case REMOVE:
                    DefaultGameUser user = (DefaultGameUser) this.gameUser.get();
                    removeItem(user, cosmeticId);
                    return;
                case CLEAR:
                    this.previewCartCosmetics.run();
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
            }
        }

        private void removeItem(DefaultGameUser user, int cosmeticId) {
            GameUserData userData = user.getUserData();
            GameUserItem item = userData.getItem(cosmeticId);
            if (item == null) {
                return;
            }
            userData.getItems().remove(item);
        }
    }
}

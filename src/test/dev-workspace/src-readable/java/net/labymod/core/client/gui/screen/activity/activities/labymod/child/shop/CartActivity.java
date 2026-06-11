package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.notification.Notification;
import net.labymod.core.shop.ShopController;
import net.labymod.core.shop.ShoppingCart;
import net.labymod.core.shop.event.CurrencyUpdateEvent;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import net.labymod.core.shop.models.PriceItem;
import net.labymod.core.shop.models.cart.PromoCodeResponse;
import net.labymod.core.shop.models.config.ShopConfig;
import net.labymod.core.shop.models.config.ShopCurrency;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/CartActivity.class */
@AutoActivity
@Link("activity/shop/cart.lss")
public class CartActivity extends Activity {
    private static final DecimalFormat FORMAT = new DecimalFormat("#.##");
    private final ListSession<Widget> listSession = new ListSession<>();
    private final ShopController shopController;
    private final ShoppingCart cart;
    private boolean checkingOut;

    public CartActivity(ShopController shopController) {
        this.shopController = shopController;
        this.cart = shopController.shoppingCart();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Component totalArgument;
        Component promocodeComponent;
        super.initialize(parent);
        ShopCurrency selectedCurrency = this.shopController.getSelectedCurrency();
        boolean cartEmpty = this.cart.isEmpty();
        boolean showInfo = cartEmpty || this.checkingOut;
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("cart-container");
        if (showInfo) {
            DivWidget infoContainer = new DivWidget();
            infoContainer.addId("cart-info-container");
            if (cartEmpty) {
                ComponentWidget info = ComponentWidget.i18n("labymod.activity.shop.cart.empty");
                info.addId("cart-info");
                infoContainer.addChild(info);
            } else {
                VerticalListWidget<Widget> checkingOutWrapper = new VerticalListWidget<>();
                checkingOutWrapper.addId("cart-checking-out-wrapper");
                ComponentWidget info2 = ComponentWidget.i18n("labymod.activity.shop.cart.checkingOut");
                info2.addId("cart-info");
                checkingOutWrapper.addChild(info2);
                ButtonWidget cancelButton = ButtonWidget.i18n("labymod.activity.shop.cart.cancelCheckout");
                cancelButton.addId("cart-cancel");
                cancelButton.setPressable(() -> {
                    this.checkingOut = false;
                    this.cart.clear();
                    reload();
                });
                checkingOutWrapper.addChild(cancelButton);
                infoContainer.addChild(checkingOutWrapper);
            }
            flexibleContentWidget.addFlexibleContent(infoContainer);
        } else {
            VerticalListWidget<Widget> list = new VerticalListWidget<>(this.listSession);
            list.addId("cart-list");
            this.cart.forEachShopItem(shopItem -> {
                PriceItem priceFor;
                String priceString;
                HorizontalListWidget itemContainer = new HorizontalListWidget();
                itemContainer.addId("cart-item-container");
                IconWidget icon = new IconWidget(shopItem.getPrimaryIcon());
                icon.addId("item-icon");
                itemContainer.addEntry(icon);
                ComponentWidget name = ComponentWidget.text(shopItem.getName());
                name.addId("item-name");
                itemContainer.addEntry(name);
                ButtonWidget removeButton = ButtonWidget.icon(Textures.SpriteCommon.X);
                removeButton.addId("item-remove");
                removeButton.setPressable(() -> {
                    removeButton.setEnabled(false);
                    this.cart.removeItem(shopItem, result -> {
                        if (!result.isPresent()) {
                            Laby.references().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.shop.name", new Component[0])).text(Component.translatable("labymod.activity.shop.itemNotRemoved", new Component[0]).argument(Component.text(shopItem.getName()))).build());
                        }
                    });
                });
                itemContainer.addEntry(removeButton);
                if (selectedCurrency != null && (priceFor = selectedCurrency.getPriceFor(shopItem)) != null) {
                    if (priceFor.isOnlyLifetime()) {
                        priceString = priceFor.getLifetime();
                    } else {
                        priceString = "-1";
                    }
                    itemContainer.addEntry(ComponentWidget.text(priceString + selectedCurrency.getSymbol()).addId("item-price"));
                }
                list.addChild(itemContainer);
            });
            flexibleContentWidget.addFlexibleContent(new ScrollWidget((VerticalListWidget<?>) list));
        }
        if (!this.checkingOut) {
            FlexibleContentWidget flexibleContentWidget2 = new FlexibleContentWidget();
            flexibleContentWidget2.addId("cart-footer");
            HorizontalListWidget footerTop = new HorizontalListWidget();
            footerTop.addId("top-footer");
            AtomicReference<Float> totalPrice = new AtomicReference<>(Float.valueOf(0.0f));
            AtomicReference<Float> totalSalePrice = new AtomicReference<>(Float.valueOf(0.0f));
            PromoCodeResponse.Code promoCode = this.cart.getPromoCode();
            int shopItemId = promoCode == null ? -1 : promoCode.getShopItemId();
            float saleMultiplier = promoCode == null ? 1.0f : promoCode.getMultiplier();
            if (selectedCurrency != null) {
                this.cart.forEachShopItem(item -> {
                    PriceItem priceFor = selectedCurrency.getPriceFor(item);
                    if (priceFor != null && priceFor.isOnlyLifetime()) {
                        totalPrice.updateAndGet(v -> {
                            return Float.valueOf(v.floatValue() + priceFor.getLifetime());
                        });
                        totalSalePrice.updateAndGet(v2 -> {
                            if (shopItemId == -1 || shopItemId == item.getId()) {
                                return Float.valueOf(v2.floatValue() + (priceFor.getLifetime() * saleMultiplier));
                            }
                            return Float.valueOf(v2.floatValue() + priceFor.getLifetime());
                        });
                    }
                });
            }
            String currencySymbol = selectedCurrency == null ? "?" : selectedCurrency.getSymbol();
            String totalPriceString = floatToCurrency(totalPrice.get().floatValue());
            String totalSalePriceString = floatToCurrency(totalSalePrice.get().floatValue());
            if (totalPriceString.equals(totalSalePriceString)) {
                totalArgument = Component.text(totalPriceString + currencySymbol);
            } else {
                totalArgument = Component.empty().append(Component.text(totalPriceString + currencySymbol).decorate(TextDecoration.STRIKETHROUGH).color(NamedTextColor.RED)).append(Component.space()).append(Component.text(totalSalePriceString + currencySymbol));
            }
            ComponentWidget totalWidget = ComponentWidget.component(Component.translatable("labymod.activity.shop.cart.total", totalArgument));
            totalWidget.addId("cart-total");
            footerTop.addEntry(totalWidget);
            if (this.cart.getPromoCode() == null) {
                promocodeComponent = Component.translatable("labymod.activity.shop.cart.promocode.add.button", new Component[0]);
            } else {
                promocodeComponent = Component.translatable("labymod.activity.shop.cart.promocode.remove.button", Component.text(this.cart.getPromoCode().getName()));
            }
            ButtonWidget promocodeButton = ButtonWidget.component(promocodeComponent);
            promocodeButton.addId("cart-promocode");
            promocodeButton.setPressable(() -> {
                if (this.cart.getPromoCode() != null) {
                    promocodeButton.setEnabled(false);
                    this.cart.removePromoCode(result -> {
                        promocodeButton.setEnabled(true);
                        if (!result.hasException()) {
                            this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove.success");
                            return;
                        }
                        Throwable exception = result.exception().getCause();
                        if (exception instanceof ShoppingCart.ShoppingCartKeywordException) {
                            ShoppingCart.ShoppingCartKeywordException keywordException = (ShoppingCart.ShoppingCartKeywordException) exception;
                            this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove." + keywordException.getMessage());
                        } else {
                            exception.printStackTrace();
                            this.shopController.pushNotification("labymod.activity.shop.cart.promocode.remove.error");
                        }
                    });
                } else {
                    TextFieldWidget textField = new TextFieldWidget();
                    textField.setFocused(true);
                    PopupWidget.builder().title(Component.translatable("labymod.activity.shop.cart.promocode.popup.title", new Component[0])).widgetSupplier(() -> {
                        return textField;
                    }).confirmComponent(Component.translatable("labymod.activity.shop.cart.promocode.popup.redeem", new Component[0])).confirmCallback(() -> {
                        String text = textField.getText().trim();
                        if (text.isEmpty()) {
                            return;
                        }
                        promocodeButton.setEnabled(false);
                        this.cart.applyPromoCode(text.toUpperCase(Locale.ROOT), result2 -> {
                            promocodeButton.setEnabled(true);
                            if (!result2.hasException()) {
                                this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add.success");
                                return;
                            }
                            Throwable exception = result2.exception().getCause();
                            if (exception instanceof ShoppingCart.ShoppingCartKeywordException) {
                                ShoppingCart.ShoppingCartKeywordException keywordException = (ShoppingCart.ShoppingCartKeywordException) exception;
                                this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add." + keywordException.getMessage());
                            } else {
                                exception.printStackTrace();
                                this.shopController.pushNotification("labymod.activity.shop.cart.promocode.add.undefined_error");
                            }
                        });
                    }).build().displayInOverlay();
                }
            });
            footerTop.addEntry(promocodeButton);
            flexibleContentWidget2.addContent(footerTop);
            HorizontalListWidget footerBottom = new HorizontalListWidget();
            footerBottom.addId("bottom-footer");
            ShopConfig config = this.shopController.getConfig();
            if (config != null) {
                DropdownWidget<ShopCurrency> currencyDropdown = new DropdownWidget<>();
                List<ShopCurrency> currencies = config.getCurrencies();
                ShopCurrency autoCurrency = new ShopCurrency(config.getDefaultCurrency());
                currencyDropdown.add(autoCurrency);
                ShopCurrency currency = selectedCurrency;
                String preferredCurrency = Laby.labyAPI().config().other().preferredCurrency().get();
                if (preferredCurrency.equals("null")) {
                    currency = autoCurrency;
                }
                currencyDropdown.addAll(currencies);
                currencyDropdown.setSelected(currency);
                currencyDropdown.setChangeListener(newCurrency -> {
                    ConfigProperty<String> currencyProperty = Laby.labyAPI().config().other().preferredCurrency();
                    if (newCurrency.isDummy()) {
                        currencyProperty.set("null");
                    } else {
                        currencyProperty.set(newCurrency.getCode());
                    }
                });
                footerBottom.addEntry(currencyDropdown);
            }
            ButtonWidget checkoutButton = ButtonWidget.i18n("labymod.activity.shop.cart.checkout");
            checkoutButton.addId("accent-button");
            checkoutButton.removeId("primary-button");
            checkoutButton.addId("cart-checkout");
            checkoutButton.setEnabled((cartEmpty || this.cart.getCartId() == null) ? false : true);
            checkoutButton.setPressable(() -> {
                this.checkingOut = true;
                String urlSuffix = "?cart_uuid=" + String.valueOf(this.cart.getCartId());
                if (selectedCurrency != null) {
                    urlSuffix = urlSuffix + "&currency=" + selectedCurrency.getCode();
                }
                PromoCodeResponse.Code code = this.cart.getPromoCode();
                if (code instanceof PromoCodeResponse.RefCode) {
                    urlSuffix = urlSuffix + "&ref=" + code.getCode();
                }
                Laby.labyAPI().minecraft().chatExecutor().openUrl("https://www.labymod.net/checkout" + urlSuffix);
                reload();
            });
            footerBottom.addEntry(checkoutButton);
            flexibleContentWidget2.addContent(footerBottom);
            flexibleContentWidget.addContent(flexibleContentWidget2);
        }
        ((Document) this.document).addChild(flexibleContentWidget);
    }

    @Subscribe
    public void onCurrencyUpdate(CurrencyUpdateEvent event) {
        reload();
    }

    public void onCartUpdate(ShopCartUpdateEvent event) {
        Laby.labyAPI().minecraft().executeOnRenderThread(this::reload);
    }

    private String floatToCurrency(float price) {
        return FORMAT.format(price);
    }
}

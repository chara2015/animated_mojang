package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.I18n;
import net.labymod.core.shop.ShopController;
import net.labymod.core.shop.models.PriceItem;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.shop.models.config.ShopCurrency;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/widgets/ShopItemWidget.class */
@AutoWidget
public class ShopItemWidget extends FlexibleContentWidget {
    private static final String TRANSLATION_KEY_PREFIX = "labymod.activity.shop.item.";
    private final ShopItem item;
    private final ShopCurrency selectedCurrency;
    private final ShopController shopController;

    public ShopItemWidget(ShopItem item, ShopCurrency selectedCurrency, ShopController shopController) {
        this.item = item;
        this.selectedCurrency = selectedCurrency;
        this.shopController = shopController;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        PriceItem priceFor;
        String priceString;
        super.initialize(parent);
        DivWidget itemImageWrapper = new DivWidget();
        IconWidget iconWidget = new IconWidget(this.item.getPrimaryIcon());
        iconWidget.addId("item-image");
        itemImageWrapper.addId("item-image-wrapper");
        itemImageWrapper.addChild(iconWidget);
        boolean purchaseable = !this.item.isSoon();
        ComponentWidget priceComponent = null;
        if (this.item.isOwned()) {
            priceComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.price.owned").addId("item-owned");
        } else if (this.item.isFree()) {
            priceComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.price.free").addId("item-free");
        } else if (this.item.isPlusOnly() && !this.shopController.isLabyPlus()) {
            purchaseable = false;
            priceComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.price.plus").addId("item-plus");
        } else if (this.selectedCurrency != null && purchaseable && (priceFor = this.selectedCurrency.getPriceFor(this.item.getId())) != null) {
            if (priceFor.isOnlyLifetime()) {
                priceString = priceFor.getLifetime();
            } else {
                priceString = I18n.translate("labymod.activity.shop.item.price.from", Float.valueOf(priceFor.getCheapestPrice()));
            }
            priceComponent = ComponentWidget.text(priceString + this.selectedCurrency.getSymbol());
        }
        if (priceComponent != null) {
            priceComponent.addId("item-price");
            itemImageWrapper.addChild(priceComponent);
        }
        ComponentWidget infoComponent = null;
        if (this.item.isSoon()) {
            infoComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.info.soon").addId("item-info-soon");
        } else if (this.item.isPlusOnly() && this.shopController.isLabyPlus()) {
            infoComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.info.plus").addId("item-info-plus");
        } else if (this.item.isLimited()) {
            infoComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.info.limited").addId("item-info-limited");
        } else if (this.item.isNew()) {
            infoComponent = (ComponentWidget) ComponentWidget.i18n("labymod.activity.shop.item.info.new").addId("item-info-new");
        }
        if (infoComponent != null) {
            infoComponent.addId("item-info");
            itemImageWrapper.addChild(infoComponent);
        }
        addFlexibleContent(itemImageWrapper);
        DivWidget itemNameWrapper = new DivWidget();
        itemNameWrapper.addId("item-name-wrapper");
        itemNameWrapper.addChild(ComponentWidget.text(this.item.getName()).addId("item-name"));
        addContent(itemNameWrapper);
        setAttributeState(AttributeState.ENABLED, purchaseable);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        if (!isAttributeStateEnabled(AttributeState.ENABLED)) {
            return false;
        }
        return super.onPress();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected boolean playInteractionSoundAfterHandling() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected SoundType getInteractionSound() {
        return SoundType.BUTTON_CLICK;
    }

    public ShopItem item() {
        return this.item;
    }
}

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop;

import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.property.Property;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.ShopItemWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SquareGridWidget;
import net.labymod.core.shop.ShopController;
import net.labymod.core.shop.event.ShopItemOwnedStateUpdateEvent;
import net.labymod.core.shop.event.ShopLabyPlusToggleEvent;
import net.labymod.core.shop.models.ItemCategory;
import net.labymod.core.shop.models.ItemType;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.shop.models.config.ShopCurrency;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/shop/ShopOverviewActivity.class */
@AutoActivity
@Link("activity/shop/overview.lss")
public class ShopOverviewActivity extends Activity {
    private final ShopController shopController;
    private final SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> sectionedListWidget = new SectionedListWidget<>();
    private final Supplier<String> searchQuerySupplier;
    private ItemType itemType;

    public ShopOverviewActivity(ShopController shopController, Supplier<String> searchQuerySupplier, Runnable onFocusedSectionChanged) {
        this.shopController = shopController;
        this.searchQuerySupplier = searchQuerySupplier;
        this.sectionedListWidget.onFocusedSectionChanged(onFocusedSectionChanged);
        shopController.previewedItem().addChangeListener(this::refreshSelectedActiveState);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.itemType == null) {
            return;
        }
        String searchQuery = this.searchQuerySupplier.get();
        if (searchQuery != null) {
            searchQuery = searchQuery.toLowerCase(Locale.ROOT).replace(" ", "");
        }
        for (ItemCategory category : this.shopController.getCategories()) {
            if (category.getType().isVisible() && (category.getType() == this.itemType || searchQuery != null)) {
                addSection(this.sectionedListWidget, category, searchQuery);
            }
        }
        ((Document) this.document).addChild(new ScrollWidget(this.sectionedListWidget));
    }

    public void focusCategory(ItemCategory itemCategory, Runnable runnable) {
        if (this.itemType != itemCategory.getType()) {
            this.itemType = itemCategory.getType();
            reload();
            this.labyAPI.minecraft().executeNextTick(() -> {
                this.sectionedListWidget.setFocusedSection(itemCategory, true);
                runnable.run();
            });
        } else {
            this.sectionedListWidget.setFocusedSection(itemCategory, true);
            runnable.run();
        }
    }

    public ItemCategory getFocusedCategory() {
        if (this.sectionedListWidget != null) {
            return this.sectionedListWidget.getFocusedSection();
        }
        return null;
    }

    public void focusType(ItemType itemType) {
        if (this.itemType == itemType) {
            return;
        }
        this.itemType = itemType;
        if (((Document) this.document).isInitialized()) {
            reload();
        }
    }

    public ItemType getItemType() {
        return this.itemType;
    }

    public SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> getSectionedListWidget() {
        return this.sectionedListWidget;
    }

    @Subscribe
    public void onItemOwnedUpdate(ShopItemOwnedStateUpdateEvent event) {
        ShopItemWidget widgetFor = getWidgetFor(event.item());
        if (widgetFor != null) {
            widgetFor.reInitialize();
        }
    }

    private void addSection(SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> sectionedListWidget, ItemCategory itemCategory, String searchQuery) {
        ShopCurrency selectedCurrency = this.shopController.getSelectedCurrency();
        ShopItem previewedItem = this.shopController.previewedItem().get();
        SquareGridWidget<Widget> itemGrid = new SquareGridWidget<>();
        itemGrid.addId("item-grid");
        IntIterator it = itemCategory.getItems().iterator();
        while (it.hasNext()) {
            Integer id = (Integer) it.next();
            ShopItem item = this.shopController.getItem(id.intValue());
            if (item != null && (searchQuery == null || doesCosmeticMatchQuery(searchQuery, item))) {
                ShopItemWidget itemWidget = new ShopItemWidget(item, selectedCurrency, this.shopController);
                itemWidget.addId("item");
                if (previewedItem != null && previewedItem.getId() == item.getId()) {
                    itemWidget.setActive(true);
                }
                itemWidget.setPressListener(() -> {
                    Property<ShopItem> shopItemProperty = this.shopController.previewedItem();
                    if (shopItemProperty.get() != item) {
                        shopItemProperty.set(item);
                        return true;
                    }
                    return false;
                });
                DivWidget stencilWrapper = new DivWidget();
                stencilWrapper.addId("stencil-wrapper");
                stencilWrapper.addChild(itemWidget);
                itemGrid.addChild(stencilWrapper);
            }
        }
        if (itemGrid.getChildren().isEmpty()) {
            return;
        }
        String key = itemCategory.getLocalizedIdentifier();
        sectionedListWidget.addSection(Component.text(key == null ? itemCategory.getIdentifier() : key), itemCategory, itemGrid);
    }

    @Subscribe
    public void onShopLabyPlusUpdate(ShopLabyPlusToggleEvent event) {
        forEachAndGetItemWidget(widget -> {
            if (widget.item().isPlusOnly()) {
                widget.reInitialize();
                return false;
            }
            return false;
        });
    }

    private boolean doesCosmeticMatchQuery(String searchQuery, ShopItem shopItem) {
        return matches(searchQuery, shopItem.getName()) || matches(searchQuery, shopItem.getCreatorName()) || matches(searchQuery, shopItem.getSeasonName());
    }

    private boolean matches(String searchQuery, String value) {
        if (value == null) {
            return false;
        }
        return value.toLowerCase(Locale.ROOT).replace(" ", "").contains(searchQuery);
    }

    private void refreshSelectedActiveState(ShopItem shopItem) {
        int id = shopItem == null ? Integer.MIN_VALUE : shopItem.getId();
        forEachAndGetItemWidget(widget -> {
            widget.setActive(widget.item().getId() == id);
            return false;
        });
    }

    private ShopItemWidget getWidgetFor(ShopItem shopItem) {
        int id = shopItem == null ? Integer.MIN_VALUE : shopItem.getId();
        return forEachAndGetItemWidget(widget -> {
            return widget.item().getId() == id;
        });
    }

    private ShopItemWidget forEachAndGetItemWidget(Predicate<ShopItemWidget> predicate) {
        List<Widget> children = this.sectionedListWidget.getChildren();
        for (Widget child : children) {
            if (child instanceof SquareGridWidget) {
                Iterator<? extends Widget> it = child.getChildren().iterator();
                while (it.hasNext()) {
                    Widget childChild = it.next();
                    if (childChild instanceof WrappedWidget) {
                        WrappedWidget wrappedWidget = (WrappedWidget) childChild;
                        childChild = wrappedWidget.childWidget();
                    }
                    if (childChild.hasId("stencil-wrapper")) {
                        childChild = childChild.getChildren().get(0);
                    }
                    if (childChild instanceof ShopItemWidget) {
                        ShopItemWidget itemWidget = (ShopItemWidget) childChild;
                        if (predicate.test(itemWidget)) {
                            return itemWidget;
                        }
                    }
                }
            }
        }
        return null;
    }
}

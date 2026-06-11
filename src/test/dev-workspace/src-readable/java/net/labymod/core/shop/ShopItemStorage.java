package net.labymod.core.shop;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.shop.models.ShopItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShopItemStorage.class */
public class ShopItemStorage {
    private static final Logging LOGGER = Logging.create((Class<?>) ShopItemStorage.class);
    private final Set<Integer> items;
    private final Map<Integer, GameUserItem> customItems;
    private final Runnable updateListener;
    private boolean fireUpdate;

    public ShopItemStorage() {
        this(() -> {
        });
    }

    public ShopItemStorage(Runnable updateListener) {
        this.items = new HashSet();
        this.customItems = new HashMap();
        this.fireUpdate = true;
        this.updateListener = updateListener;
    }

    public Set<Integer> getItems() {
        return this.items;
    }

    public void forEach(Consumer<ShopItem> consumer) {
        for (Integer id : this.items) {
            ShopItem item = LabyMod.references().shopController().getItem(id.intValue());
            if (item != null) {
                consumer.accept(item);
            }
        }
    }

    public void forEachGameItem(Consumer<GameUserItem> consumer) {
        forEach(shopItem -> {
            if (this.customItems.containsKey(Integer.valueOf(shopItem.getId()))) {
                consumer.accept(this.customItems.get(Integer.valueOf(shopItem.getId())));
                return;
            }
            GameUserItem gameUserItem = shopItem.asGameUserItem();
            if (gameUserItem != null) {
                consumer.accept(gameUserItem);
            }
        });
    }

    public boolean canFitItem(ShopItem item) {
        AtomicBoolean result = new AtomicBoolean(true);
        forEach(stored -> {
            if (!canBeCombined(stored, item)) {
                result.set(false);
            }
        });
        return result.get();
    }

    public Collection<ShopItem> makeItemFit(ShopItem item) {
        Collection<ShopItem> remove = new HashSet<>();
        forEach(stored -> {
            if (!canBeCombined(stored, item)) {
                remove.add(stored);
            }
        });
        for (ShopItem rem : remove) {
            removeItem(rem);
        }
        return remove;
    }

    private boolean canBeCombined(ShopItem a, ShopItem b) {
        return !Objects.equals(a.getCategory(), b.getCategory());
    }

    public boolean hasItem(ShopItem item) {
        return this.items.contains(Integer.valueOf(item.getId()));
    }

    public void addItem(ShopItem item) {
        if (this.items.add(Integer.valueOf(item.getId())) && this.fireUpdate) {
            this.updateListener.run();
        }
    }

    public void addItem(GameUserItem item) {
        this.customItems.put(Integer.valueOf(item.identifier()), item);
        if (this.items.add(Integer.valueOf(item.identifier())) && this.fireUpdate) {
            this.updateListener.run();
        }
    }

    public void removeItem(ShopItem item) {
        if (this.items.remove(Integer.valueOf(item.getId()))) {
            this.updateListener.run();
        }
    }

    public void wrapUpdate(Runnable runnable) {
        this.fireUpdate = false;
        runnable.run();
        this.fireUpdate = true;
        this.updateListener.run();
    }

    public void useItemsFromUser(GameUser user) {
        wrapUpdate(() -> {
            GameUserData userData = ((DefaultGameUser) user).getUserData();
            this.items.clear();
            this.customItems.clear();
            for (GameUserItem item : userData.getItems()) {
                addItem(item);
            }
        });
    }
}

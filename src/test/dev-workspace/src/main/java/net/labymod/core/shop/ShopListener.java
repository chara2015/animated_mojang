package net.labymod.core.shop;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.notification.Notification;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.labymodnet.models.Emote;
import net.labymod.core.labymodnet.models.UserItems;
import net.labymod.core.main.LabyMod;
import net.labymod.core.shop.models.ItemType;
import net.labymod.core.shop.models.ShopItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/shop/ShopListener.class */
public class ShopListener {
    private final ShopController shopController;
    private List<Cosmetic> cosmetics;
    private List<Emote> emotes;

    ShopListener(ShopController shopController) {
        this.shopController = shopController;
    }

    public void refreshOwnedShopItems() {
        UserItems userItems = LabyMod.references().labyModNetService().getUserItems();
        if (userItems == null) {
            this.cosmetics = new ArrayList();
            this.emotes = new ArrayList();
            return;
        }
        this.cosmetics = userItems.getCosmetics();
        if (this.cosmetics == null) {
            this.cosmetics = new ArrayList();
        }
        this.emotes = userItems.getEmotes();
        if (this.emotes == null) {
            this.emotes = new ArrayList();
        }
    }

    public boolean isOwned(ShopItem item) {
        ItemType type = item.getType();
        int id = item.getItemId();
        boolean owned = false;
        if (type == ItemType.COSMETIC) {
            Iterator<Cosmetic> it = this.cosmetics.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Cosmetic cosmetic = it.next();
                if (cosmetic.getItemId() == id) {
                    owned = true;
                    break;
                }
            }
        } else if (type == ItemType.EMOTE) {
            Iterator<Emote> it2 = this.emotes.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Emote emote = it2.next();
                if (emote.getItemId() == id) {
                    owned = true;
                    break;
                }
            }
        }
        return owned;
    }

    @Subscribe
    public void onLabyModNet(LabyModNetRefreshEvent event) {
        LabyConnectSession session;
        refreshOwnedShopItems();
        ObjectIterator it = this.shopController.getShopItems().iterator();
        while (it.hasNext()) {
            ShopItem shopItem = (ShopItem) it.next();
            shopItem.setOwned(isOwned(shopItem));
        }
        DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService) LabyMod.references().labyModNetService();
        if (labyModNetService.getState() == LabyModNetService.State.OK && (session = Laby.references().labyConnect().getSession()) != null && session.self().getUniqueId() == labyModNetService.getUserUniqueId()) {
            this.shopController.connectedToLabyConnect().set(true);
        }
    }

    @Subscribe
    public void updateOutfit(UserUpdateDataEvent event) {
        if (event.phase() == Phase.POST && event.gameUser().getUniqueId().equals(Laby.labyAPI().getUniqueId())) {
            this.shopController.currentOutfit().useItemsFromUser(event.gameUser());
        }
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        if (!event.previousSession().getUniqueId().equals(event.newSession().getUniqueId()) && this.shopController.shoppingCart().clear()) {
            Notification notification = Notification.builder().title(Component.text("LabyMod Shop")).text(Component.translatable("labymod.activity.shop.cart.notification.sessionClear", new Component[0])).build();
            Laby.references().notificationController().push(notification);
        }
    }

    @Subscribe
    public void onLabyConnect(LabyConnectStateUpdateEvent event) {
        if (event.state() != LabyConnectState.PLAY) {
            this.shopController.connectedToLabyConnect().set(false);
            this.shopController.setLabyPlus(false);
        } else {
            LabyConnectSession session = Laby.references().labyConnect().getSession();
            this.shopController.setLabyPlus(session != null && session.self().isLabyPlus());
            DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService) LabyMod.references().labyModNetService();
            this.shopController.connectedToLabyConnect().set(Boolean.valueOf(labyModNetService.getState() == LabyModNetService.State.OK && labyModNetService.getUserUniqueId().equals(Laby.labyAPI().getUniqueId())));
        }
    }
}

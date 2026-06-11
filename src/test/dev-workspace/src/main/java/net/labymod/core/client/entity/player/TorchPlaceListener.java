package net.labymod.core.client.entity.player;

import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerUseItemOnBlockEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/TorchPlaceListener.class */
public class TorchPlaceListener {
    private final LabyAPI labyAPI;
    private final ConfigProperty<Boolean> enabled;

    public TorchPlaceListener(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.enabled = labyAPI.config().ingame().improvedTorchPlacementInOffHand();
    }

    @Subscribe
    public void onClientPlayerUseItemOnBlock(ClientPlayerUseItemOnBlockEvent event) {
        if (!this.enabled.get().booleanValue()) {
            return;
        }
        ClientPlayer player = event.clientPlayer();
        ItemStack itemOff = player.getOffHandItemStack();
        if (itemOff.isAir()) {
            return;
        }
        ItemStack itemMain = player.getMainHandItemStack();
        if (itemMain.isFood() || itemMain.isBow() || itemMain.isItem(VanillaItems.GLASS_BOTTLE)) {
            event.setCancelled(true);
        }
    }
}

package net.labymod.core.main.listener;

import net.labymod.api.LabyAPI;
import net.labymod.api.Namespaces;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.entity.layers.ItemInHandLayerRenderEvent;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.event.client.render.model.entity.HumanoidModelPoseAnimationEvent;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/TotemListener.class */
public class TotemListener {
    private static final ResourceLocation TOTEM_IDENTIFIER = ResourceLocation.create(Namespaces.MINECRAFT, "totem_of_undying");
    private final Minecraft minecraft;
    private final ConfigProperty<Boolean> configProperty;

    public TotemListener(LabyAPI labyAPI) {
        this.minecraft = labyAPI.minecraft();
        this.configProperty = labyAPI.config().ingame().hideTotemInOffHand();
    }

    @Subscribe
    public void cancelShieldInOffHandFirstPerson(RenderFirstPersonItemInHandEvent event) {
        if (isEnabled() && event.phase() == RenderFirstPersonItemInHandEvent.TransformPhase.HEAD && event.hand() == LivingEntity.Hand.OFF_HAND && isTotem(event.itemStack())) {
            event.setRenderItem(false);
        }
    }

    @Subscribe
    public void cancelVanillaArmPoseThirdPerson(HumanoidModelPoseAnimationEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (!isMainHand(player, event.handSide()) && isTotem(player.getOffHandItemStack())) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void modifyItemsInHandInThirdPerson(ItemInHandLayerRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        LivingEntity entity = event.livingEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (!isMainHand(player, event.handSide()) && isTotem(event.itemStack())) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onIngameOverlayElementRender(IngameOverlayElementRenderEvent event) {
        if (!isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        IngameOverlayElementRenderEvent.OverlayElementType type = event.elementType();
        ClientPlayer player = this.minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        if ((type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_ITEM || type == IngameOverlayElementRenderEvent.OverlayElementType.OFFHAND_TEXTURE) && isTotem(player.getOffHandItemStack())) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onHudWidgetDropzoneElementShift(HudWidgetDropzoneElementShiftEvent event) {
        if (isEnabled() && event.isOffHandSide() && isTotem(event.itemStack())) {
            event.setCancelled(true);
        }
    }

    public boolean isMainHand(LivingEntity entity, LivingEntity.HandSide side) {
        return entity.isMainHandRight() == (side == LivingEntity.HandSide.RIGHT);
    }

    public boolean isTotem(ItemStack itemStack) {
        return itemStack.getIdentifier().equals(TOTEM_IDENTIFIER);
    }

    public boolean isEnabled() {
        return this.configProperty.get().booleanValue();
    }
}

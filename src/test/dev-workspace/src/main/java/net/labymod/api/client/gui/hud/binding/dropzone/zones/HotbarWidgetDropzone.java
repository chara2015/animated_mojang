package net.labymod.api.client.gui.hud.binding.dropzone.zones;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.options.AttackIndicatorPosition;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.render.overlay.HudWidgetDropzoneElementShiftEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/dropzone/zones/HotbarWidgetDropzone.class */
public abstract class HotbarWidgetDropzone extends HudWidgetDropzone {
    private static final int HOTBAR_WIDTH = 150;
    private static final int OFFHAND_OFFSET = 30;
    private static final int INDICATOR_OFFSET = 25;
    private final boolean isLeft;

    public HotbarWidgetDropzone(String id, boolean isLeft) {
        super(id);
        this.isLeft = isLeft;
    }

    public float getOffset(HudWidgetRendererAccessor renderer, HudSize hudWidgetSize) {
        float offset = 94.0f;
        if (!renderer.isEditor()) {
            Minecraft minecraft = Laby.labyAPI().minecraft();
            ClientPlayer clientPlayer = minecraft.getClientPlayer();
            ItemStack mainHandItemStack = clientPlayer == null ? null : clientPlayer.getMainHandItemStack();
            ItemStack offHandItemStack = clientPlayer == null ? null : clientPlayer.getOffHandItemStack();
            MainHand mainHand = minecraft.options().mainHand();
            boolean isOffhandSide = this.isLeft == (mainHand != MainHand.LEFT);
            ItemStack itemStack = isOffhandSide ? offHandItemStack : mainHandItemStack;
            if (isOffhandSide && itemStack != null && !itemStack.isAir()) {
                HudWidgetDropzoneElementShiftEvent event = (HudWidgetDropzoneElementShiftEvent) Laby.fireEvent(new HudWidgetDropzoneElementShiftEvent(true, itemStack));
                if (!event.isCancelled()) {
                    offset = 94.0f + 30.0f;
                }
            }
            AttackIndicatorPosition indicatorPosition = minecraft.options().attackIndicatorPosition();
            if (indicatorPosition == AttackIndicatorPosition.HOTBAR && !isOffhandSide) {
                HudWidgetDropzoneElementShiftEvent event2 = (HudWidgetDropzoneElementShiftEvent) Laby.fireEvent(new HudWidgetDropzoneElementShiftEvent(false, itemStack));
                if (!event2.isCancelled()) {
                    offset += 25.0f;
                }
            }
        }
        return this.isLeft ? (-offset) - hudWidgetSize.getScaledWidth() : offset;
    }

    @Override // net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone
    public HudWidgetAnchor getAnchor() {
        if (this.isLeft) {
            return HudWidgetAnchor.RIGHT_BOTTOM;
        }
        return HudWidgetAnchor.LEFT_BOTTOM;
    }
}

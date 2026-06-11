package net.labymod.v1_21.mixins.client.gui.screens.inventory;

import net.labymod.api.Textures;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v1_21.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/gui/screens/inventory/MixinInventoryScreen.class */
@Mixin({fpt.class})
public abstract class MixinInventoryScreen extends fpl<cqw> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;

    public MixinInventoryScreen(cqw param0, cmw param1, wz param2) {
        super(param0, param1, param2);
    }

    @Insert(method = {"init()V"}, at = @At("TAIL"))
    private void labyMod$init(InsertInfo info) {
        if (this.labyMod$inventorySlotRegistry == null) {
            this.labyMod$inventorySlotRegistry = LabyMod.references().inventorySlotRegistry();
        }
        InventoryLayoutOldAnimation animation = (InventoryLayoutOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(InventoryLayoutOldAnimation.NAME);
        if (animation == null) {
            return;
        }
        if (animation.removeRecipeBook()) {
            labyMod$removeRecipeBook();
        }
        this.labyMod$oldSurvivalLayoutTexture = animation.canUseOldSurvivalInventory();
        for (crq slot : D().i) {
            getSlotByIndex(animation, slot);
        }
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(fhz graphics, akr location, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = (akr) Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.a(location, $$1, $$2, $$3, $$4, $$5, $$6);
    }

    private void labyMod$removeRecipeBook() {
        p();
    }

    @NotNull
    private crq getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull crq slot) {
        Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.d);
        if (pair == null) {
            return slot;
        }
        InventorySlotData legacySlot = pair.getFirst();
        InventorySlotData modernSlot = pair.getSecond();
        if (inventoryLayout.canUseOldSurvivalInventory()) {
            return ((SlotAccessor) slot).setPosition(legacySlot.getX(), legacySlot.getY());
        }
        return ((SlotAccessor) slot).setPosition(modernSlot.getX(), modernSlot.getY());
    }
}

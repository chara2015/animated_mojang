package net.labymod.v1_16_5.mixins.client.gui.screens.inventory;

import net.labymod.api.Textures;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v1_16_5.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/gui/screens/inventory/MixinInventoryScreen.class */
@Mixin({dql.class})
public abstract class MixinInventoryScreen extends dqe<biz> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;

    public MixinInventoryScreen(biz param0, bfv param1, nr param2) {
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
        for (bjr slot : h().a) {
            getSlotByIndex(animation, slot);
        }
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bind(Lnet/minecraft/resources/ResourceLocation;)V"))
    private void labyMod$setOldSurvivalLayoutTexture(ekd instance, vk location) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            instance.a((vk) Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation());
        } else {
            instance.a(location);
        }
    }

    private void labyMod$removeRecipeBook() {
        this.m.clear();
    }

    @NotNull
    private bjr getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull bjr slot) {
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

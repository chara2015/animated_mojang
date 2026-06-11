package net.labymod.v1_12_2.mixins.client.gui;

import net.labymod.api.Textures;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinGuiInventory.class */
@Mixin({bmx.class})
public abstract class MixinGuiInventory extends bmg {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;

    public MixinGuiInventory(afr lvt_1_1_) {
        super(lvt_1_1_);
    }

    @Insert(method = {"initGui()V"}, at = @At("TAIL"))
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
        for (agr slot : this.h.c) {
            getSlotByIndex(animation, slot);
        }
    }

    @Redirect(method = {"drawGuiContainerBackgroundLayer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void labyMod$setOldSurvivalLayoutTexture(cdr instance, nf location) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            instance.a((nf) Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation());
        } else {
            instance.a(location);
        }
    }

    private void labyMod$removeRecipeBook() {
        this.n.clear();
    }

    @NotNull
    private agr getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull agr slot) {
        Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.e);
        if (pair == null) {
            return slot;
        }
        InventorySlotData legacySlot = pair.getFirst();
        InventorySlotData modernSlot = pair.getSecond();
        if (inventoryLayout.canUseOldSurvivalInventory()) {
            slot.f = legacySlot.getX();
            slot.g = legacySlot.getY();
        } else {
            slot.f = modernSlot.getX();
            slot.g = modernSlot.getY();
        }
        return slot;
    }
}

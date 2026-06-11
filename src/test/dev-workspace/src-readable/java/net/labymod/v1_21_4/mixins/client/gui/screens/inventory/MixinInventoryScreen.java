package net.labymod.v1_21_4.mixins.client.gui.screens.inventory;

import java.util.function.Function;
import net.labymod.api.Textures;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v1_21_4.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/gui/screens/inventory/MixinInventoryScreen.class */
@Mixin({fwc.class})
public abstract class MixinInventoryScreen extends fvb<ctg> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;

    public MixinInventoryScreen(ctg param0, cox param1, wp param2) {
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
        for (cua slot : F().k) {
            getSlotByIndex(animation, slot);
        }
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Ljava/util/function/Function;Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(fof graphics, Function<akv, gmj> renderTypeMapper, akv location, int $$1, int $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = (akv) Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.a(renderTypeMapper, location, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    private void labyMod$removeRecipeBook() {
        p();
    }

    @NotNull
    private cua getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull cua slot) {
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

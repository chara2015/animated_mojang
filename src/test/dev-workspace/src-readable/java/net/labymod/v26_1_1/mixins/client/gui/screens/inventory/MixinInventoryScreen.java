package net.labymod.v26_1_1.mixins.client.gui.screens.inventory;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.labymod.api.Textures;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v26_1_1.client.world.inventory.SlotAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/gui/screens/inventory/MixinInventoryScreen.class */
@Mixin({InventoryScreen.class})
public abstract class MixinInventoryScreen extends AbstractContainerScreen<InventoryMenu> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldSurvivalLayoutTexture;

    public MixinInventoryScreen(InventoryMenu param0, Inventory param1, Component param2) {
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
        for (Slot slot : getMenu().slots) {
            getSlotByIndex(animation, slot);
        }
    }

    @Redirect(method = {"extractBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V"))
    private void labyMod$setOldSurvivalLayoutTexture(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int $$1, int $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
        if (this.labyMod$oldSurvivalLayoutTexture) {
            location = (Identifier) Textures.SURVIVAL_INVENTORY_BACKGROUND.getMinecraftLocation();
        }
        graphics.blit(renderPipeline, location, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    private void labyMod$removeRecipeBook() {
        clearWidgets();
    }

    @NotNull
    private Slot getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull Slot slot) {
        Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.PLAYER, slot.index);
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

package net.labymod.v26_1.mixins.client.gui.screens.inventory;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v26_1.client.world.inventory.SlotAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/client/gui/screens/inventory/MixinCreativeModeInventoryScreen.class */
@Mixin({CreativeModeInventoryScreen.class})
public abstract class MixinCreativeModeInventoryScreen extends AbstractContainerScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldCreativeLayoutTexture;
    private InventoryLayoutOldAnimation labyMod$inventoryLayout;
    private int labyMod$entityXShift;
    private int labyMod$entityYShift;

    public MixinCreativeModeInventoryScreen(CreativeModeInventoryScreen.ItemPickerMenu pickerMenu, Inventory inventory, Component component) {
        super(pickerMenu, inventory, component);
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
        this.labyMod$inventoryLayout = animation;
        this.labyMod$oldCreativeLayoutTexture = animation.canUseOldCreativeLayout();
        this.labyMod$entityXShift = animation.getEntityXShift();
        this.labyMod$entityYShift = animation.getEntityYShift();
        labyMod$modifySlots();
    }

    @Redirect(method = {"extractBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;extractEntityInInventoryFollowsMouse(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIIFFFLnet/minecraft/world/entity/LivingEntity;)V"))
    private void labyMod$setEntityToOldPosition(GuiGraphicsExtractor graphics, int x, int y, int width, int height, int scale, float modelHeight, float mouseX, float mouseY, LivingEntity entity) {
        if (this.labyMod$oldCreativeLayoutTexture) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, x - this.labyMod$entityXShift, y - this.labyMod$entityYShift, width, height, scale, modelHeight, mouseX - this.labyMod$entityXShift, mouseY - this.labyMod$entityYShift, entity);
        } else {
            InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, x, y, width, height, scale, modelHeight, mouseX, mouseY, entity);
        }
    }

    @Redirect(method = {"extractBackground"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIII)V", ordinal = 0))
    private void labyMod$setOldCreativeTexture(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier location, int $$1, int $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8) {
        if (!this.labyMod$oldCreativeLayoutTexture) {
            graphics.blit(renderPipeline, location, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
            return;
        }
        String path = location.getPath();
        int lastIndex = path.lastIndexOf(47);
        String name = path.substring(lastIndex);
        int firstIndex = name.indexOf(95);
        String name2 = name.substring(firstIndex + 1);
        String name3 = name2.substring(0, name2.length() - ".png".length());
        ResourceLocation resourceLocation = null;
        if (name3.equals("inventory")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_INVENTORY;
        }
        if (name3.equals("item_search")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_ITEM_SEARCH;
        }
        if (name3.equals("items")) {
            resourceLocation = Textures.CREATIVE_INVENTORY_TAB_ITEMS;
        }
        if (resourceLocation != null) {
            graphics.blit(renderPipeline, (Identifier) resourceLocation.getMinecraftLocation(), $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
        }
    }

    @Inject(method = {"selectTab"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER)})
    private void labyMod$onSelectTab(CreativeModeTab tab, CallbackInfo ci) {
        labyMod$modifySlots();
    }

    private void labyMod$modifySlots() {
        if (this.labyMod$oldCreativeLayoutTexture) {
            for (Slot slot : getMenu().slots) {
                Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.CREATIVE, slot.x, slot.y);
                if (pair != null) {
                    getSlotByIndex(this.labyMod$inventoryLayout, pair.getFirst(), slot);
                }
            }
        }
    }

    @NotNull
    private Slot getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull InventorySlotData legacySlot, @NotNull Slot slot) {
        if (!inventoryLayout.canUseOldCreativeLayout()) {
            return slot;
        }
        return ((SlotAccessor) slot).setPosition(legacySlot.getX(), legacySlot.getY());
    }
}

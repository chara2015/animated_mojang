package net.labymod.v1_19_4.mixins.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.gui.inventory.InventorySlotData;
import net.labymod.core.client.gui.inventory.InventorySlotRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.InventoryLayoutOldAnimation;
import net.labymod.v1_19_4.client.world.inventory.SlotAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/gui/screens/inventory/MixinCreativeModeInventoryScreen.class */
@Mixin({eup.class})
public abstract class MixinCreativeModeInventoryScreen extends eus<b> {
    private InventorySlotRegistry labyMod$inventorySlotRegistry;
    private boolean labyMod$oldCreativeLayoutTexture;
    private InventoryLayoutOldAnimation labyMod$inventoryLayout;
    private int labyMod$entityXShift;
    private int labyMod$entityYShift;

    public MixinCreativeModeInventoryScreen(b pickerMenu, byl inventory, tj component) {
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
        this.labyMod$entityXShift = animation.getEntityXShiftOld();
        this.labyMod$entityYShift = animation.getEntityYShift();
        labyMod$modifySlots();
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/InventoryScreen;renderEntityInInventoryFollowsMouse(Lcom/mojang/blaze3d/vertex/PoseStack;IIIFFLnet/minecraft/world/entity/LivingEntity;)V"))
    private void labyMod$setEntityToOldPosition(ehe poseStack, int x, int y, int scale, float mouseX, float mouseY, bfx entity) {
        if (this.labyMod$oldCreativeLayoutTexture) {
            eva.a(poseStack, x - this.labyMod$entityXShift, y - this.labyMod$entityYShift, scale, mouseX - this.labyMod$entityXShift, mouseY - this.labyMod$entityYShift, entity);
        } else {
            eva.a(poseStack, x, y, scale, mouseX, mouseY, entity);
        }
    }

    @Redirect(method = {"renderBg"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", ordinal = 1))
    private void labyMod$setOldCreativeTexture(int slot, add location) {
        if (!this.labyMod$oldCreativeLayoutTexture) {
            RenderSystem.setShaderTexture(slot, location);
            return;
        }
        String path = location.a();
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
            RenderSystem.setShaderTexture(slot, (add) resourceLocation.getMinecraftLocation());
        }
    }

    @Inject(method = {"selectTab"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER)})
    private void labyMod$onSelectTab(ced tab, CallbackInfo ci) {
        labyMod$modifySlots();
    }

    private void labyMod$modifySlots() {
        if (this.labyMod$oldCreativeLayoutTexture) {
            for (ccw slot : C().i) {
                Pair<InventorySlotData, InventorySlotData> pair = this.labyMod$inventorySlotRegistry.getModernSlot(InventorySlotRegistry.InventoryType.CREATIVE, slot.f, slot.g);
                if (pair != null) {
                    getSlotByIndex(this.labyMod$inventoryLayout, pair.getFirst(), slot);
                }
            }
        }
    }

    @NotNull
    private ccw getSlotByIndex(@NotNull InventoryLayoutOldAnimation inventoryLayout, @NotNull InventorySlotData legacySlot, @NotNull ccw slot) {
        if (!inventoryLayout.canUseOldCreativeLayout()) {
            return slot;
        }
        return ((SlotAccessor) slot).setPosition(legacySlot.getX(), legacySlot.getY());
    }
}

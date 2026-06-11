package net.labymod.v1_8_9.mixins.client.world.item;

import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.Item;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.v1_8_9.client.util.VersionedWailaUtil;
import net.labymod.v1_8_9.client.world.item.VersionedAirItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/world/item/MixinItemStack.class */
@Mixin({zx.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {
    private ResourceLocation labyMod$defaultAirLocation;
    private int labyMod$lastItemSlot;

    @Shadow
    private zw d;

    @Shadow
    public int c;

    @Shadow
    public int b;

    @Shadow
    private int f;

    @Shadow
    private dn e;

    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer = new NbtDataComponentContainer((Supplier<NBTTagCompound>) () -> {
        return this.e;
    });

    @Shadow
    public abstract zw b();

    @Shadow
    public abstract String shadow$q();

    @Shadow
    public abstract int h();

    @Shadow
    public abstract aba m();

    @Shadow
    protected abstract boolean d(zx zxVar);

    @Shadow
    public abstract dn o();

    @Shadow
    public abstract void d(dn dnVar);

    @Shadow
    public abstract zx shadow$k();

    /* JADX WARN: Removed duplicated region for block: B:19:0x00b0  */
    @org.spongepowered.asm.mixin.injection.Inject(method = {"getTooltip"}, at = {@org.spongepowered.asm.mixin.injection.At("RETURN")}, cancellable = true)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void labyMod$fireTooltipEvent(wn r8, boolean r9, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable<java.util.List<java.lang.String>> r10) {
        /*
            Method dump skipped, instruction units count: 289
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.v1_8_9.mixins.client.world.item.MixinItemStack.labyMod$fireTooltipEvent(wn, boolean, org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable):void");
    }

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        return getAsItem().getIdentifier();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return getAsItem().getMaximumStackSize();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return getAsItem().getMaximumDamage();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        Item item = b();
        return item != null ? item : VersionedAirItem.AIR;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return h();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return this.c;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        return this.d == zy.m || this.d == zy.q || this.d == zy.l || this.d == zy.B || this.d == zy.u;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return this.d instanceof yo;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        if (b() == null) {
            return false;
        }
        aba action = m();
        return action == aba.b || action == aba.c;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int foodNutrition() {
        zs zsVarB = b();
        if (zsVarB instanceof zs) {
            zs food = zsVarB;
            return food.h((zx) null);
        }
        return 0;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public float foodSaturationValue() {
        zs zsVarB = b();
        if (zsVarB instanceof zs) {
            zs food = zsVarB;
            return food.i((zx) null) * food.h((zx) null) * 2.0f;
        }
        return 0.0f;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return this.d == zy.aR || this.d == zy.bY;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getSize() {
        return this.b;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.b = size;
    }

    @Redirect(method = {"getIsItemStackEqual"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemStackEqual(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean labyMod$fixEqualsCheck(zx instance, zx other) {
        return labyMod$equals(d(other), (zx) this, other);
    }

    private boolean labyMod$equals(boolean legacyResult, zx thisStack, zx otherStack) {
        if (!Laby.labyAPI().config().multiplayer().classicPvP().oldEquip().enabled().get().booleanValue()) {
            return legacyResult;
        }
        boolean equalsStackSize = thisStack.b == otherStack.b;
        boolean equalsItem = thisStack.b() == otherStack.b();
        boolean equalsNBT = thisStack.o() != null || otherStack.o() == null;
        boolean equalsNBTCompound = thisStack.o() == null || thisStack.o().equals(otherStack.o());
        boolean equals = equalsStackSize && equalsItem && equalsNBT && equalsNBTCompound;
        boolean isBowItem = (otherStack.b() instanceof yt) || (thisStack.b() instanceof yt);
        boolean isFlintAndSteelItem = (otherStack.b() instanceof zr) || (thisStack.b() instanceof zr);
        boolean result = (isBowItem || isFlintAndSteelItem) ? equals || legacyResult : legacyResult;
        if (ave.A().h != null) {
            int slot = ave.A().h.bi.c;
            if (slot != this.labyMod$lastItemSlot) {
                result = false;
            }
            if (thisStack.equals(otherStack)) {
                this.labyMod$lastItemSlot = slot;
            }
        }
        return result;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer().deserialize(shadow$q());
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        NBTTagCompound wrapped = this.labyMod$dataComponentContainer.getWrapped();
        return wrapped == null ? DataComponentContainer.EMPTY : this.labyMod$dataComponentContainer;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getOrCreateDataComponentContainer() {
        getOrCreateNBTTag();
        return getDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @Nullable
    public NBTTagCompound getNBTTag() {
        return o();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        dn tag = o();
        if (tag == null) {
            dn dnVar = new dn();
            tag = dnVar;
            d(dnVar);
        }
        return (NBTTagCompound) tag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return zx.b((zx) itemStack, (zx) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$k();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getLegacyItemData() {
        return this.f;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setLegacyItemData(int legacyData) {
        this.f = legacyData;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return VersionedWailaUtil.canHarvest((zx) this, state.block());
    }
}

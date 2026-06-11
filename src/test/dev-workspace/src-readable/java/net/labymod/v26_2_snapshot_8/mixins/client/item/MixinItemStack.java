package net.labymod.v26_2_snapshot_8.mixins.client.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.v26_2_snapshot_8.client.component.data.VersionedDataComponentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/item/MixinItemStack.class */
@Mixin({ItemStack.class})
@Implements({@Interface(iface = net.labymod.api.client.world.item.ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements net.labymod.api.client.world.item.ItemStack {

    @Shadow
    private int count;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer = new VersionedDataComponentContainer((Supplier<DataComponentMap>) this::getComponents);

    @Deprecated
    private NBTTagCompound labyMod$dummyTag;

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract int getDamageValue();

    @Shadow
    public abstract int getUseDuration(LivingEntity livingEntity);

    @Shadow
    public abstract Component getHoverName();

    @Shadow
    public abstract ItemStack shadow$copy();

    @Shadow
    public abstract DataComponentMap getComponents();

    @Shadow
    public abstract int getMaxDamage();

    @Shadow
    public abstract boolean isCorrectToolForDrops(BlockState blockState);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(Item.TooltipContext context, Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<Component> lines = new ArrayList<>((Collection<? extends Component>) cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(lines);
            return;
        }
        if (Minecraft.getInstance().options.advancedItemTooltips) {
            tooltipType = ItemStackTooltipEvent.TooltipType.ADVANCED;
        } else {
            tooltipType = ItemStackTooltipEvent.TooltipType.NORMAL;
        }
        ItemStackTooltipEvent.TooltipType type = tooltipType;
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        List<net.labymod.api.client.component.Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        ItemStackTooltipEvent event = (ItemStackTooltipEvent) Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.isCreative()));
        lines.clear();
        for (net.labymod.api.client.component.Component line : event.getTooltipLines()) {
            lines.add((Component) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(lines);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public net.labymod.api.client.world.item.Item getAsItem() {
        return getItem();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return getDamageValue();
    }

    @Intrinsic
    public int itemStack$getUseDuration(net.labymod.api.client.entity.LivingEntity entity) {
        return getUseDuration((LivingEntity) entity);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        Item item = getItem();
        return item == Items.WOODEN_SWORD || item == Items.STONE_SWORD || item == Items.IRON_SWORD || item == Items.GOLDEN_SWORD || item == Items.DIAMOND_SWORD || item == Items.NETHERITE_SWORD || item == Items.COPPER_SWORD;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return getItem() instanceof BlockItem;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        FoodProperties foodProperties = (FoodProperties) getComponents().get(DataComponents.FOOD);
        return foodProperties != null;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return getItem() == Items.FISHING_ROD || getItem() == Items.CARROT_ON_A_STICK;
    }

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        return getAsItem().getIdentifier();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return ((ItemStack) this).getMaxStackSize();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return getMaxDamage();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getSize() {
        return this.count;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.count = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public net.labymod.api.client.component.Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(getHoverName());
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        DataComponentMap wrapped = container.getWrapped();
        if (wrapped == DataComponentMap.EMPTY) {
            return DataComponentContainer.EMPTY;
        }
        return container;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getOrCreateDataComponentContainer() {
        return getDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @Nullable
    public NBTTagCompound getNBTTag() {
        return null;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        if (this.labyMod$dummyTag == null) {
            this.labyMod$dummyTag = new CompoundTag();
        }
        return this.labyMod$dummyTag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(net.labymod.api.client.world.item.ItemStack itemStack) {
        return ItemStack.matches((ItemStack) itemStack, (ItemStack) this);
    }

    @Intrinsic
    public net.labymod.api.client.world.item.ItemStack itemStack$copy() {
        return shadow$copy();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(net.labymod.api.client.world.block.BlockState state) {
        return isCorrectToolForDrops((BlockState) state);
    }
}

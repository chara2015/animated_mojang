package net.labymod.v1_21_11.mixins.client.item;

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
import net.labymod.v1_21_11.client.component.data.VersionedDataComponentContainer;
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

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/item/MixinItemStack.class */
@Mixin({ItemStack.class})
@Implements({@Interface(iface = net.labymod.api.client.world.item.ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements net.labymod.api.client.world.item.ItemStack {

    @Shadow
    private int r;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer = new VersionedDataComponentContainer((Supplier<DataComponentMap>) this::a);

    @Deprecated
    private NBTTagCompound labyMod$dummyTag;

    @Shadow
    public abstract Item h();

    @Shadow
    public abstract int o();

    @Shadow
    public abstract int a(LivingEntity livingEntity);

    @Shadow
    public abstract Component y();

    @Shadow
    public abstract ItemStack shadow$v();

    @Shadow
    public abstract DataComponentMap a();

    @Shadow
    public abstract int k();

    @Shadow
    public abstract int p();

    @Shadow
    public abstract boolean b(BlockState blockState);

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
        ItemStackTooltipEvent event = Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.isCreative()));
        lines.clear();
        for (net.labymod.api.client.component.Component line : event.getTooltipLines()) {
            lines.add((Component) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(lines);
    }

    @NotNull
    public net.labymod.api.client.world.item.Item getAsItem() {
        return h();
    }

    public int getCurrentDamageValue() {
        return o();
    }

    @Intrinsic
    public int itemStack$getUseDuration(net.labymod.api.client.entity.LivingEntity entity) {
        return a((LivingEntity) entity);
    }

    public boolean isSword() {
        Item item = h();
        return item == Items.WOODEN_SWORD || item == Items.STONE_SWORD || item == Items.IRON_SWORD || item == Items.GOLDEN_SWORD || item == Items.DIAMOND_SWORD || item == Items.NETHERITE_SWORD || item == Items.COPPER_SWORD;
    }

    public boolean isItem() {
        return !isBlock();
    }

    public boolean isBlock() {
        return h() instanceof BlockItem;
    }

    public boolean isFood() {
        FoodProperties foodProperties = (FoodProperties) a().get(DataComponents.FOOD);
        return foodProperties != null;
    }

    public boolean isAir() {
        return getAsItem().isAir();
    }

    public boolean isFishingTool() {
        return h() == Items.FISHING_ROD || h() == Items.CARROT_ON_A_STICK;
    }

    public ResourceLocation getIdentifier() {
        return getAsItem().getIdentifier();
    }

    public int getMaximumStackSize() {
        return k();
    }

    public int getMaximumDamage() {
        return p();
    }

    public int getSize() {
        return this.r;
    }

    public void setSize(int size) {
        this.r = size;
    }

    public net.labymod.api.client.component.Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(y());
    }

    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        DataComponentMap wrapped = container.getWrapped();
        if (wrapped == DataComponentMap.EMPTY) {
            return DataComponentContainer.EMPTY;
        }
        return container;
    }

    @NotNull
    public DataComponentContainer getOrCreateDataComponentContainer() {
        return getDataComponentContainer();
    }

    @Nullable
    public NBTTagCompound getNBTTag() {
        return null;
    }

    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        if (this.labyMod$dummyTag == null) {
            this.labyMod$dummyTag = new CompoundTag();
        }
        return this.labyMod$dummyTag;
    }

    public boolean matches(net.labymod.api.client.world.item.ItemStack itemStack) {
        return ItemStack.matches((ItemStack) itemStack, (ItemStack) this);
    }

    @Intrinsic
    public net.labymod.api.client.world.item.ItemStack itemStack$copy() {
        return shadow$v();
    }

    public boolean isCorrectTool(net.labymod.api.client.world.block.BlockState state) {
        return b((BlockState) state);
    }
}

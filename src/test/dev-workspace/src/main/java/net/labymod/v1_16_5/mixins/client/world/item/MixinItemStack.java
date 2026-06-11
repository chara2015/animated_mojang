package net.labymod.v1_16_5.mixins.client.world.item;

import java.util.List;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.Item;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/world/item/MixinItemStack.class */
@Mixin({bmb.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int f;

    @Shadow
    private md i;

    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer = new NbtDataComponentContainer((Supplier<NBTTagCompound>) () -> {
        return this.i;
    });

    @Shadow
    public abstract blx b();

    @Shadow
    public abstract int g();

    @Shadow
    public abstract boolean F();

    @Shadow
    public abstract int k();

    @Shadow
    public abstract nr r();

    @Shadow
    public abstract md p();

    @Shadow
    public abstract md o();

    @Shadow
    public abstract bmb shadow$i();

    @Shadow
    public abstract boolean b(ceh cehVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(bfw player, bnl flag, CallbackInfoReturnable<List<nr>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<?> list = (List) cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(list);
            return;
        }
        if (djz.C().k.p) {
            tooltipType = ItemStackTooltipEvent.TooltipType.ADVANCED;
        } else {
            tooltipType = ItemStackTooltipEvent.TooltipType.NORMAL;
        }
        ItemStackTooltipEvent.TooltipType type = tooltipType;
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        List<Component> mappedLines = componentMapper.fromMinecraftComponents(list);
        ItemStackTooltipEvent event = (ItemStackTooltipEvent) Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, player != null && player.b_()));
        list.clear();
        for (Component line : event.getTooltipLines()) {
            list.add((nr) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(list);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        return b();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return g();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return k();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        blx item = b();
        return item == bmd.kl || item == bmd.kq || item == bmd.kA || item == bmd.kv || item == bmd.kF || item == bmd.kK;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return b() instanceof bkh;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        return F();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int foodNutrition() {
        bhz properties = b().t();
        if (properties == null) {
            return 0;
        }
        return properties.a();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public float foodSaturationValue() {
        bhz properties = b().t();
        if (properties == null) {
            return 0.0f;
        }
        return properties.b() * properties.a() * 2.0f;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return b() == bmd.mi || b() == bmd.pk;
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
    public int getSize() {
        return this.f;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.f = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(r());
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
        p();
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
        return p();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return bmb.b((bmb) itemStack, (bmb) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$i();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((ceh) state);
    }
}

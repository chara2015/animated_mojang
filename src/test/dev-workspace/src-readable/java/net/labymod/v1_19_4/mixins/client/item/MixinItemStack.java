package net.labymod.v1_19_4.mixins.client.item;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/item/MixinItemStack.class */
@Mixin({cfv.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int s;

    @Shadow
    private re v;

    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer = new NbtDataComponentContainer((Supplier<NBTTagCompound>) () -> {
        return this.v;
    });

    @Shadow
    public abstract cfq c();

    @Shadow
    public abstract int j();

    @Shadow
    public abstract boolean L();

    @Shadow
    public abstract int q();

    @Shadow
    public abstract tj x();

    @Shadow
    public abstract re v();

    @Shadow
    public abstract re u();

    @Shadow
    public abstract cfv shadow$o();

    @Shadow
    public abstract boolean b(dbq dbqVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(bym player, chl flag, CallbackInfoReturnable<List<tj>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<?> list = (List) cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(list);
            return;
        }
        if (emh.N().m.m) {
            tooltipType = ItemStackTooltipEvent.TooltipType.ADVANCED;
        } else {
            tooltipType = ItemStackTooltipEvent.TooltipType.NORMAL;
        }
        ItemStackTooltipEvent.TooltipType type = tooltipType;
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        List<Component> mappedLines = componentMapper.fromMinecraftComponents(list);
        ItemStackTooltipEvent event = (ItemStackTooltipEvent) Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        list.clear();
        for (Component line : event.getTooltipLines()) {
            list.add((tj) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(list);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        return c();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return j();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return q();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        cfq item = c();
        return item == cfy.nT || item == cfy.nY || item == cfy.oi || item == cfy.od || item == cfy.on || item == cfy.os;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return c() instanceof cdq;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        return L();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int foodNutrition() {
        cba properties = c().v();
        if (properties == null) {
            return 0;
        }
        return properties.a();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public float foodSaturationValue() {
        cba properties = c().v();
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
        return c() == cfy.qd || c() == cfy.nb;
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
        return this.s;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.s = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(x());
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
        v();
        return getDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @Nullable
    public NBTTagCompound getNBTTag() {
        return u();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        return v();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return cfv.b((cfv) itemStack, (cfv) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$o();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((dbq) state);
    }
}

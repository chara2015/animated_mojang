package net.labymod.v1_20_1.mixins.client.item;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/item/MixinItemStack.class */
@Mixin({cfz.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int s;

    @Shadow
    private qr v;

    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer = new NbtDataComponentContainer((Supplier<NBTTagCompound>) () -> {
        return this.v;
    });

    @Shadow
    public abstract cfu d();

    @Shadow
    public abstract int k();

    @Shadow
    public abstract boolean M();

    @Shadow
    public abstract int r();

    @Shadow
    public abstract sw y();

    @Shadow
    public abstract qr w();

    @Shadow
    public abstract qr v();

    @Shadow
    public abstract cfz shadow$p();

    @Shadow
    public abstract boolean b(dcb dcbVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(byo player, chq flag, CallbackInfoReturnable<List<sw>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<?> list = (List) cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(list);
            return;
        }
        if (enn.N().m.m) {
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
            list.add((sw) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(list);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        return d();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return k();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return r();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        cfu item = d();
        return item == cgc.nX || item == cgc.oc || item == cgc.om || item == cgc.oh || item == cgc.or || item == cgc.ow;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return d() instanceof cds;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        return M();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int foodNutrition() {
        cbc properties = d().v();
        if (properties == null) {
            return 0;
        }
        return properties.a();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public float foodSaturationValue() {
        cbc properties = d().v();
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
        return d() == cgc.qh || d() == cgc.nf;
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
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(y());
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
        w();
        return getDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @Nullable
    public NBTTagCompound getNBTTag() {
        return v();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        return w();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return cfz.a((cfz) itemStack, (cfz) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$p();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((dcb) state);
    }
}

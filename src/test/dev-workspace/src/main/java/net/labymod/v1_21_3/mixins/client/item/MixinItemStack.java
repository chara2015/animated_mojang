package net.labymod.v1_21_3.mixins.client.item;

import java.util.ArrayList;
import java.util.Collection;
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
import net.labymod.api.event.EventBus;
import net.labymod.api.event.client.world.ItemStackTooltipEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.v1_21_3.client.component.data.VersionedDataComponentContainer;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/item/MixinItemStack.class */
@Mixin({cxp.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int m;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer = new VersionedDataComponentContainer((Supplier<kq>) this::a);

    @Deprecated
    private NBTTagCompound labyMod$dummyTag;

    @Shadow
    public abstract cxl h();

    @Shadow
    public abstract int o();

    @Shadow
    public abstract int a(bwg bwgVar);

    @Shadow
    public abstract xv y();

    @Shadow
    public abstract cxp shadow$v();

    @Shadow
    public abstract kq a();

    @Shadow
    public abstract int k();

    @Shadow
    public abstract int p();

    @Shadow
    public abstract boolean b(dxv dxvVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(b context, cpx player, czh flag, CallbackInfoReturnable<List<xv>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<xv> lines = new ArrayList<>((Collection<? extends xv>) cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(lines);
            return;
        }
        if (fmg.Q().n.m) {
            tooltipType = ItemStackTooltipEvent.TooltipType.ADVANCED;
        } else {
            tooltipType = ItemStackTooltipEvent.TooltipType.NORMAL;
        }
        ItemStackTooltipEvent.TooltipType type = tooltipType;
        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        List<Component> mappedLines = componentMapper.fromMinecraftComponents(lines);
        ItemStackTooltipEvent event = (ItemStackTooltipEvent) Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, flag.b()));
        lines.clear();
        for (Component line : event.getTooltipLines()) {
            lines.add((xv) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(lines);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        return h();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return o();
    }

    @Intrinsic
    public int itemStack$getUseDuration(LivingEntity entity) {
        return a((bwg) entity);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        cxl item = h();
        return item == cxt.pi || item == cxt.pn || item == cxt.px || item == cxt.ps || item == cxt.pC || item == cxt.pH;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return h() instanceof cvv;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        csz foodProperties = (csz) a().a(ku.w);
        return foodProperties != null;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return h() == cxt.rJ || h() == cxt.ok;
    }

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        return getAsItem().getIdentifier();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return k();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return p();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getSize() {
        return this.m;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.m = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(y());
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        kq wrapped = container.getWrapped();
        if (wrapped == kq.a) {
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
            this.labyMod$dummyTag = new ux();
        }
        return this.labyMod$dummyTag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return cxp.a((cxp) itemStack, (cxp) this);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((dxv) state);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$v();
    }
}

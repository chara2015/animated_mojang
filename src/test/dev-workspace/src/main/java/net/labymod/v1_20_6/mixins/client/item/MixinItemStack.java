package net.labymod.v1_20_6.mixins.client.item;

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
import net.labymod.v1_20_6.client.component.data.VersionedDataComponentContainer;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/item/MixinItemStack.class */
@Mixin({cur.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int o;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer = new VersionedDataComponentContainer((Supplier<ki>) this::a);

    @Deprecated
    private NBTTagCompound labyMod$dummyTag;

    @Shadow
    public abstract cum g();

    @Shadow
    public abstract int n();

    @Shadow
    public abstract int u();

    @Shadow
    public abstract xp x();

    @Shadow
    public abstract cur shadow$s();

    @Shadow
    public abstract ki a();

    @Shadow
    public abstract int j();

    @Shadow
    public abstract int o();

    @Shadow
    public abstract boolean b(dse dseVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(b context, cmz player, cwk flag, CallbackInfoReturnable<List<xp>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<xp> lines = new ArrayList<>((Collection<? extends xp>) cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(lines);
            return;
        }
        if (ffh.Q().m.m) {
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
            lines.add((xp) componentMapper.toMinecraftComponent(line));
        }
        cir.setReturnValue(lines);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public Item getAsItem() {
        return g();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return n();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return u();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        cum item = g();
        return item == cuu.oL || item == cuu.oQ || item == cuu.pa || item == cuu.oV || item == cuu.pf || item == cuu.pk;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return g() instanceof csp;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        cpt foodProperties = (cpt) a().a(km.v);
        return foodProperties != null;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return g() == cuu.qV || g() == cuu.nR;
    }

    @Override // net.labymod.api.client.world.item.Item
    public ResourceLocation getIdentifier() {
        return getAsItem().getIdentifier();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumStackSize() {
        return j();
    }

    @Override // net.labymod.api.client.world.item.Item
    public int getMaximumDamage() {
        return o();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getSize() {
        return this.o;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.o = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(x());
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        ki wrapped = container.getWrapped();
        if (wrapped == ki.a) {
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
            this.labyMod$dummyTag = new us();
        }
        return this.labyMod$dummyTag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return cur.a((cur) itemStack, (cur) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$s();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((dse) state);
    }
}

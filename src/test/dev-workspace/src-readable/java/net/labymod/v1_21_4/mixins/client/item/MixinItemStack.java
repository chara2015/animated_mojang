package net.labymod.v1_21_4.mixins.client.item;

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
import net.labymod.v1_21_4.client.component.data.VersionedDataComponentContainer;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/item/MixinItemStack.class */
@Mixin({cwq.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private int n;
    private final VersionedDataComponentContainer labyMod$dataComponentContainer = new VersionedDataComponentContainer((Supplier<kr>) this::a);

    @Deprecated
    private NBTTagCompound labyMod$dummyTag;

    @Shadow
    public abstract cwm h();

    @Shadow
    public abstract int o();

    @Shadow
    public abstract int a(bvi bviVar);

    @Shadow
    public abstract wp y();

    @Shadow
    public abstract cwq shadow$v();

    @Shadow
    public abstract kr a();

    @Shadow
    public abstract int k();

    @Shadow
    public abstract int p();

    @Shadow
    public abstract boolean b(dwy dwyVar);

    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(b context, coy player, cyi flag, CallbackInfoReturnable<List<wp>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<wp> lines = new ArrayList<>((Collection<? extends wp>) cir.getReturnValue());
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(lines);
            return;
        }
        if (flk.Q().n.m) {
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
            lines.add((wp) componentMapper.toMinecraftComponent(line));
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
        return a((bvi) entity);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        cwm item = h();
        return item == cwu.pr || item == cwu.pw || item == cwu.pG || item == cwu.pB || item == cwu.pL || item == cwu.pQ;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return h() instanceof cuw;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        csa foodProperties = (csa) a().a(kv.w);
        return foodProperties != null;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return h() == cwu.rS || h() == cwu.ot;
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
        return this.n;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.n = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().minecraft().componentMapper().fromMinecraftComponent(y());
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public DataComponentContainer getDataComponentContainer() {
        VersionedDataComponentContainer container = this.labyMod$dataComponentContainer;
        kr wrapped = container.getWrapped();
        if (wrapped == kr.a) {
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
            this.labyMod$dummyTag = new tq();
        }
        return this.labyMod$dummyTag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return cwq.a((cwq) itemStack, (cwq) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$v();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return b((dwy) state);
    }
}

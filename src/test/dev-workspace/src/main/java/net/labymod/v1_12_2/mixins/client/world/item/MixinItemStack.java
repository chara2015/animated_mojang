package net.labymod.v1_12_2.mixins.client.world.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
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
import net.labymod.v1_12_2.client.util.VersionedWailaUtil;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/item/MixinItemStack.class */
@Mixin({aip.class})
@Implements({@Interface(iface = ItemStack.class, prefix = "itemStack$", remap = Interface.Remap.NONE)})
public abstract class MixinItemStack implements ItemStack {

    @Shadow
    private ain e;

    @Shadow
    public int d;

    @Shadow
    public int c;

    @Shadow
    private int h;

    @Shadow
    private fy f;

    @Unique
    private final NbtDataComponentContainer labyMod$dataComponentContainer = new NbtDataComponentContainer((Supplier<NBTTagCompound>) () -> {
        return this.f;
    });

    @Shadow
    public abstract ain c();

    @Shadow
    public abstract String shadow$r();

    @Shadow
    public abstract int i();

    @Shadow
    public abstract aip shadow$l();

    @Shadow
    public abstract akc n();

    @Shadow
    public abstract fy p();

    @Shadow
    public abstract void b(fy fyVar);

    @Shadow
    public abstract boolean b(awt awtVar);

    @Shadow
    public abstract float a(awt awtVar);

    @Inject(method = {"getTooltip"}, at = {@At("RETURN")}, cancellable = true)
    private void labyMod$fireTooltipEvent(aed player, akb flag, CallbackInfoReturnable<List<String>> cir) {
        ItemStackTooltipEvent.TooltipType tooltipType;
        EventBus eventBus = Laby.labyAPI().eventBus();
        List<String> lines = (List) cir.getReturnValue();
        if (!eventBus.hasListeners(ItemStackTooltipEvent.class)) {
            cir.setReturnValue(lines);
            return;
        }
        if (bib.z().t.z) {
            tooltipType = ItemStackTooltipEvent.TooltipType.ADVANCED;
        } else {
            tooltipType = ItemStackTooltipEvent.TooltipType.NORMAL;
        }
        ItemStackTooltipEvent.TooltipType type = tooltipType;
        LegacyComponentSerializer serializer = Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer();
        List<Component> mappedLines = new ArrayList<>(lines.size());
        for (String line : lines) {
            mappedLines.add(serializer.deserialize(line));
        }
        ItemStackTooltipEvent event = (ItemStackTooltipEvent) Laby.fireEvent(new ItemStackTooltipEvent(this, mappedLines, type, player != null && player.z()));
        lines.clear();
        ComponentMapper componentMapper = Laby.references().componentMapper();
        for (Component line2 : event.getTooltipLines()) {
            hh mappedMinecraftComponent = (hh) componentMapper.toMinecraftComponent(line2);
            lines.add(mappedMinecraftComponent.d());
        }
        cir.setReturnValue(lines);
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
        return c();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getCurrentDamageValue() {
        return i();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getUseDuration(LivingEntity entity) {
        return this.d;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isSword() {
        return this.e == air.p || this.e == air.t || this.e == air.o || this.e == air.E || this.e == air.x;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isItem() {
        return !isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isBlock() {
        return this.e instanceof ahb;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFood() {
        if (c() == null) {
            return false;
        }
        akc action = n();
        return action == akc.b || action == akc.c;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int foodNutrition() {
        aij aijVarC = c();
        if (aijVarC instanceof aij) {
            aij food = aijVarC;
            return food.h((aip) null);
        }
        return 0;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public float foodSaturationValue() {
        aij aijVarC = c();
        if (aijVarC instanceof aij) {
            aij food = aijVarC;
            return food.i((aip) null) * food.h((aip) null) * 2.0f;
        }
        return 0.0f;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isFishingTool() {
        return this.e == air.aZ || this.e == air.cj;
    }

    @Override // net.labymod.api.client.world.item.Item
    public boolean isAir() {
        return getAsItem().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getSize() {
        return this.c;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setSize(int size) {
        this.c = size;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public Component getDisplayName() {
        return Laby.labyAPI().renderPipeline().componentRenderer().legacySectionSerializer().deserialize(shadow$r());
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
        return p();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    public NBTTagCompound getOrCreateNBTTag() {
        fy tag = p();
        if (tag == null) {
            fy fyVar = new fy();
            tag = fyVar;
            b(fyVar);
        }
        return (NBTTagCompound) tag;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean matches(ItemStack itemStack) {
        return aip.b((aip) itemStack, (aip) this);
    }

    @Intrinsic
    public ItemStack itemStack$copy() {
        return shadow$l();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public int getLegacyItemData() {
        return this.h;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public void setLegacyItemData(int legacyData) {
        this.h = legacyData;
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    public boolean isCorrectTool(BlockState state) {
        return VersionedWailaUtil.canHarvest((aip) this, state.block());
    }
}

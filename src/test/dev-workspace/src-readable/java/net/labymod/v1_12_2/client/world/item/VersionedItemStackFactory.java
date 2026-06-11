package net.labymod.v1_12_2.client.world.item;

import javax.inject.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_12_2.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/world/item/VersionedItemStackFactory.class */
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final aip FALLBACK_ITEM = new aip(air.f);

    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        ain item = (ain) ain.g.c((nf) location.getMinecraftLocation());
        if (item == null) {
            return MinecraftUtil.fromMinecraft(FALLBACK_ITEM);
        }
        aip stack = new aip(item, count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        aow block = state.block();
        bsb level = bib.z().f;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        et blockPos = new et(position.getX(), position.getY(), position.getZ());
        aip itemStack = block.a(level, blockPos, (awt) state);
        updateItemStack(itemStack, level.r(blockPos));
        return MinecraftUtil.fromMinecraft(itemStack);
    }

    private void updateItemStack(aip stack, avj entity) {
        if (entity == null) {
            return;
        }
        ain item = stack.c();
        fy compound = new fy();
        entity.b(compound);
        if (item == air.ci && compound.e("Owner")) {
            fy ownerTag = compound.p("Owner");
            fy skullOwnerTag = new fy();
            skullOwnerTag.a("SkullOwner", ownerTag);
            stack.b(skullOwnerTag);
        }
    }
}

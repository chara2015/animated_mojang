package net.labymod.v1_17_1.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_17_1.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        bqm item = (bqm) gw.Z.a((ww) location.getMinecraftLocation());
        bqq stack = new bqq(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        bqq item = new bqq(state.block());
        eji level = dvp.C().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        gg pos = new gg(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(bqq itemStack, ciq blockEntity) {
        if (blockEntity == null) {
            return;
        }
        na tag = blockEntity.b(new na());
        bqm item = itemStack.c();
        if ((item instanceof brc) && tag.e("SkullOwner")) {
            na skullOwnerTag = tag.p("SkullOwner");
            na itemTag = itemStack.t();
            itemTag.a("SkullOwner", skullOwnerTag);
        }
    }
}

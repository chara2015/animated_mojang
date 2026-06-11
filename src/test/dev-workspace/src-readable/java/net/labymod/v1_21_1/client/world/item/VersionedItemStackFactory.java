package net.labymod.v1_21_1.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_1.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cul item = (cul) lt.g.a((akr) location.getMinecraftLocation());
        cuq stack = new cuq(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cuq item = new cuq(state.block());
        fzf level = fgo.Q().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        jd pos = new jd(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.H_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cuq item, dqh blockEntity, ka registryAccess) {
        if (blockEntity == null) {
            return;
        }
        ub compound = blockEntity.f(registryAccess);
        blockEntity.a(compound);
        cso.a(item, blockEntity.r(), compound);
        item.b(blockEntity.s());
    }
}

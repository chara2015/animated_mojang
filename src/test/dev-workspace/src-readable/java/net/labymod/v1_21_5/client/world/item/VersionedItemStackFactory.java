package net.labymod.v1_21_5.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_5.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        dag item = (dag) mh.g.a((alr) location.getMinecraftLocation());
        dak stack = new dak(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        dak item = new dak(state.block());
        glo level = fqq.Q().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        iw pos = new iw(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.J_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(dak item, dyo blockEntity, ju registryAccess) {
        if (blockEntity == null) {
            return;
        }
        ua compound = blockEntity.f(registryAccess);
        blockEntity.a(compound);
        cys.a(item, blockEntity.p(), compound);
        item.b(blockEntity.q());
    }
}

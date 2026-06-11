package net.labymod.v1_21_3.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_3.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cxl item = (cxl) ma.g.a((alz) location.getMinecraftLocation());
        cxp stack = new cxp(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cxp item = new cxp(state.block());
        gfk level = fmg.Q().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        jh pos = new jh(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.K_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cxp item, dux blockEntity, ke registryAccess) {
        if (blockEntity == null) {
            return;
        }
        ux compound = blockEntity.f(registryAccess);
        blockEntity.a(compound);
        cvv.a(item, blockEntity.q(), compound);
        item.b(blockEntity.r());
    }
}

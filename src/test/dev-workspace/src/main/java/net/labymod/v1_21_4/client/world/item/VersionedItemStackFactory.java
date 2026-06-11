package net.labymod.v1_21_4.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_4.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cwm item = (cwm) mb.g.a((akv) location.getMinecraftLocation());
        cwq stack = new cwq(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cwq item = new cwq(state.block());
        gga level = flk.Q().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        ji pos = new ji(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.K_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cwq item, dua blockEntity, kf registryAccess) {
        if (blockEntity == null) {
            return;
        }
        tq compound = blockEntity.f(registryAccess);
        blockEntity.a(compound);
        cuw.a(item, blockEntity.p(), compound);
        item.b(blockEntity.q());
    }
}

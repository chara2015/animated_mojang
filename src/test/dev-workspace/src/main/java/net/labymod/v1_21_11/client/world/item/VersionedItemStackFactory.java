package net.labymod.v1_21_11.client.world.item;

import com.mojang.logging.LogUtils;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        dlp item = (dlp) mi.h.a((amo) location.getMinecraftLocation());
        dlt stack = new dlt(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        dlt item = new dlt(state.block());
        hif level = gfj.V().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        is pos = new is(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.J_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(dlt item, elb blockEntity, jr registryAccess) {
        if (blockEntity == null) {
            return;
        }
        j collector = new j(blockEntity.v(), LOGGER);
        try {
            fnp output = fnp.a(collector, registryAccess);
            blockEntity.f(output);
            blockEntity.b(output);
            dkb.a(item, blockEntity.s(), output);
            item.b(blockEntity.t());
            collector.close();
        } catch (Throwable th) {
            try {
                collector.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}

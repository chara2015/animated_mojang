package net.labymod.v1_21_10.client.world.item;

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
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        dhl item = (dhl) mo.h.a((amj) location.getMinecraftLocation());
        dhp stack = new dhp(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        dhp item = new dhp(state.block());
        gzn level = fzz.W().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        ja pos = new ja(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.L_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(dhp item, egg blockEntity, jy registryAccess) {
        if (blockEntity == null) {
            return;
        }
        j collector = new j(blockEntity.v(), LOGGER);
        try {
            fio output = fio.a(collector, registryAccess);
            blockEntity.f(output);
            blockEntity.b(output);
            dfx.a(item, blockEntity.s(), output);
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

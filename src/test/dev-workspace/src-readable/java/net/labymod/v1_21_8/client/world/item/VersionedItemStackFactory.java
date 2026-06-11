package net.labymod.v1_21_8.client.world.item;

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
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        dcr item = (dcr) mm.g.a((ame) location.getMinecraftLocation());
        dcv stack = new dcv(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        dcv item = new dcv(state.block());
        grk level = fue.R().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        jb pos = new jb(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos), level.K_());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(dcv item, eaz blockEntity, jz registryAccess) {
        if (blockEntity == null) {
            return;
        }
        j collector = new j(blockEntity.t(), LOGGER);
        try {
            fcz output = fcz.a(collector, registryAccess);
            blockEntity.f(output);
            blockEntity.b(output);
            dbd.a(item, blockEntity.q(), output);
            item.b(blockEntity.r());
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

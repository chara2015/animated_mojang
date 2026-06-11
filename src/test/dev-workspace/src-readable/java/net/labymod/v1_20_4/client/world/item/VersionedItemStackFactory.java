package net.labymod.v1_20_4.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_20_4.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cmt item = (cmt) kd.h.a((ahg) location.getMinecraftLocation());
        cmy stack = new cmy(item);
        stack.f(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cmy item = new cmy(state.block());
        fns level = evi.O().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        hx pos = new hx(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cmy itemStack, dgv blockEntity) {
        if (blockEntity == null) {
            return;
        }
        sn tag = blockEntity.q();
        ckr.a(itemStack, blockEntity.v(), tag);
        cmt item = itemStack.d();
        if ((item instanceof cnm) && tag.e("SkullOwner")) {
            sn skullOwnerTag = tag.p("SkullOwner");
            sn itemTag = itemStack.w();
            itemTag.a("SkullOwner", skullOwnerTag);
            sn blockEntityTag = itemTag.p("BlockEntityTag");
            blockEntityTag.r("SkullOwner");
            blockEntityTag.r("x");
            blockEntityTag.r("y");
            blockEntityTag.r("z");
        }
    }
}

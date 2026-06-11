package net.labymod.v1_19_4.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_19_4.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cfq item = (cfq) ja.i.a((add) location.getMinecraftLocation());
        cfv stack = new cfv(item);
        stack.f(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cfv item = new cfv(state.block());
        fdj level = emh.N().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        gt pos = new gt(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cfv itemStack, cze blockEntity) {
        if (blockEntity == null) {
            return;
        }
        re tag = blockEntity.o();
        cdq.a(itemStack, blockEntity.u(), tag);
        cfq item = itemStack.c();
        if ((item instanceof cgj) && tag.e("SkullOwner")) {
            re skullOwnerTag = tag.p("SkullOwner");
            re itemTag = itemStack.v();
            itemTag.a("SkullOwner", skullOwnerTag);
            re blockEntityTag = itemTag.p("BlockEntityTag");
            blockEntityTag.r("SkullOwner");
            blockEntityTag.r("x");
            blockEntityTag.r("y");
            blockEntityTag.r("z");
        }
    }
}

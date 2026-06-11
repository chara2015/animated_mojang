package net.labymod.v1_20_1.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_20_1.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        cfu item = (cfu) jb.i.a((acq) location.getMinecraftLocation());
        cfz stack = new cfz(item);
        stack.f(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        cfz item = new cfz(state.block());
        few level = enn.N().s;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        gu pos = new gu(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(cfz itemStack, czn blockEntity) {
        if (blockEntity == null) {
            return;
        }
        qr tag = blockEntity.o();
        cds.a(itemStack, blockEntity.u(), tag);
        cfu item = itemStack.d();
        if ((item instanceof cgn) && tag.e("SkullOwner")) {
            qr skullOwnerTag = tag.p("SkullOwner");
            qr itemTag = itemStack.w();
            itemTag.a("SkullOwner", skullOwnerTag);
            qr blockEntityTag = itemTag.p("BlockEntityTag");
            blockEntityTag.r("SkullOwner");
            blockEntityTag.r("x");
            blockEntityTag.r("y");
            blockEntityTag.r("z");
        }
    }
}

package net.labymod.v1_18_2.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_18_2.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        bus item = (bus) hb.X.a((yt) location.getMinecraftLocation());
        buw stack = new buw(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        buw item = new buw(state.block());
        ems level = dyr.D().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        gj pos = new gj(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c_(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(buw itemStack, cmr blockEntity) {
        if (blockEntity == null) {
            return;
        }
        ok tag = blockEntity.o();
        bta.a(itemStack, blockEntity.u(), tag);
        bus item = itemStack.c();
        if ((item instanceof bvi) && tag.e("SkullOwner")) {
            ok skullOwnerTag = tag.p("SkullOwner");
            ok itemTag = itemStack.u();
            itemTag.a("SkullOwner", skullOwnerTag);
            ok blockEntityTag = itemTag.p("BlockEntityTag");
            blockEntityTag.r("SkullOwner");
            blockEntityTag.r("x");
            blockEntityTag.r("y");
            blockEntityTag.r("z");
        }
    }
}

package net.labymod.v1_16_5.client.world.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_16_5.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        blx item = (blx) gm.T.a((vk) location.getMinecraftLocation());
        bmb stack = new bmb(item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        bmb item = new bmb(state.block());
        dwt level = djz.C().r;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        fx pos = new fx(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.c(pos));
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(bmb itemStack, ccj blockEntity) {
        if (blockEntity == null) {
            return;
        }
        md tag = blockEntity.a(new md());
        blx item = itemStack.b();
        if ((item instanceof bmm) && tag.e("SkullOwner")) {
            md skullOwnerTag = tag.p("SkullOwner");
            md itemTag = itemStack.p();
            itemTag.a("SkullOwner", skullOwnerTag);
        }
    }
}

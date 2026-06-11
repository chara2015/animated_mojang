package net.labymod.v1_8_9.client.world.item;

import javax.inject.Inject;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.v1_8_9.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/world/item/VersionedItemStackFactory.class */
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final zx FALLBACK_ITEM = new zx(zy.e);

    @Inject
    public VersionedItemStackFactory() {
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(ResourceLocation location, int count) {
        zw item = (zw) zw.e.a((jy) location.getMinecraftLocation());
        if (item == null) {
            return MinecraftUtil.fromMinecraft(FALLBACK_ITEM);
        }
        zx stack = new zx(item);
        stack.b = count;
        return MinecraftUtil.fromMinecraft(stack);
    }

    @Override // net.labymod.api.client.world.item.ItemStackFactory
    public ItemStack create(BlockState state) {
        afh block = state.block();
        IntVector3 position = state.position();
        bdb level = ave.A().f;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        cj blockPos = new cj(position.getX(), position.getY(), position.getZ());
        zw item = block.c(level, blockPos);
        int metadata = block.j(level, blockPos);
        zx itemStack = new zx(item, 1, metadata);
        updateItemStack(itemStack, level.s(blockPos));
        return MinecraftUtil.fromMinecraft(itemStack);
    }

    private void updateItemStack(zx stack, akw entity) {
        if (entity == null) {
            return;
        }
        zw item = stack.b();
        dn compound = new dn();
        entity.b(compound);
        if (item == zy.bX && compound.c("Owner")) {
            dn ownerTag = compound.m("Owner");
            dn skullOwnerTag = new dn();
            skullOwnerTag.a("SkullOwner", ownerTag);
            stack.d(skullOwnerTag);
        }
    }
}

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueOutput;
import org.slf4j.Logger;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/world/item/VersionedItemStackFactory.class */
@Singleton
@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject
    public VersionedItemStackFactory() {
    }

    public ItemStack create(ResourceLocation location, int count) {
        Item item = (Item) BuiltInRegistries.ITEM.getValue((Identifier) location.getMinecraftLocation());
        net.minecraft.world.item.ItemStack stack = new net.minecraft.world.item.ItemStack(item);
        stack.setCount(count);
        return MinecraftUtil.fromMinecraft(stack);
    }

    public ItemStack create(BlockState state) {
        net.minecraft.world.item.ItemStack item = new net.minecraft.world.item.ItemStack(state.block());
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return create(VanillaItems.STONE);
        }
        IntVector3 position = state.position();
        BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
        addNbtData(item, level.getBlockEntity(pos), level.registryAccess());
        return MinecraftUtil.fromMinecraft(item);
    }

    private void addNbtData(net.minecraft.world.item.ItemStack item, BlockEntity blockEntity, RegistryAccess registryAccess) {
        if (blockEntity == null) {
            return;
        }
        ProblemReporter.ScopedCollector collector = new ProblemReporter.ScopedCollector(blockEntity.problemPath(), LOGGER);
        try {
            TagValueOutput output = TagValueOutput.createWithContext(collector, registryAccess);
            blockEntity.saveCustomOnly(output);
            blockEntity.removeComponentsFromTag(output);
            BlockItem.setBlockEntityData(item, blockEntity.getType(), output);
            item.applyComponents(blockEntity.collectComponents());
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

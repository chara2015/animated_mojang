package net.minecraft.advancements.criterion;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.TagValueOutput;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/NbtPredicate.class */
public final class NbtPredicate extends Record {
    private final CompoundTag tag;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<NbtPredicate> CODEC = TagParser.LENIENT_CODEC.xmap(NbtPredicate::new, (v0) -> {
        return v0.tag();
    });
    public static final StreamCodec<ByteBuf, NbtPredicate> STREAM_CODEC = ByteBufCodecs.COMPOUND_TAG.map(NbtPredicate::new, (v0) -> {
        return v0.tag();
    });
    public static final String SELECTED_ITEM_TAG = "SelectedItem";

    public NbtPredicate(CompoundTag $$0) {
        this.tag = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NbtPredicate.class), NbtPredicate.class, "tag", "FIELD:Lnet/minecraft/advancements/criterion/NbtPredicate;->tag:Lnet/minecraft/nbt/CompoundTag;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NbtPredicate.class), NbtPredicate.class, "tag", "FIELD:Lnet/minecraft/advancements/criterion/NbtPredicate;->tag:Lnet/minecraft/nbt/CompoundTag;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NbtPredicate.class, Object.class), NbtPredicate.class, "tag", "FIELD:Lnet/minecraft/advancements/criterion/NbtPredicate;->tag:Lnet/minecraft/nbt/CompoundTag;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public CompoundTag tag() {
        return this.tag;
    }

    public boolean matches(DataComponentGetter $$0) {
        CustomData $$1 = (CustomData) $$0.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return $$1.matchedBy(this.tag);
    }

    public boolean matches(Entity $$0) {
        return matches(getEntityTagToCompare($$0));
    }

    public boolean matches(Tag $$0) {
        return $$0 != null && NbtUtils.compareNbt(this.tag, $$0, true);
    }

    public static CompoundTag getEntityTagToCompare(Entity $$0) {
        ProblemReporter.ScopedCollector $$1 = new ProblemReporter.ScopedCollector($$0.problemPath(), LOGGER);
        try {
            TagValueOutput $$2 = TagValueOutput.createWithContext($$1, $$0.registryAccess());
            $$0.saveWithoutId($$2);
            if ($$0 instanceof Player) {
                Player $$3 = (Player) $$0;
                ItemStack $$4 = $$3.getInventory().getSelectedItem();
                if (!$$4.isEmpty()) {
                    $$2.store(SELECTED_ITEM_TAG, ItemStack.CODEC, $$4);
                }
            }
            CompoundTag compoundTagBuildResult = $$2.buildResult();
            $$1.close();
            return compoundTagBuildResult;
        } catch (Throwable th) {
            try {
                $$1.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}

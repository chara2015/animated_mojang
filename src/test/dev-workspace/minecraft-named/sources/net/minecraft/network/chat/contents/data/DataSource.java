package net.minecraft.network.chat.contents.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.MapCodec;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/data/DataSource.class */
public interface DataSource {
    Stream<CompoundTag> getData(CommandSourceStack commandSourceStack) throws CommandSyntaxException;

    MapCodec<? extends DataSource> codec();
}

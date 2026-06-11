package net.minecraft.network.chat.numbers;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.numbers.NumberFormat;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/numbers/NumberFormatType.class */
public interface NumberFormatType<T extends NumberFormat> {
    MapCodec<T> mapCodec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
}

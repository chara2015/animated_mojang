package net.minecraft.network.chat.numbers;

import net.minecraft.network.chat.MutableComponent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/numbers/NumberFormat.class */
public interface NumberFormat {
    MutableComponent format(int i);

    NumberFormatType<? extends NumberFormat> type();
}

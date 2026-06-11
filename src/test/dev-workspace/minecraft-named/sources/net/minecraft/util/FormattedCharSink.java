package net.minecraft.util;

import net.minecraft.network.chat.Style;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/FormattedCharSink.class */
@FunctionalInterface
public interface FormattedCharSink {
    boolean accept(int i, Style style, int i2);
}

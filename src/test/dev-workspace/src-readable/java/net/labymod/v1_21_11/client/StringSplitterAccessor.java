package net.labymod.v1_21_11.client;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.StringSplitter;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/StringSplitterAccessor.class */
public interface StringSplitterAccessor {
    StringSplitter.WidthProvider getWidthProvider();

    static StringSplitterAccessor cast(Object obj) {
        return (StringSplitterAccessor) CastUtil.requireInstanceOf(obj, StringSplitterAccessor.class);
    }
}

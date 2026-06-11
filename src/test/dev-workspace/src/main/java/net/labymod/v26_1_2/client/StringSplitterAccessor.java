package net.labymod.v26_1_2.client;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.StringSplitter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/StringSplitterAccessor.class */
public interface StringSplitterAccessor {
    StringSplitter.WidthProvider getWidthProvider();

    static StringSplitterAccessor cast(Object obj) {
        return (StringSplitterAccessor) CastUtil.requireInstanceOf(obj, StringSplitterAccessor.class);
    }
}

package net.labymod.v26_2_snapshot_8.client;

import net.labymod.api.util.CastUtil;
import net.minecraft.client.StringSplitter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/StringSplitterAccessor.class */
public interface StringSplitterAccessor {
    StringSplitter.WidthProvider getWidthProvider();

    static StringSplitterAccessor cast(Object obj) {
        return (StringSplitterAccessor) CastUtil.requireInstanceOf(obj, StringSplitterAccessor.class);
    }
}

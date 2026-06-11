package net.labymod.v1_16_5.client;

import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/StringSplitterAccessor.class */
public interface StringSplitterAccessor {
    f getWidthProvider();

    static StringSplitterAccessor cast(Object obj) {
        return (StringSplitterAccessor) CastUtil.requireInstanceOf(obj, StringSplitterAccessor.class);
    }
}

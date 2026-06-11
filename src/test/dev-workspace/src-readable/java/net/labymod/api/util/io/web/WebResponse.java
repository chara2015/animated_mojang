package net.labymod.api.util.io.web;

import net.labymod.api.util.io.web.exception.WebRequestException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/WebResponse.class */
public interface WebResponse<T> {
    void success(T t) throws Exception;

    void failed(WebRequestException webRequestException);
}

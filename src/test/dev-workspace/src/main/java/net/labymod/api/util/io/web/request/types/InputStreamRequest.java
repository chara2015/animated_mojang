package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.Response;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/InputStreamRequest.class */
public class InputStreamRequest extends AbstractRequest<WebInputStream, InputStreamRequest> {
    protected InputStreamRequest() {
    }

    public static InputStreamRequest create() {
        return new InputStreamRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public WebInputStream handle(Response<WebInputStream> response, WebInputStream inputStream) {
        return inputStream;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public InputStreamRequest prepareCopy() {
        return new InputStreamRequest();
    }
}

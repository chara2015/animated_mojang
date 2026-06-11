package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/exception/RealmsServiceException.class */
public class RealmsServiceException extends Exception {
    public final RealmsError realmsError;

    public RealmsServiceException(RealmsError $$0) {
        this.realmsError = $$0;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.realmsError.logMessage();
    }
}

package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/exception/RetryCallException.class */
public class RetryCallException extends RealmsServiceException {
    public static final int DEFAULT_DELAY = 5;
    public final int delaySeconds;

    public RetryCallException(int $$0, int $$1) {
        super(RealmsError.CustomError.retry($$1));
        if ($$0 < 0 || $$0 > 120) {
            this.delaySeconds = 5;
        } else {
            this.delaySeconds = $$0;
        }
    }
}

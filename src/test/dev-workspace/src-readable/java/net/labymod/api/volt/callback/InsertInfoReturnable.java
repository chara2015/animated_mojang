package net.labymod.api.volt.callback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/callback/InsertInfoReturnable.class */
public class InsertInfoReturnable<T> extends InsertInfo {
    private T returnValue;

    public InsertInfoReturnable(boolean cancellable) {
        super(cancellable);
    }

    public T getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(T returnValue) {
        this.returnValue = returnValue;
        cancel();
    }
}

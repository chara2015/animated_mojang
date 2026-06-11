package net.labymod.api.volt.rename;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/rename/MethodInfo.class */
public class MethodInfo {
    private final String name;
    private final String descriptor;

    public MethodInfo(String name, String descriptor) {
        this.name = name;
        this.descriptor = descriptor;
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}

package net.labymod.core.main.debug.filewatcher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/filewatcher/NamedPathType.class */
public enum NamedPathType implements PathType {
    UNKNOWN(new String[0]),
    TEXTURE(".png", ".jpg"),
    LSS(".lss");

    public static final NamedPathType[] VALUES = values();
    private final String[] fileExtensions;

    NamedPathType(String... fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    @Override // net.labymod.core.main.debug.filewatcher.PathType
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }
}

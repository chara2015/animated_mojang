package net.labymod.core.client.os.module;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleVersion.class */
public interface ModuleVersion {
    int getMajor();

    int getMinor();

    int getRevision();

    int getBuild();

    static ModuleVersion simple(int major, int minor, int revision, int build) {
        return new Simple(major, minor, revision, build);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleVersion$Simple.class */
    public static class Simple implements ModuleVersion {
        private final int major;
        private final int minor;
        private final int revision;
        private final int build;

        private Simple(int major, int minor, int revision, int build) {
            this.major = major;
            this.minor = minor;
            this.revision = revision;
            this.build = build;
        }

        @Override // net.labymod.core.client.os.module.ModuleVersion
        public int getMajor() {
            return 0;
        }

        @Override // net.labymod.core.client.os.module.ModuleVersion
        public int getMinor() {
            return 0;
        }

        @Override // net.labymod.core.client.os.module.ModuleVersion
        public int getRevision() {
            return 0;
        }

        @Override // net.labymod.core.client.os.module.ModuleVersion
        public int getBuild() {
            return 0;
        }
    }
}

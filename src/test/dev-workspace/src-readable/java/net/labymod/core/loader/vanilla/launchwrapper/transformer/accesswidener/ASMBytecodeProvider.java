package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import net.labymod.accesswidener.bytecode.BytecodeProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/ASMBytecodeProvider.class */
public class ASMBytecodeProvider implements BytecodeProvider {
    public int makePublic(int access) {
        return (access & (-7)) | 1;
    }

    public int makeProtected(int access) {
        if ((access & 1) != 0) {
            return access;
        }
        return (access & (-3)) | 4;
    }

    public int makeFinalIfPrivate(int access, String name, int ownerAccess) {
        if (name.equals("<init>") || name.equals("<clint>")) {
            return access;
        }
        if ((ownerAccess & 512) != 0 || (access & 8) != 0) {
            return access;
        }
        if ((access & 2) != 0) {
            return access | 16;
        }
        return access;
    }

    public int removeFinal(int access) {
        return access & (-17);
    }
}

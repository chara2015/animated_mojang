package net.labymod.core.loader.isolated.lwjgl;

import net.labymod.core.loader.isolated.IsolatedLibrary;
import net.labymod.core.loader.isolated.IsolatedLibraryPredicate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/isolated/lwjgl/LWJGL2Filter.class */
public class LWJGL2Filter implements IsolatedLibraryPredicate {
    @Override // java.util.function.Predicate
    public boolean test(IsolatedLibrary isolatedLibrary) {
        return !isolatedLibrary.getGroup().equals("org.lwjgl.lwjgl");
    }
}

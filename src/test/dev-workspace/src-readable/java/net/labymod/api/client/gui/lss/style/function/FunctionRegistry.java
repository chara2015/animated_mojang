package net.labymod.api.client.gui.lss.style.function;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/function/FunctionRegistry.class */
@Referenceable
public interface FunctionRegistry {
    void registerFunction(String str, Class<?>... clsArr);

    boolean isFunctionRegistered(String str);

    Class<?>[] getParameterTypes(String str, int i);

    int[] getAllowedParameterCounts(String str);
}

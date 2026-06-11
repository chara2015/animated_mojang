package net.labymod.core.client.gui.lss.style.function;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.FunctionRegistry;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/function/DefaultFunctionRegistry.class */
@Singleton
@Implements(FunctionRegistry.class)
public class DefaultFunctionRegistry implements FunctionRegistry {
    private final Map<String, Map<Integer, Class<?>[]>> functions = new HashMap();

    @Override // net.labymod.api.client.gui.lss.style.function.FunctionRegistry
    public void registerFunction(String functionName, Class<?>... parameterTypes) {
        this.functions.computeIfAbsent(functionName, s -> {
            return new HashMap();
        }).put(Integer.valueOf(parameterTypes.length), parameterTypes);
    }

    @Override // net.labymod.api.client.gui.lss.style.function.FunctionRegistry
    public boolean isFunctionRegistered(String functionName) {
        return this.functions.containsKey(functionName);
    }

    @Override // net.labymod.api.client.gui.lss.style.function.FunctionRegistry
    public Class<?>[] getParameterTypes(String functionName, int parameterCount) {
        Map<Integer, Class<?>[]> functionOptions = this.functions.get(functionName);
        if (functionOptions == null) {
            return null;
        }
        return functionOptions.get(Integer.valueOf(parameterCount));
    }

    @Override // net.labymod.api.client.gui.lss.style.function.FunctionRegistry
    public int[] getAllowedParameterCounts(String functionName) {
        Map<Integer, Class<?>[]> functionOptions = this.functions.get(functionName);
        if (functionOptions == null) {
            return null;
        }
        int[] counts = new int[functionOptions.keySet().size()];
        int i = 0;
        for (Integer count : functionOptions.keySet()) {
            int i2 = i;
            i++;
            counts[i2] = count.intValue();
        }
        return counts;
    }
}

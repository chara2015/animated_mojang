package net.labymod.api.client.gui.lss.variable;

import java.util.Map;
import java.util.Objects;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/variable/LssVariableHolder.class */
public interface LssVariableHolder {
    Map<String, LssVariable> getLssVariables();

    void updateLssVariable(LssVariable lssVariable);

    void forceUpdateLssVariable(LssVariable lssVariable);

    @Nullable
    default LssVariableHolder getParentVariableHolder() {
        return null;
    }

    default LssVariable getVariable(@NotNull String key) {
        LssVariable variable = getLssVariables().get(key);
        if (variable != null) {
            return variable;
        }
        LssVariableHolder parent = getParentVariableHolder();
        if (parent != null) {
            return parent.getVariable(key);
        }
        return null;
    }

    default void setVariable(@NotNull String key, @NotNull String value) {
        Map<String, LssVariable> lssVariables = getLssVariables();
        LssVariable variable = lssVariables.get(key);
        if (variable != null && Objects.equals(value, variable.value())) {
            return;
        }
        LssVariable variable2 = new LssVariable(this, key, value, TimeUtil.getCurrentTimeMillis());
        lssVariables.put(key, variable2);
        updateLssVariable(variable2);
    }

    default void setVariable(@NotNull String key, @NotNull Number value) {
        setVariable(key, String.valueOf(value));
    }

    default void setVariable(@NotNull String key, boolean value) {
        setVariable(key, String.valueOf(value));
    }

    default void clearVariable(@NotNull String key) {
        LssVariable variable = getLssVariables().remove(key);
        if (variable != null) {
            updateLssVariable(variable);
        }
    }
}

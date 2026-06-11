package net.labymod.api.client.gui.lss.variable;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/variable/LssVariable.class */
public class LssVariable {
    private static final WidgetModifier WIDGET_MODIFIER = Laby.references().widgetModifier();
    private final LssVariableHolder holder;
    private final String key;
    private final String value;
    private final long timestamp;

    public LssVariable(@NotNull LssVariableHolder holder, @NotNull String key, @NotNull String value, long timestamp) {
        if (!WIDGET_MODIFIER.isVariableKey(key)) {
            throw new IllegalArgumentException("Invalid variable key: '" + key + "', must start with '--' followed by a char other than '-'");
        }
        this.holder = holder;
        this.key = key;
        this.value = value;
        this.timestamp = timestamp;
    }

    @NotNull
    public LssVariableHolder holder() {
        return this.holder;
    }

    @NotNull
    public String key() {
        return this.key;
    }

    @NotNull
    public String value() {
        return this.value;
    }

    public long timestamp() {
        return this.timestamp;
    }
}

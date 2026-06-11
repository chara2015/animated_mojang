package net.labymod.api.client.gui.hud.binding;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.service.Identifiable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/binding/HudWidgetBinding.class */
public abstract class HudWidgetBinding implements Identifiable {
    protected final String namespace;
    protected final String id;

    public HudWidgetBinding(@NotNull Object holder, @NotNull String id) {
        Objects.requireNonNull(holder, "Holder cannot be null");
        Objects.requireNonNull(id, "Identifier cannot be null");
        this.namespace = Laby.labyAPI().getNamespace(holder);
        if (this.namespace.equals("labymod")) {
            throw new IllegalArgumentException("Please supply a holder that belongs to your addon");
        }
        this.id = id;
    }

    protected HudWidgetBinding(String id) {
        this.namespace = "labymod";
        this.id = id;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.id;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HudWidgetBinding that = (HudWidgetBinding) o;
        return Objects.equals(this.namespace, that.getNamespace()) && Objects.equals(this.id, that.getId());
    }

    public int hashCode() {
        int result = this.namespace.hashCode();
        return (31 * result) + this.id.hashCode();
    }

    public String toString() {
        return getClass().getSimpleName() + "{namespace='" + this.namespace + "', id='" + this.id + "'}";
    }
}

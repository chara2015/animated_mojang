package net.labymod.api.client.world.object;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/WorldObject.class */
public interface WorldObject {
    @NotNull
    DoubleVector3 position();

    @Nullable
    Widget createWidget();

    @NotNull
    default DoubleVector3 previousPosition() {
        return position();
    }

    @NotNull
    default CullVolume cullVolume() {
        return CullVolume.point(position());
    }

    default boolean canBeCulled() {
        return true;
    }

    default boolean shouldRenderInOverlay() {
        return true;
    }

    default boolean shouldRemove() {
        return false;
    }

    default void onRemove() {
    }
}

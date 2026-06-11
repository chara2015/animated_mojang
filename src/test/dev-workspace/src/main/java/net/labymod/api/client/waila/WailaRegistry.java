package net.labymod.api.client.waila;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.waila.change.WailaChangeValidator;
import net.labymod.api.client.waila.tooltip.WailaTooltip;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.math.vector.IntVector3;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/WailaRegistry.class */
@Referenceable
public interface WailaRegistry {
    void register(WailaPosition wailaPosition, WailaTooltip wailaTooltip);

    void registerBefore(WailaPosition wailaPosition, String str, WailaTooltip wailaTooltip);

    void registerAfter(WailaPosition wailaPosition, String str, WailaTooltip wailaTooltip);

    void registerChangeValidator(WailaChangeValidator wailaChangeValidator);

    @Nullable
    BlockEntity findBlockEntity(IntVector3 intVector3);

    default void registerLeft(WailaTooltip tooltip) {
        register(WailaPosition.LEFT, tooltip);
    }

    default void registerBeforeLeft(String id, WailaTooltip tooltip) {
        registerBefore(WailaPosition.LEFT, id, tooltip);
    }

    default void registerAfterLeft(String id, WailaTooltip tooltip) {
        registerAfter(WailaPosition.LEFT, id, tooltip);
    }

    default void registerRight(WailaTooltip tooltip) {
        register(WailaPosition.RIGHT, tooltip);
    }

    default void registerBeforeRight(String id, WailaTooltip tooltip) {
        registerBefore(WailaPosition.RIGHT, id, tooltip);
    }

    default void registerAfterRight(String id, WailaTooltip tooltip) {
        registerAfter(WailaPosition.RIGHT, id, tooltip);
    }

    default void registerMiddle(WailaTooltip tooltip) {
        register(WailaPosition.MIDDLE, tooltip);
    }

    default void registerBeforeMiddle(String id, WailaTooltip tooltip) {
        registerBefore(WailaPosition.MIDDLE, id, tooltip);
    }

    default void registerAfterMiddle(String id, WailaTooltip tooltip) {
        registerAfter(WailaPosition.MIDDLE, id, tooltip);
    }
}

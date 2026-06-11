package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/FieldOfViewEvent.class */
public class FieldOfViewEvent extends DefaultCancellable implements Event {
    private final FogType fogType;
    private final float originalFieldOfView;
    private final float partialTicks;
    private final boolean useFieldOfViewSetting;
    private float modifiedFieldOfView;

    public FieldOfViewEvent(FogType fogType, float originalFieldOfView, float partialTicks, boolean useFieldOfViewSetting) {
        this.fogType = fogType;
        this.originalFieldOfView = originalFieldOfView;
        this.partialTicks = partialTicks;
        this.useFieldOfViewSetting = useFieldOfViewSetting;
    }

    public float getFov() {
        return isCancelled() ? this.modifiedFieldOfView : this.originalFieldOfView;
    }

    public float getModifiedFieldOfView() {
        return this.modifiedFieldOfView;
    }

    public void setModifiedFieldOfView(float modifiedFieldOfView) {
        this.modifiedFieldOfView = modifiedFieldOfView;
        setCancelled(true);
    }

    public float getOriginalFieldOfView() {
        return this.originalFieldOfView;
    }

    public FogType getFogType() {
        return this.fogType;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public boolean isUseFieldOfViewSetting() {
        return this.useFieldOfViewSetting;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/FieldOfViewEvent$FogType.class */
    public enum FogType {
        LAVA(true),
        NONE,
        POWDER_SNOW,
        DIMENSION_OR_BOSS,
        ATMOSPHERIC,
        WATER(true);

        private final boolean fluid;

        FogType() {
            this(false);
        }

        FogType(boolean fluid) {
            this.fluid = fluid;
        }

        public boolean isFluid() {
            return this.fluid;
        }
    }
}

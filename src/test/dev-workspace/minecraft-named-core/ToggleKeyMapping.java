package net.minecraft.client;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.BooleanSupplier;
import net.minecraft.client.KeyMapping;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/ToggleKeyMapping.class */
public class ToggleKeyMapping extends KeyMapping {
    private final BooleanSupplier needsToggle;
    private boolean releasedByScreenWhenDown;
    private final boolean shouldRestore;

    public ToggleKeyMapping(String $$0, int $$1, KeyMapping.Category $$2, BooleanSupplier $$3, boolean $$4) {
        this($$0, InputConstants.Type.KEYSYM, $$1, $$2, $$3, $$4);
    }

    public ToggleKeyMapping(String $$0, InputConstants.Type $$1, int $$2, KeyMapping.Category $$3, BooleanSupplier $$4, boolean $$5) {
        super($$0, $$1, $$2, $$3);
        this.needsToggle = $$4;
        this.shouldRestore = $$5;
    }

    @Override // net.minecraft.client.KeyMapping
    protected boolean shouldSetOnIngameFocus() {
        return super.shouldSetOnIngameFocus() && !this.needsToggle.getAsBoolean();
    }

    @Override // net.minecraft.client.KeyMapping
    public void setDown(boolean $$0) {
        if (this.needsToggle.getAsBoolean()) {
            if ($$0) {
                super.setDown(!isDown());
                return;
            }
            return;
        }
        super.setDown($$0);
    }

    @Override // net.minecraft.client.KeyMapping
    protected void release() {
        if ((this.needsToggle.getAsBoolean() && isDown()) || this.releasedByScreenWhenDown) {
            this.releasedByScreenWhenDown = true;
        }
        reset();
    }

    public boolean shouldRestoreStateOnScreenClosed() {
        boolean $$0 = this.shouldRestore && this.needsToggle.getAsBoolean() && this.key.getType() == InputConstants.Type.KEYSYM && this.releasedByScreenWhenDown;
        this.releasedByScreenWhenDown = false;
        return $$0;
    }

    protected void reset() {
        super.setDown(false);
    }
}

package net.labymod.core.main.user.shop.cosmetic.appearance;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.util.Color;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/appearance/PartAppearanceStore.class */
public class PartAppearanceStore {
    private final Map<ModelPart, Appearance> appearances = new IdentityHashMap();
    private final AtomicLong globalVersion = new AtomicLong(0);

    public long globalVersion() {
        return this.globalVersion.get();
    }

    public Appearance getOrCreate(ModelPart part) {
        return this.appearances.computeIfAbsent(part, k -> {
            return new Appearance();
        });
    }

    public Appearance get(ModelPart part) {
        return this.appearances.get(part);
    }

    public long getVersion(ModelPart part) {
        Appearance appearance = this.appearances.get(part);
        if (appearance == null) {
            return 0L;
        }
        return appearance.version;
    }

    public void setColor(ModelPart part, Color argb) {
        Appearance appearance = getOrCreate(part);
        setColor(appearance, argb);
    }

    public void setColor(Appearance appearance, Color argb) {
        if (appearance.argb == argb) {
            return;
        }
        appearance.argb = argb;
        updateAppearanceVersion(appearance);
    }

    public void setRainbowCycle(ModelPart part, Long rainbowCycle) {
        Appearance appearance = getOrCreate(part);
        setRainbowCycle(appearance, rainbowCycle);
    }

    public void setRainbowCycle(Appearance appearance, Long rainbowCycle) {
        if (Objects.equals(appearance.rainbowCycle, rainbowCycle)) {
            return;
        }
        appearance.rainbowCycle = rainbowCycle;
        updateAppearanceVersion(appearance);
    }

    public void setVisible(ModelPart modelPart, boolean visible) {
        Appearance appearance = getOrCreate(modelPart);
        setVisible(appearance, visible);
    }

    public void setVisible(Appearance appearance, boolean visible) {
        if (appearance.visible == visible) {
            return;
        }
        appearance.visible = visible;
        updateAppearanceVersion(appearance);
    }

    public void updateExtrude(ModelPart part) {
        Appearance appearance = getOrCreate(part);
        updateAppearanceVersion(appearance);
    }

    private void updateAppearanceVersion(Appearance appearance) {
        appearance.version++;
        this.globalVersion.incrementAndGet();
    }

    public void setGlowing(ModelPart modelPart, boolean glowing) {
        Appearance appearance = getOrCreate(modelPart);
        setGlowing(appearance, glowing);
    }

    public void setGlowing(Appearance appearance, boolean glowing) {
        if (appearance.glowing == glowing) {
            return;
        }
        appearance.glowing = glowing;
        updateAppearanceVersion(appearance);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/appearance/PartAppearanceStore$Appearance.class */
    public static class Appearance {
        public static final int INVALID_DISPLAY_LIST = -1;
        public Color argb;
        public Long rainbowCycle;
        public boolean glowing;
        public boolean visible;
        public int displayList = -1;
        long version;

        public boolean isCompiledList() {
            return this.displayList != -1;
        }

        public float getRainbowCycle() {
            if (this.rainbowCycle == null) {
                return 0.0f;
            }
            return this.rainbowCycle.longValue() / 1000.0f;
        }
    }
}

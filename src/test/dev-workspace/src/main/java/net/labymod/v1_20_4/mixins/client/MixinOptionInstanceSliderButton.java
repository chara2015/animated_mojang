package net.labymod.v1_20_4.mixins.client;

import net.labymod.api.util.StringUtil;
import net.labymod.v1_20_4.client.gui.WrappedIntRangeBase;
import net.labymod.v1_20_4.mixins.client.gui.components.MixinAbstractSliderButton;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/MixinOptionInstanceSliderButton.class */
@Mixin({i.class})
public abstract class MixinOptionInstanceSliderButton<T> extends MixinAbstractSliderButton {

    @Nullable
    private g labyMod$wrappedIntRangeBase;

    @Shadow
    @Final
    private k<T> e;
    private boolean labyMod$integer;

    @Override // net.labymod.v1_20_4.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMinValue() {
        if (this.labyMod$wrappedIntRangeBase == null) {
            this.labyMod$wrappedIntRangeBase = labyMod$createWrappedIntRangeBase();
        }
        if (this.labyMod$wrappedIntRangeBase != null) {
            this.labyMod$integer = true;
            return this.labyMod$wrappedIntRangeBase.d();
        }
        return 0.0f;
    }

    @Override // net.labymod.v1_20_4.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getMaxValue() {
        if (this.labyMod$wrappedIntRangeBase == null) {
            this.labyMod$wrappedIntRangeBase = labyMod$createWrappedIntRangeBase();
        }
        if (this.labyMod$wrappedIntRangeBase != null) {
            this.labyMod$integer = true;
            return this.labyMod$wrappedIntRangeBase.b();
        }
        return 1.0f;
    }

    @Override // net.labymod.v1_20_4.mixins.client.gui.components.MixinAbstractSliderButton, net.labymod.core.client.accessor.gui.SliderButtonAccessor
    public float getSteps() {
        return this.labyMod$integer ? 1.0f : 0.0f;
    }

    private g labyMod$createWrappedIntRangeBase() {
        String name;
        int lastIndex;
        Class<?> cls = this.e.getClass();
        if (cls.isAnonymousClass() && (lastIndex = (name = cls.getName()).lastIndexOf(36)) != -1 && StringUtil.isNumeric(name.substring(lastIndex + 1))) {
            cls = cls.getEnclosingClass();
        }
        if (g.class.isAssignableFrom(cls)) {
            return new WrappedIntRangeBase(this.e);
        }
        return null;
    }
}

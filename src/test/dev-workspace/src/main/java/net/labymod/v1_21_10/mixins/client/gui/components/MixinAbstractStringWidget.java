package net.labymod.v1_21_10.mixins.client.gui.components;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/components/MixinAbstractStringWidget.class */
@Mixin({gdl.class})
public abstract class MixinAbstractStringWidget<K extends AbstractWidget<?>> extends MixinAbstractWidget<K> {
    @Shadow
    protected abstract int c();
}

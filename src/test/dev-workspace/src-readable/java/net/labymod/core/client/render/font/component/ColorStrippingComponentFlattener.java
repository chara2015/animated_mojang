package net.labymod.core.client.render.font.component;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.flattener.FlattenerListener;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ColorStrippingComponentFlattener.class */
public class ColorStrippingComponentFlattener implements ComponentFlattener {
    private final ComponentFlattener wrapped;

    public ColorStrippingComponentFlattener(ComponentFlattener wrapped) {
        this.wrapped = wrapped;
    }

    @Override // net.labymod.api.client.component.flattener.ComponentFlattener
    public void flatten(@NotNull Component input, @NotNull FlattenerListener listener) {
        this.wrapped.flatten(input, new WrappedFlattenerListener(listener));
    }

    @Override // net.labymod.api.client.component.flattener.ComponentFlattener
    public String getIdentifier() {
        return this.wrapped.getIdentifier();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    public ComponentFlattener.Builder toBuilder2() {
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ColorStrippingComponentFlattener$WrappedFlattenerListener.class */
    private static class WrappedFlattenerListener implements FlattenerListener {
        private final FlattenerListener wrapped;

        private WrappedFlattenerListener(FlattenerListener wrapped) {
            this.wrapped = wrapped;
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void push(@NotNull Component source) {
            this.wrapped.push(source);
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void component(@NotNull String text) {
            this.wrapped.component(Laby.references().textColorStripper().stripColorCodes(text));
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void pop(@NotNull Component source) {
            this.wrapped.pop(source);
        }
    }
}

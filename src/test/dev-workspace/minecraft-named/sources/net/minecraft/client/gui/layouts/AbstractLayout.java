package net.minecraft.client.gui.layouts;

import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/AbstractLayout.class */
public abstract class AbstractLayout implements Layout {
    private int x;
    private int y;
    protected int width;
    protected int height;

    public AbstractLayout(int $$0, int $$1, int $$2, int $$3) {
        this.x = $$0;
        this.y = $$1;
        this.width = $$2;
        this.height = $$3;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public void setX(int $$0) {
        visitChildren($$1 -> {
            int $$2 = $$1.getX() + ($$0 - getX());
            $$1.setX($$2);
        });
        this.x = $$0;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public void setY(int $$0) {
        visitChildren($$1 -> {
            int $$2 = $$1.getY() + ($$0 - getY());
            $$1.setY($$2);
        });
        this.y = $$0;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getX() {
        return this.x;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getY() {
        return this.y;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getWidth() {
        return this.width;
    }

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    public int getHeight() {
        return this.height;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/AbstractLayout$AbstractChildWrapper.class */
    protected static abstract class AbstractChildWrapper {
        public final LayoutElement child;
        public final LayoutSettings.LayoutSettingsImpl layoutSettings;

        protected AbstractChildWrapper(LayoutElement $$0, LayoutSettings $$1) {
            this.child = $$0;
            this.layoutSettings = $$1.getExposed();
        }

        public int getHeight() {
            return this.child.getHeight() + this.layoutSettings.paddingTop + this.layoutSettings.paddingBottom;
        }

        public int getWidth() {
            return this.child.getWidth() + this.layoutSettings.paddingLeft + this.layoutSettings.paddingRight;
        }

        public void setX(int $$0, int $$1) {
            float $$2 = this.layoutSettings.paddingLeft;
            float $$3 = ($$1 - this.child.getWidth()) - this.layoutSettings.paddingRight;
            int $$4 = (int) Mth.lerp(this.layoutSettings.xAlignment, $$2, $$3);
            this.child.setX($$4 + $$0);
        }

        public void setY(int $$0, int $$1) {
            float $$2 = this.layoutSettings.paddingTop;
            float $$3 = ($$1 - this.child.getHeight()) - this.layoutSettings.paddingBottom;
            int $$4 = Math.round(Mth.lerp(this.layoutSettings.yAlignment, $$2, $$3));
            this.child.setY($$4 + $$0);
        }
    }
}

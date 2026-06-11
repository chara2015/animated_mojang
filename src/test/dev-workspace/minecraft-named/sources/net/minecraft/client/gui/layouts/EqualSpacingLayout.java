package net.minecraft.client.gui.layouts;

import com.mojang.math.Divisor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.gui.layouts.AbstractLayout;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/EqualSpacingLayout.class */
public class EqualSpacingLayout extends AbstractLayout {
    private final Orientation orientation;
    private final List<ChildContainer> children;
    private final LayoutSettings defaultChildLayoutSettings;

    public EqualSpacingLayout(int $$0, int $$1, Orientation $$2) {
        this(0, 0, $$0, $$1, $$2);
    }

    public EqualSpacingLayout(int $$0, int $$1, int $$2, int $$3, Orientation $$4) {
        super($$0, $$1, $$2, $$3);
        this.children = new ArrayList();
        this.defaultChildLayoutSettings = LayoutSettings.defaults();
        this.orientation = $$4;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.client.gui.layouts.Layout
    public void arrangeElements() throws MatchException {
        super.arrangeElements();
        if (this.children.isEmpty()) {
        }
        int $$0 = 0;
        int $$1 = this.orientation.getSecondaryLength(this);
        for (ChildContainer $$2 : this.children) {
            $$0 += this.orientation.getPrimaryLength($$2);
            $$1 = Math.max($$1, this.orientation.getSecondaryLength($$2));
        }
        int $$3 = this.orientation.getPrimaryLength(this) - $$0;
        int $$4 = this.orientation.getPrimaryPosition(this);
        Iterator<ChildContainer> $$5 = this.children.iterator();
        ChildContainer $$6 = $$5.next();
        this.orientation.setPrimaryPosition($$6, $$4);
        int $$42 = $$4 + this.orientation.getPrimaryLength($$6);
        if (this.children.size() >= 2) {
            Divisor $$7 = new Divisor($$3, this.children.size() - 1);
            while ($$7.hasNext()) {
                int $$43 = $$42 + $$7.nextInt();
                ChildContainer $$8 = $$5.next();
                this.orientation.setPrimaryPosition($$8, $$43);
                $$42 = $$43 + this.orientation.getPrimaryLength($$8);
            }
        }
        int $$9 = this.orientation.getSecondaryPosition(this);
        for (ChildContainer $$10 : this.children) {
            this.orientation.setSecondaryPosition($$10, $$9, $$1);
        }
        switch (this.orientation) {
            case HORIZONTAL:
                this.height = $$1;
                break;
            case VERTICAL:
                this.width = $$1;
                break;
        }
    }

    @Override // net.minecraft.client.gui.layouts.Layout
    public void visitChildren(Consumer<LayoutElement> $$0) {
        this.children.forEach($$1 -> {
            $$0.accept($$1.child);
        });
    }

    public LayoutSettings newChildLayoutSettings() {
        return this.defaultChildLayoutSettings.copy();
    }

    public LayoutSettings defaultChildLayoutSetting() {
        return this.defaultChildLayoutSettings;
    }

    public <T extends LayoutElement> T addChild(T t) {
        return (T) addChild(t, newChildLayoutSettings());
    }

    public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
        this.children.add(new ChildContainer($$0, $$1));
        return $$0;
    }

    public <T extends LayoutElement> T addChild(T t, Consumer<LayoutSettings> consumer) {
        return (T) addChild(t, (LayoutSettings) Util.make(newChildLayoutSettings(), consumer));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation.class */
    public enum Orientation {
        HORIZONTAL,
        VERTICAL;

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getPrimaryLength(LayoutElement $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getWidth();
                case VERTICAL:
                    return $$0.getHeight();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getPrimaryLength(ChildContainer $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getWidth();
                case VERTICAL:
                    return $$0.getHeight();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getSecondaryLength(LayoutElement $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getHeight();
                case VERTICAL:
                    return $$0.getWidth();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getSecondaryLength(ChildContainer $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getHeight();
                case VERTICAL:
                    return $$0.getWidth();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        void setPrimaryPosition(ChildContainer $$0, int $$1) {
            switch (this) {
                case HORIZONTAL:
                    $$0.setX($$1, $$0.getWidth());
                    break;
                case VERTICAL:
                    $$0.setY($$1, $$0.getHeight());
                    break;
            }
        }

        void setSecondaryPosition(ChildContainer $$0, int $$1, int $$2) {
            switch (this) {
                case HORIZONTAL:
                    $$0.setY($$1, $$2);
                    break;
                case VERTICAL:
                    $$0.setX($$1, $$2);
                    break;
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getPrimaryPosition(LayoutElement $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getX();
                case VERTICAL:
                    return $$0.getY();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        int getSecondaryPosition(LayoutElement $$0) throws MatchException {
            switch (this) {
                case HORIZONTAL:
                    return $$0.getY();
                case VERTICAL:
                    return $$0.getX();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/EqualSpacingLayout$ChildContainer.class */
    static class ChildContainer extends AbstractLayout.AbstractChildWrapper {
        protected ChildContainer(LayoutElement $$0, LayoutSettings $$1) {
            super($$0, $$1);
        }
    }
}

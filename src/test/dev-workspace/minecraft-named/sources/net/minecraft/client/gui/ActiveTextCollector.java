package net.minecraft.client.gui;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.ActiveArea;
import net.minecraft.client.gui.font.EmptyArea;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.state.GuiTextRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.Density;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fc;
import org.joml.Vector2f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ActiveTextCollector.class */
public interface ActiveTextCollector {
    public static final double PERIOD_PER_SCROLLED_PIXEL = 0.5d;
    public static final double MIN_SCROLL_PERIOD = 3.0d;

    Parameters defaultParameters();

    void defaultParameters(Parameters parameters);

    void accept(TextAlignment textAlignment, int i, int i2, Parameters parameters, FormattedCharSequence formattedCharSequence);

    void acceptScrolling(Component component, int i, int i2, int i3, int i4, int i5, Parameters parameters);

    default void accept(int $$0, int $$1, FormattedCharSequence $$2) {
        accept(TextAlignment.LEFT, $$0, $$1, defaultParameters(), $$2);
    }

    default void accept(int $$0, int $$1, Component $$2) {
        accept(TextAlignment.LEFT, $$0, $$1, defaultParameters(), $$2.getVisualOrderText());
    }

    default void accept(TextAlignment $$0, int $$1, int $$2, Parameters $$3, Component $$4) {
        accept($$0, $$1, $$2, $$3, $$4.getVisualOrderText());
    }

    default void accept(TextAlignment $$0, int $$1, int $$2, Component $$3) {
        accept($$0, $$1, $$2, $$3.getVisualOrderText());
    }

    default void accept(TextAlignment $$0, int $$1, int $$2, FormattedCharSequence $$3) {
        accept($$0, $$1, $$2, defaultParameters(), $$3);
    }

    default void acceptScrolling(Component $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
        acceptScrolling($$0, $$1, $$2, $$3, $$4, $$5, defaultParameters());
    }

    default void acceptScrollingWithDefaultCenter(Component $$0, int $$1, int $$2, int $$3, int $$4) {
        acceptScrolling($$0, ($$1 + $$2) / 2, $$1, $$2, $$3, $$4);
    }

    default void defaultScrollingHelper(Component $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, Parameters $$8) {
        int $$9 = ((($$4 + $$5) - $$7) / 2) + 1;
        int $$10 = $$3 - $$2;
        if ($$6 > $$10) {
            int $$11 = $$6 - $$10;
            double $$12 = Util.getMillis() / 1000.0d;
            double $$13 = Math.max(((double) $$11) * 0.5d, 3.0d);
            double $$14 = (Math.sin(1.5707963267948966d * Math.cos((6.283185307179586d * $$12) / $$13)) / 2.0d) + 0.5d;
            double $$15 = Mth.lerp($$14, Density.SURFACE, $$11);
            Parameters $$16 = $$8.withScissor($$2, $$3, $$4, $$5);
            accept(TextAlignment.LEFT, $$2 - ((int) $$15), $$9, $$16, $$0.getVisualOrderText());
            return;
        }
        int $$17 = Mth.clamp($$1, $$2 + ($$6 / 2), $$3 - ($$6 / 2));
        accept(TextAlignment.CENTER, $$17, $$9, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ActiveTextCollector$Parameters.class */
    public static final class Parameters extends Record {
        private final Matrix3x2fc pose;
        private final float opacity;
        private final ScreenRectangle scissor;

        public Parameters(Matrix3x2fc $$0, float $$1, ScreenRectangle $$2) {
            this.pose = $$0;
            this.opacity = $$1;
            this.scissor = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Parameters.class), Parameters.class, "pose;opacity;scissor", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->opacity:F", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->scissor:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Parameters.class), Parameters.class, "pose;opacity;scissor", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->opacity:F", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->scissor:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Parameters.class, Object.class), Parameters.class, "pose;opacity;scissor", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->pose:Lorg/joml/Matrix3x2fc;", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->opacity:F", "FIELD:Lnet/minecraft/client/gui/ActiveTextCollector$Parameters;->scissor:Lnet/minecraft/client/gui/navigation/ScreenRectangle;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix3x2fc pose() {
            return this.pose;
        }

        public float opacity() {
            return this.opacity;
        }

        public ScreenRectangle scissor() {
            return this.scissor;
        }

        public Parameters(Matrix3x2fc $$0) {
            this($$0, 1.0f, null);
        }

        public Parameters withPose(Matrix3x2fc $$0) {
            return new Parameters($$0, this.opacity, this.scissor);
        }

        public Parameters withScale(float $$0) {
            return withPose(this.pose.scale($$0, $$0, new Matrix3x2f()));
        }

        public Parameters withOpacity(float $$0) {
            if (this.opacity == $$0) {
                return this;
            }
            return new Parameters(this.pose, $$0, this.scissor);
        }

        public Parameters withScissor(ScreenRectangle $$0) {
            if ($$0.equals(this.scissor)) {
                return this;
            }
            return new Parameters(this.pose, this.opacity, $$0);
        }

        public Parameters withScissor(int $$0, int $$1, int $$2, int $$3) {
            ScreenRectangle $$4 = new ScreenRectangle($$0, $$2, $$1 - $$0, $$3 - $$2).transformAxisAligned(this.pose);
            if (this.scissor != null) {
                $$4 = (ScreenRectangle) Objects.requireNonNullElse(this.scissor.intersection($$4), ScreenRectangle.empty());
            }
            return withScissor($$4);
        }
    }

    static void findElementUnderCursor(GuiTextRenderState $$0, float $$1, float $$2, final Consumer<Style> $$3) {
        ScreenRectangle $$4 = $$0.bounds();
        if ($$4 == null || !$$4.containsPoint((int) $$1, (int) $$2)) {
            return;
        }
        Vector2f vector2fTransformPosition = $$0.pose.invert(new Matrix3x2f()).transformPosition(new Vector2f($$1, $$2));
        final float $$6 = vector2fTransformPosition.x();
        final float $$7 = vector2fTransformPosition.y();
        $$0.ensurePrepared().visit(new Font.GlyphVisitor() { // from class: net.minecraft.client.gui.ActiveTextCollector.1
            @Override // net.minecraft.client.gui.Font.GlyphVisitor
            public void acceptGlyph(TextRenderable.Styled $$02) {
                acceptActiveArea($$02);
            }

            @Override // net.minecraft.client.gui.Font.GlyphVisitor
            public void acceptEmptyArea(EmptyArea $$02) {
                acceptActiveArea($$02);
            }

            private void acceptActiveArea(ActiveArea $$02) {
                if (ActiveTextCollector.isPointInRectangle($$6, $$7, $$02.activeLeft(), $$02.activeTop(), $$02.activeRight(), $$02.activeBottom())) {
                    $$3.accept($$02.style());
                }
            }
        });
    }

    static boolean isPointInRectangle(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
        return $$0 >= $$2 && $$0 < $$4 && $$1 >= $$3 && $$1 < $$5;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/ActiveTextCollector$ClickableStyleFinder.class */
    public static class ClickableStyleFinder implements ActiveTextCollector {
        private static final Parameters INITIAL = new Parameters(new Matrix3x2f());
        private final Font font;
        private final int testX;
        private final int testY;
        private boolean includeInsertions;
        private Style result;
        private Parameters defaultParameters = INITIAL;
        private final Consumer<Style> styleScanner = $$0 -> {
            if ($$0.getClickEvent() != null || (this.includeInsertions && $$0.getInsertion() != null)) {
                this.result = $$0;
            }
        };

        public ClickableStyleFinder(Font $$0, int $$1, int $$2) {
            this.font = $$0;
            this.testX = $$1;
            this.testY = $$2;
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public Parameters defaultParameters() {
            return this.defaultParameters;
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void defaultParameters(Parameters $$0) {
            this.defaultParameters = $$0;
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void accept(TextAlignment $$0, int $$1, int $$2, Parameters $$3, FormattedCharSequence $$4) {
            int $$5 = $$0.calculateLeft($$1, this.font, $$4);
            GuiTextRenderState $$6 = new GuiTextRenderState(this.font, $$4, $$3.pose(), $$5, $$2, ARGB.white($$3.opacity()), 0, true, true, $$3.scissor());
            ActiveTextCollector.findElementUnderCursor($$6, this.testX, this.testY, this.styleScanner);
        }

        @Override // net.minecraft.client.gui.ActiveTextCollector
        public void acceptScrolling(Component $$0, int $$1, int $$2, int $$3, int $$4, int $$5, Parameters $$6) {
            int $$7 = this.font.width($$0);
            Objects.requireNonNull(this.font);
            defaultScrollingHelper($$0, $$1, $$2, $$3, $$4, $$5, $$7, 9, $$6);
        }

        public ClickableStyleFinder includeInsertions(boolean $$0) {
            this.includeInsertions = $$0;
            return this;
        }

        public Style result() {
            return this.result;
        }
    }
}

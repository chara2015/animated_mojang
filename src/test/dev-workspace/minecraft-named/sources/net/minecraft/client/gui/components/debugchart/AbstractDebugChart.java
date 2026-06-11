package net.minecraft.client.gui.components.debugchart;

import java.util.Objects;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.debugchart.SampleStorage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/debugchart/AbstractDebugChart.class */
public abstract class AbstractDebugChart {
    protected static final int CHART_HEIGHT = 60;
    protected static final int LINE_WIDTH = 1;
    protected final Font font;
    protected final SampleStorage sampleStorage;

    protected abstract String toDisplayString(double d);

    protected abstract int getSampleHeight(double d);

    protected abstract int getSampleColor(long j);

    protected AbstractDebugChart(Font $$0, SampleStorage $$1) {
        this.font = $$0;
        this.sampleStorage = $$1;
    }

    public int getWidth(int $$0) {
        return Math.min(this.sampleStorage.capacity() + 2, $$0);
    }

    public int getFullHeight() {
        Objects.requireNonNull(this.font);
        return 60 + 9;
    }

    public void drawChart(GuiGraphics $$0, int $$1, int $$2) {
        int $$3 = $$0.guiHeight();
        $$0.fill($$1, $$3 - 60, $$1 + $$2, $$3, -1873784752);
        long $$4 = 0;
        long $$5 = 2147483647L;
        long $$6 = -2147483648L;
        int $$7 = Math.max(0, this.sampleStorage.capacity() - ($$2 - 2));
        int $$8 = this.sampleStorage.size() - $$7;
        for (int $$9 = 0; $$9 < $$8; $$9++) {
            int $$10 = $$1 + $$9 + 1;
            int $$11 = $$7 + $$9;
            long $$12 = getValueForAggregation($$11);
            $$5 = Math.min($$5, $$12);
            $$6 = Math.max($$6, $$12);
            $$4 += $$12;
            drawDimensions($$0, $$3, $$10, $$11);
        }
        $$0.hLine($$1, ($$1 + $$2) - 1, $$3 - 60, -1);
        $$0.hLine($$1, ($$1 + $$2) - 1, $$3 - 1, -1);
        $$0.vLine($$1, $$3 - 60, $$3, -1);
        $$0.vLine(($$1 + $$2) - 1, $$3 - 60, $$3, -1);
        if ($$8 > 0) {
            String $$13 = toDisplayString($$5) + " min";
            String $$14 = toDisplayString($$4 / ((double) $$8)) + " avg";
            String $$15 = toDisplayString($$6) + " max";
            Objects.requireNonNull(this.font);
            $$0.drawString(this.font, $$13, $$1 + 2, ($$3 - 60) - 9, -2039584);
            Objects.requireNonNull(this.font);
            $$0.drawCenteredString(this.font, $$14, $$1 + ($$2 / 2), ($$3 - 60) - 9, -2039584);
            Font font = this.font;
            int iWidth = (($$1 + $$2) - this.font.width($$15)) - 2;
            Objects.requireNonNull(this.font);
            $$0.drawString(font, $$15, iWidth, ($$3 - 60) - 9, -2039584);
        }
        renderAdditionalLinesAndLabels($$0, $$1, $$2, $$3);
    }

    protected void drawDimensions(GuiGraphics $$0, int $$1, int $$2, int $$3) {
        drawMainDimension($$0, $$1, $$2, $$3);
        drawAdditionalDimensions($$0, $$1, $$2, $$3);
    }

    protected void drawMainDimension(GuiGraphics $$0, int $$1, int $$2, int $$3) {
        long $$4 = this.sampleStorage.get($$3);
        int $$5 = getSampleHeight($$4);
        int $$6 = getSampleColor($$4);
        $$0.fill($$2, $$1 - $$5, $$2 + 1, $$1, $$6);
    }

    protected void drawAdditionalDimensions(GuiGraphics $$0, int $$1, int $$2, int $$3) {
    }

    protected long getValueForAggregation(int $$0) {
        return this.sampleStorage.get($$0);
    }

    protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
    }

    protected void drawStringWithShade(GuiGraphics $$0, String $$1, int $$2, int $$3) {
        int iWidth = $$2 + this.font.width($$1) + 1;
        Objects.requireNonNull(this.font);
        $$0.fill($$2, $$3, iWidth, $$3 + 9, -1873784752);
        $$0.drawString(this.font, $$1, $$2 + 1, $$3 + 1, -2039584, false);
    }

    protected int getSampleColor(double $$0, double $$1, int $$2, double $$3, int $$4, double $$5, int $$6) {
        double $$02 = Mth.clamp($$0, $$1, $$5);
        if ($$02 < $$3) {
            return ARGB.srgbLerp((float) (($$02 - $$1) / ($$3 - $$1)), $$2, $$4);
        }
        return ARGB.srgbLerp((float) (($$02 - $$3) / ($$5 - $$3)), $$4, $$6);
    }
}

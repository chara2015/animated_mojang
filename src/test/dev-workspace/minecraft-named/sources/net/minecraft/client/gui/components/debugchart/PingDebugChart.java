package net.minecraft.client.gui.components.debugchart;

import java.util.Locale;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.CommonColors;
import net.minecraft.util.debugchart.SampleStorage;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/debugchart/PingDebugChart.class */
public class PingDebugChart extends AbstractDebugChart {
    private static final int CHART_TOP_VALUE = 500;

    public PingDebugChart(Font $$0, SampleStorage $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.client.gui.components.debugchart.AbstractDebugChart
    protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
        drawStringWithShade($$0, "500 ms", $$1 + 1, ($$3 - 60) + 1);
    }

    @Override // net.minecraft.client.gui.components.debugchart.AbstractDebugChart
    protected String toDisplayString(double $$0) {
        return String.format(Locale.ROOT, "%d ms", Integer.valueOf((int) Math.round($$0)));
    }

    @Override // net.minecraft.client.gui.components.debugchart.AbstractDebugChart
    protected int getSampleHeight(double $$0) {
        return (int) Math.round(($$0 * 60.0d) / 500.0d);
    }

    @Override // net.minecraft.client.gui.components.debugchart.AbstractDebugChart
    protected int getSampleColor(long $$0) {
        return getSampleColor($$0, Density.SURFACE, CommonColors.GREEN, 250.0d, CommonColors.YELLOW, 500.0d, CommonColors.RED);
    }
}

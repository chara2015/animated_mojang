package net.labymod.api.client.gui.screen.widget.widgets.transform;

import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/transform/FadingWidget.class */
@AutoWidget
public class FadingWidget extends WrappedWidget {
    private final long timestamp;
    private BooleanSupplier forceRendered;

    protected FadingWidget(Widget widget, long timestamp) {
        super(widget);
        this.timestamp = timestamp;
    }

    public static FadingWidget until(Widget widget, long timestamp) {
        return new FadingWidget(widget, timestamp);
    }

    public static FadingWidget forMillis(Widget widget, long millis) {
        return until(widget, TimeUtil.getMillis() + millis);
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setForceRendered(BooleanSupplier forceRendered) {
        this.forceRendered = forceRendered;
    }

    private boolean isRendered() {
        if (this.forceRendered == null || !this.forceRendered.getAsBoolean()) {
            return this.timestamp > TimeUtil.getMillis() && getAlpha() >= 0.0225f;
        }
        return this.forceRendered.getAsBoolean();
    }

    public long getMillisLeft() {
        return this.timestamp - TimeUtil.getMillis();
    }

    private float getAlpha() {
        if (this.forceRendered != null && this.forceRendered.getAsBoolean()) {
            return 1.0f;
        }
        double alphaRoot = MathHelper.clamp(((getMillisLeft() / 50.0d) / 200.0d) * 10.0d, 0.0d, 1.0d);
        return (float) (alphaRoot * alphaRoot);
    }

    @Override // net.labymod.api.client.gui.screen.widget.WrappedWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        if (isRendered()) {
            float alpha = getAlpha();
            Laby.labyAPI().renderPipeline().multiplyAlpha(alpha, () -> {
                super.render(context);
            });
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.WrappedWidget, net.labymod.api.client.gui.element.Element
    public boolean isVisible() {
        if (this.childWidget instanceof AbstractWidget) {
            return ((AbstractWidget) this.childWidget).visible().get().booleanValue();
        }
        return false;
    }
}

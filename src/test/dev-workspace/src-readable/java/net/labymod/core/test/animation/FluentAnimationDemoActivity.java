package net.labymod.core.test.animation;

import net.labymod.api.client.animation.easing.Easings;
import net.labymod.api.client.animation.playback.LoopMode;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/animation/FluentAnimationDemoActivity.class */
@AutoActivity
@Link("test/fluent-animation-demo.lss")
public class FluentAnimationDemoActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ComponentWidget title = ComponentWidget.text("Fluent Animation API Demo");
        title.addId("title");
        ((Document) this.document).addChild(title);
        VerticalListWidget<Widget> column = new VerticalListWidget<>();
        column.addId("demo-column");
        HorizontalListWidget row1 = new HorizontalListWidget();
        row1.addId("demo-row");
        DivWidget fadeBox = new DivWidget();
        fadeBox.addId("box", "fade");
        fadeBox.setPressListener(() -> {
            fadeBox.animate("opacity").from(Float.valueOf(0.0f)).to(Float.valueOf(1.0f)).duration(600L).easing(Easings.EASE_OUT).play();
            return true;
        });
        row1.addEntry(labeled("Fade (click)", fadeBox));
        DivWidget slideBox = new DivWidget();
        slideBox.addId("box", "slide");
        slideBox.setPressListener(() -> {
            slideBox.animate("translateX").from(Float.valueOf(-20.0f)).to(Float.valueOf(20.0f)).duration(500L).easing(Easings.EASE_IN_OUT).play();
            return true;
        });
        row1.addEntry(labeled("Slide (click)", slideBox));
        DivWidget pulseBox = new DivWidget();
        pulseBox.addId("box", "pulse");
        pulseBox.setPressListener(() -> {
            pulseBox.animate("scaleX").from(Float.valueOf(0.5f)).to(Float.valueOf(1.5f)).duration(400L).easing(Easings.BOUNCE).play();
            return true;
        });
        row1.addEntry(labeled("Pulse (click)", pulseBox));
        column.addChild(row1);
        HorizontalListWidget row2 = new HorizontalListWidget();
        row2.addId("demo-row");
        DivWidget loopFade = new DivWidget();
        loopFade.addId("box", "loop-fade");
        loopFade.animate("opacity").from(Float.valueOf(0.2f)).to(Float.valueOf(1.0f)).duration(800L).loopMode(LoopMode.PING_PONG).easing(Easings.EASE_IN_OUT).play();
        row2.addEntry(labeled("PingPong Fade", loopFade));
        DivWidget loopSlide = new DivWidget();
        loopSlide.addId("box", "loop-slide");
        loopSlide.animate("translateX").from(Float.valueOf(-15.0f)).to(Float.valueOf(15.0f)).duration(600L).loopMode(LoopMode.PING_PONG).easing(Easings.EASE_IN_OUT).play();
        row2.addEntry(labeled("PingPong Slide", loopSlide));
        DivWidget loopScale = new DivWidget();
        loopScale.addId("box", "loop-scale");
        loopScale.animate("scaleX").from(Float.valueOf(0.8f)).to(Float.valueOf(1.2f)).duration(500L).loopMode(LoopMode.LOOP).play();
        row2.addEntry(labeled("Loop Scale", loopScale));
        column.addChild(row2);
        HorizontalListWidget row3 = new HorizontalListWidget();
        row3.addId("demo-row");
        DivWidget fastBox = new DivWidget();
        fastBox.addId("box", "fast");
        fastBox.animate("translateX").from(Float.valueOf(-20.0f)).to(Float.valueOf(20.0f)).duration(1000L).speed(2.0f).loopMode(LoopMode.PING_PONG).easing(Easings.EASE_IN_OUT).play();
        row3.addEntry(labeled("2x Speed", fastBox));
        DivWidget slowBox = new DivWidget();
        slowBox.addId("box", "slow");
        slowBox.animate("translateX").from(Float.valueOf(-20.0f)).to(Float.valueOf(20.0f)).duration(1000L).speed(0.5f).loopMode(LoopMode.PING_PONG).easing(Easings.EASE_IN_OUT).play();
        row3.addEntry(labeled("0.5x Speed", slowBox));
        DivWidget multiBox = new DivWidget();
        multiBox.addId("box", "multi");
        multiBox.animate().duration(1000L).loopMode(LoopMode.PING_PONG).property("opacity", Float.class).from(Float.valueOf(0.3f)).to(Float.valueOf(1.0f)).easing(Easings.EASE_IN_OUT).and().property("scaleX", Float.class).from(Float.valueOf(0.7f)).to(Float.valueOf(1.3f)).easing(Easings.EASE_OUT).and().play();
        row3.addEntry(labeled("Multi-Prop", multiBox));
        column.addChild(row3);
        HorizontalListWidget row4 = new HorizontalListWidget();
        row4.addId("demo-row");
        DivWidget controlBox = new DivWidget();
        controlBox.addId("box", "control");
        controlBox.animate("translateX").from(Float.valueOf(-25.0f)).to(Float.valueOf(25.0f)).duration(800L).loopMode(LoopMode.PING_PONG).easing(Easings.EASE_IN_OUT).play();
        row4.addEntry(labeled("Controlled", controlBox));
        ButtonWidget pauseBtn = ButtonWidget.text("Pause", () -> {
            controlBox.animation().pause();
        });
        pauseBtn.addId("control-btn");
        row4.addEntry(pauseBtn);
        ButtonWidget resumeBtn = ButtonWidget.text("Resume", () -> {
            controlBox.animation().resume();
        });
        resumeBtn.addId("control-btn");
        row4.addEntry(resumeBtn);
        column.addChild(row4);
        HorizontalListWidget row5 = new HorizontalListWidget();
        row5.addId("demo-row");
        ComponentWidget statusLabel = ComponentWidget.text("Click box to animate...");
        statusLabel.addId("status-label");
        DivWidget callbackBox = new DivWidget();
        callbackBox.addId("box", "callback");
        callbackBox.setPressListener(() -> {
            statusLabel.setComponent(Component.text("Animating..."));
            callbackBox.animate("opacity").from(Float.valueOf(0.0f)).to(Float.valueOf(1.0f)).duration(1000L).easing(Easings.EASE_OUT).onComplete(() -> {
                statusLabel.setComponent(Component.text("Done!"));
            }).play();
            return true;
        });
        row5.addEntry(labeled("Callback (click)", callbackBox));
        row5.addEntry(statusLabel);
        column.addChild(row5);
        HorizontalListWidget row6 = new HorizontalListWidget();
        row6.addId("demo-row");
        DivWidget easingBox = new DivWidget();
        easingBox.addId("box", "easing-demo");
        easingBox.animate().duration(1500L).loopMode(LoopMode.LOOP).property("translateY", Float.class).from(Float.valueOf(-10.0f)).to(Float.valueOf(10.0f)).easing(Easings.BOUNCE).and().property("opacity", Float.class).from(Float.valueOf(0.4f)).to(Float.valueOf(1.0f)).easing(Easings.EASE_IN).and().play();
        row6.addEntry(labeled("Bounce+Fade", easingBox));
        DivWidget cubicBox = new DivWidget();
        cubicBox.addId("box", "cubic-demo");
        cubicBox.animate("translateX").from(Float.valueOf(-20.0f)).to(Float.valueOf(20.0f)).duration(1200L).loopMode(LoopMode.PING_PONG).easing(Easings.cubicBezier(0.68d, -0.55d, 0.27d, 1.55d)).play();
        row6.addEntry(labeled("CubicBezier", cubicBox));
        column.addChild(row6);
        ComponentWidget lssTitle = ComponentWidget.text("LSS @keyframes Animations");
        lssTitle.addId("section-title");
        column.addChild(lssTitle);
        HorizontalListWidget row7 = new HorizontalListWidget();
        row7.addId("demo-row");
        DivWidget lssFade = new DivWidget();
        lssFade.addId("box", "lss-fade");
        lssFade.setPressListener(() -> {
            lssFade.playAnimation("fade-in");
            return true;
        });
        row7.addEntry(labeled("LSS Fade (click)", lssFade));
        DivWidget lssSlide = new DivWidget();
        lssSlide.addId("box", "lss-slide");
        lssSlide.setPressListener(() -> {
            lssSlide.playAnimation("slide-in");
            return true;
        });
        row7.addEntry(labeled("LSS Slide (click)", lssSlide));
        DivWidget lssPulse = new DivWidget();
        lssPulse.addId("box", "lss-pulse");
        lssPulse.setPressListener(() -> {
            lssPulse.playAnimation("pulse", () -> {
                statusLabel.setComponent(Component.text("LSS pulse done!"));
            });
            return true;
        });
        row7.addEntry(labeled("LSS Pulse (click)", lssPulse));
        column.addChild(row7);
        HorizontalListWidget row8 = new HorizontalListWidget();
        row8.addId("demo-row");
        DivWidget lssLoop = new DivWidget();
        lssLoop.addId("box", "lss-loop");
        lssLoop.playAnimation("breathe");
        row8.addEntry(labeled("LSS PingPong", lssLoop));
        DivWidget lssInfinite = new DivWidget();
        lssInfinite.addId("box", "lss-infinite");
        lssInfinite.playAnimation("slide-loop");
        row8.addEntry(labeled("LSS Loop", lssInfinite));
        DivWidget lssMulti = new DivWidget();
        lssMulti.addId("box", "lss-multi");
        lssMulti.setPressListener(() -> {
            lssMulti.playAnimation("multi-stage");
            return true;
        });
        row8.addEntry(labeled("LSS 3-Stage (click)", lssMulti));
        column.addChild(row8);
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) column);
        scrollWidget.addId("scroll");
        ((Document) this.document).addChild(scrollWidget);
    }

    private DivWidget labeled(String text, Widget content) {
        DivWidget wrapper = new DivWidget();
        wrapper.addId("labeled-entry");
        ComponentWidget label = ComponentWidget.text(text);
        label.addId("entry-label");
        wrapper.addChild(label);
        wrapper.addChild(content);
        return wrapper;
    }
}

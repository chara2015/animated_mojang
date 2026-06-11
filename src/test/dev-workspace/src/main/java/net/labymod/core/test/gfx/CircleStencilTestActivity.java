package net.labymod.core.test.gfx;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.state.ClipShape;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/gfx/CircleStencilTestActivity.class */
@AutoActivity
public class CircleStencilTestActivity extends TestActivity {
    private static final float SIZE = 50.0f;
    private boolean clipEnabled = true;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.O) {
            this.clipEnabled = !this.clipEnabled;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        Stack stack = context.stack();
        stack.push();
        stack.translate(0.0f, 0.0f, 0.0f);
        float scaleValue = (TimeUtil.getMillis() / 3600.0f) % 2.0f;
        stack.scale(scaleValue);
        ScreenCanvas canvas = context.canvas();
        if (this.clipEnabled) {
            canvas.pushClipShape(ClipShape.circle(50.0f, 50.0f, 50.0f));
        }
        canvas.submitRelativeRect(0.0f, 0.0f, 50.0f, 50.0f, -65536);
        canvas.submitRelativeRect(0.0f, 50.0f, 50.0f, 50.0f, -16711936);
        canvas.submitRelativeRect(50.0f, 50.0f, 50.0f, 50.0f, -16776961);
        canvas.submitRelativeRect(50.0f, 0.0f, 50.0f, 50.0f, -1);
        if (this.clipEnabled) {
            canvas.popClipShape();
        }
        stack.pop();
        canvas.submitRenderableComponent(RenderableComponent.of("Clip Test: " + (this.clipEnabled ? "§aenabled" : "§cdisabled")), 0.0f, 0.0f, -1, 1);
    }
}

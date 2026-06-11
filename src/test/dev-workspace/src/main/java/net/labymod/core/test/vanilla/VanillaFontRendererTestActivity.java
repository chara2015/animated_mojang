package net.labymod.core.test.vanilla;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.renderer.text.Font;
import net.labymod.api.client.gfx.pipeline.renderer.text.Fonts;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/vanilla/VanillaFontRendererTestActivity.class */
@AutoActivity
public class VanillaFontRendererTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        ScreenCanvas canvas = context.canvas();
        TextRenderer textRenderer = Laby.references().textRenderer();
        Font currentFont = textRenderer.getCurrentFont();
        textRenderer.setCurrentFont(Fonts.MINECRAFT);
        TextComponent text = Component.text("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z", Style.builder().color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD).build());
        canvas.submitComponent(text, 2.0f, 2.0f, -1, 0, 2.0f, 1);
        float y = 2.0f + (canvas.getLineHeight() * 2.0f);
        TextComponent build = Component.text().text("Player Head: ").append(Component.icon(Icon.head("Robby_"))).append(Component.text("Robby_", Style.builder().color(NamedTextColor.GOLD).build())).build();
        float y2 = y + 2.0f;
        canvas.submitComponent((Component) build, 2.0f, y2, -1, 1.0f, 1);
        float y3 = y2 + canvas.getLineHeight() + 9.0f;
        canvas.submitText("§nTest, Current MC-Version: " + Laby.labyAPI().minecraft().getVersion(), 2.0f, y3, -1, 1.0f, 1);
        float y4 = y3 + canvas.getLineHeight();
        canvas.submitText("➤ test", 2.0f, y4, -1, 4.0f, 1);
        canvas.submitComponent((Component) Component.text("➤ test", NamedTextColor.RED, TextDecoration.UNDERLINED), 2.0f, y4 + (canvas.getLineHeight() * 4.0f), -1, 4.0f, 1);
        textRenderer.setCurrentFont(currentFont);
    }
}

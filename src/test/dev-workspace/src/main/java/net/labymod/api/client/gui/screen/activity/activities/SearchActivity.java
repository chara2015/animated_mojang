package net.labymod.api.client.gui.screen.activity.activities;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.util.function.Consumers;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/activities/SearchActivity.class */
@Link("activity/search-activity.lss")
@AutoActivity
public class SearchActivity extends Activity {
    private final Consumer<String> updateListener;
    private final Consumer<TextFieldWidget> widgetConsumer;
    private TextFieldWidget textFieldWidget;
    private boolean skipNextCharacter;

    public SearchActivity(Consumer<String> updateListener) {
        this(updateListener, null);
    }

    public SearchActivity(Consumer<String> updateListener, Consumer<TextFieldWidget> widgetConsumer) {
        this.updateListener = (Consumer) Objects.requireNonNull(updateListener);
        this.widgetConsumer = widgetConsumer;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.textFieldWidget = (TextFieldWidget) new TextFieldWidget().blockFirstKeyPress(true).addId("search-input");
        this.textFieldWidget.updateListener(this.updateListener);
        document().addChild(this.textFieldWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        if (this.skipNextCharacter) {
            this.skipNextCharacter = false;
            return false;
        }
        return super.charTyped(key, character);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        Consumers.accept(this.widgetConsumer, this.textFieldWidget);
    }

    public void skipNextCharacter() {
        this.skipNextCharacter = true;
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        return false;
    }
}

package net.labymod.api.client.gui.screen.widget.widgets.popup;

import java.util.Objects;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/popup/AdvancedPopupActivity.class */
@AutoActivity
public class AdvancedPopupActivity extends Activity {
    private final AdvancedPopupWidget popupWidget;

    protected AdvancedPopupActivity(@NotNull AdvancedPopupWidget popupWidget) {
        Objects.requireNonNull(popupWidget, "popup");
        this.popupWidget = popupWidget;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ((Document) this.document).addChild(this.popupWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ESCAPE) {
            this.popupWidget.popup().close();
            return true;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        return true;
    }
}

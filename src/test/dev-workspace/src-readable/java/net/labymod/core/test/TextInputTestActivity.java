package net.labymod.core.test;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.core.client.gui.screen.widget.widgets.MultilineTextFieldWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/TextInputTestActivity.class */
@AutoActivity
@Link("test/text-input.lss")
public class TextInputTestActivity extends TestActivity {
    private static final TagInputWidget.TagCollection TAGS = new TagInputWidget.TagCollection();

    static {
        TAGS.add("test");
        TAGS.add("test2");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ((Document) this.document).addChild(new MultilineTextFieldWidget());
        ((Document) this.document).addChild(new TagInputWidget(TAGS));
    }
}

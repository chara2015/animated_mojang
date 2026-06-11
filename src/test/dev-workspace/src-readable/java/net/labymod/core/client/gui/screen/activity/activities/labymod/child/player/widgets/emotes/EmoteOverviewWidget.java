package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/emotes/EmoteOverviewWidget.class */
@AutoWidget
public class EmoteOverviewWidget extends AbstractWidget<Widget> {
    private final EmotesActivity activity;
    private final EmoteItem emote;

    public EmoteOverviewWidget(EmotesActivity activity, EmoteItem emote) {
        this.activity = activity;
        this.emote = emote;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.children.clear();
        addChild(ComponentWidget.text(this.emote.getName()));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    protected void onAttributeStateChanged(AttributeState state, boolean newState) {
        super.onAttributeStateChanged(state, newState);
        if (state != AttributeState.HOVER) {
            return;
        }
        if (newState) {
            this.activity.playEmote(this.emote);
        } else {
            this.activity.stopEmote(this.emote);
        }
    }
}

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.EmotesActivity;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/emotes/EmoteCustomizationSegmentWidget.class */
@AutoWidget
public class EmoteCustomizationSegmentWidget extends WheelWidget.Segment {
    private final EmotesActivity activity;
    private final EmoteItem emote;
    private final int index;
    private final int wheelIndex;

    public EmoteCustomizationSegmentWidget(EmotesActivity activity, int index, int wheelIndex, EmoteItem emote) {
        this.activity = activity;
        this.emote = emote;
        this.index = index;
        this.wheelIndex = wheelIndex;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.children.clear();
        addChild(ComponentWidget.text(this.emote.getName()));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.WheelWidget.Segment
    protected void onSelectionChanged() {
        boolean selected = isSegmentSelected();
        if (!selected) {
            this.activity.stopEmote(this.emote);
        } else {
            this.activity.playEmote(this.emote);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        return super.onPress();
    }
}

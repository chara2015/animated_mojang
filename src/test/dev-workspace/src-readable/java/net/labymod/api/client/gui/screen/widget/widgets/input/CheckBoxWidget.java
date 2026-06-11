package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.cursor.CursorTypes;
import net.labymod.api.client.sound.SoundType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/CheckBoxWidget.class */
@AutoWidget
public class CheckBoxWidget extends AbstractWidget<Widget> {
    private State state = State.UNCHECKED;

    public CheckBoxWidget() {
        setHoverCursor(CursorTypes.POINTING_HAND);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "CheckBox";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isHovered()) {
            onPress();
            callActionListeners();
            return true;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        this.state = this.state.toggle();
        Laby.references().soundService().play(this.state == State.CHECKED ? SoundType.SWITCH_TOGGLE_ON : SoundType.SWITCH_TOGGLE_OFF);
        return super.onPress();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    public State state() {
        return this.state;
    }

    public void setState(State checked) {
        this.state = checked;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/CheckBoxWidget$State.class */
    public enum State {
        UNCHECKED,
        PARTLY,
        CHECKED;

        public State toggle() {
            return this == UNCHECKED ? CHECKED : UNCHECKED;
        }
    }
}

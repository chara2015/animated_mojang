package net.labymod.api.client.gui.screen.state.states.commands;

import java.util.Objects;
import net.labymod.api.client.gui.screen.state.recorder.GenericTaskRecorderState;
import net.labymod.api.client.gui.screen.state.recorder.RecorderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/commands/GuiGenericTaskComponent.class */
public class GuiGenericTaskComponent extends GuiCommandComponent {
    private final Runnable task;

    public GuiGenericTaskComponent(Runnable task) {
        Objects.requireNonNull(task, "task cannot be null");
        this.task = task;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent
    public RecorderState createState() {
        return new GenericTaskRecorderState(this.task);
    }
}

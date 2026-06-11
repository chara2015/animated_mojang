package net.labymod.api.client.gui.screen.state.states.commands;

import net.labymod.api.client.gui.screen.state.ClearInfo;
import net.labymod.api.client.gui.screen.state.recorder.ClearRecorderState;
import net.labymod.api.client.gui.screen.state.recorder.RecorderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/commands/GuiClearComponent.class */
public class GuiClearComponent extends GuiCommandComponent {
    private final ClearInfo clearInfo;

    public GuiClearComponent(ClearInfo clearInfo) {
        this.clearInfo = clearInfo;
    }

    @Override // net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent
    public RecorderState createState() {
        return new ClearRecorderState(this.clearInfo);
    }

    @Override // net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent
    public boolean shouldRecordMesh() {
        return true;
    }
}

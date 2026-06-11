package net.labymod.api.client.gui.screen.state.states.commands;

import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.recorder.RecorderState;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/commands/GuiCommandComponent.class */
public abstract class GuiCommandComponent implements GuiComponent {
    public abstract RecorderState createState();

    public boolean shouldRecordMesh() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    public Rectangle bounds() {
        throw unsupportedOperationException("bounds");
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    @Nullable
    public ScissorArea getScissorArea() {
        throw unsupportedOperationException("getScissorArea");
    }

    private UnsupportedOperationException unsupportedOperationException(@NotNull String methodName) {
        return new UnsupportedOperationException("The method '" + methodName + "' is not supported by this component.");
    }
}

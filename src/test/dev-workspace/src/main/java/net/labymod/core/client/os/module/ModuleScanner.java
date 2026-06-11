package net.labymod.core.client.os.module;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleScanner.class */
public abstract class ModuleScanner {
    protected static final Logging LOGGER = Logging.getLogger();
    private final boolean[] states = createStates();
    private final List<ModuleSolutionProvider> solutionProviders = new ArrayList();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleScanner$State.class */
    public enum State {
        SWAP_BUFFERS;

        public static final State[] VALUES = values();
    }

    public abstract void scan(State state);

    protected void addSolutionProvider(ModuleSolutionProvider provider) {
        this.solutionProviders.add(provider);
    }

    protected List<ModuleSolutionProvider> getSolutionProviders() {
        return this.solutionProviders;
    }

    public boolean getState(State state) {
        return this.states[state.ordinal()];
    }

    public void setState(State state, boolean value) {
        this.states[state.ordinal()] = value;
    }

    private boolean[] createStates() {
        boolean[] states = new boolean[State.VALUES.length];
        for (State state : State.VALUES) {
            states[state.ordinal()] = false;
        }
        return states;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleScanner$Empty.class */
    public static class Empty extends ModuleScanner {
        @Override // net.labymod.core.client.os.module.ModuleScanner
        public void scan(State state) {
        }
    }
}

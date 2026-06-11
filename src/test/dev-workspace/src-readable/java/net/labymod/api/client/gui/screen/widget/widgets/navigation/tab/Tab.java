package net.labymod.api.client.gui.screen.widget.widgets.navigation.tab;

import net.labymod.api.client.gui.screen.ScreenInstance;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/navigation/tab/Tab.class */
public abstract class Tab {
    private final boolean useSingleInstance;
    private ScreenInstance instance;
    private int index;
    private boolean transitionToRight;
    private boolean hidden;

    public abstract ScreenInstance createScreen();

    public Tab() {
        this(true);
    }

    public Tab(boolean useSingleInstance) {
        this.useSingleInstance = useSingleInstance;
    }

    public ScreenInstance provideScreen() {
        if (!this.useSingleInstance || this.instance == null) {
            this.instance = createScreen();
        }
        return this.instance;
    }

    public boolean isScreen(Class<? extends ScreenInstance> screenClass) {
        return provideScreen().getClass() == screenClass;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setTransitionToRight(boolean transitionToRight) {
        this.transitionToRight = transitionToRight;
    }

    public boolean isTransitionToRight() {
        return this.transitionToRight;
    }
}

package net.labymod.api.client.gui.screen.activity.util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/util/PageNavigator.class */
public class PageNavigator {
    private int previousPage;
    private int currentPage;
    private int minimumPage;
    private int maximumPage;
    private boolean specialPage;
    private final Runnable onPageSwitch;

    public PageNavigator(Runnable onPageSwitch) {
        this.onPageSwitch = onPageSwitch;
    }

    public void switchPage(boolean left) {
        this.previousPage = this.currentPage;
        this.currentPage += left ? -1 : 1;
        if (this.onPageSwitch != null) {
            this.onPageSwitch.run();
        }
    }

    public int getPreviousPage() {
        return this.previousPage;
    }

    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMinimumPage() {
        return this.minimumPage;
    }

    public void setMinimumPage(int minimumPage) {
        this.minimumPage = minimumPage;
    }

    public int getMaximumPage() {
        return this.maximumPage;
    }

    public void setMaximumPage(int maximumPage) {
        this.maximumPage = maximumPage;
    }

    public boolean isSpecialPage() {
        return this.specialPage;
    }

    public void setSpecialPage(boolean specialPage) {
        this.specialPage = specialPage;
    }

    public void setPreviousPageToCurrentPage() {
        this.previousPage = this.currentPage;
    }

    public void reset() {
        this.currentPage = 0;
        this.previousPage = 0;
        this.minimumPage = 0;
        this.maximumPage = 0;
        this.specialPage = false;
    }
}

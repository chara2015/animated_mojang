package net.labymod.api.client.options;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/options/MinecraftOptions.class */
public interface MinecraftOptions {
    public static final int INVALID_SIMULATION_DISTANCE = -1;

    int getFrameLimit();

    int getChatBackgroundColor();

    void setModelParts(int i);

    int getModelParts();

    int getBackgroundColorWithOpacity(int i);

    ChatVisibility chatVisibility();

    MainHand mainHand();

    void setMainHand(MainHand mainHand);

    void sendOptionsToServer();

    float getChatWidth();

    float getChatHeightOpen();

    float getChatHeightClosed();

    double getChatScale();

    double getChatTextOpacity();

    double getTextBackgroundOpacity();

    double getChatLineSpacing();

    boolean isChatColorsEnabled();

    boolean isChatLinksEnabled();

    boolean isChatLinkConfirmationEnabled();

    boolean isSmoothCamera();

    void setSmoothCamera(boolean z);

    String getCurrentLanguage();

    String getLastKnownServer();

    void setLastKnownServer(String str);

    Perspective perspective();

    boolean isEyeProtectionActive();

    void setEyeProtectionActive(boolean z);

    MinecraftInputMapping getInputMapping(String str);

    MinecraftInputMapping attackInput();

    MinecraftInputMapping useItemInput();

    MinecraftInputMapping sprintInput();

    MinecraftInputMapping sneakInput();

    boolean isFullscreen();

    void setFullscreen(boolean z);

    void setPerspective(Perspective perspective);

    boolean isBobbing();

    void setBobbing(boolean z);

    double getFov();

    double getFovEffectScale();

    boolean isHideGUI();

    boolean isDebugEnabled();

    void save();

    AttackIndicatorPosition attackIndicatorPosition();

    boolean isBackgroundForChatOnly();

    int getRenderDistance();

    int getActualRenderDistance();

    boolean isVSyncEnabled();

    default float getChatHeight(boolean open) {
        return open ? getChatHeightOpen() : getChatHeightClosed();
    }

    default boolean isHideSplashTexts() {
        return false;
    }

    default int getSimulationDistance() {
        return -1;
    }
}

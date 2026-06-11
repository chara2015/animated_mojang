package net.labymod.api.client.options;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/options/ChatVisibility.class */
public enum ChatVisibility {
    HIDDEN,
    SHOWN,
    COMMANDS_ONLY;

    public boolean isMessageVisible(ChatVisibility messageVisibility) {
        switch (this) {
            case HIDDEN:
                return false;
            case SHOWN:
                return true;
            case COMMANDS_ONLY:
                return messageVisibility == COMMANDS_ONLY;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
        }
    }
}

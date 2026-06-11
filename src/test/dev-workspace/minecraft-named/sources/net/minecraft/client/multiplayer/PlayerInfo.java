package net.minecraft.client.multiplayer;

import com.mojang.authlib.GameProfile;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.chat.SignedMessageValidator;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.PlayerTeam;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/PlayerInfo.class */
public class PlayerInfo {
    private final GameProfile profile;
    private Supplier<PlayerSkin> skinLookup;
    private int latency;
    private Component tabListDisplayName;
    private RemoteChatSession chatSession;
    private SignedMessageValidator messageValidator;
    private int tabListOrder;
    private GameType gameMode = GameType.DEFAULT_MODE;
    private boolean showHat = true;

    public PlayerInfo(GameProfile $$0, boolean $$1) {
        this.profile = $$0;
        this.messageValidator = fallbackMessageValidator($$1);
    }

    private static Supplier<PlayerSkin> createSkinLookup(GameProfile $$0) {
        Minecraft $$1 = Minecraft.getInstance();
        boolean $$2 = !$$1.isLocalPlayer($$0.id());
        return $$1.getSkinManager().createLookup($$0, $$2);
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public RemoteChatSession getChatSession() {
        return this.chatSession;
    }

    public SignedMessageValidator getMessageValidator() {
        return this.messageValidator;
    }

    public boolean hasVerifiableChat() {
        return this.chatSession != null;
    }

    protected void setChatSession(RemoteChatSession $$0) {
        this.chatSession = $$0;
        this.messageValidator = $$0.createMessageValidator(ProfilePublicKey.EXPIRY_GRACE_PERIOD);
    }

    protected void clearChatSession(boolean $$0) {
        this.chatSession = null;
        this.messageValidator = fallbackMessageValidator($$0);
    }

    private static SignedMessageValidator fallbackMessageValidator(boolean $$0) {
        return $$0 ? SignedMessageValidator.REJECT_ALL : SignedMessageValidator.ACCEPT_UNSIGNED;
    }

    public GameType getGameMode() {
        return this.gameMode;
    }

    protected void setGameMode(GameType $$0) {
        this.gameMode = $$0;
    }

    public int getLatency() {
        return this.latency;
    }

    protected void setLatency(int $$0) {
        this.latency = $$0;
    }

    public PlayerSkin getSkin() {
        if (this.skinLookup == null) {
            this.skinLookup = createSkinLookup(this.profile);
        }
        return this.skinLookup.get();
    }

    public PlayerTeam getTeam() {
        return Minecraft.getInstance().level.getScoreboard().getPlayersTeam(getProfile().name());
    }

    public void setTabListDisplayName(Component $$0) {
        this.tabListDisplayName = $$0;
    }

    public Component getTabListDisplayName() {
        return this.tabListDisplayName;
    }

    public void setShowHat(boolean $$0) {
        this.showHat = $$0;
    }

    public boolean showHat() {
        return this.showHat;
    }

    public void setTabListOrder(int $$0) {
        this.tabListOrder = $$0;
    }

    public int getTabListOrder() {
        return this.tabListOrder;
    }
}

package net.labymod.core.main.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.labymod.user.UserDiscoverEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.models.Implements;
import net.labymod.api.service.ChainedService;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.labyconnect.object.lootbox.LootBoxService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.group.DefaultGroupService;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.item.CosmeticIndexService;
import net.labymod.core.main.user.shop.spray.SprayService;
import net.labymod.core.main.user.shop.spray.model.SprayPacks;
import net.labymod.core.main.user.type.GameUserItemTypeAdapter;
import net.labymod.core.main.user.type.SprayPacksTypeAdapter;
import net.labymod.core.main.user.whitelist.WhitelistService;
import net.labymod.core.main.util.LabyLibHttpClients;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/DefaultGameUserService.class */
@Singleton
@Implements(GameUserService.class)
public class DefaultGameUserService extends ChainedService implements GameUserService {
    private final Gson userGson;
    private final Map<UUID, GameUser> users;
    private final DefaultGroupService groupService;
    private final CosmeticIndexService cosmeticIndexService;
    private final SprayService sprayService;
    private final EmoteService emoteService;
    private final WhitelistService whitelistService;
    private final LootBoxService lootBoxService;
    private final LabyAPI labyAPI;
    private GameUser clientUser;
    private long timeLastCleanup;

    @Inject
    public DefaultGameUserService(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        DefaultReferenceStorage references = LabyMod.references();
        LabyLibHttpClients httpClients = references.labyLibHttpClients();
        this.cosmeticIndexService = (CosmeticIndexService) registerService(new CosmeticIndexService(httpClients, labyAPI.renderPipeline().modelService()));
        this.groupService = (DefaultGroupService) registerService((DefaultGroupService) Laby.references().groupService());
        this.whitelistService = (WhitelistService) registerService(new WhitelistService(httpClients));
        this.emoteService = (EmoteService) registerService(references.emoteService());
        this.sprayService = (SprayService) registerService(references.sprayService());
        this.lootBoxService = (LootBoxService) registerService(references.lootBoxService());
        this.users = new GameUserHashMap();
        this.userGson = new GsonBuilder().registerTypeAdapter(SprayPacks.class, new SprayPacksTypeAdapter()).registerTypeAdapter(TypeToken.getParameterized(List.class, new Type[]{GameUserItem.class}).getType(), new GameUserItemTypeAdapter(this.cosmeticIndexService)).create();
        EventBus eventBus = this.labyAPI.eventBus();
        eventBus.registerListener(this);
        this.timeLastCleanup = TimeUtil.getCurrentTimeMillis();
    }

    public void resolveUserData(UUID uniqueId, boolean asynchronous) {
        try {
            GameUser gameUser = gameUser(uniqueId);
            resolveUserData(gameUser, asynchronous);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void resolveUserData(GameUser gameUser, boolean asynchronous) {
        ((GsonRequest) ((GsonRequest) Request.ofGson(JsonElement.class).url(Constants.Urls.LABYNET_USER_DATA, gameUser.getUniqueId().toString())).async(asynchronous)).execute(element -> {
            if (!(gameUser instanceof DefaultGameUser)) {
                return;
            }
            DefaultGameUser defaultGameUser = (DefaultGameUser) gameUser;
            if (element.hasException()) {
                int responseCode = element.exception().getResponseCode();
                LOGGER.error("User data for {} could not be resolved. (Response Code: {})", gameUser.getUniqueId(), Integer.valueOf(responseCode));
            } else {
                defaultGameUser.updateUserData((JsonElement) element.getOrDefault(new JsonObject()));
            }
        });
    }

    @Override // net.labymod.api.user.GameUserService
    @NotNull
    public GameUser gameUser(@NotNull UUID uniqueId) {
        GameUser gameUser = this.users.get(uniqueId);
        if (gameUser != null && !gameUser.isDisposed()) {
            gameUser.updateTimeLastUsed();
            return gameUser;
        }
        GameUser newGameUser = new DefaultGameUser(uniqueId);
        this.users.put(uniqueId, newGameUser);
        this.whitelistService.fetch(newGameUser, whitelisted -> {
            this.labyAPI.eventBus().fire(new UserDiscoverEvent(newGameUser, whitelisted.booleanValue()));
        });
        return newGameUser;
    }

    @Override // net.labymod.api.user.GameUserService
    @NotNull
    public GameUser clientGameUser() {
        if (this.clientUser == null || this.clientUser.isDisposed()) {
            this.clientUser = gameUser(this.labyAPI.getUniqueId());
        }
        this.clientUser.updateTimeLastUsed();
        return this.clientUser;
    }

    @Override // net.labymod.api.service.ChainedService, net.labymod.api.service.Service
    public void onServiceUnload() {
        clear();
        super.onServiceUnload();
    }

    private void clear() {
        this.users.clear();
        this.whitelistService.reset();
        this.clientUser = null;
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        if (this.clientUser != null) {
            this.clientUser.dispose();
            this.clientUser = null;
        }
        resolveUserData(event.newSession().getUniqueId(), true);
    }

    @Subscribe
    public void onUserLoad(UserDiscoverEvent event) {
        if (event.isWhitelisted()) {
            resolveUserData(event.gameUser(), true);
        }
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() == Phase.POST) {
            checkCleanup();
        }
    }

    private void checkCleanup() {
        long currentTime = TimeUtil.getCurrentTimeMillis();
        long timePassed = currentTime - this.timeLastCleanup;
        if (timePassed < 60000) {
            return;
        }
        ClientPacketListener clientPacketListener = this.labyAPI.minecraft().getClientPacketListener();
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        List<UUID> toRemove = new ArrayList<>();
        for (GameUser user : this.users.values()) {
            if (user.isUnused() && !user.equals(this.clientUser)) {
                UUID id = user.getUniqueId();
                if (clientPacketListener != null) {
                    NetworkPlayerInfo playerInfo = clientPacketListener.getNetworkPlayerInfo(id);
                    if (playerInfo != null) {
                    }
                }
                if (session != null) {
                    Friend friend = session.getFriend(id);
                    if (friend != null) {
                    }
                }
                toRemove.add(id);
            }
        }
        for (UUID uniqueId : toRemove) {
            GameUser user2 = this.users.remove(uniqueId);
            if (user2 != null) {
                user2.dispose();
            }
        }
        this.timeLastCleanup = currentTime;
    }

    @Subscribe
    public void onCapeRender(PlayerCapeRenderEvent event) {
        if (event.phase() == Phase.PRE) {
            GameUser gameUser = event.player().gameUser();
            if (gameUser instanceof DefaultGameUser) {
                DefaultGameUser defaultGameUser = (DefaultGameUser) gameUser;
                if (defaultGameUser.shouldHideCape()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public WhitelistService whitelistService() {
        return this.whitelistService;
    }

    public EmoteService emoteService() {
        return this.emoteService;
    }

    public CosmeticIndexService cosmeticIndexService() {
        return this.cosmeticIndexService;
    }

    public Gson getUserGson() {
        return this.userGson;
    }

    @Override // net.labymod.api.user.GameUserService
    @NotNull
    public Map<UUID, GameUser> getGameUsers() {
        return Collections.unmodifiableMap(this.users);
    }
}

package net.labymod.core.main.user.shop.item.metadata.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.GameUserItemStorage;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.CosmeticIndexService;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.serialization.DefaultItemMetadataSerializer;
import net.labymod.core.main.user.shop.item.metadata.serialization.ItemMetadataSerializer;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/util/ItemMetadataUtil.class */
public final class ItemMetadataUtil {
    private static final Logging LOGGER = Logging.create((Class<?>) ItemMetadataUtil.class);
    private static final ItemMetadataSerializer SERIALIZER = new DefaultItemMetadataSerializer();

    public static void updateGameUser(Cosmetic cosmetic, @Nullable ItemTextureListener listener) {
        LabyConnect labyConnect = Laby.references().labyConnect();
        LabyConnectSession session = labyConnect.getSession();
        if (session == null) {
            return;
        }
        UUID uniqueId = session.self().getUniqueId();
        TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            return;
        }
        updateGameUser(uniqueId, cosmetic, listener);
    }

    public static void updateGameUser(UUID uniqueId, Cosmetic cosmetic, @Nullable ItemTextureListener listener) {
        GameUserService gameUserService = Laby.references().gameUserService();
        GameUser gameUser = gameUserService.gameUser(uniqueId);
        updateGameUser(cosmetic, gameUser, listener);
    }

    public static void updateGameUser(Cosmetic cosmetic, GameUser user, @Nullable ItemTextureListener listener) {
        if (!(user instanceof DefaultGameUser)) {
            return;
        }
        DefaultGameUser defaultGameUser = (DefaultGameUser) user;
        GameUserService gameUserService = Laby.references().gameUserService();
        CosmeticIndexService cosmeticIndexService = ((DefaultGameUserService) gameUserService).cosmeticIndexService();
        GameUserData userData = defaultGameUser.getUserData();
        boolean hasItem = userData.hasItem(cosmetic.getItemId());
        List<GameUserItem> items = userData.getItems();
        if (cosmetic.isEnabled()) {
            if (hasItem) {
                updateMetadata(cosmetic, cosmeticIndexService, defaultGameUser);
                return;
            }
            CosmeticDefinition definition = cosmeticIndexService.getDefinitionById(cosmetic.getItemId());
            if (definition == null) {
                LOGGER.error("No Cosmetic with ID \"{}\" could be found.", Integer.valueOf(cosmetic.getItemId()));
                return;
            }
            CosmeticDetails itemDetails = definition.details();
            ItemMetadata itemMetadata = new ItemMetadata(itemDetails);
            try {
                serialize(itemMetadata, cosmetic.getData());
            } catch (MetadataException exception) {
                LOGGER.error("Item options for \"{}\" could not be serialized because \"{}\"", itemDetails.getId() + "/" + itemDetails.getName(), exception.getMessage());
            }
            items.add(new GameUserItem(definition, itemMetadata));
            items.sort(Comparator.comparingInt((v0) -> {
                return v0.identifier();
            }));
            GameUserItemStorage storage = defaultGameUser.getUserItemStorage();
            storage.prepare(defaultGameUser, userData);
            defaultGameUser.getCosmeticStateStorage().prepare(defaultGameUser, userData);
            return;
        }
        CollectionHelper.removeIf(items, item -> {
            if (item.identifier() == cosmetic.getItemId()) {
                return true;
            }
            return false;
        });
    }

    public static void serialize(ItemMetadata metadata, String[] options) throws MetadataException {
        serialize(metadata, metadata.getDetails(), options);
    }

    public static void serialize(ItemMetadata metadata, CosmeticDetails details, String[] options) throws MetadataException {
        SERIALIZER.serialize(metadata, details, convertOptions(details.getId(), options));
    }

    public static List<Object> deserialize(ItemMetadata metadata) throws MetadataException {
        return SERIALIZER.deserialize(metadata);
    }

    private static void updateMetadata(Cosmetic cosmetic, CosmeticIndexService cosmeticIndexService, DefaultGameUser gameUser) {
        CosmeticDefinition definition;
        GameUserItem userItem = gameUser.getUserData().getItem(cosmetic.getItemId());
        if (userItem == null || (definition = cosmeticIndexService.getDefinitionById(cosmetic.getItemId())) == null) {
            return;
        }
        CosmeticDetails details = definition.details();
        try {
            ItemMetadata itemMetadata = userItem.itemMetadata();
            itemMetadata.getCosmeticTexture();
            itemMetadata.invalidate();
            serialize(itemMetadata, details, cosmetic.getData());
            itemMetadata.getCosmeticTexture();
        } catch (Exception exception) {
            LOGGER.error("Item options for \"{}\" could not be serialized because \"{}\"", details.getId() + "/" + details.getName(), exception.getMessage());
        }
    }

    private static String[] convertOptions(int identifier, String[] options) {
        if (identifier == 36 || identifier == 32) {
            if (options.length < 4) {
                return options;
            }
            String xOption = options[0];
            String yOption = options[1];
            String widthOption = options[2];
            String heightOption = options[3];
            List<String> mappedOptions = new ArrayList<>();
            mappedOptions.add(xOption + ";" + yOption + ";" + widthOption + ";" + heightOption);
            for (int i = 4; i < options.length; i++) {
                mappedOptions.add(options[i]);
            }
            return (String[]) mappedOptions.toArray(new String[0]);
        }
        return options;
    }
}

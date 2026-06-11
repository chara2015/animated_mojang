package net.labymod.core.labyconnect.object.lootbox;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.laby.lib.incentives.Incentive;
import net.laby.lib.incentives.IncentiveService;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.Service;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.core.labyconnect.session.message.IncentiveMessageListener;
import net.labymod.core.main.user.shop.JsonBodyDeserializer;
import net.labymod.core.main.util.LabyLibHttpClients;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/object/lootbox/LootBoxService.class */
@Singleton
@Referenceable
public class LootBoxService extends Service {
    private final IncentiveService incentiveService;
    private final List<LootBoxInventoryItem> inventoryItems = new ArrayList();
    private final Int2ObjectMap<LootBox> lootBoxes = new Int2ObjectOpenHashMap();
    private int amountAvailable = 0;
    private int amountOpened = 0;
    private boolean featureAvailable = false;
    private boolean initialized = false;
    private final LabyAPI api = Laby.labyAPI();

    @Inject
    public LootBoxService(LabyLibHttpClients httpClients) {
        this.incentiveService = new IncentiveService(httpClients.createValidatedHttpClient(JsonBodyDeserializer.defaultGson()));
    }

    @Override // net.labymod.api.service.Service
    protected void prepare() {
        super.prepare();
        this.incentiveService.getIndex((context, result) -> {
            LOGGER.warn("Skipping {}: {}", context, result.errors());
        }).ifSuccess(index -> {
            for (Incentive incentive : index.incentives()) {
                try {
                    registerIncentive(incentive);
                } catch (LootBoxException exception) {
                    LOGGER.error("Failed to register incentive {}", incentive.name(), exception);
                }
            }
            this.initialized = !this.lootBoxes.isEmpty();
            LOGGER.info("Service initialized with {} incentives. ({})", Integer.valueOf(this.lootBoxes.size()), String.join(", ", getLootBoxIds()));
        }).ifFailure(error -> {
            LOGGER.error("Failed to load incentives index: {}", error.message());
        });
    }

    private List<String> getLootBoxIds() {
        List<String> list = new ArrayList<>();
        ObjectIterator it = this.lootBoxes.values().iterator();
        while (it.hasNext()) {
            LootBox entry = (LootBox) it.next();
            list.add(entry.getId());
        }
        return list;
    }

    private void registerIncentive(Incentive incentive) {
        LootBox lootBox = new LootBox(incentive.id(), incentive.name(), this.incentiveService);
        lootBox.loadModel();
        registerLootBox(lootBox);
    }

    public void tick() {
        List<LootBoxInventoryItem> inventoryItems = getInventoryItems();
        for (LootBoxInventoryItem inventoryItem : inventoryItems) {
            LootBox lootBox = inventoryItem.getLootBox();
            if (lootBox != null) {
                lootBox.tick();
            }
        }
    }

    public void load() {
        ObjectIterator it = this.lootBoxes.values().iterator();
        while (it.hasNext()) {
            LootBox lootBox = (LootBox) it.next();
            lootBox.loadModel();
        }
    }

    public void requestIncentive(int typeId) {
        IncentiveMessageListener listener;
        DefaultLabyConnectSession session = (DefaultLabyConnectSession) this.api.labyConnect().getSession();
        if (session != null && (listener = session.getIncentiveListener()) != null) {
            listener.requestIncentive(typeId);
        }
    }

    public void spawnLootBox(UUID playerId, LootBoxContent content, int lootBoxType) {
        if (!Laby.labyAPI().config().ingame().lootBoxes().get().booleanValue()) {
            return;
        }
        Minecraft minecraft = this.api.minecraft();
        MinecraftCamera camera = minecraft.getCamera();
        Optional<Player> optional = minecraft.clientWorld().getPlayer(playerId);
        if (optional.isEmpty() || camera == null) {
            return;
        }
        Player player = optional.get();
        DoubleVector3 position = player.position().toDoubleVector3();
        float yaw = player.getRotationYaw();
        float rotation = MathHelper.toRadiansFloat(yaw + 90.0f);
        float cos = MathHelper.cos(rotation);
        float sin = MathHelper.sin(rotation);
        position.add(cos * 3.0f, 0.0d, sin * 3.0f);
        ClientWorld world = minecraft.clientWorld();
        int y2 = world.getTopBlockYOf(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
        position.setY(y2 + (y2 >= 1 ? 1 : 0));
        if (player == minecraft.getClientPlayer()) {
            Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_LOOTBOX_OPEN, 0.1f, 1.0f, position);
        }
        Laby.references().worldObjectRegistry().register(new LootBoxObject(player, position, yaw, content, lootBoxType));
    }

    public void openLootBox(int type) {
        synchronized (this) {
            this.amountAvailable--;
            this.amountOpened++;
            Iterator<LootBoxInventoryItem> it = this.inventoryItems.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                LootBoxInventoryItem inventoryItem = it.next();
                if (inventoryItem.getType() == type && inventoryItem.isAvailable()) {
                    inventoryItem.setAvailable(false);
                    break;
                }
            }
        }
    }

    public void updateInventory(int amountAvailable, int amountOpened, List<LootBoxInventoryItem> items) {
        synchronized (this) {
            this.amountAvailable = amountAvailable;
            this.amountOpened = amountOpened;
            this.inventoryItems.clear();
            this.inventoryItems.addAll(items);
            this.featureAvailable = true;
            LOGGER.info("Updated inventory with {} items.", Integer.valueOf(this.inventoryItems.size()));
        }
    }

    @Nullable
    public LootBox byId(int id) {
        return (LootBox) this.lootBoxes.get(id);
    }

    public int getAmountAvailable() {
        int i;
        synchronized (this) {
            i = this.amountAvailable;
        }
        return i;
    }

    public int getAmountOpened() {
        int i;
        synchronized (this) {
            i = this.amountOpened;
        }
        return i;
    }

    public List<LootBoxInventoryItem> getInventoryItems() {
        return this.inventoryItems;
    }

    public boolean isFeatureAvailable() {
        return this.initialized && this.featureAvailable;
    }

    private void registerLootBox(LootBox lootBox) {
        this.lootBoxes.put(lootBox.getType(), lootBox);
    }
}

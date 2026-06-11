package net.labymod.core.client.world.rplace;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ConcurrentModificationException;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.client.network.server.ServerLobbyEvent;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.event.client.render.world.RenderBlockSelectionBoxEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.util.KeyValue;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.world.rplace.art.ColoredBlock;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.core.labyconnect.DefaultLabyConnect;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonMessage;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/RPlaceListener.class */
@Singleton
public class RPlaceListener {
    private static final int ART_COLOR = 872415231;
    private final RPlaceRegistry registry = LabyMod.references().rPlaceRegistry();
    private final RenderPipeline legacyPipeline;
    private final RectangleRenderer rectangleRenderer;
    private final GFXRenderPipeline renderPipeline;
    private final Minecraft minecraft;
    private final RenderEnvironmentContext context;
    private final ComponentRenderer componentRenderer;
    private final Window window;
    private final LabyConnect labyConnect;
    private int lastTargetBlockX;
    private int lastTargetBlockY;
    private int lastTargetBlockZ;
    private boolean dirty;
    private long timeLastDirty;
    private long timeLastTargetBlock;
    private long timeLastBlockChanged;
    private long timeLastWrongBlock;
    private boolean isSearchingForBlockInInventory;

    public RPlaceListener() {
        LabyAPI labyAPI = Laby.labyAPI();
        this.legacyPipeline = labyAPI.renderPipeline();
        this.rectangleRenderer = this.legacyPipeline.rectangleRenderer();
        this.renderPipeline = labyAPI.gfxRenderPipeline();
        this.minecraft = labyAPI.minecraft();
        this.context = this.renderPipeline.renderEnvironmentContext();
        this.componentRenderer = this.legacyPipeline.componentRenderer();
        this.window = this.minecraft.minecraftWindow();
        this.labyConnect = labyAPI.labyConnect();
    }

    @Subscribe
    public void onServerLobby(ServerLobbyEvent event) {
        this.registry.setCurrentLobby(event.serverData(), event.getLobbyName());
    }

    @Subscribe
    public void onServerDisconnect(ServerDisconnectEvent event) {
        this.registry.setCurrentLobby(null, null);
        this.registry.clear(true);
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (!this.registry.isEnabled() || event.phase() != Phase.PRE) {
            return;
        }
        try {
            long timePassed = TimeUtil.getMillis() - this.timeLastBlockChanged;
            if (!this.dirty || timePassed < 50) {
                return;
            }
            this.dirty = false;
            ItemStack targetItemStack = RPlaceUtils.getRequiredItemStack(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
            if (targetItemStack != null) {
                RPlaceUtils.selectSlot(targetItemStack);
                this.timeLastBlockChanged = TimeUtil.getMillis();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        try {
            long timePassed = TimeUtil.getMillis() - this.timeLastTargetBlock;
            if (timePassed > 1000) {
                this.isSearchingForBlockInInventory = false;
                return;
            }
            ScreenInstance screen = event.getScreen();
            if (!NamedScreen.CREATIVE_INVENTORY.isScreen(screen)) {
                this.isSearchingForBlockInInventory = false;
                if (screen == null) {
                    this.dirty = true;
                    return;
                }
                return;
            }
            ItemStack targetItemStack = RPlaceUtils.getRequiredItemStack(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
            if (targetItemStack != null) {
                this.isSearchingForBlockInInventory = true;
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent event) {
        if (!this.registry.isEnabled() || event.phase() != Phase.POST) {
            return;
        }
        try {
            if (this.isSearchingForBlockInInventory) {
                ScreenWrapper screen = this.window.currentScreen();
                if (!NamedScreen.CREATIVE_INVENTORY.isScreen(screen)) {
                    this.isSearchingForBlockInInventory = false;
                } else {
                    Bounds bounds = this.window.bounds();
                    event.screenContext().canvas().submitComponent((Component) Component.translatable("labymod.command.command.rplaceoverlay.pickHint", NamedTextColor.GREEN), bounds.getCenterX(), bounds.getBottom() - 15.0f, -1, 1.0f, 3);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }

    @Subscribe
    public void onRenderBlockSelectionBox(RenderBlockSelectionBoxEvent event) {
        if (!this.registry.isEnabled()) {
            return;
        }
        this.timeLastTargetBlock = TimeUtil.getMillis();
        if (this.lastTargetBlockX == event.getX() && this.lastTargetBlockY == event.getY() && this.lastTargetBlockZ == event.getZ()) {
            return;
        }
        this.lastTargetBlockX = event.getX();
        this.lastTargetBlockY = event.getY();
        this.lastTargetBlockZ = event.getZ();
        this.dirty = true;
        this.timeLastDirty = TimeUtil.getMillis();
    }

    @Subscribe
    public void renderWorld(RenderWorldEvent event) {
        if (event.phase() != Phase.POST || !this.registry.isEnabled()) {
            return;
        }
        try {
            Stack stack = event.stack();
            MinecraftCamera camera = event.camera();
            float partialTicks = event.getPartialTicks();
            for (KeyValue<PixelArt> entry : this.registry.getElements()) {
                PixelArt art = entry.getValue();
                if (art.getResourceLocation() != null) {
                    renderPixelArt(stack, camera, partialTicks, art);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            if (!(t instanceof ConcurrentModificationException)) {
                this.registry.disableOnError();
            }
        }
    }

    private void renderPixelArt(Stack stack, MinecraftCamera camera, float partialTicks, PixelArt art) {
        int iMin;
        BlockState blockState;
        ClientPlayer clientPlayer = this.minecraft.getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        ClientWorld world = this.minecraft.clientWorld();
        ItemStackRenderer itemStackRenderer = this.minecraft.itemStackRenderer();
        int originX = art.getToX();
        int originY = art.getY() + 1;
        int originZ = art.getToZ();
        float yaw = -MathHelper.lerp(clientPlayer.getRotationYaw(), clientPlayer.getPreviousRotationYaw(), partialTicks);
        stack.push();
        int prevLighting = this.context.getPackedLight();
        this.context.setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
        DoubleVector3 cameraPosition = camera.position();
        stack.translate(-cameraPosition.getX(), -cameraPosition.getY(), -cameraPosition.getZ());
        boolean far = cameraPosition.getY() > ((double) (this.registry.getMapY() + 24));
        float zFightOffset = far ? 0.1f : 0.02f;
        stack.push();
        stack.translate(originX, originY + zFightOffset, originZ);
        stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
        ((ResourceRenderer) ((ResourceRenderer) ((ResourceRenderer) Laby.references().resourceRenderer().texture(art.getResourceLocation()).pos(0.0f, 0.0f)).size(art.getWidth(), art.getHeight())).sprite(0.0f, 0.0f, 256.0f).resolution(256.0f, 256.0f).color(ART_COLOR)).render(stack);
        stack.pop();
        long timeFadeOut = TimeUtil.getMillis() - this.timeLastTargetBlock;
        boolean isTagVisible = timeFadeOut < 1000;
        float rot = (TimeUtil.getMillis() % 360000) / 10.0f;
        ColoredBlock targetBlock = null;
        boolean isCorrectItemInHand = false;
        int phase = 0;
        while (phase < 2) {
            BatchRectangleRenderer batch = null;
            if (phase == 0) {
                batch = this.rectangleRenderer.beginBatch(stack);
            }
            Position position = clientPlayer.position();
            Position prevPosition = clientPlayer.previousPosition();
            double targetX = MathHelper.lerp(position.getX(), prevPosition.getX(), partialTicks);
            double targetY = MathHelper.lerp(position.getY(), prevPosition.getY(), partialTicks);
            double targetZ = MathHelper.lerp(position.getZ(), prevPosition.getZ(), partialTicks);
            if (phase == 1) {
                targetX += Math.sin(Math.toRadians(yaw)) * 2.0d;
                targetZ += Math.cos(Math.toRadians(yaw)) * 2.0d;
            }
            int radius = phase == 1 ? 4 : 40;
            for (int x = 0; x < art.getWidth(); x++) {
                for (int y = 0; y < art.getHeight(); y++) {
                    int blockPosX = originX + x;
                    int blockPosZ = originZ + y;
                    double distanceToCamera = Math.pow(((double) blockPosX) - targetX, 2.0d) + Math.pow(((double) originY) - targetY, 2.0d) + Math.pow(((double) blockPosZ) - targetZ, 2.0d);
                    if (distanceToCamera <= radius * radius) {
                        float progress = (float) Math.clamp(1.0d - (distanceToCamera / ((double) (radius * radius))), 0.0d, 1.0d);
                        if (progress > 0.0f) {
                            float scale = 0.027f * progress;
                            ColoredBlock block = art.getBlockAt(x, y);
                            if (block != null && ((blockState = world.getBlockState(blockPosX, originY - 1, blockPosZ)) == null || !RPlaceUtils.equalsItem(blockState.block(), block.getItemStack()))) {
                                boolean isTargetBlock = this.lastTargetBlockX == blockPosX && this.lastTargetBlockZ == blockPosZ;
                                if (isTargetBlock) {
                                    targetBlock = block;
                                    Inventory inventory = clientPlayer.inventory();
                                    ItemStack itemInHand = inventory.itemStackAt(inventory.getSelectedIndex());
                                    isCorrectItemInHand = RPlaceUtils.equalsItem(itemInHand, targetBlock.getItemStack());
                                }
                                stack.push();
                                stack.translate(blockPosX, originY, blockPosZ);
                                if (phase == 0) {
                                    boolean isGreen = isTargetBlock && isCorrectItemInHand && isTagVisible;
                                    int borderColor = (isGreen ? 65280 : 16711680) | (Math.min((int) (progress * 255.0f), 255) << 24);
                                    stack.push();
                                    stack.translate(0.0f, zFightOffset + 0.01f, 0.0f);
                                    stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                                    ((BatchRectangleRenderer) batch.pos(0.2f, 0.2f, 1.0f - 0.2f, 1.0f - 0.2f).color(0)).outline(borderColor & GlConst.GL_ALL_ATTRIB_BITS, borderColor, 0.2f).build();
                                    stack.pop();
                                }
                                if (phase == 1) {
                                    stack.translate(0.5d, 0.25d, 0.5d);
                                    stack.rotate(rot, 0.0f, 1.0f, 0.0f);
                                    stack.scale(scale, -scale, scale);
                                    stack.translate(-8.0f, -8.0f, -150.0f);
                                    itemStackRenderer.renderItemStack(stack, block.getItemStack(), 0, 0);
                                }
                                stack.pop();
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                batch.upload();
            }
            phase++;
        }
        if (targetBlock != null && isTagVisible) {
            stack.push();
            stack.translate(((double) this.lastTargetBlockX) + 0.5d, ((double) originY) + 0.25d, ((double) this.lastTargetBlockZ) + 0.5d);
            Component displayName = targetBlock.getItemStack().getDisplayName();
            RenderableComponent renderableComponent = RenderableComponent.of(displayName);
            long timeFadeIn = TimeUtil.getMillis() - this.timeLastDirty;
            double progress2 = CubicBezier.BOUNCE.curve(Math.clamp(timeFadeIn / 250.0d, 0.0d, 1.0d)) * Math.clamp(3.0d - (timeFadeOut / 200.0d), 0.0d, 1.0d);
            int opacity = (int) Math.min(255.0d, progress2 * 255.0d);
            float tagScale = (float) (0.029999999329447746d * progress2);
            float width = renderableComponent.getWidth();
            float height = renderableComponent.getHeight();
            long timePassedCheck = TimeUtil.getMillis() - this.timeLastWrongBlock;
            double progressCheck = CubicBezier.BOUNCE.curve(Math.clamp(timePassedCheck / 250.0d, 0.0d, 1.0d));
            stack.rotate(180.0f - camera.getYaw(), 0.0f, 1.0f, 0.0f);
            stack.translate(0.0f, 0.5f, 0.0f);
            stack.rotate(-camera.getPitch(), 1.0f, 0.0f, 0.0f);
            stack.scale(tagScale, -tagScale, tagScale);
            RectangleRenderer rectangleRenderer = this.rectangleRenderer;
            float f = (float) (((double) ((-width) / 2.0f)) - progressCheck);
            float f2 = (float) (((double) ((-height) / 2.0f)) - progressCheck);
            float f3 = (float) (((double) (width / 2.0f)) + progressCheck);
            float f4 = (float) (((double) (height / 2.0f)) + progressCheck);
            if (isCorrectItemInHand) {
                iMin = 65280 | (Math.min((int) (progressCheck * 80.0d), 80) << 24);
            } else {
                iMin = 16777215 | (Math.min(opacity, 80) << 24);
            }
            rectangleRenderer.renderRectangle(stack, f, f2, f3, f4, iMin);
            stack.translate(0.0d, 0.0d, 0.01d);
            this.rectangleRenderer.renderRectangle(stack, ((-width) / 2.0f) - 0.5f, ((-height) / 2.0f) - 0.5f, (width / 2.0f) + 0.5f, (height / 2.0f) + 0.5f, 2105376 | (Math.min(opacity, 128) << 24));
            stack.translate(0.0d, 0.0d, 0.01d);
            this.componentRenderer.builder().text(renderableComponent).pos((-width) / 2.0f, (-height) / 2.0f).color(16777215 | (opacity << 24)).drawMode(TextDrawMode.POLYGON_OFFSET).shadow(true).render(stack);
            if (isCorrectItemInHand) {
                float checkScale = (float) (8.0d * progressCheck);
                Textures.SpriteCommon.GREEN_CHECKED.render(stack, (width / 2.0f) - (checkScale / 2.0f), ((-checkScale) / 2.0f) + (height / 2.0f), checkScale);
            } else {
                this.timeLastWrongBlock = TimeUtil.getMillis();
            }
            stack.pop();
        }
        this.context.setPackedLight(prevLighting);
        stack.pop();
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent event) {
        ClientPlayer clientPlayer;
        String string;
        PixelArt art;
        if (!this.registry.isEnabled()) {
            return;
        }
        try {
            ClientWorld world = this.minecraft.clientWorld();
            if (world == null || (clientPlayer = this.minecraft.getClientPlayer()) == null) {
                return;
            }
            if (event.type() == ClientPlayerInteractEvent.InteractionType.PICK_BLOCK && (art = RPlaceUtils.getPixelArtAt(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ)) != null) {
                ItemStack expectedItem = RPlaceUtils.getRequiredItemStack(art, this.lastTargetBlockX, this.lastTargetBlockZ);
                event.setCancelled(true);
                if (expectedItem != null) {
                    RPlaceUtils.pickItem(expectedItem);
                }
            }
            if (event.type() == ClientPlayerInteractEvent.InteractionType.INTERACT || event.type() == ClientPlayerInteractEvent.InteractionType.ATTACK) {
                LabyConnectSession session = this.labyConnect.getSession();
                if (session == null) {
                    return;
                }
                long timePassed = TimeUtil.getMillis() - this.timeLastTargetBlock;
                if (this.timeLastDirty == 0 || timePassed > 200) {
                    return;
                }
                ItemStack itemInHand = clientPlayer.inventory().itemStackAt(clientPlayer.inventory().getSelectedIndex());
                if (itemInHand.isAir()) {
                    return;
                }
                Block currentBlock = world.getBlockState(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ).block();
                JsonObject payload = new JsonObject();
                payload.addProperty("action", "place");
                payload.addProperty("x", Integer.valueOf(this.lastTargetBlockX));
                payload.addProperty("z", Integer.valueOf(this.lastTargetBlockZ));
                payload.addProperty("from", currentBlock.id().toString());
                payload.addProperty("to", itemInHand.getIdentifier().toString());
                PixelArt art2 = RPlaceUtils.getPixelArtAt(this.lastTargetBlockX, this.lastTargetBlockY, this.lastTargetBlockZ);
                if (art2 != null) {
                    ItemStack expectedItem2 = RPlaceUtils.getRequiredItemStack(art2, this.lastTargetBlockX, this.lastTargetBlockZ);
                    if (expectedItem2 == null) {
                        string = null;
                    } else {
                        string = expectedItem2.getIdentifier().toString();
                    }
                    String expectedIdentifier = string;
                    JsonObject overlay = new JsonObject();
                    overlay.addProperty("expected", expectedIdentifier);
                    overlay.addProperty(MappingNamespace.MINECRAFT_OBFUSCATED, Boolean.valueOf(art2.isLabyConnect()));
                    payload.add("overlay", overlay);
                }
                if (!this.registry.isOnTestServer()) {
                    ((DefaultLabyConnect) this.labyConnect).sendPacket(new PacketAddonMessage("rplace", (JsonElement) payload));
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

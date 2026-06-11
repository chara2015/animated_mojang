package net.labymod.core.test.overlay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/overlay/StreamOverlayTestActivity.class */
@AutoActivity
public class StreamOverlayTestActivity extends TestActivity {
    private static StreamOverlay overlay;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (overlay == null) {
            EventBus eventBus = Laby.labyAPI().eventBus();
            StreamOverlay streamOverlay = new StreamOverlay();
            overlay = streamOverlay;
            eventBus.registerListener(streamOverlay);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        float centerX = bounds().getCenterX();
        float centerY = bounds().getCenterY();
        ScreenCanvas canvas = context.canvas();
        if (overlay == null) {
            canvas.submitRenderableComponent(RenderableComponent.of("Stream Overlay not enabled"), centerX, centerY, -1, 3);
        } else {
            canvas.submitRenderableComponent(RenderableComponent.of(overlay.getState()), centerX, centerY / 2.0f, -1, 3);
            overlay.renderStream(context, centerX - 100.0f, (centerY / 2.0f) + 10.0f, 200.0f, 112.5f);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/overlay/StreamOverlayTestActivity$StreamOverlay.class */
    public static class StreamOverlay {
        private static final int MAX_FRAME_BYTES = 16777216;
        private static final int MAX_DECOMPRESSED_BYTES = 67108864;
        private final ResourceLocation resourceLocation;
        private ServerSocket serverSocket;
        private SimpleTexture gameImageTexture;
        private float modifierWindowX;
        private float modifierWindowY;
        private byte[] frame = new byte[0];
        private String state = "Initializing...";
        private final Map<Integer, Float> modifiers = new HashMap();
        private int hoverModifierIndex = -1;
        private int draggingModifierIndex = -1;
        private long timeLastRender = TimeUtil.getMillis();

        public StreamOverlay() {
            try {
                this.serverSocket = new ServerSocket(8085, 50, InetAddress.getLoopbackAddress());
                LabyExecutors.newSingleThreadExecutor("StreamOverlay#%d").execute(() -> {
                    while (!this.serverSocket.isClosed()) {
                        try {
                            try {
                                this.state = "Waiting for connection...";
                                Socket socket = this.serverSocket.accept();
                                connection(socket);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            this.state = "Closed: " + e2.getMessage();
                            return;
                        }
                    }
                    this.state = "Closed";
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.resourceLocation = Laby.references().resourceLocationFactory().create("labymod", "test");
        }

        private void connection(Socket socket) throws IOException {
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.state = "Waiting for image...";
            while (!socket.isClosed()) {
                handle(dataInputStream);
            }
        }

        private void handle(DataInputStream dataInputStream) throws IOException {
            int length = dataInputStream.readInt();
            if (length < 0 || length > 16777216) {
                throw new IOException("Frame length " + length + " out of bounds [0, 16777216]");
            }
            this.state = "Streaming...";
            byte[] buffer = new byte[length];
            dataInputStream.readFully(buffer);
            byte[] frame = decompress(buffer);
            if (frame == null) {
                return;
            }
            synchronized (this) {
                this.frame = frame;
            }
            toResourceLocation(frame);
        }

        @Subscribe
        public void onRender(IngameOverlayRenderEvent event) {
            if (event.phase() != Phase.POST) {
                return;
            }
            event.stack();
            Bounds bounds = Laby.labyAPI().minecraft().minecraftWindow().bounds();
            float width = bounds.getWidth();
            float height = bounds.getHeight();
            ScreenContext context = event.context();
            renderStreamOverlay(context, 0.0f, 0.0f, width, height);
            renderModifiers(context, this.modifierWindowX, this.modifierWindowY, 200.0f, height);
        }

        @Subscribe
        public void onMouseClick(MouseButtonEvent event) {
            MouseButtonEvent.Action action = event.action();
            if (action == MouseButtonEvent.Action.CLICK) {
                if (event.button() == MouseButton.LEFT) {
                    if (this.hoverModifierIndex == -2) {
                        this.modifiers.put(Integer.valueOf(this.modifiers.size()), Float.valueOf(0.0f));
                    } else if (this.hoverModifierIndex >= 0) {
                        this.draggingModifierIndex = this.hoverModifierIndex;
                    }
                }
                if (event.button() == MouseButton.RIGHT) {
                    if (this.hoverModifierIndex == -2) {
                        this.modifiers.clear();
                    } else if (this.hoverModifierIndex >= 0) {
                        this.modifiers.put(Integer.valueOf(this.hoverModifierIndex), Float.valueOf(0.0f));
                    }
                }
                if (event.button() == MouseButton.MIDDLE) {
                    this.draggingModifierIndex = -3;
                    if (KeyHandler.isControlDown()) {
                        System.out.println("stack.translate(" + getDisplay(0) + "F * UNIT, " + getDisplay(1) + "F * UNIT, " + getDisplay(2) + "F * UNIT);");
                        System.out.println("stack.rotate(" + getDisplay(3) + "F, 1, 0, 0);\nstack.rotate(" + getDisplay(4) + "F, 0, 1, 0);\nstack.rotate(" + getDisplay(5) + "F, 0, 0, 1);");
                    }
                }
            }
            if (action == MouseButtonEvent.Action.RELEASE) {
                this.draggingModifierIndex = -1;
            }
        }

        private void renderModifiers(ScreenContext context, float x, float y, float width, float height) {
            ScreenCanvas canvas = context.canvas();
            MutableMouse mouse = context.mouse();
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (this.draggingModifierIndex == -3) {
                this.modifierWindowX += (mouseX - this.modifierWindowX) - (width / 2.0f);
                this.modifierWindowY += (mouseY - this.modifierWindowY) - 20.0f;
            }
            this.hoverModifierIndex = -1;
            int minIndex = Integer.MAX_VALUE;
            int maxIndex = Integer.MIN_VALUE;
            for (Map.Entry<Integer, Float> entry : this.modifiers.entrySet()) {
                minIndex = Math.min(minIndex, entry.getKey().intValue());
                maxIndex = Math.max(maxIndex, entry.getKey().intValue());
            }
            for (int index = minIndex; index <= maxIndex; index++) {
                Float modifier = this.modifiers.get(Integer.valueOf(index));
                if (modifier != null) {
                    canvas.submitAbsoluteRect(x + 1.0f, y + 1.0f, x + width, y + 8.0f, Integer.MIN_VALUE);
                    float progress = (modifier.floatValue() - (-8)) / (8 - (-8));
                    boolean hover = mouse.isInside(x + 1.0f, y + 1.0f, width - 2.0f, 8.0d);
                    if (hover) {
                        this.hoverModifierIndex = index;
                    }
                    if (this.draggingModifierIndex == index) {
                        float progress2 = ((mouseX - x) - 1.0f) / (width - 2.0f);
                        progress = Math.max(0.0f, Math.min(1.0f, progress2));
                        this.modifiers.put(Integer.valueOf(this.draggingModifierIndex), Float.valueOf((-8) + (progress * (8 - (-8)))));
                    }
                    canvas.submitAbsoluteRect(x + 1.0f + (progress * (width - 2.0f)), y + 1.0f, x + 1.0f + (progress * (width - 2.0f)) + 1.0f, y + 8.0f, hover ? Integer.MAX_VALUE : Integer.MIN_VALUE);
                    canvas.submitRenderableComponent(RenderableComponent.of(String.format(Locale.US, "%.2f", modifier)), x + 2.0f + width, y + 2.5f, -1, 0.67f, 0);
                    canvas.submitRenderableComponent(RenderableComponent.of(index + "."), x - 5.0f, y + 2.5f, -1, 0.67f, 0);
                    y += 8.0f;
                }
            }
            boolean hoverAdd = mouse.isInside(x + 1.0f, y + 1.0f, 8.0d, 8.0d);
            if (hoverAdd) {
                this.hoverModifierIndex = -2;
            }
            canvas.submitAbsoluteRect(x + 1.0f, y + 1.0f, x + 8.0f, y + 8.0f, hoverAdd ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            canvas.submitRenderableComponent(RenderableComponent.of("+"), x + 2.0f, y + 1.0f, hoverAdd ? -16777216 : -1, 0);
        }

        private void renderStreamOverlay(ScreenContext context, float x, float y, float width, float height) {
            float opacity = 0.0f;
            ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
            if (clientPlayer != null && !KeyHandler.isShiftDown()) {
                float z = (float) clientPlayer.position().getZ();
                if (z >= 277.0f && z <= 285.0f) {
                    opacity = 1.0f - ((z - 277.0f) / (285.0f - 277.0f));
                } else if (z < 277.0f) {
                    opacity = 1.0f;
                }
                if (opacity <= 0.0f || opacity >= 1.0f) {
                    opacity = 0.5f;
                }
            }
            float opacity2 = Math.min(1.0f, opacity);
            RenderPipeline pipeline = Laby.labyAPI().renderPipeline();
            pipeline.setAlpha(opacity2);
            renderStream(context, x, y, width, height);
            pipeline.setAlpha(1.0f);
        }

        public void renderStream(ScreenContext context, float x, float y, float width, float height) {
            byte[] frame;
            this.timeLastRender = TimeUtil.getMillis();
            synchronized (this) {
                frame = this.frame;
            }
            if (frame == null || frame.length == 0) {
                return;
            }
            try {
                ResourceLocation resourceLocation = this.resourceLocation;
                if (resourceLocation == null) {
                    return;
                }
                ScreenCanvas canvas = context.canvas();
                canvas.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, resourceLocation), x, y, width, height, UVCoordinates.of(0.0f, 0.0f, width, height, width, height), -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private ResourceLocation toResourceLocation(byte[] buffer) {
            int width = ((buffer[0] & 255) << 24) | ((buffer[1] & 255) << 16) | ((buffer[2] & 255) << 8) | (buffer[3] & 255);
            int height = ((buffer[4] & 255) << 24) | ((buffer[5] & 255) << 16) | ((buffer[6] & 255) << 8) | (buffer[7] & 255);
            if (width <= 0 || height <= 0) {
                return null;
            }
            TextureRepository repository = Laby.references().textureRepository();
            GameImageProvider provider = Laby.references().gameImageProvider();
            if (this.gameImageTexture == null || this.gameImageTexture.getImage().getWidth() != width || this.gameImageTexture.getImage().getHeight() != height) {
                GameImage gameImage = provider.createImage(width, height);
                this.gameImageTexture = SimpleTexture.simple(this.resourceLocation, gameImage);
            }
            boolean swap = MinecraftVersions.V1_12_2.orOlder();
            int a = swap ? 16 : 0;
            int b = swap ? 0 : 16;
            GameImage image = this.gameImageTexture.getImage();
            for (int index = 0; index < width * height; index++) {
                image.setARGB(index % width, index / width, (-16777216) | ((buffer[8 + (index * 3)] & 255) << a) | ((buffer[(8 + (index * 3)) + 1] & 255) << 8) | ((buffer[(8 + (index * 3)) + 2] & 255) << b));
            }
            long timeSinceLastRender = TimeUtil.getMillis() - this.timeLastRender;
            if (timeSinceLastRender < 1000) {
                repository.register(this.resourceLocation, this.gameImageTexture);
            }
            return this.resourceLocation;
        }

        public static byte[] decompress(byte[] input) {
            byte[] buffer;
            ByteArrayOutputStream out;
            int total;
            GZIPInputStream gis;
            try {
                buffer = new byte[8192];
                out = new ByteArrayOutputStream();
                total = 0;
                gis = new GZIPInputStream(new ByteArrayInputStream(input));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            while (true) {
                try {
                    int len = gis.read(buffer);
                    if (len > 0) {
                        total += len;
                        if (total > MAX_DECOMPRESSED_BYTES) {
                            throw new IOException("Decompressed output exceeded allowed size (67108864 bytes)");
                        }
                        out.write(buffer, 0, len);
                    } else {
                        gis.close();
                        return out.toByteArray();
                    }
                } finally {
                }
                e.printStackTrace();
                return null;
            }
        }

        public String getState() {
            return this.state;
        }

        public float getModifierInternal(int index) {
            return this.modifiers.computeIfAbsent(Integer.valueOf(index), i -> {
                return Float.valueOf(0.0f);
            }).floatValue();
        }

        public static float getModifier(int index) {
            if (StreamOverlayTestActivity.overlay == null) {
                return 0.0f;
            }
            return StreamOverlayTestActivity.overlay.getModifierInternal(index);
        }

        public static void applyModifierTranslate(Stack stack) {
            stack.translate(getModifier(0) * 0.0625f, getModifier(1) * 0.0625f, getModifier(2) * 0.0625f);
        }

        public static void applyModifierRotate(Stack stack) {
            stack.rotate(getModifier(3), 1.0f, 0.0f, 0.0f);
            stack.rotate(getModifier(4), 0.0f, 1.0f, 0.0f);
            stack.rotate(getModifier(5), 0.0f, 0.0f, 1.0f);
        }

        private static String getDisplay(int index) {
            return String.format(Locale.US, "%.2f", Float.valueOf(getModifier(index)));
        }
    }
}

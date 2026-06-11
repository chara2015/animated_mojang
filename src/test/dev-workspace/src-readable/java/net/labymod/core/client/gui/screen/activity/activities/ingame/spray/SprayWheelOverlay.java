package net.labymod.core.client.gui.screen.activity.activities.ingame.spray;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.function.Function;
import net.laby.lib.sprays.Spray;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity;
import net.labymod.api.client.gui.screen.activity.util.PageNavigator;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.TimeUnit;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.gui.screen.widget.widgets.spray.SpraySegmentWidget;
import net.labymod.core.client.gui.screen.widget.widgets.spray.SprayWheelWidget;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.spray.SprayRegistry;
import net.labymod.core.main.user.util.SprayCooldownTracker;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/spray/SprayWheelOverlay.class */
@AutoActivity
@Link("activity/overlay/spray-wheel.lss")
public class SprayWheelOverlay extends AbstractWheelInteractionOverlayActivity {
    private static final Function<String, String> TRANSLATION_KEY_FACTORY = s -> {
        return "labymod.activity.sprayWheel." + s;
    };
    private static final String SELECT = TRANSLATION_KEY_FACTORY.apply("select");
    private static final String NEXT_SPRAY = TRANSLATION_KEY_FACTORY.apply("nextSpray");
    private static final String NO_SPRAYS = TRANSLATION_KEY_FACTORY.apply("noSprays");
    private static final String ATTEMPT_TO_SPRAY_ON_ENTITY = TRANSLATION_KEY_FACTORY.apply("attemptToSprayOnEntity");
    private static final String NOT_CONNECTED = TRANSLATION_KEY_FACTORY.apply("notConnected");
    private static final String FULL_BLOCK = TRANSLATION_KEY_FACTORY.apply("fullBlock");
    private static final String COOLDOWN = TRANSLATION_KEY_FACTORY.apply("cooldown");
    private static final Style DEFAULT_STYLE = Style.builder().color(NamedTextColor.RED).build();
    private final ClientWorld clientLevel = Laby.references().clientWorld();
    private boolean sprayed;

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        refreshUserData();
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void openInteractionOverlay() {
        super.openInteractionOverlay();
        if (!isSearchActivityOpen()) {
            Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_SPRAY_CAN_SHAKE, 1.0f, 1.0f);
        }
    }

    private void refreshUserData() {
        SprayWheelWidget.Storage storage = SprayWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        PageNavigator pageNavigator = pageNavigator();
        pageNavigator.setMinimumPage(0);
        int maxPages = MathHelper.ceil(storage.getSprays().size() / getSegmentCount()) - 1;
        pageNavigator.setMaximumPage(maxPages);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity
    protected void renderInteractionOverlay(ScreenContext context) {
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        boolean interactionOpen = isInteractionOpen();
        if (interactionOpen) {
            titleWidget().setComponent(createTitleComponent());
        }
        if (interactionOpen && canSpray() && this.sprayed) {
            refresh(true);
            this.sprayed = false;
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected Component createTitleComponent() {
        if (!hasEntries()) {
            return Component.translatable(NO_SPRAYS, new Component[0]);
        }
        if (canSpray()) {
            return Component.translatable(SELECT, new Component[0]);
        }
        return createNextSprayComponent();
    }

    private Component createNextSprayComponent() {
        return Component.translatable(NEXT_SPRAY, Component.text(TimeUnit.parseToString(sprayCooldownTracker().getClientDuration()), NamedTextColor.YELLOW));
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected boolean hasEntries() {
        return !SprayWheelWidget.Storage.INSTANCE.getSprays().isEmpty();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected WheelWidget createWheelWidget() {
        SprayWheelWidget wheel = new SprayWheelWidget(() -> {
            return pageNavigator().getCurrentPage();
        }, () -> {
            return this.getSegmentCount();
        });
        wheel.querySupplier(() -> {
            CharSequence searchText = getSearchText();
            if (CharSequences.isEmpty(searchText)) {
                return null;
            }
            return searchText;
        });
        wheel.segmentSupplier((index, wheelIndex, spray) -> {
            boolean canUseSpray = canSpray();
            if (spray == null) {
                WheelWidget.Segment segment = new WheelWidget.Segment();
                segment.setSelectable(false);
                return segment;
            }
            SpraySegmentWidget segment2 = new SpraySegmentWidget(spray, canUseSpray);
            segment2.addId("spray-wrapper");
            segment2.setSelectable(true);
            return segment2;
        });
        return wheel;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected Key getKeyToOpen() {
        return this.labyAPI.config().hotkeys().sprayWheelKey().get();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void onInitializeMappedKeys(Object2IntMap<Key> mappedKeys) {
        mappedKeys.put(Key.NUM1, 0);
        mappedKeys.put(Key.NUM2, 1);
        mappedKeys.put(Key.NUM3, 2);
        mappedKeys.put(Key.NUM4, 3);
        mappedKeys.put(Key.NUM5, 4);
        mappedKeys.put(Key.NUM6, 5);
        mappedKeys.put(Key.NUMPAD1, 0);
        mappedKeys.put(Key.NUMPAD2, 1);
        mappedKeys.put(Key.NUMPAD3, 2);
        mappedKeys.put(Key.NUMPAD4, 3);
        mappedKeys.put(Key.NUMPAD5, 4);
        mappedKeys.put(Key.NUMPAD6, 5);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void onKey(Key key, KeyEvent.State state) {
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected boolean shouldOpenInteractionMenu() {
        return this.labyAPI.config().ingame().spray().enabled().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void closeInteractionOverlay() {
        spray(null, false);
        super.closeInteractionOverlay();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isSearchActivityOpen()) {
            spray(null, true);
            closeScreen();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private void spray(Spray forcedSpray, boolean closeMenu) {
        Spray spray = forcedSpray;
        if (spray == null) {
            spray = findSelectedSpray();
        }
        spray(spray);
        if (closeMenu) {
            closeInteraction();
        }
    }

    @Nullable
    private Spray findSelectedSpray() {
        for (AbstractWidget<?> child : wheelWidget().getChildren()) {
            if (child instanceof SpraySegmentWidget) {
                SpraySegmentWidget segmentWidget = (SpraySegmentWidget) child;
                if (segmentWidget.isSelectable() && segmentWidget.isSegmentSelected()) {
                    return segmentWidget.getSpray();
                }
            }
        }
        return null;
    }

    private void spray(Spray spray) {
        float rotation;
        if (spray == null || !canSpray()) {
            return;
        }
        HitResult result = Laby.labyAPI().minecraft().getHitResult();
        if (result == null) {
            LOGGER.error("result returned null, this shouldn't happen!", new Object[0]);
            return;
        }
        HitResult.HitType type = result.type();
        if (type == HitResult.HitType.ENTITY) {
            displayClientMessage(Component.translatable(ATTEMPT_TO_SPRAY_ON_ENTITY, DEFAULT_STYLE));
            return;
        }
        if (type == HitResult.HitType.MISS) {
            displayClientMessage(Component.translatable(FULL_BLOCK, DEFAULT_STYLE));
            return;
        }
        BlockHitResult blockHitResult = (BlockHitResult) result;
        BlockState blockState = this.clientLevel.getBlockState(blockHitResult.getBlockPosition());
        boolean invisible = false;
        if (blockState.renderShape() == RenderShape.INVISIBLE) {
            invisible = true;
        } else {
            AxisAlignedBoundingBox bounds = blockState.bounds();
            if (!MathHelper.isBox(bounds)) {
                invisible = true;
            }
        }
        if (invisible) {
            displayClientMessage(Component.translatable(FULL_BLOCK, DEFAULT_STYLE));
            return;
        }
        MinecraftCamera camera = this.labyAPI.minecraft().getCamera();
        Direction blockDirection = blockHitResult.getBlockDirection();
        FloatVector3 location = result.location();
        if (blockDirection == Direction.UP) {
            rotation = 180.0f - camera.getYaw();
        } else if (blockDirection == Direction.DOWN) {
            rotation = camera.getYaw();
        } else {
            rotation = calculateDegrees(camera.renderPosition(), location, blockDirection);
        }
        performSpray(spray, location, blockDirection, rotation);
    }

    private void performSpray(Spray spray, FloatVector3 location, Direction blockDirection, float rotation) {
        SprayRegistry sprayRegistry = LabyMod.references().sprayRegistry();
        SprayRegistry.SprayState state = sprayRegistry.sprayClient((short) spray.id(), location.getX(), location.getY(), location.getZ(), blockDirection, rotation);
        if (state == SprayRegistry.SprayState.SUCCESS) {
            spray();
        } else if (state == SprayRegistry.SprayState.SPRAY_COOLDOWN) {
            displayClientMessage(Component.translatable(COOLDOWN, DEFAULT_STYLE).argument(Component.text(Long.valueOf(sprayCooldownTracker().getClientDuration() / 1000), NamedTextColor.YELLOW)));
        } else {
            displayClientMessage(Component.translatable(NOT_CONNECTED, DEFAULT_STYLE));
        }
    }

    private void displayClientMessage(Component component) {
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
    }

    private float calculateDegrees(DoubleVector3 source, FloatVector3 destination, Direction direction) {
        float degrees = 0.0f;
        if (direction == Direction.WEST) {
            float degrees2 = (float) Math.toDegrees(Math.atan2(((double) destination.getZ()) - source.getZ(), ((double) destination.getX()) - source.getX()));
            degrees = -degrees2;
        } else if (direction == Direction.EAST) {
            float degrees3 = (float) Math.toDegrees(Math.atan2(((double) destination.getZ()) - source.getZ(), ((double) destination.getX()) - source.getX()));
            degrees = 180.0f - degrees3;
        } else if (direction == Direction.NORTH) {
            float degrees4 = (float) Math.toDegrees(-Math.atan2(((double) destination.getX()) - source.getX(), ((double) destination.getZ()) - source.getZ()));
            degrees = -degrees4;
        } else if (direction == Direction.SOUTH) {
            float degrees5 = (float) Math.toDegrees(-Math.atan2(((double) destination.getX()) - source.getX(), ((double) destination.getZ()) - source.getZ()));
            degrees = 180.0f - degrees5;
        }
        if (degrees >= 180.0f) {
            degrees -= 360.0f;
        }
        if (degrees <= -180.0f) {
            degrees += 360.0f;
        }
        if (degrees >= -90.0f && degrees <= 90.0f) {
            boolean negative = degrees < 0.0f;
            double value = ((double) Math.abs(degrees)) / 90.0d;
            double curve = new CubicBezier(0.9d, 0.2d, 0.9d, 0.2d).curve(value);
            degrees = ((float) (curve * 90.0d)) * (negative ? -1 : 1);
        }
        return degrees;
    }

    private SprayCooldownTracker sprayCooldownTracker() {
        DefaultGameUser user = (DefaultGameUser) Laby.references().gameUserService().clientGameUser();
        return user.sprayCooldownTracker();
    }

    private void spray() {
        sprayCooldownTracker().sprayClient();
        this.sprayed = true;
    }

    private boolean canSpray() {
        return sprayCooldownTracker().canSprayClient();
    }
}

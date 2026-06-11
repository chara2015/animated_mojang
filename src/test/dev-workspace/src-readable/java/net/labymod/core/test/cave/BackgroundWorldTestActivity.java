package net.labymod.core.test.cave;

import java.io.IOException;
import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.title.MainMenuWidget;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.main.LabyMod;
import net.labymod.core.test.TestActivity;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/cave/BackgroundWorldTestActivity.class */
@Link("activity/main-menu.lss")
@AutoActivity
public class BackgroundWorldTestActivity extends TestActivity {
    private MainMenuWidget mainMenuWidget;
    private DynamicBackgroundController world;
    private SchematicRenderer schematicRenderer;
    private CinematicCamera camera;
    private float prevMouseX;
    private float prevMouseY;
    private double prevX;
    private double prevY;
    private double prevZ;
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private float moveForward;
    private float moveStrafing;
    private boolean jumping;
    private boolean sprinting;
    private boolean sneaking;
    private boolean freeCam = false;
    private final float flySpeed = 0.05f;
    private float jumpMovementFactor = 0.02f;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DefaultReferenceStorage references = LabyMod.references();
        AbstractBootLogoRenderer logo = references.bootLogoController().renderer();
        DynamicBackgroundController world = references.dynamicBackgroundController();
        boolean fadeIn = this.labyAPI.config().appearance().fadeOutAnimation().get() == FadeOutAnimationType.FADING;
        this.mainMenuWidget = new MainMenuWidget(logo, world, fadeIn);
        this.mainMenuWidget.addId("main-menu");
        ((Document) this.document).addChild(this.mainMenuWidget);
        this.world = this.mainMenuWidget.world();
        this.schematicRenderer = world.getSchematicRenderer();
        this.camera = this.schematicRenderer.camera();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.SimpleActivity
    public boolean shouldRenderBackground() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        this.mainMenuWidget.logo().updateProgress(getProgress(), true);
        super.render(context);
        MutableMouse mouse = context.mouse();
        if (this.freeCam) {
            float mouseDeltaX = mouse.getX() - this.prevMouseX;
            float mouseDeltaY = mouse.getY() - this.prevMouseY;
            Location location = this.camera.positionModifier(3);
            float partialTicks = this.labyAPI.minecraft().getPartialTicks();
            double x = this.prevX + ((this.x - this.prevX) * ((double) partialTicks));
            double y = this.prevY + ((this.y - this.prevY) * ((double) partialTicks));
            double z = this.prevZ + ((this.z - this.prevZ) * ((double) partialTicks));
            location.setPosition(x, y, z);
            if (this.prevMouseX != -1.0f && this.prevMouseY != -1.0f) {
                location.addRotation(mouseDeltaX * 0.5f, mouseDeltaY * 0.5f, 0.0d);
            }
            if (location.getPitch() > 90.0d) {
                location.setPitch(90.0d);
            } else if (location.getPitch() < -90.0d) {
                location.setPitch(-90.0d);
            }
            this.prevMouseX = mouse.getX();
            this.prevMouseY = mouse.getY();
            return;
        }
        this.prevMouseX = -1.0f;
        this.prevMouseY = -1.0f;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (!this.world.isOpeningPlayed() && getProgress() >= 1.0f) {
            this.world.playOpening();
        }
        if (!this.world.isOpeningPlayed() || this.camera.getProgress() < 1.0f || ((Document) this.document).getLastInitialTime() + 8000 < TimeUtil.getMillis()) {
        }
        updateMovementInput();
        tickMovement();
    }

    private void updateMovementInput() {
        Minecraft minecraft = this.labyAPI.minecraft();
        this.moveForward = 0.0f;
        this.moveStrafing = 0.0f;
        if (minecraft.isKeyPressed(Key.W)) {
            this.moveForward += 1.0f;
        }
        if (minecraft.isKeyPressed(Key.S)) {
            this.moveForward -= 1.0f;
        }
        if (minecraft.isKeyPressed(Key.A)) {
            this.moveStrafing += 1.0f;
        }
        if (minecraft.isKeyPressed(Key.D)) {
            this.moveStrafing -= 1.0f;
        }
        this.jumping = minecraft.isKeyPressed(Key.SPACE);
        this.sprinting = minecraft.options().sprintInput().isActuallyDown();
        this.sneaking = minecraft.options().sneakInput().isActuallyDown();
        if (this.freeCam) {
            return;
        }
        if (this.moveForward != 0.0f || this.moveStrafing != 0.0f) {
            updateFreeCam(true, false);
        }
    }

    private void tickMovement() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (Math.abs(this.motionX) < 0.003d) {
            this.motionX = 0.0d;
        }
        if (Math.abs(this.motionY) < 0.003d) {
            this.motionY = 0.0d;
        }
        if (Math.abs(this.motionZ) < 0.003d) {
            this.motionZ = 0.0d;
        }
        this.moveStrafing *= 0.98f;
        this.moveForward *= 0.98f;
        travelFlying(this.moveForward, 0.0f, this.moveStrafing);
    }

    private void travelFlying(float forward, float vertical, float strafe) {
        if (this.sneaking) {
            this.moveStrafing = strafe / 0.3f;
            this.moveForward = forward / 0.3f;
            double d = this.motionY;
            Objects.requireNonNull(this);
            this.motionY = d - ((double) (0.05f * 3.0f));
        }
        if (this.jumping) {
            double d2 = this.motionY;
            Objects.requireNonNull(this);
            this.motionY = d2 + ((double) (0.05f * 3.0f));
        }
        double prevMotionY = this.motionY;
        float prevJumpMovementFactor = this.jumpMovementFactor;
        Objects.requireNonNull(this);
        this.jumpMovementFactor = 0.05f * (this.sprinting ? 2 : 1);
        travel(forward, vertical, -strafe);
        this.motionY = prevMotionY * 0.6d;
        this.jumpMovementFactor = prevJumpMovementFactor;
    }

    private void travel(double forward, double vertical, double strafe) {
        double distance = (strafe * strafe) + (vertical * vertical) + (forward * forward);
        if (distance >= 9.999999747378752E-5d) {
            double distance2 = Math.sqrt(distance);
            if (distance2 < 1.0d) {
                distance2 = 1.0d;
            }
            double distance3 = ((double) this.jumpMovementFactor) / distance2;
            double strafe2 = strafe * distance3;
            double vertical2 = vertical * distance3;
            double forward2 = forward * distance3;
            Location location = this.camera.positionModifier(3);
            double fixedYaw = this.camera.location().getYaw();
            double yaw = (-location.getYaw()) - fixedYaw;
            double yawRadians = Math.toRadians(-yaw);
            double sin = Math.sin(yawRadians);
            double cos = Math.cos(yawRadians);
            this.motionX += (strafe2 * cos) + (forward2 * sin);
            this.motionY += vertical2;
            this.motionZ += ((-forward2) * cos) + (strafe2 * sin);
        }
        this.x += this.motionX;
        this.y -= this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.9100000262260437d;
        this.motionY *= 0.9800000190734863d;
        this.motionZ *= 0.9100000262260437d;
    }

    private void updateFreeCam(boolean enabled, boolean showUI) {
        this.mainMenuWidget.getChild("ui").setVisible(showUI);
        this.world.setWind(!enabled);
        if (enabled) {
            this.labyAPI.minecraft().mouseHandler().grabMouseNative();
            Location location = this.camera.location();
            this.camera.moveTo(0L, CubicBezier.LINEAR, new Location(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), 0.0d));
            Location wind = this.camera.positionModifier(0);
            wind.setPosition(0.0d, 0.0d, 0.0d);
            wind.setRotation(0.0d, 0.0d, 0.0d);
        } else {
            this.labyAPI.minecraft().mouseHandler().ungrabMouseNative();
        }
        this.freeCam = enabled;
        this.prevMouseX = -1.0f;
        this.prevMouseY = -1.0f;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ESCAPE || key == Key.T) {
            updateFreeCam(!this.freeCam, this.freeCam && key != Key.T);
            return true;
        }
        if (key == Key.O) {
            if (this.freeCam) {
                updateFreeCam(false, true);
            }
            resetFreeCamPosition();
            DefaultReferenceStorage references = LabyMod.references();
            AbstractBootLogoRenderer logo = references.bootLogoController().renderer();
            logo.initialize();
            this.world.reset();
            this.labyAPI.minecraft().minecraftWindow().displayScreen(new BackgroundWorldTestActivity());
            return true;
        }
        if (KeyHandler.isControlDown() && key == Key.S) {
            try {
                this.schematicRenderer.schematic().saveTo(Constants.Files.LABYMOD_DIRECTORY.resolve("normal_cave.schem"));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }
        }
        if (key == Key.R) {
            this.world.getSchematicRenderer().setDirty();
            return true;
        }
        if (key == Key.H) {
            resetFreeCamPosition();
            return true;
        }
        return super.keyPressed(key, type);
    }

    private void resetFreeCamPosition() {
        this.x = 0.0d;
        this.y = 0.0d;
        this.z = 0.0d;
        this.motionX = 0.0d;
        this.motionY = 0.0d;
        this.motionZ = 0.0d;
        Location location = this.camera.positionModifier(3);
        location.setRotation(0.0d, 0.0d, 0.0d);
        location.setPosition(0.0d, 0.0d, 0.0d);
    }

    private float getProgress() {
        long timePassed = TimeUtil.getMillis() - this.mainMenuWidget.getLastInitialTime();
        return MathHelper.clamp(timePassed / 2000.0f, 0.0f, 1.0f);
    }
}

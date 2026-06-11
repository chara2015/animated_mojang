package net.labymod.core.main.user.shop.item.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.laby.lib.cosmetics.TextureType;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.geometry.DepthMap;
import net.labymod.core.main.user.shop.item.metadata.type.BooleanMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.ColorArrayMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.IntMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.OffsetVectorMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.SizeMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.StringMetadata;
import net.labymod.core.main.user.shop.item.metadata.type.TextureDetailsMetadata;
import net.labymod.core.main.user.shop.item.model.OffsetVector;
import net.labymod.core.main.user.shop.item.model.TextureDetails;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/ItemMetadata.class */
public class ItemMetadata implements MetadataWatchable {
    public static final String SHOULDER_SIDE_KEY = "shoulder_side";
    public static final String SIDE_KEY = "side";
    public static final String TEXTURE_KEY = "texture";
    public static final String MOJANG_UUID_KEY = "mojang_uuid";
    public static final String OFFSET_KEY = "offset";
    public static final String RGB_KEY = "rgb";
    public static final String SIZE_KEY = "size";
    public static final String BRIGHTNESS_KEY = "brightness";
    public static final String LEFT_VISIBLE_KEY = "left_visible";
    public static final String RIGHT_VISIBLE_KEY = "right_visible";
    public static final String CAN_SLEEP_KEY = "can_sleep";
    public static final String CAN_BLINK_KEY = "can_blink";
    public static final String NAMETAG_KEY = "nametag";
    public static final String NAMETAG_ENABLED_KEY = "nametag_enabled";
    public static final String BORDER_ENABLED_KEY = "border_enabled";
    private final CosmeticDetails details;
    private DepthMap depthMap;
    private boolean changed;
    private final MetadataProperty<FloatVector4> size = new MetadataProperty<>(SIZE_KEY, this);
    private final MetadataProperty<OffsetVector> offset = new MetadataProperty<>(OFFSET_KEY, this);
    private final MetadataProperty<Boolean> rightSide = new MetadataProperty<>(SIDE_KEY, this);
    private final MetadataProperty<Boolean> leftVisible = new MetadataProperty<>(LEFT_VISIBLE_KEY, this);
    private final MetadataProperty<Boolean> rightVisible = new MetadataProperty<>(RIGHT_VISIBLE_KEY, this);
    private final MetadataProperty<Integer> brightness = new MetadataProperty<>(BRIGHTNESS_KEY, this);
    private final MetadataProperty<Boolean> canBlink = new MetadataProperty<>(CAN_BLINK_KEY, this);
    private final MetadataProperty<Boolean> canSleep = new MetadataProperty<>(CAN_SLEEP_KEY, this);
    private final MetadataProperty<TextureDetails> cosmeticTexture = new MetadataProperty<>(TEXTURE_KEY, this);
    private final MetadataProperty<TextureDetails> skinTexture = new MetadataProperty<>(MOJANG_UUID_KEY, this);
    private final MetadataProperty<Color[]> colors = new MetadataProperty<>(RGB_KEY, this);
    private final MetadataProperty<Boolean> nameTagEnabled = new MetadataProperty<>(NAMETAG_ENABLED_KEY, this);
    private final MetadataProperty<String> nametag = new MetadataProperty<>(NAMETAG_KEY, this);
    private final MetadataProperty<Boolean> borderEnabled = new MetadataProperty<>(BORDER_ENABLED_KEY, this);
    private final List<AbstractMetadata<?>> dataList = new ArrayList();

    public ItemMetadata(CosmeticDetails details) {
        this.details = details;
        registerDefaultData();
    }

    private void registerDefaultData() {
        Function function = BooleanMetadata::new;
        Supplier supplier = () -> {
            return false;
        };
        MetadataProperty<Boolean> metadataProperty = this.rightSide;
        Objects.requireNonNull(metadataProperty);
        register(function, supplier, metadataProperty::reset, SHOULDER_SIDE_KEY, SIDE_KEY);
        Function function2 = TextureDetailsMetadata::new;
        MetadataProperty<TextureDetails> metadataProperty2 = this.cosmeticTexture;
        Objects.requireNonNull(metadataProperty2);
        register(function2, null, metadataProperty2::reset, TEXTURE_KEY);
        Function function3 = TextureDetailsMetadata::new;
        MetadataProperty<TextureDetails> metadataProperty3 = this.skinTexture;
        Objects.requireNonNull(metadataProperty3);
        register(function3, null, metadataProperty3::reset, MOJANG_UUID_KEY);
        Function function4 = OffsetVectorMetadata::new;
        Supplier supplier2 = OffsetVector::new;
        MetadataProperty<OffsetVector> metadataProperty4 = this.offset;
        Objects.requireNonNull(metadataProperty4);
        register(function4, supplier2, metadataProperty4::reset, OFFSET_KEY);
        Function function5 = ColorArrayMetadata::new;
        Supplier supplier3 = () -> {
            return new Color[0];
        };
        MetadataProperty<Color[]> metadataProperty5 = this.colors;
        Objects.requireNonNull(metadataProperty5);
        register(function5, supplier3, metadataProperty5::reset, RGB_KEY);
        Function function6 = BooleanMetadata::new;
        Supplier supplier4 = () -> {
            return false;
        };
        MetadataProperty<Boolean> metadataProperty6 = this.nameTagEnabled;
        Objects.requireNonNull(metadataProperty6);
        register(function6, supplier4, metadataProperty6::reset, NAMETAG_ENABLED_KEY);
        Function function7 = StringMetadata::new;
        Supplier supplier5 = () -> {
            return "";
        };
        MetadataProperty<String> metadataProperty7 = this.nametag;
        Objects.requireNonNull(metadataProperty7);
        register(function7, supplier5, metadataProperty7::reset, NAMETAG_KEY);
        Function function8 = BooleanMetadata::new;
        Supplier supplier6 = () -> {
            return false;
        };
        MetadataProperty<Boolean> metadataProperty8 = this.borderEnabled;
        Objects.requireNonNull(metadataProperty8);
        register(function8, supplier6, metadataProperty8::reset, BORDER_ENABLED_KEY);
        Function function9 = SizeMetadata::new;
        Supplier supplier7 = FloatVector4::new;
        MetadataProperty<FloatVector4> metadataProperty9 = this.size;
        Objects.requireNonNull(metadataProperty9);
        register(function9, supplier7, metadataProperty9::reset, SIZE_KEY);
        Function function10 = BooleanMetadata::new;
        Supplier supplier8 = () -> {
            return true;
        };
        MetadataProperty<Boolean> metadataProperty10 = this.leftVisible;
        Objects.requireNonNull(metadataProperty10);
        register(function10, supplier8, metadataProperty10::reset, LEFT_VISIBLE_KEY);
        Function function11 = BooleanMetadata::new;
        Supplier supplier9 = () -> {
            return true;
        };
        MetadataProperty<Boolean> metadataProperty11 = this.rightVisible;
        Objects.requireNonNull(metadataProperty11);
        register(function11, supplier9, metadataProperty11::reset, RIGHT_VISIBLE_KEY);
        Function function12 = BooleanMetadata::new;
        Supplier supplier10 = () -> {
            return true;
        };
        MetadataProperty<Boolean> metadataProperty12 = this.canSleep;
        Objects.requireNonNull(metadataProperty12);
        register(function12, supplier10, metadataProperty12::reset, CAN_SLEEP_KEY);
        Function function13 = BooleanMetadata::new;
        Supplier supplier11 = () -> {
            return true;
        };
        MetadataProperty<Boolean> metadataProperty13 = this.canBlink;
        Objects.requireNonNull(metadataProperty13);
        register(function13, supplier11, metadataProperty13::reset, CAN_BLINK_KEY);
        Function function14 = IntMetadata::new;
        Supplier supplier12 = () -> {
            return 0;
        };
        MetadataProperty<Integer> metadataProperty14 = this.brightness;
        Objects.requireNonNull(metadataProperty14);
        register(function14, supplier12, metadataProperty14::reset, BRIGHTNESS_KEY);
    }

    private <T> void register(Function<String[], AbstractMetadata<T>> metadataFactory, Supplier<T> defaultValue, Consumer<String> invalidator, String... keys) {
        AbstractMetadata<T> metadata = metadataFactory.apply(keys);
        if (defaultValue != null) {
            metadata.defaultValue(defaultValue);
        }
        register(metadata.withMetadataInvalidator(invalidator));
    }

    private void register(AbstractMetadata<?> metadata) {
        this.dataList.add(metadata.withWatchable(this));
    }

    public void invalidate() {
        for (AbstractMetadata<?> metadata : this.dataList) {
            metadata.invalidate();
        }
    }

    public CosmeticDetails getDetails() {
        return this.details;
    }

    public OffsetVector getOffset() {
        return this.offset.get();
    }

    public boolean isRightSide() {
        return this.rightSide.get().booleanValue();
    }

    public TextureDetails getCosmeticTexture() {
        return this.cosmeticTexture.get();
    }

    public TextureDetails getSkinTexture() {
        return this.skinTexture.get();
    }

    public Optional<TextureDetails> texture() {
        return Optional.ofNullable(this.cosmeticTexture.get());
    }

    public Color[] getColors() {
        return this.colors.get();
    }

    public FloatVector4 getSize() {
        return this.size.get();
    }

    public int getBrightness() {
        return this.brightness.get().intValue();
    }

    public boolean isLeftVisible() {
        return this.leftVisible.get().booleanValue();
    }

    public boolean isRightVisible() {
        return this.rightVisible.get().booleanValue();
    }

    public boolean canBlink() {
        return this.canBlink.get().booleanValue();
    }

    public boolean canSleep() {
        return this.canSleep.get().booleanValue();
    }

    public DepthMap getDepthMap() {
        return this.depthMap;
    }

    public boolean isNameTagEnabled() {
        return this.nameTagEnabled.get().booleanValue();
    }

    public String getNametag() {
        return this.nametag.get();
    }

    public boolean isBorderEnabled() {
        return this.borderEnabled.get().booleanValue();
    }

    public void setDepthMap(DepthMap depthMap) {
        this.depthMap = depthMap;
        onUpdate();
    }

    public <T> T readValue(String str) {
        for (AbstractMetadata<?> abstractMetadata : this.dataList) {
            if (abstractMetadata.hasKey(str)) {
                return (T) abstractMetadata.getValue();
            }
        }
        throw new IllegalStateException(String.format(Locale.ROOT, "No key \"%s\" was found (%s/%s)", str, Integer.valueOf(this.details.getId()), this.details.getName()));
    }

    public List<AbstractMetadata<?>> dataList() {
        return this.dataList;
    }

    public boolean isChanged() {
        boolean changed = this.changed;
        this.changed = false;
        return changed;
    }

    public boolean isMojangBound() {
        return this.details.getTextureType() == TextureType.MOJANG_BOUND;
    }

    public boolean isForcedPlayerSkin(boolean mojangBound) {
        UUID id;
        TextureDetails details = mojangBound ? getSkinTexture() : getCosmeticTexture();
        return (details == null || (id = details.getUuid()) == null || id.getMostSignificantBits() != 0) ? false : true;
    }

    public boolean isTypeAndMojangBound() {
        return this.details.getTextureType() == TextureType.TYPE_AND_MOJANG_BOUND;
    }

    public boolean isUserBound() {
        return this.details.getTextureType() == TextureType.USER_BOUND;
    }

    @Override // net.labymod.core.main.user.shop.item.metadata.MetadataWatchable
    public void onUpdate() {
        this.changed = true;
    }
}

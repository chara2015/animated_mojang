package net.labymod.core.flint.marketplace;

import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import net.labymod.api.BuildData;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.models.addon.info.dependency.AddonDependency;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.I18n;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.util.markdown.MarkdownDocument;
import net.labymod.api.util.version.VersionMultiRange;
import net.labymod.api.util.version.VersionRange;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.flint.downloader.FlintDownloader;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification.class */
public class FlintModification {
    private static final FlintController CONTROLLER = LabyMod.references().flintController();
    private static final FlintDownloader DOWNLOADER = LabyMod.references().flintDownloader();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    protected Set<FlintTag> flintTags;
    protected Image icon;
    protected Image thumbnail;
    protected Image[] sliderImages;
    protected int id;
    protected String namespace;
    protected String name;
    protected String changelog;
    protected String licence;
    protected String author;
    protected boolean featured;
    protected boolean verified;
    protected int organization;
    protected int version;
    protected int downloads;
    protected int ranking;
    protected int releases;
    protected int[] tags;
    protected Rating rating;
    protected AddonMeta[] meta;
    protected String[] permissions;
    protected AddonDependency[] dependencies;

    @SerializedName("short_description")
    protected String shortDescription;

    @SerializedName("flint_url")
    protected String flintUrl;

    @SerializedName("version_string")
    protected String versionString;

    @SerializedName("download_string")
    protected String downloadsString;

    @SerializedName("brand_images")
    protected Image[] brandImages;

    @SerializedName("last_update")
    protected long lastUpdate;

    @SerializedName("required_labymod_build")
    protected int requiredLabyModBuild;

    @SerializedName("file_hash")
    protected String fileHash;
    private transient String niceVersionString;
    private transient FlintPermission[] flintPermissions;
    private transient MarkdownDocument description;
    private transient boolean compatible;

    public Rating getRating() {
        return this.rating;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public String getFlintUrl() {
        return this.flintUrl;
    }

    public String getFileHash() {
        return this.fileHash;
    }

    public boolean isBuildCompatible() {
        return Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() || this.requiredLabyModBuild <= BuildData.getBuildNumber();
    }

    public boolean isCompatible() {
        return this.compatible;
    }

    public void setCompatible(boolean compatible) {
        this.compatible = compatible;
    }

    public String getVersionString() {
        if (this.niceVersionString != null || this.versionString == null) {
            return this.niceVersionString != null ? this.niceVersionString : this.versionString;
        }
        if (this.versionString.equals("*")) {
            String translation = I18n.getTranslation("labymod.addons.allVersions", new Object[0]);
            this.niceVersionString = translation == null ? this.versionString : translation;
            return this.niceVersionString;
        }
        String[] ranges = this.versionString.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ranges.length; i++) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            String range = ranges[i];
            String[] versions = range.split("<");
            if (versions.length == 1) {
                stringBuilder.append(versions[0]);
            } else {
                if (versions[0].equals("*")) {
                    stringBuilder.append("1.8.9");
                } else {
                    stringBuilder.append(versions[0]);
                }
                if (versions[1].equals("*")) {
                    stringBuilder.append('+');
                } else {
                    stringBuilder.append(" - ").append(versions[1]);
                }
            }
        }
        this.niceVersionString = stringBuilder.toString();
        return this.niceVersionString;
    }

    public VersionCompatibility getVersionCompatibility() {
        return this.versionString == null ? new VersionRange("*") : new VersionMultiRange(this.versionString);
    }

    public String getChangelog() {
        return this.changelog;
    }

    public String getLicence() {
        if (this.licence == null) {
            return null;
        }
        return this.licence.replace("-", " ");
    }

    public String getDownloadsString() {
        return this.downloadsString == null ? "?" : this.downloadsString;
    }

    public int getReleases() {
        return this.releases;
    }

    public int getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public int getDownloads() {
        return this.downloads;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public boolean isFeatured() {
        return this.featured;
    }

    public long getLastUpdate() {
        return this.lastUpdate * 1000;
    }

    public void forEachAddonDependency(Version version, Consumer<AddonDependency> consumer) {
        if (this.dependencies == null) {
            return;
        }
        for (AddonDependency dependency : this.dependencies) {
            Optional<VersionCompatibility> compatability = dependency.getCompatability();
            if (!compatability.isPresent() || compatability.get().isCompatible(version)) {
                consumer.accept(dependency);
            }
        }
    }

    @Deprecated
    public AddonDependency[] getDependencies() {
        if (this.dependencies == null) {
            this.dependencies = new AddonDependency[0];
        }
        return this.dependencies;
    }

    public Optional<List<Review>> getOrLoadReviews(ResultCallback<List<Review>> consumer) {
        return CONTROLLER.getOrLoadReviews(this.namespace, consumer);
    }

    public Optional<List<Changelog>> getOrLoadChangelog(ResultCallback<List<Changelog>> consumer) {
        return CONTROLLER.getOrLoadChangelog(this.namespace, consumer);
    }

    public MarkdownDocument getOrLoadDescription(ResultCallback<MarkdownDocument> consumer) {
        if (this.description == null) {
            CONTROLLER.loadDescription(this.namespace, result -> {
                if (result.hasException()) {
                    consumer.acceptException(result.exception());
                } else if (result.isEmpty()) {
                    consumer.acceptRaw(null);
                } else {
                    this.description = Laby.references().markdownParser().parse((String) result.get());
                    consumer.acceptRaw(this.description);
                }
            });
        }
        return this.description;
    }

    public Set<FlintTag> getTags() {
        if (this.flintTags == null) {
            Set<FlintTag> flintTags = new HashSet<>();
            if (this.tags != null) {
                for (int tag : this.tags) {
                    Optional<FlintTag> optionalFlintTag = CONTROLLER.getTag(tag);
                    Objects.requireNonNull(flintTags);
                    optionalFlintTag.ifPresent((v1) -> {
                        r1.add(v1);
                    });
                }
            }
            this.flintTags = flintTags;
        }
        return this.flintTags;
    }

    public Image getIcon() {
        if (this.icon == null && this.brandImages != null) {
            Image[] imageArr = this.brandImages;
            int length = imageArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Image brandImage = imageArr[i];
                if (brandImage.getType() != ImageType.ICON) {
                    i++;
                } else {
                    this.icon = brandImage;
                    break;
                }
            }
        }
        return this.icon;
    }

    public Image getThumbnail() {
        if (this.thumbnail == null && this.brandImages != null) {
            Image[] imageArr = this.brandImages;
            int length = imageArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Image brandImage = imageArr[i];
                if (brandImage.getType() != ImageType.THUMBNAIL) {
                    i++;
                } else {
                    this.thumbnail = brandImage;
                    break;
                }
            }
        }
        return this.thumbnail;
    }

    public Image[] getSliderImages() {
        if (this.sliderImages == null && this.brandImages != null) {
            List<Image> sliderImages = Lists.newArrayList();
            for (Image brandImage : this.brandImages) {
                if (brandImage.getType() == ImageType.IMAGE) {
                    sliderImages.add(brandImage);
                }
            }
            this.sliderImages = (Image[]) sliderImages.toArray(new Image[0]);
        }
        return this.sliderImages;
    }

    public Image[] getBrandImages() {
        return this.brandImages;
    }

    public String getAuthor() {
        if (this.author == null) {
            FlintOrganization organization = getOrganization(null);
            if (organization != null) {
                this.author = organization.getDisplayName();
            }
            return this.author;
        }
        return this.author;
    }

    public FlintOrganization getOrganization(ResultCallback<FlintOrganization> result) {
        return CONTROLLER.getOrganization(this.organization, result);
    }

    public boolean isOrganizationLabyMod() {
        return this.organization == 1;
    }

    public FlintModification loadModification(ResultCallback<FlintModification> result) {
        return CONTROLLER.loadModification(this.namespace, result);
    }

    public Optional<LoadedAddon> getAsLoadedAddon() {
        return DefaultAddonService.getInstance().getAddon(this.namespace);
    }

    public boolean isInstalled() {
        return getAsLoadedAddon().isPresent();
    }

    public boolean isDeleted() {
        return DOWNLOADER.isScheduledForRemoval(this.namespace);
    }

    public String[] getRawPermissions() {
        return this.permissions;
    }

    public FlintPermission[] getPermissions() {
        if (this.flintPermissions == null) {
            ArrayList<FlintPermission> permissions = new ArrayList<>(this.permissions.length);
            for (String key : this.permissions) {
                permissions.add(CONTROLLER.getPermission(key));
            }
            permissions.sort(Comparator.comparing(permission -> {
                return Boolean.valueOf(!permission.isCritical());
            }));
            this.flintPermissions = (FlintPermission[]) permissions.toArray(new FlintPermission[0]);
        }
        return this.flintPermissions;
    }

    public int getRanking() {
        return this.ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean hasMeta(AddonMeta addonMeta) {
        if (this.meta == null) {
            return false;
        }
        for (AddonMeta meta : this.meta) {
            if (meta == addonMeta) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification$ImageType.class */
    public enum ImageType {
        ICON(256),
        THUMBNAIL(1280),
        IMAGE(1280, 1920);

        private static final ImageType[] VALUES = values();
        private final int smallSize;
        private final int fullSize;

        ImageType(int smallSize, int fullSize) {
            this.smallSize = smallSize;
            this.fullSize = fullSize;
        }

        ImageType(int size) {
            this(size, size);
        }

        public int getSmallSize() {
            return this.smallSize;
        }

        public int getFullSize() {
            return this.fullSize;
        }

        public static ImageType of(String name) {
            for (ImageType imageType : VALUES) {
                if (imageType.name().equalsIgnoreCase(name)) {
                    return imageType;
                }
            }
            return null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification$Rating.class */
    public static class Rating {
        private final int count;
        private final double rating;

        public Rating(int count, double rating) {
            this.count = count;
            this.rating = rating;
        }

        public int getCount() {
            return this.count;
        }

        public double getRating() {
            return this.rating;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification$Image.class */
    public static class Image {
        private String type;
        private String hash;

        public Image() {
        }

        public Image(String type, String hash) {
            this.type = type;
            this.hash = hash;
        }

        public ImageType getType() {
            return ImageType.of(this.type);
        }

        public String getIconUrl() {
            return String.format(Locale.ROOT, FlintUrls.BRAND_MODIFICATION_URL, this.hash);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification$Review.class */
    public static class Review {
        private int rating;
        private String comment;
        private FlintUser user;

        @SerializedName("added_at")
        private String addedAt;
        private long addedAtLong = -1;

        public int getRating() {
            return this.rating;
        }

        public String getComment() {
            return this.comment;
        }

        public FlintUser user() {
            return this.user;
        }

        public String getAddedAtString() {
            return this.addedAt;
        }

        public long getAddedAt() {
            if (this.addedAtLong == -1) {
                try {
                    this.addedAtLong = FlintModification.DATE_FORMAT.parse(this.addedAt).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return this.addedAtLong;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintModification$Changelog.class */
    public static class Changelog {
        private String changelog;
        private String release;

        @SerializedName("added_at")
        private String addedAt;
        private long addedAtLong = -1;
        private VersionCompatibility releaseVersion;

        public String getChanges() {
            return this.changelog;
        }

        public String getRelease() {
            return this.release;
        }

        public VersionCompatibility releaseVersion() {
            if (this.releaseVersion == null) {
                this.releaseVersion = VersionDeserializer.from(this.release);
            }
            return this.releaseVersion;
        }

        public String getAddedAtString() {
            return this.addedAt;
        }

        public long getAddedAt() {
            if (this.addedAtLong == -1) {
                try {
                    this.addedAtLong = FlintModification.DATE_FORMAT.parse(this.addedAt).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return this.addedAtLong;
        }
    }
}

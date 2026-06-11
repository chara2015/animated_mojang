package net.labymod.api.client.component.object;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Namespaces;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/object/ObjectSprite.class */
public interface ObjectSprite {
    GlyphSourceDescription sourceDescription();

    String getDescription();

    static AtlasObjectSprite atlasSprite(ResourceLocation atlas, ResourceLocation sprite) {
        return new AtlasObjectSprite(atlas, sprite);
    }

    static PlayerObjectSprite playerProfile(GlyphSourceDescription.PlayerSprite.Profile profile, boolean hat) {
        return new PlayerObjectSprite(profile, hat);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite.class */
    public static final class AtlasObjectSprite extends Record implements ObjectSprite {
        private final ResourceLocation atlas;
        private final ResourceLocation sprite;
        public static final ResourceLocation DEFAULT_ATLAS = ResourceLocation.create(Namespaces.MINECRAFT, "blocks");

        public AtlasObjectSprite(ResourceLocation atlas, ResourceLocation sprite) {
            this.atlas = atlas;
            this.sprite = sprite;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AtlasObjectSprite.class), AtlasObjectSprite.class, "atlas;sprite", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->atlas:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->sprite:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AtlasObjectSprite.class), AtlasObjectSprite.class, "atlas;sprite", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->atlas:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->sprite:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AtlasObjectSprite.class, Object.class), AtlasObjectSprite.class, "atlas;sprite", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->atlas:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$AtlasObjectSprite;->sprite:Lnet/labymod/api/client/resources/ResourceLocation;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public ResourceLocation atlas() {
            return this.atlas;
        }

        public ResourceLocation sprite() {
            return this.sprite;
        }

        private static String toShortName(ResourceLocation location) {
            return location.getNamespace().equals(Namespaces.MINECRAFT) ? location.getPath() : location.toString();
        }

        @Override // net.labymod.api.client.component.object.ObjectSprite
        public GlyphSourceDescription sourceDescription() {
            return GlyphSourceDescription.atlasSprite(atlas(), sprite());
        }

        @Override // net.labymod.api.client.component.object.ObjectSprite
        public String getDescription() {
            String name = toShortName(sprite());
            if (atlas().equals(DEFAULT_ATLAS)) {
                return "[" + name + "]";
            }
            return "[" + name + "@" + toShortName(atlas()) + "]";
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite.class */
    public static final class PlayerObjectSprite extends Record implements ObjectSprite {
        private final GlyphSourceDescription.PlayerSprite.Profile profile;
        private final boolean hat;

        public PlayerObjectSprite(GlyphSourceDescription.PlayerSprite.Profile profile, boolean hat) {
            this.profile = profile;
            this.hat = hat;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PlayerObjectSprite.class), PlayerObjectSprite.class, "profile;hat", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->profile:Lnet/labymod/api/client/component/GlyphSourceDescription$PlayerSprite$Profile;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PlayerObjectSprite.class), PlayerObjectSprite.class, "profile;hat", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->profile:Lnet/labymod/api/client/component/GlyphSourceDescription$PlayerSprite$Profile;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PlayerObjectSprite.class, Object.class), PlayerObjectSprite.class, "profile;hat", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->profile:Lnet/labymod/api/client/component/GlyphSourceDescription$PlayerSprite$Profile;", "FIELD:Lnet/labymod/api/client/component/object/ObjectSprite$PlayerObjectSprite;->hat:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public GlyphSourceDescription.PlayerSprite.Profile profile() {
            return this.profile;
        }

        public boolean hat() {
            return this.hat;
        }

        @Override // net.labymod.api.client.component.object.ObjectSprite
        public GlyphSourceDescription sourceDescription() {
            GlyphSourceDescription.PlayerSprite.Profile gameProfile = profile();
            return GlyphSourceDescription.playerSprite(gameProfile, hat());
        }

        @Override // net.labymod.api.client.component.object.ObjectSprite
        public String getDescription() {
            return (String) this.profile.name().map(name -> {
                return "[" + name + " head]";
            }).orElse("[unknown player head]");
        }
    }
}

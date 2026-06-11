package net.minecraft.sounds;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/sounds/SoundSource.class */
public enum SoundSource {
    MASTER("master"),
    MUSIC("music"),
    RECORDS("record"),
    WEATHER("weather"),
    BLOCKS("block"),
    HOSTILE("hostile"),
    NEUTRAL("neutral"),
    PLAYERS("player"),
    AMBIENT("ambient"),
    VOICE("voice"),
    UI("ui");

    private final String name;

    SoundSource(String $$0) {
        this.name = $$0;
    }

    public String getName() {
        return this.name;
    }
}

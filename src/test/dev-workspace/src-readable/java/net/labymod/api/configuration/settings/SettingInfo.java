package net.labymod.api.configuration.settings;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import net.labymod.api.configuration.loader.Config;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/SettingInfo.class */
public class SettingInfo<M extends Member & AnnotatedElement> {
    private final Config config;
    private final M member;

    public SettingInfo(Config config, M member) {
        this.config = config;
        this.member = member;
    }

    public Config config() {
        return this.config;
    }

    public M getMember() {
        return this.member;
    }
}

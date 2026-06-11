package net.labymod.v1_12_2.client.gui.screen.key.mapper;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKey;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gui/screen/key/mapper/VersionedKeyMapper.class */
@Singleton
@Implements(KeyMapper.class)
public class VersionedKeyMapper extends DefaultKeyMapper {
    @Override // net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper
    protected DefaultKey createKey(Key key, int keyCode, int legacyKeyCode) {
        return new VersionedKey(key, keyCode, legacyKeyCode);
    }
}

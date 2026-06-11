package com.mojang.realmsclient.gui.screens.configuration;

import com.mojang.realmsclient.dto.RealmsServer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/screens/configuration/RealmsConfigurationTab.class */
public interface RealmsConfigurationTab {
    void updateData(RealmsServer realmsServer);

    default void onSelected(RealmsServer $$0) {
    }

    default void onDeselected(RealmsServer $$0) {
    }
}

package com.mojang.realmsclient.gui;

import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.util.RealmsPersistence;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/gui/RealmsNewsManager.class */
public class RealmsNewsManager {
    private final RealmsPersistence newsLocalStorage;
    private boolean hasUnreadNews;
    private String newsLink;

    public RealmsNewsManager(RealmsPersistence $$0) {
        this.newsLocalStorage = $$0;
        RealmsPersistence.RealmsPersistenceData $$1 = $$0.read();
        this.hasUnreadNews = $$1.hasUnreadNews;
        this.newsLink = $$1.newsLink;
    }

    public boolean hasUnreadNews() {
        return this.hasUnreadNews;
    }

    public String newsLink() {
        return this.newsLink;
    }

    public void updateUnreadNews(RealmsNews $$0) {
        RealmsPersistence.RealmsPersistenceData $$1 = updateNewsStorage($$0);
        this.hasUnreadNews = $$1.hasUnreadNews;
        this.newsLink = $$1.newsLink;
    }

    private RealmsPersistence.RealmsPersistenceData updateNewsStorage(RealmsNews $$0) {
        RealmsPersistence.RealmsPersistenceData $$1 = this.newsLocalStorage.read();
        if ($$0.newsLink() == null || $$0.newsLink().equals($$1.newsLink)) {
            return $$1;
        }
        RealmsPersistence.RealmsPersistenceData $$2 = new RealmsPersistence.RealmsPersistenceData();
        $$2.newsLink = $$0.newsLink();
        $$2.hasUnreadNews = true;
        this.newsLocalStorage.save($$2);
        return $$2;
    }
}

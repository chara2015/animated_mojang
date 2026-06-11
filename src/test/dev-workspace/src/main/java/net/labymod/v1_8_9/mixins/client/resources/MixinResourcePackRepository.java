package net.labymod.v1_8_9.mixins.client.resources;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/resources/MixinResourcePackRepository.class */
@Mixin({bnm.class})
public class MixinResourcePackRepository {

    @Shadow
    @Final
    private File f;

    @Shadow
    @Final
    private static Logger c;

    @Overwrite
    private void i() {
        if (!this.f.isDirectory()) {
            return;
        }
        try {
            List<File> files = Lists.newArrayList(FileUtils.listFiles(this.f, TrueFileFilter.TRUE, (IOFileFilter) null));
            Collections.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            int tries = 0;
            for (File file : files) {
                int i = tries;
                tries++;
                if (i >= 10) {
                    c.info("Deleting old server resource pack {}", new Object[]{file.getName()});
                    FileUtils.deleteQuietly(file);
                }
            }
        } catch (IllegalArgumentException exception) {
            c.error("Error while deleting old server resource pack : {}", new Object[]{exception.getMessage()});
        }
    }
}

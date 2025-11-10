package org.codehaus.plexus.components.io.fileselectors;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * The default file selector: Selects all files.
 */
@Singleton
@Named(AllFilesFileSelector.ROLE_HINT)
public class AllFilesFileSelector implements FileSelector {
    /**
     * The all files selectors role-hint: "all".
     */
    public static final String ROLE_HINT = "all";

    @Override
    public boolean isSelected(FileInfo fileInfo) {
        return true;
    }
}

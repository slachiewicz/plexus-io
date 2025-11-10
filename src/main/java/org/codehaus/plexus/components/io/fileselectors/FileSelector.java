package org.codehaus.plexus.components.io.fileselectors;

import java.io.IOException;

/**
 * Interface of a component, which selects/deselects files.
 */
public interface FileSelector {
    /**
     * Returns, whether the given file is selected.
     * @param fileInfo An instance of FileInfo with the files meta data.
     *   It is recommended, that the caller creates an instance
     *   of {@link org.codehaus.plexus.components.io.resources.PlexusIoResource}.
     */
    boolean isSelected(FileInfo fileInfo) throws IOException;
}

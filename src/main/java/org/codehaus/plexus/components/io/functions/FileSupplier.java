package org.codehaus.plexus.components.io.functions;

import java.io.File;

/**
 * Implemented by resources that are files on something filesystem-like.
 */
public interface FileSupplier {
    /**
     * Supplies the file for this resource, not null.
     * @return The file
     */
    File getFile();
}

package org.codehaus.plexus.components.io.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.codehaus.plexus.components.io.fileselectors.FileInfo;
import org.codehaus.plexus.components.io.functions.ContentSupplier;
import org.codehaus.plexus.components.io.functions.SizeSupplier;

/**
 * A resource is a file-like entity. It may be an actual file,
 * an URL, a zip entry, or something like that.
 */
public interface PlexusIoResource extends FileInfo, SizeSupplier, ContentSupplier {
    /**
     * Unknown resource size.
     */
    long UNKNOWN_RESOURCE_SIZE = -1;

    /**
     * Unknown modification date
     */
    long UNKNOWN_MODIFICATION_DATE = 0;

    /**
     * Returns the date, when the resource was last modified, if known.
     * Otherwise, returns {@link #UNKNOWN_MODIFICATION_DATE}.
     * @see java.io.File#lastModified()
     */
    long getLastModified();

    /**
     * Returns, whether the resource exists.
     */
    boolean isExisting();

    /**
     * Returns the resources size, if known. Otherwise returns
     * {@link #UNKNOWN_RESOURCE_SIZE}.
     */
    @Override
    long getSize();

    /**
     * Returns, whether the {@link FileInfo} refers to a file.
     */
    @Override
    boolean isFile();

    /**
     * Returns, whether the {@link FileInfo} refers to a directory.
     */
    @Override
    boolean isDirectory();

    /**
     * Creates an {@link java.io.InputStream}, which may be used to read
     * the files contents. This is useful, if the file selector
     * comes to a decision based on the files contents.
     * <p>
     * Please note that this InputStream is unbuffered. Clients should wrap this in a
     * BufferedInputStream or attempt reading reasonably large chunks (8K+).
     */
    @Override
    InputStream getContents() throws IOException;

    /**
     * Returns an {@link URL}, which may be used to reference the
     * resource, if possible.
     * @return An URL referencing the resource, if possible, or null.
     *   In the latter case, you are forced to use {@link #getContents()}.
     */
    URL getURL() throws IOException;
}

package org.codehaus.plexus.components.io.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public abstract class PlexusIoURLResource extends AbstractPlexusIoResource {
    protected PlexusIoURLResource(
            String name,
            long lastModified,
            long size,
            boolean isFile,
            boolean isDirectory,
            boolean isExisting) {
        super(name, lastModified, size, isFile, isDirectory, isExisting);
    }

    @Override
    public InputStream getContents() throws IOException {
        final URL url = getURL();
        try {
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            return uc.getInputStream();
        } catch (IOException e) {
            throw new IOException(getDescriptionForError(url), e);
        }
    }

    public String getDescriptionForError(URL url) {
        return url != null ? url.toExternalForm() : "url=null";
    }

    @Override
    public abstract URL getURL() throws IOException;
}

package org.codehaus.plexus.components.io.resources;

/**
 * Default implementation of {@link PlexusIoResource}.
 */
public abstract class AbstractPlexusIoResource implements PlexusIoResource {
    private final String name;

    private final long lastModified, size;
    private final boolean isFile, isDirectory, isExisting;

    protected AbstractPlexusIoResource(
            String name,
            long lastModified,
            long size,
            boolean isFile,
            boolean isDirectory,
            boolean isExisting) {
        this.name = name;
        this.lastModified = lastModified;
        this.size = size;
        this.isFile = isFile;
        this.isDirectory = isDirectory;
        this.isExisting = isExisting;
    }

    @Override
    public long getLastModified() {
        return lastModified;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public boolean isDirectory() {
        return isDirectory;
    }

    @Override
    public boolean isExisting() {
        return isExisting;
    }

    @Override
    public boolean isFile() {
        return isFile;
    }

    @Override
    public boolean isSymbolicLink() {
        return false;
    }
}

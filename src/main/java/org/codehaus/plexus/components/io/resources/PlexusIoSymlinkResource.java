package org.codehaus.plexus.components.io.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.output.DeferredFileOutputStream;
import org.codehaus.plexus.components.io.attributes.PlexusIoResourceAttributes;
import org.codehaus.plexus.components.io.functions.SymlinkDestinationSupplier;

public class PlexusIoSymlinkResource extends PlexusIoFileResource implements SymlinkDestinationSupplier {
    private final String symLinkDestination;
    private final PlexusIoFileResource targetResource;

    PlexusIoSymlinkResource(File symlinkfile, String name, PlexusIoResourceAttributes attrs)
            throws IOException {
        this(symlinkfile, name, attrs, symlinkfile.toPath());
    }

    PlexusIoSymlinkResource(
            File symlinkfile, String name, PlexusIoResourceAttributes attrs, Path linkPath)
            throws IOException {
        this(symlinkfile, name, attrs, linkPath, java.nio.file.Files.readSymbolicLink(linkPath));
    }

    private PlexusIoSymlinkResource(
            File symlinkfile, String name, PlexusIoResourceAttributes attrs, Path path, Path linkPath)
            throws IOException {
        this(symlinkfile, name, attrs, linkPath.toString(), (PlexusIoFileResource)
                ResourceFactory.createResource(path.resolveSibling(linkPath).toFile()));
    }

    private PlexusIoSymlinkResource(
            File symlinkfile,
            String name,
            PlexusIoResourceAttributes attrs,
            String symLinkDestination,
            PlexusIoFileResource targetResource)
            throws IOException {
        super(symlinkfile, name, attrs, targetResource.getFileAttributes(), null, null);
        this.symLinkDestination = symLinkDestination;
        this.targetResource = targetResource;
    }

    @Override
    public String getSymlinkDestination() throws IOException {
        return symLinkDestination;
    }

    public PlexusIoResource getTarget() {
        return targetResource;
    }

    public PlexusIoResource getLink() throws IOException {
        return new PlexusIoFileResource(getFile(), getName(), getAttributes());
    }

    @Override
    public long getSize() {
        DeferredFileOutputStream dfos = getDfos();
        if (dfos == null) {
            return targetResource.getSize();
        }
        if (dfos.isInMemory()) {
            return dfos.getByteCount();
        }
        return dfos.getFile().length();
    }

    @Override
    public boolean isDirectory() {
        return targetResource.isDirectory();
    }

    @Override
    public boolean isExisting() {
        return targetResource.isExisting();
    }

    @Override
    public boolean isFile() {
        return targetResource.isFile();
    }

    @Override
    public long getLastModified() {
        return targetResource.getLastModified();
    }
}

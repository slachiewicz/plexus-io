package org.codehaus.plexus.components.io.filemappers;

import javax.inject.Named;

/**
 * An implementation of {@link FileMapper}, which changes the files extension.
 */
@Named(FileExtensionMapper.ROLE_HINT)
public class FileExtensionMapper extends AbstractFileMapper {
    /**
     * The file extension mappers role-hint: "fileExtension".
     */
    public static final String ROLE_HINT = "fileExtension";

    private String targetExtension;

    /**
     * Sets the target files extension.
     *
     * @param pTargetExtension the target extensions
     * @throws IllegalArgumentException
     *             The target extension is null or empty.
     */
    public void setTargetExtension(String pTargetExtension) {
        if (pTargetExtension == null) {
            throw new IllegalArgumentException("The target extension is null.");
        }
        if (pTargetExtension.isEmpty()) {
            throw new IllegalArgumentException("The target extension is empty.");
        }
        if (pTargetExtension.charAt(0) == '.') {
            targetExtension = pTargetExtension;
        } else {
            targetExtension = '.' + pTargetExtension;
        }
    }

    /**
     * Returns the target files extension.
     * @return The target extension
     */
    public String getTargetExtension() {
        return targetExtension;
    }

    @Override
    public String getMappedFileName(String pName) {
        final String ext = getTargetExtension();
        if (ext == null) {
            throw new IllegalStateException("The target extension has not been set.");
        }
        final String name = super.getMappedFileName(pName); // Check arguments
        final int dirSep = Math.max(pName.lastIndexOf('/'), pName.lastIndexOf('\\'));
        final int offset = pName.lastIndexOf('.');
        if (offset <= dirSep) {
            return name + ext;
        }
        return name.substring(0, offset) + ext;
    }
}

package org.codehaus.plexus.components.io.filemappers;

import javax.inject.Named;

/**
 * A file mapper, which maps by adding a suffix to the filename.
 * If the filename contains dot, the suffix will be added before.
 * Example: {@code directory/archive.tar.gz => directory/archivesuffix.tar.gz}
 */
@Named(SuffixFileMapper.ROLE_HINT)
public class SuffixFileMapper extends AbstractFileMapper {
    /**
     * The suffix mappers role-hint: "suffix".
     */
    public static final String ROLE_HINT = "suffix";

    private String suffix;

    /**
     * Returns the suffix to add.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the suffix to add.
     */
    public void setSuffix(String suffix) {
        if (suffix == null) {
            throw new IllegalArgumentException("The suffix is null.");
        }
        this.suffix = suffix;
    }

    @Override
    public String getMappedFileName(String pName) {
        final String name = super.getMappedFileName(pName);
        if (suffix == null) {
            throw new IllegalStateException("The suffix has not been set.");
        }
        final int dirSep = Math.max(name.lastIndexOf('/'), name.lastIndexOf('\\'));
        String filename = dirSep > 0 ? name.substring(dirSep + 1) : name;
        String dirname = dirSep > 0 ? name.substring(0, dirSep + 1) : "";
        if (filename.contains(".")) {
            String beforeExtension = filename.substring(0, filename.indexOf('.'));
            String afterExtension = filename.substring(filename.indexOf('.') + 1);
            return dirname + beforeExtension + suffix + "." + afterExtension;
        }
        return name + suffix;
    }
}

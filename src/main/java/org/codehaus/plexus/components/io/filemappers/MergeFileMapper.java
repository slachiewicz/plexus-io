package org.codehaus.plexus.components.io.filemappers;

import javax.inject.Named;

/**
 * A file mapper, which maps to a constant target name.
 */
@Named(MergeFileMapper.ROLE_HINT)
public class MergeFileMapper extends AbstractFileMapper {
    /**
     * The merge mappers role-hint: "merge".
     */
    public static final String ROLE_HINT = "merge";

    private String targetName;

    /**
     * Sets the merge mappers target name.
     *
     * @throws IllegalArgumentException
     *             The target name is null or empty.
     */
    public void setTargetName(String pName) {
        if (pName == null) {
            throw new IllegalArgumentException("The target name is null.");
        }
        if (pName.isEmpty()) {
            throw new IllegalArgumentException("The target name is empty.");
        }
        targetName = pName;
    }

    /**
     * Returns the merge mappers target name.
     *
     * @throws IllegalArgumentException
     *             The target name is null or empty.
     */
    public String getTargetName() {
        return targetName;
    }

    @Override
    public String getMappedFileName(String pName) {
        final String name = getTargetName();
        if (name == null) {
            throw new IllegalStateException("The target file name has not been set.");
        }
        super.getMappedFileName(pName); // Check for null, etc.
        return name;
    }
}

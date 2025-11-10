package org.codehaus.plexus.components.io.filemappers;

import javax.inject.Named;

/**
 * Implementation of a flattening file mapper: Removes all directory parts.
 */
@Named(FlattenFileMapper.ROLE_HINT)
public class FlattenFileMapper extends AbstractFileMapper {
    /**
     * The flatten file mappers role-hint: "flatten".
     */
    public static final String ROLE_HINT = "flatten";

    @Override
    public String getMappedFileName(String pName) {
        String name = super.getMappedFileName(pName); // Check for null, etc.
        int offset = pName.lastIndexOf('/');
        if (offset >= 0) {
            name = name.substring(offset + 1);
        }
        offset = pName.lastIndexOf('\\');
        if (offset >= 0) {
            name = name.substring(offset + 1);
        }
        return name;
    }
}

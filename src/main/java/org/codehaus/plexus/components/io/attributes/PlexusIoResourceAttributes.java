package org.codehaus.plexus.components.io.attributes;

import org.jspecify.annotations.Nullable;

public interface PlexusIoResourceAttributes {
    int UNKNOWN_OCTAL_MODE = -1;

    boolean isOwnerReadable();

    boolean isOwnerWritable();

    boolean isOwnerExecutable();

    boolean isGroupReadable();

    boolean isGroupWritable();

    boolean isGroupExecutable();

    boolean isWorldReadable();

    boolean isWorldWritable();

    boolean isWorldExecutable();

    /**
     * Gets the unix user id.
     * @return The unix user id, may be null ("not set"), even on unix
     */
    Integer getUserId();

    /**
     * Gets the unix group id.
     * @return The unix group id, may be null ("not set"), even on unix
     */
    @Nullable
    Integer getGroupId();

    /**
     * Returns the user name of the user owning the file. Probably not null :)
     * @return The user name
     */
    @Nullable
    String getUserName();

    /**
     * The group name. May be null if groups are unsupported
     * @return the group names
     */
    @Nullable
    String getGroupName();

    /**
     * Octal mode attributes.
     * {@link #UNKNOWN_OCTAL_MODE} if unsupported on current file/file system
     */
    int getOctalMode();

    /**
     * Indicates if this is a symbolic link element.
     * For file-based resource attributes this value may be always "false" for versions prior to java7.
     * @return True if the file is a symlink or false if not.
     */
    boolean isSymbolicLink();
}

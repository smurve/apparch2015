package org.smurve.hsr2014.domain;

/**
 * Operations that can be performed on entities (domain objects) by a user.
 */
public enum EntityOperation {
    READ, READALL, DELETE, CREATEORUPDATE,

    /*
     * If a role has admin-access for a entityType, all different kinds of manipulations are allowed.
     */
    ADMIN_ACCESS;
}

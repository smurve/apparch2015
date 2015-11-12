package org.smurve.hsr2014.utils.db;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * Sets creationDate and modificationDate of a domain object (entity) on save and on update.
 */
@SuppressWarnings("serial")
public class DateManipulationInterceptor extends EmptyInterceptor {
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        Date now = new Date();
        setValue(state, propertyNames, "crDate", now);
        setValue(state, propertyNames, "modDate", now);
        return true;
    }

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames,
                                Type[] types) {

        Date now = new Date();
        setValue(currentState, propertyNames, "modDate", now);
        return true;
    }

    private void setValue(Object[] currentState, String[] propertyNames, String propertyToSet, Object value) {
        int index = ArrayUtils.indexOf(propertyNames, propertyToSet);
        if (index >= 0) {
            currentState[index] = value;
        }
    }
}

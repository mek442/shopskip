package com.skip;

import com.skip.exception.GenericBusinessException;
import com.skip.util.log.*;

import org.hibernate.Session;
import org.hibernate.Query;

import com.skip.HibernateQueryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Generic helper class for hibernate finder methods.
 *
 * @author  Mostafa  SkipTechnology
 * @version $Revision: 1.4 $, $Date: 2006/04/30 11:56:04 $
 */
public class HibernateFinderHelper {

    /**
     * The logger object.
     */
    private static Logger log = LogService.getLogger(HibernateFinderHelper.class);

    /**
     * Find all fields that apply to the following:
     * <p/>
     *
     * @param hibernateTemplate hibernate session
     * @param entity            entity class.
     * @param fieldName         field name to search
     * @param fieldValue        value to find
     * @param ignoreCase        true if case should be ignored
     * @param orderDirection    sorting direction. If true: ascending, false: descending.
     * @param distinct          true if we need distinct results.
     * @param startIndex        the start index within the result set (1 = first record); any zero/negative values are regarded
     *                          as 1, and any values greater than or equal to the total number of instances will simply return an empty
     *                          set.
     * @param endIndex          the end index within the result set, any values
     *                          greater than or equal to the total number of instances will cause the full set to be returned.
     * @return List of matching ModelBase matching entities.
     */
    public static List findFields(
            HibernateQueryHelper hibernateTemplate,
            Class entity,
            String fieldName,
            Object fieldValue,
            Boolean ignoreCase,
            Boolean orderDirection,
            Boolean distinct,
            Integer startIndex,
            Integer endIndex) throws GenericBusinessException {
        if (startIndex < 1) {
            startIndex = 1;
        }
        if ((endIndex - startIndex) < 0) {
            // Just return an empty list.
            return new ArrayList();
        }
        if (entity == null) {
            return null;
        }
        Session session = hibernateTemplate.getSession();
        String dist = "";
        if (distinct) {
            dist = " distinct ";
        }
        String queryString = " select  " + dist + " " + fieldName + " ";
        queryString += " from " + entity.getName();
        if (fieldValue != null) {
            queryString += " where ";
            if (ignoreCase) {
                queryString += "lower(" + fieldName + ") ";
                if (fieldValue instanceof String) {
                    fieldValue = ((String) fieldValue).toLowerCase();
                }
            } else {
                queryString += fieldName + " ";
            }
            queryString += " like :" + fieldName;
        }
        String direction = "";
        if (orderDirection != null) {
            direction = " order by " + fieldName;
            if (orderDirection) {
                direction += " asc ";
            } else {
                direction += " desc ";
            }
            queryString += direction;
        }
        log.debug("Query is: " + queryString);
        Query query = session.createQuery(queryString);
        if (fieldValue != null) {
            query.setParameter(fieldName, fieldValue);
        }
        query.setFirstResult(startIndex - 1);
        query.setMaxResults(endIndex - startIndex + 1);
        return query.list();
    }

    /**
     * Find all entities that apply to the following:
     * <p/>
     * If the fieldNameValues are null, a list of all entities is returned.
     * otherwise the query is executed on the passed values.
     * To order the result, pass a Map with field boolean pairs.
     * The field name is the field to order, the Boolean determines the sorting order:
     * asc = True
     * desc = False
     * no sorting = Null
     *
     * @param hibernateTemplate hibernate session
     * @param entity
     * @param fieldNameValues
     * @param isOr
     * @param orderFieldValues
     * @param startIndex        the start index within the result set (1 = first record); any zero/negative values are regarded
     *                          as 1, and any values greater than or equal to the total number of instances will simply return an empty
     *                          set.
     * @param endIndex          the end index within the result set, any values
     *                          greater than or equal to the total number of instances will cause the full set to be returned.
     * @return List of matching ModelBase matching entities.
     */
    public static List<SkipModelBase> findByField(
            HibernateQueryHelper hibernateTemplate,
            Class entity,
            Map<String, Object> fieldNameValues,
            Boolean isOr, Boolean ignoreCase, java.util.LinkedHashMap<String, Boolean> orderFieldValues,
            Integer startIndex,
            Integer endIndex) throws GenericBusinessException {
        if (startIndex < 1) {
            startIndex = 1;
        }
        if ((endIndex - startIndex) < 0) {
            // Just return an empty list.
            return new ArrayList();
        }
        if (entity == null) {
            return null;
        }
        Session session = hibernateTemplate.getSession();
        String queryString = "from " + entity.getName() + " ";
        queryString += buildWhereClause(fieldNameValues, isOr, ignoreCase);
        if (orderFieldValues != null) {
            // There is a order by part;
            boolean firstOrderField = true;
            for (String fieldName : orderFieldValues.keySet()) {
                Boolean orderDirection = orderFieldValues.get(fieldName);
                String direction = null;
                if (orderDirection) {
                    direction = " asc ";
                } else {
                    direction = " desc ";
                }
                if (firstOrderField) {
                    firstOrderField = false;
                    queryString += " order by ";
                    queryString += fieldName;
                    queryString += " ";
                    queryString += direction;

                } else {
                    queryString += " , ";
                    queryString += fieldName;
                    queryString += " ";
                    queryString += direction;
                }
            }

        }
        log.debug("Query is: " + queryString);
        Query query = session.createQuery(queryString);

        // Now bind the variables.
        if (fieldNameValues != null) {
            for (String fieldName : fieldNameValues.keySet()) {
                Object value = fieldNameValues.get(fieldName);
                if (ignoreCase && value instanceof String) {
                    value = ((String) value).toLowerCase();
                }
                query.setParameter(fieldName, value);
            }
        }
        query.setFirstResult(startIndex - 1);
        query.setMaxResults(endIndex - startIndex + 1);

        return query.list();
    }

    /**
     * Obtains the result count of the findby query.
     *
     * @param hibernateTemplate
     * @param entity
     * @param fieldNameValues
     * @param isOr
     * @param ignoreCase
     * @return the result size.
     * @throws GenericBusinessException
     */
    public static int findByFieldSize(
            HibernateQueryHelper hibernateTemplate,
            Class entity,
            Map<String, Object> fieldNameValues,
            Boolean isOr, Boolean ignoreCase) throws GenericBusinessException {
        Session session = hibernateTemplate.getSession();
        String queryString = "select count(*) from " + entity.getName() + " ";
        queryString += buildWhereClause(fieldNameValues, isOr, ignoreCase);
        log.debug("Query is: " + queryString);
        Query query = session.createQuery(queryString);
        // Now bind the variables.
        if (fieldNameValues != null) {
            for (String fieldName : fieldNameValues.keySet()) {
                Object value = fieldNameValues.get(fieldName);
                if (ignoreCase && value instanceof String) {
                    value = ((String) value).toLowerCase();
                }
                query.setParameter(fieldName, value);
            }
        }
        List list = query.list();
        return (Integer) list.get(0);
    }

    /* Build the where clause of the query */
    private static String buildWhereClause(Map<String, Object> fieldNameValues, Boolean isOr, Boolean ignoreCase) {
        // Build the where clause:
        String queryString = "";
        if (fieldNameValues != null) {
            // There is a where clause;
            boolean firstField = true;
            for (String fieldName : fieldNameValues.keySet()) {
                Object value = fieldNameValues.get(fieldName);
                boolean isStringValue = false;
                if (value instanceof String || value instanceof Character) {
                    isStringValue = true;
                }
                if (firstField) {
                    firstField = false;
                    queryString += " where ";
                    if (ignoreCase && isStringValue) {
                        queryString += " lower(";
                    }
                    queryString += fieldName;
                    if (ignoreCase && isStringValue) {
                        queryString += ") ";
                    }
                    queryString += " like :" + fieldName;
                    queryString += " ";
                } else {
                    if (isOr) {
                        queryString += " or ";
                    } else {
                        queryString += " and ";
                    }
                    if (ignoreCase && isStringValue) {
                        queryString += " lower(";
                    }
                    queryString += fieldName;
                    if (ignoreCase && isStringValue) {
                        queryString += ") ";
                    }
                    queryString += " like :" + fieldName;
                }

            }
        }
        return queryString;
    }

}

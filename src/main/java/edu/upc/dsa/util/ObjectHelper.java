package edu.upc.dsa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectHelper {
    public static String[] getFields(Object entity) {

        Class theClass = entity.getClass();

        Field[] fields = theClass.getDeclaredFields();

        String[] sFields = new String[fields.length];
        int i=0;

        for (Field f: fields) sFields[i++]=f.getName();

        return sFields;

    }

    public static void setter(Object object, String property, Object value) {
        try {
            Method method = object.getClass().getMethod("set" + capitalize(property), value.getClass());
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Capitaliza (pone en mayusculas) la primera letra de la cadena dada.
     * Si la cadena es nula o está vacía, la devuelve tal cual.
     *
     * @param str la cadena a capitalizar
     * @return la cadena capitalizada
     */
    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Object getter(Object object, String property) {
        try {
            Method method = object.getClass().getMethod("get" + capitalize(property));
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
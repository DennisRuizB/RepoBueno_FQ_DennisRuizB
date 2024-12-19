package edu.upc.dsa.DB;

import edu.upc.dsa.models.User;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {
    void save(Object entity);                                           // Crud
    void close();// cRud
    void update(Object object);                                         // crUd
    void delete(Object object);
    Object get(Class theClass,String key , Object value);// cruD
    public Object getGeneralisimo(Class theClass, HashMap key); // donde key son los nombres de la columnas y value el valor



    List<Object> findAll(Class theClass);                               // cR
    List<Object> findAll(Class theClass, HashMap params);
    List<Object> query(String query, Class theClass, HashMap params);
}

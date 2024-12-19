package edu.upc.dsa.DB;

import edu.upc.dsa.models.User;
import edu.upc.dsa.util.ObjectHelper;
import edu.upc.dsa.util.QueryHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SessionImpl implements Session {
    private final Connection conn;

    public SessionImpl(Connection conn) {
        this.conn = conn;
    }

    public void save(Object entity) {


        // INSERT INTO Partida () ()
        String insertQuery = QueryHelper.createQueryINSERT(entity);
        // INSERT INTO User (ID, lastName, firstName, address, city) VALUES (0, ?, ?, ?,?)


        PreparedStatement pstm = null;

        try {
            pstm = conn.prepareStatement(insertQuery);

            int i = 1;
            for (String field: ObjectHelper.getFields(entity)) {

                    pstm.setObject(i, ObjectHelper.getter(entity, field));

                i++;
            }

            pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Object get(Class theClass, String key, Object value) {
        String selectQuery = QueryHelper.createQuerySELECT(theClass, key);
        //Object value = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(selectQuery);
            pstm.setObject(1, value);
            rs = pstm.executeQuery();
            if (rs.next()) {
                Object object = theClass.newInstance();
                for (String field : ObjectHelper.getFields(object)) {
                    ObjectHelper.setter(object, field, rs.getObject(field));
                }
                return object;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public Object getGeneralisimo(Class theClass, HashMap key) {
        String selectQuery = QueryHelper.createQuerySELECTgeneralisimo(theClass, key);
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = conn.prepareStatement(selectQuery);
            int i = 1;
            for (Object val : key.values()) {
                pstm.setObject(i++, val);
            }
            rs = pstm.executeQuery();
            if (rs.next()) {
                Object object = theClass.newInstance();
                for (String field : ObjectHelper.getFields(object)) {
                    ObjectHelper.setter(object, field, rs.getObject(field));
                }
                return object;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void update(Object entity) {
        String tableName = entity.getClass().getSimpleName();
        String[] fields = ObjectHelper.getFields(entity);
        StringBuilder sb = new StringBuilder("UPDATE ").append(tableName).append(" SET ");

        boolean firstField = true;
        for (String field : fields) {
            if (!field.equals("ID")) {
                if (!firstField) {
                    sb.append(", ");
                }
                sb.append(field).append(" = ?");
                firstField = false;
            }
        }
        sb.append(" WHERE ID = ?");

        PreparedStatement pstm = null;
        try {
            // Prepara la sentencia SQL
            pstm = conn.prepareStatement(sb.toString());
            int i = 1;

            // Configura los valores de los campos
            for (int j = 0; j < fields.length + 1; j++) {
                if (j < fields.length) {
                    String field = fields[j];
                    if (!field.equals("ID")) {
                        Object value = ObjectHelper.getter(entity, field);
                        if (value != null) {
                            pstm.setObject(i++, value);
                        } else {
                            pstm.setNull(i++, java.sql.Types.NULL);
                        }
                    }
                } else { // Última iteración: configuramos el ID
                    Object idValue = ObjectHelper.getter(entity, "Id");
                    if (idValue != null) {
                        pstm.setObject(i, idValue);
                    } else {
                        throw new SQLException("ID value is null. Cannot perform update without a valid ID.");
                    }
                }
            }

            // Ejecuta la actualización
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Log de errores, considera usar un logger
        } finally {
            // Cierra el PreparedStatement
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public void delete(Object object) {

    }

    public List<Object> findAll(Class theClass) {
        return null;
    }


    public List<Object> findAll(Class theClass, HashMap params) { //selecciona todo lo que coincida con los parametros que le pasas y devuelve una lista de objetos
        String theQuery = QueryHelper.createSelectFindAll(theClass, params);
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Object> result = new ArrayList<>();

        try {
            pstm = conn.prepareStatement(theQuery);

            int i = 1;
            for (Object value : params.values()) {
                pstm.setObject(i++, value);
            }

            rs = pstm.executeQuery();

            while (rs.next()) {
                Object object = theClass.newInstance();
                for (String field : ObjectHelper.getFields(object)) {
                    ObjectHelper.setter(object, field, rs.getObject(field));
                }
                result.add(object);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<Object> query(String query, Class theClass, HashMap params) {
        return null;
    }
}

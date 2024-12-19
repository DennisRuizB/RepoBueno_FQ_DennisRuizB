package edu.upc.dsa.DB;


import java.sql.Connection;
import java.sql.DriverManager;

public class FactorySession {

    public static Session openSession() {
        Connection conn = getConnection();
        Session session = new SessionImpl(conn);
        return session;
    }



    public static Connection getConnection() {
        String db = DBUtils.getDb();
        String host = DBUtils.getDbHost();
        String port = DBUtils.getDbPort();
        String user = DBUtils.getDbUser();
        String pass = DBUtils.getDbPasswd();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://" + host + ":" + port + "/" +
                    db + "?user=" + user + "&password=" + pass);
        } catch (Exception e) {
            System.err.println("Error al intentar conectar a la base de datos:");
            System.err.println("URL: jdbc:mariadb://" + host + ":" + port + "/" + db);
            System.err.println("Usuario: " + user);
            e.printStackTrace();
        }
        return connection;
    }
    public static Session openSession(String url, String user, String password) {
        return null;
    }
}

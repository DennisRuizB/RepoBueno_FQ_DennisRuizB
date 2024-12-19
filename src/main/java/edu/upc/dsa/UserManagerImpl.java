package edu.upc.dsa;

import edu.upc.dsa.DB.FactorySession;
import edu.upc.dsa.DB.Session;
import edu.upc.dsa.DB.UserDAO;
import edu.upc.dsa.DB.UserDAOImpl;
import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.Relacion;
import edu.upc.dsa.models.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
public class UserManagerImpl implements UserManager{
    private static UserManager instance;
    protected List<User> users;
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }

    @Override
    public User Register(User u) {
        Session session = FactorySession.openSession();
        try {
            session.save(u);
            logger.info("new User added: " + u.getDatos());
        }
        catch (Exception e) {
            logger.error("Error al añadir el usuario " + u.getDatos());
            e.printStackTrace();

        }
        finally {
            session.close();
        }

        return u;
    }

    @Override
    public User Register(String username, String password, String email) {
        User u = new User(email, username,password);
        Session session = FactorySession.openSession();
        try {
            session.save(u);
            logger.info("new User added: " + u.getDatos());
        }
        catch (Exception e) {
            logger.error("Error al añadir el usuario " + u.getDatos());
            e.printStackTrace();

        }
        finally {
            session.close();
        }
        return u;
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public User updateUser(User u) {
        for (int i=0; i<this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(u.getUsername())) {
                this.users.set(i, u);
                logger.info("updateUser("+u.getUsername()+"): "+u.getDatos());
                return u;
            }
        }
        logger.warn("not found "+u.getUsername());
        return null;
    }

    @Override
    public User deleteUser(String username, String password) {
        logger.info("Trying deleteUser("+username+")");
        for (int i=0; i<this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                logger.info("deleteUser("+username+"): "+u.getDatos());
                this.users.remove(i);
                return u;
            }
        }
        logger.warn("not found "+username);
        return null;
    }

    @Override
    public User IniciarSesion(String user, String password) {

        Session session = FactorySession.openSession();
        HashMap<String, String> key = new HashMap<>();
        key.put("username", user);
        key.put("password", password);
        try {
            User u = (User) session.getGeneralisimo(User.class, key);
            if (u != null) {
                logger.info("LogIn succesful de user " + user);
                return u;
            }
            else {
                logger.warn("LogIn fail con user " + user);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);
        return ret;
    }


    @Override
    public List<Products> getProductsOfUser(User u) {
        UserDAO userDAO = new UserDAOImpl();
        return userDAO.getProductsOfUser(u);
    }


    @Override
    public void addProductToUser(User u, Products p) {
        UserDAO userDAO = new UserDAOImpl();
        userDAO.addProductToUser(u,p);
    }

    @Override
    public User getUser(String id) {
        Session session = FactorySession.openSession();
        try {
            HashMap<String, String> key = new HashMap<>();
            key.put("id", id);
            User u = (User) session.getGeneralisimo(User.class, key);
            if (u != null) {
                logger.info("Usuario encontrado con id " + id);
                return u;
            }
            else {
                logger.warn("Usuario no encontrado con id " + id);
            }
        }
        catch (Exception e) {
            logger.error("Error al obtener el usuario con id " + id);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }



}

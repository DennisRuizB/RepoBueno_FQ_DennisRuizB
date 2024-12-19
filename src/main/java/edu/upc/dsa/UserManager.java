package edu.upc.dsa;
import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {

    public User Register(String username, String password, String email);
    public User Register(User u);

    public List<User> findAll();
    public User IniciarSesion(String username, String password);
    public User updateUser(User u);
    public User deleteUser(String username, String password);
    public List<Products> getProductsOfUser(User u);
    public void addProductToUser(User u, Products p);
    public User getUser(String username);




    public int size();


}

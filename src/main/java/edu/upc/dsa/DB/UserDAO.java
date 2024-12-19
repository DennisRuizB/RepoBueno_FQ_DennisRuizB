package edu.upc.dsa.DB;

import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserDAO {
    // Otros métodos existentes
    void addProductToUser(User u, Products p);
    List<Products> getProductsOfUser(User u);
}

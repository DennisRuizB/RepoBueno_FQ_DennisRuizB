package edu.upc.dsa.DB;

import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.Relacion;
import edu.upc.dsa.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class UserDAOImpl implements UserDAO {
    // Otros métodos existentes
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Override
    public List<Products> getProductsOfUser(User u) {
        Session session = FactorySession.openSession();
        List<Products> products = new ArrayList<>();
        try {
            // Obtener las relaciones del usuario
            HashMap<String, String> key = new HashMap<>();
            key.put("user_id", u.getId());
            List<Relacion> relaciones = session.findAll(Relacion.class, key);

            // Obtener los productos basados en las relaciones
            for (Relacion relacion : relaciones) {
                HashMap<String, Integer> productKey = new HashMap<>();
                productKey.put("id", relacion.getProduct_id());
                Products product = (Products) session.getGeneralisimo(Products.class, productKey);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public void addProductToUser(User u, Products p) {
        Session session = FactorySession.openSession();
        try {
            // Crear la relación entre el usuario y el producto
            Relacion relacion = new Relacion();
            relacion.setUser_id(u.getId());
            relacion.setProduct_id(p.getId());
            session.save(relacion);

            // Restar el precio del producto del dinero del usuario
            int nuevoDinero = u.getDinero() - p.getPrice();
            u.setDinero(nuevoDinero);

            // Actualizar el dinero del usuario en la base de datos
            session.update(u);

            logger.info("Product added to user: " + u.getId() + " with product: " + p.getId() + ". New balance: " + nuevoDinero);
        } catch (Exception e) {
            logger.error("Error adding product to user: " + u.getId() + " with product: " + p.getId());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
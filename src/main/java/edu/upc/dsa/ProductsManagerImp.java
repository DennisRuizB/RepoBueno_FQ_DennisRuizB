package edu.upc.dsa;

import edu.upc.dsa.DB.Session;
import edu.upc.dsa.models.Products;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

import edu.upc.dsa.DB.FactorySession;
import edu.upc.dsa.DB.Session;

public class ProductsManagerImp implements ProductsManager {
    private static ProductsManager instance;
    protected List<Products> products;
    final static Logger logger = Logger.getLogger(ProductsManagerImp.class);

    private ProductsManagerImp() {
        this.products = new LinkedList<>();
    }

    public static ProductsManager getInstance() {
        if (instance==null) instance = new ProductsManagerImp();
        return instance;
    }

    @Override
    public Products addProduct(Products p) {
        Session session = FactorySession.openSession();

        try {
            session.save(p);
            logger.info("new Object added: " + p.getDatos() );
        }
        catch (Exception e) {
            logger.error("Error al añadir el producto " +  p.getDatos());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return p;
    }

    @Override
    public Products addProduct(String nameProduct, String price) {


        int priceInt = Integer.parseInt(price); // Convertir el precio a entero
        Session session = FactorySession.openSession();
        int id = 1;
        while (true) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            Products product = (Products) session.getGeneralisimo(Products.class, map);
            if (product == null) {
                break;
            }
            id++;
        }
        Products p = new Products(id, nameProduct, priceInt);
        try {
            session.save(p);
            logger.info("new Object added: " + p.getDatos() );
        }
        catch (Exception e) {
            logger.error("Error al añadir el producto " +  p.getDatos());
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return p;
    }

    @Override
    public Products addProduct(int id, String nameProduct, int price) {
        Session session = FactorySession.openSession();
        Products p = new Products(id, nameProduct, price);
        try {
            session.save(p);
            logger.info("new Object added: " + nameProduct + " with id " + id + " and price " + price);
        }
        catch (Exception e) {
            logger.error("Error al añadir el producto " + nameProduct);
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return p;
    }

    @Override
    public List<Products> getProducts() {
        List<Products> products = null;
        Session session = FactorySession.openSession();
        try {
            HashMap<String, Integer> key = new HashMap<>();
            key.put("1", 1);
            products = session.findAll(Products.class,key );
            logger.info("Productos recibidos:" + products.size());
        } catch (Exception e) {
            logger.error("Error al obtener los productos");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return products;
    }

    @Override
    public int size() {
        int ret = this.products.size();
        logger.info("size productos " + ret);
        return ret;
    }

    @Override
    public Products getProduct(String name) {
        Session session = FactorySession.openSession();
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("nameProduct", name);
            Products product = (Products) session.getGeneralisimo(Products.class, params);
            if (product != null) {
                logger.info("getProduct(" + name + "): " + product.getDatos());
                return product;
            } else {
                logger.warn("not found " + name);
            }
        } catch (Exception e) {
            logger.error("Error getting product with name " + name);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Products findProduct(int id) {
        Session session = FactorySession.openSession();
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);
            Products product = (Products) session.getGeneralisimo(Products.class, params);
            if (product != null) {
                logger.info("Producto con id " + id + " encontrado: " + product.getDatos());
                return product;
            } else {
                logger.warn("not found " + id);
            }
        } catch (Exception e) {
            logger.error("Error getting product with id " + id);
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


}

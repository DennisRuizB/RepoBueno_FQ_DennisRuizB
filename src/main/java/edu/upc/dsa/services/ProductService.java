package edu.upc.dsa.services;

import edu.upc.dsa.ProductsManager;
import edu.upc.dsa.ProductsManagerImp;
import edu.upc.dsa.models.Products;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/objects", description = "Endpoint to Object Service")
@Path("/objects")
public class ProductService {

    private ProductsManager om;

    public ProductService() {
        this.om = ProductsManagerImp.getInstance();

        if (om.size() == 0) {
                //this.om.addProduct("coco", "6");
                //this.om.addProduct("Pepsi", "2");
                //this.om.addProduct("Fanta", "3");
            }
        }

    //GET all products
    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Products.class, responseContainer="List"),
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjects() {
        List<Products> products = this.om.getProducts();
        GenericEntity<List<Products>> entity = new GenericEntity<List<Products>>(products) {};
        return Response.status(201).entity(entity).build()  ;
    }


    // GET all products
    @GET
    @ApiOperation(value = "get all Objects", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Products.class, responseContainer = "List"),
    })
    @Path("/Android")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectsForAndroid() {
        List<Products> products = this.om.getProducts();
        GenericEntity<List<Products>> entity = new GenericEntity<List<Products>>(products) {};
        return Response.status(201).entity(entity).build();
    }


    //add product
//    @POST
//    @ApiOperation(value = "add a new Object", notes = "asdasd")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Successful", response= Products.class),
//            @ApiResponse(code = 500, message = "invalid data")
//    })
//    @Path("/add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addObject(@FormParam("nameProduct") String nameProduct, @FormParam("price") String price) {
//        Products o = new Products(this.om.size()+1,nameProduct, Integer.parseInt(price));
//        if (o.getNameProduct()==" " || o.getPrice()==0){
//            return Response.status(500).entity(o).build();
//        }
//        this.om.addProduct(o);
//        System.out.println("Object added");
//        return Response.status(201).entity(o).build();
//    }

}

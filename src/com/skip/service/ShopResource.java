package com.skip.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.stereotype.Component;

import com.skip.config.ApplicationConfig;
import com.skip.resource.CreateShopRequest;
import com.skip.resource.ExternalUser;
import com.skip.resource.ListShopResponse;
import com.skip.resource.ProductResponse;
import com.skip.resource.ShopResponse;

@Path("/shop")
@Component
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ShopResource {

	
    private ConnectionFactoryRegistry connectionFactoryLocator;

    @Autowired
    protected ShopService shopService;
  
    @Context
    protected UriInfo uriInfo;

    @Path("{shopId}/product/{barcode}")
    @GET
    public Response getProduct(@Context SecurityContext sc,@PathParam("shopId") Long shopId, @PathParam("barcode") String barcode) {
        ExternalUser userMakingRequest = (ExternalUser)sc.getUserPrincipal();
        ProductResponse productByBarCode = shopService.getProductByBarCode(shopId, barcode);
        return Response.ok().entity(productByBarCode).build();
    }
    
    @POST
    public Response createShop(CreateShopRequest shop){
    	ShopResponse response;
		try {
			response = shopService.createShop(shop);
			return Response.ok().entity(response).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	
    }
    
    @Path("{shopId}")
    @GET
    public Response getShop(@PathParam("shopId") Long shopId){
    	ShopResponse response;
		try {
			response = shopService.getShop(shopId);
			return Response.ok().entity(response).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	
    }
    
    @Path("/location/{latlog}")
    @GET
    public Response getShop(@PathParam("latlog") String location){
    	List<ShopResponse> response;
		try {
			response = shopService.getShop(location);
			ListShopResponse listShopResponse = new ListShopResponse();
			listShopResponse.setShops(response);
			return Response.ok().entity(listShopResponse).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
    	
    }
    
}

package com.skip.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skip.config.ApplicationConfig;
import com.skip.encryption.AESSkipEncryption;
import com.skip.entity.product.Product;
import com.skip.entity.shop.Shop;
import com.skip.exception.GenericBusinessException;
import com.skip.exception.ServiceInstantiationException;
import com.skip.exception.UnknownServiceException;
import com.skip.resource.CreateShopRequest;
import com.skip.resource.ProductResponse;
import com.skip.resource.ShopResponse;
import com.skip.rest.exception.NotFoundException;
import com.skip.servicelocator.ServiceLocator;
import com.skip.session.IProductSession;
import com.skip.session.IShopSession;
import com.skip.session.IUserSession;
import com.skip.session.ProductSession;
import com.skip.session.ShopSession;

@Service("shopService")
public class ShopService {
	private ServiceLocator serviceLocator;

	private IProductSession productRepository;

	private ApplicationConfig applicationConfig;

	private IShopSession shopRepository;
	

	@Autowired
	public ShopService(ServiceLocator serviceLocator,
			ApplicationConfig applicationConfig, AESSkipEncryption encryption)
			throws UnknownServiceException, ServiceInstantiationException {
		// this(validator);
		this.productRepository = (ProductSession) serviceLocator
				.getService(ServiceLocator.PRODUCTSESSION_SERVICE);
		  this.shopRepository  = (IShopSession)serviceLocator.getService(ServiceLocator.SHOPSESSION_SERVICE);
		this.applicationConfig = applicationConfig;

	}

	public ProductResponse getProductByBarCode(Long shopId, String barcode) {
         try {
			Product product =    productRepository.findProductByShopidAndBarcode(shopId,barcode);
			if(product==null){
				throw new NotFoundException();
			}
			if(product!=null){
			ProductResponse productResponse = new ProductResponse();
			productResponse.setName(product.getName());
			productResponse.setBarcode(product.getBarcode());
			productResponse.setUnitprice(product.getUnitPrice().toPlainString());
			productResponse.setManufacturer(product.getManufacturer());
			productResponse.setType(product.getType());
			return productResponse;
			}
			
		} catch (GenericBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         return null;
	}

	public ShopResponse createShop(CreateShopRequest createShop) throws Exception {
		Shop shop = new Shop();
		shop.setName(createShop.getName());
		shop.setAddress(createShop.getAddress());
		shop.setLongitude(createShop.getLongitude());
		shop.setLatitude(createShop.getLatitude());
		shop.setApikey(createShop.getApikey());
		shop.setSecret(createShop.getSecret());
		shop.setAccessURL(createShop.getAccessurl());
		Shop addShop = shopRepository.addShop(shop);
		ShopResponse response = new ShopResponse();
		response.setName(addShop.getName());
		response.setAddress(addShop.getAddress());
		response.setLongitude(addShop.getLongitude());
		response.setLatitude(addShop.getLatitude());
		response.setShopId(addShop.getShopid());
	
		return response;
	}

	public ShopResponse getShop(Long shopId) throws GenericBusinessException {
		
		Shop addShop = shopRepository.getShop(shopId);
		ShopResponse response = new ShopResponse();
		response.setName(addShop.getName());
		response.setAddress(addShop.getAddress());
		response.setLongitude(addShop.getLongitude());
		response.setLatitude(addShop.getLatitude());
		response.setShopId(addShop.getShopid());
	
		return response;
		
		
	}

	public List<ShopResponse> getShop(String location) throws Exception {
		String[] locations = location.split(",");
		List<Shop> shops = shopRepository.findShopByLongitudeAndLatitude(new BigDecimal(locations[0]),new BigDecimal(locations[1]));
		List<ShopResponse> responses = new ArrayList<ShopResponse>();
		
		for (Shop shop : shops) {
			ShopResponse response = new ShopResponse();
			response.setName(shop.getName());
			response.setAddress(shop.getAddress());
			response.setLongitude(shop.getLongitude());
			response.setLatitude(shop.getLatitude());
			response.setShopId(shop.getShopid());
			responses.add(response);
		}
		
	
		return responses;
	}

}

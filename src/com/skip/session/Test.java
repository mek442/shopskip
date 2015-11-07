package com.skip.session;

import com.skip.entity.product.Product;
import com.skip.exception.ServiceInstantiationException;
import com.skip.exception.UnknownServiceException;
import com.skip.servicelocator.LocatableService;
import com.skip.servicelocator.ServiceLocator;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ProductSession pservice = (ProductSession)ServiceLocator.getService(ServiceLocator.PRODUCTSESSION_SERVICE);
			Product product = pservice.getProduct(Long.valueOf(1));
			System.out.println(product.getBarcode());
			System.out.println(product.getShop().getAddress());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}

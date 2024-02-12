package com.mpc.service;

import com.mpc.bindings.ApplyDiscountOrTaxRequest;
import com.mpc.bindings.Product;
import com.mpc.bindings.ProductRequest;

public interface ProductService {
	
	public Product getProductDetails(ProductRequest re);
	public  Product getProductById(Integer pid);
	
	public boolean saveProduct(ProductRequest req);
	public boolean deleteProduct(Integer pid);
	public boolean updateProduct(Product p);
	public Product applyDiscountOrTax(ApplyDiscountOrTaxRequest request);
	 public Product getProductByAttributes(String name, String description, int price, int quantity);
}

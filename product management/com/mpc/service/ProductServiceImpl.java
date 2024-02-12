package com.mpc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mpc.bindings.ApplyDiscountOrTaxRequest;
import com.mpc.bindings.Product;
import com.mpc.bindings.ProductRequest;
@Service
public class ProductServiceImpl implements ProductService {
	private List<Product> plist = new ArrayList<>();

	@Override
	public Product getProductDetails(ProductRequest re) {
		return getProductByAttributes(re.getName(), re.getDescription(), re.getPrice(), re.getQuantity());

	}

	@Override
	public Product getProductById(Integer pid) {
		for (Product product : plist) {
			if (product.getProduct_id() == pid) {
				return product;
			}
		}
		return null;

	}

	@Override
	public boolean saveProduct(ProductRequest req) {
		Product p = new Product();
		int productId = 1;
		p.setProduct_id(productId++);
		BeanUtils.copyProperties(req, p);
		plist.add(p);
		return plist.contains(p);
	}

	public Product getProductByAttributes(String name, String description, int price, int quantity) {
		for (Product product : plist) {
			if (product.getName().equals(name) && product.getDescription().equals(description)
					&& product.getPrice() == price && product.getQuantity() == quantity) {
				return product;
			}
		}
		return null;
	}
	
	public boolean deleteProduct(Integer pid) {
		Product id = getProductById(pid);
		if(plist.contains(id)){
			plist.remove(id);
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean updateProduct(Product p) {
		for(Product product:plist) {
			if(product.getProduct_id()==p.getProduct_id()) {
				BeanUtils.copyProperties(p, product);
				return true;
			}
		}
		return false;
		
		
	}
	 @Override
	    public Product applyDiscountOrTax(ApplyDiscountOrTaxRequest request) {
	        Product product = getProductById(request.getProductId());
	        if (product == null) {
	            throw new IllegalArgumentException("Product not found");
	        }

	        // Apply discount or tax based on the request
	        if (request.getDiscountPercentage() != null) {
	            double discountPercentage = request.getDiscountPercentage();
	            double discountAmount = product.getPrice() * (discountPercentage / 100);
	            double discountedPrice = product.getPrice() - discountAmount;
	            product.setPrice((int)discountedPrice);
	        } else if (request.getTaxRate() != null) {
	            double taxRate = request.getTaxRate();
	            double taxAmount = product.getPrice() * (taxRate / 100);
	            double newPrice = product.getPrice() + taxAmount;
	            product.setPrice((int)newPrice);
	        } else {
	            throw new IllegalArgumentException("Either discount percentage or tax rate must be provided");
	        }

	        return product;
	    }

}

package com.mpc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mpc.bindings.ApplyDiscountOrTaxRequest;
import com.mpc.bindings.Product;
import com.mpc.bindings.ProductRequest;
import com.mpc.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService pser;
	
	
	  @PostMapping("/save")
	    public ResponseEntity<String> saveProducts(@RequestBody ProductRequest req) {
	        boolean isSaved = pser.saveProduct(req);
	        if (isSaved) {
	            return ResponseEntity.ok("Successfully saved product");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops! Some error occurred");
	        }
	    }

	    @PutMapping("/update")
	    public ResponseEntity<String> productUpdate(@RequestBody Product p) {
	        boolean isUpdated = pser.updateProduct(p);
	        if (isUpdated) {
	            return ResponseEntity.ok("Successfully updated product");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops! Some error occurred");
	        }
	    }
	    
	    @PostMapping("/applyDiscountOrTax")
	    public ResponseEntity<Object> applyDiscountOrTax(@RequestBody ApplyDiscountOrTaxRequest request) {
	        try {
	            Product updatedProduct = pser.applyDiscountOrTax(request);
	            return ResponseEntity.ok(updatedProduct);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to apply discount or tax: " + e.getMessage());
	        }
	    }
	
	
	@GetMapping("/getbyid")
	public ResponseEntity<Product> getProductById(@RequestParam(name = "pid") Integer productId) {
	    Product product = pser.getProductById(productId);
	    if (product != null) {
	        return ResponseEntity.ok(product);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@DeleteMapping("/delete/{pid}")
	public ResponseEntity<String> deleteProductById(@PathVariable("pid") Integer productId) {
	    boolean isDeleted = pser.deleteProduct(productId);
	    if (isDeleted) {
	        return ResponseEntity.ok("Successfully deleted product");
	    } else {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops! Some error occurred");
	    }
	}

	
}

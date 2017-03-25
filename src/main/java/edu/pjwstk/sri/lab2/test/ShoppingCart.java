package edu.pjwstk.sri.lab2.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import edu.pjwstk.sri.lab2.dao.CategoryDao;
import edu.pjwstk.sri.lab2.dao.ProductDao;
import edu.pjwstk.sri.lab2.dto.OrderItem;
import edu.pjwstk.sri.lab2.model.Product;

@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)//M
@TransactionAttribute(TransactionAttributeType.REQUIRED)//M 
public class ShoppingCart{
	@Inject
	private ProductDao productService;
	@Inject
	private OrderItem orderItem;
	@Resource
	EJBContext ejbContext;

	private List<OrderItem> cart=new ArrayList<OrderItem>();
	
	private List<Product> products=new ArrayList<Product>();
	
	public void addToCart(Product product,int quantity){
		orderItem=new OrderItem();
		orderItem.setProduct(product);
		orderItem.setAmount(quantity);
		cart.add(orderItem);
	}
	public void removeFromCart(Product product){
		Product p=null;
		for(OrderItem oi:cart){
			if(oi.getProduct()==product){
				cart.remove(product);
				break;
			}
		}
	}
	public void makeOrder(){
		products=productService.listAll(1, 100);
		Product pNew;
		Product pOld;
		int quantity;
		int initialStock;
		int newStock;
		for(OrderItem oi:cart){
			pNew=oi.getProduct();
			quantity=oi.getAmount();
			pOld=productService.findById(pNew.getId());
			initialStock=pOld.getStock();
			newStock=initialStock-quantity;
			if(newStock<0){
				ejbContext.setRollbackOnly();//wycofujemy transakcje
				System.out.println("Braki w magazynie! -transackcja wycofana");
				return;
			}
			pOld.setStock(newStock);
			productService.update(pOld);
		}
	}
}
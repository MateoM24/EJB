package edu.pjwstk.sri.lab2.test;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.pjwstk.sri.lab2.model.Category;
import edu.pjwstk.sri.lab2.model.Product;
import edu.pjwstk.sri.lab2.dao.CategoryDao;
import edu.pjwstk.sri.lab2.dao.ProductDao;;

@Named("testBean")
@RequestScoped
public class TestBean implements Serializable {

	private static final long serialVersionUID = 24010203L;
	
	//@PersistenceContext(unitName = "sri2-persistence-unit")
	//private EntityManager em; niepotrzebne
	
	@Inject
	private CategoryDao catService;
	
	@Inject
	private ProductDao productService;
	
	@Inject
	private ShoppingCart shoppingCart;
	
	public TestBean() {
	}
	
	public void test() {
		System.out.println("==========start testBean===========");
		List<Category> listCat = catService.getList();
//		System.out.println(listAll);
		System.out.println("==========Kategorie:===========");
		for(Category c:listCat){
			System.out.println(c);
		}
		System.out.print(" ===========Produkty:============");
		List<Product> listProd = productService.listAll(0, 100);
		Product pid0 = null;
		Product pid1 = null;
		Product pid3 = null;
		for(Product p:listProd){
			long id=p.getId();
			if(id==2000l)pid0=p;
			if(id==2001l)pid1=p;
			if(id==2003l)pid3=p;
		}
		
		shoppingCart.addToCart(pid0, 2);
		shoppingCart.makeOrder();
		System.out.println("==Koniec testu==");
		
	}

}

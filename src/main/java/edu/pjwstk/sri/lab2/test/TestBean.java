package edu.pjwstk.sri.lab2.test;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		for(Product prod:listProd){
			System.out.println(prod);
		}
		Product pid0 = null;
		Product pid1 = null;
		Product pid2 = null;
		Product pid3 = null;
		for(Product p:listProd){
			long id=p.getId();
			p.setVersion(1);
			if(id==2000l)pid0=p;
			if(id==2001l)pid1=p;
			if(id==2002l)pid2=p;
			if(id==2003l)pid3=p;
		}
		
		//testy shoppingCart
		//test 1 transakcja ok
		shoppingCart.addToCart(pid0, 2);
		shoppingCart.addToCart(pid1, 2);
		shoppingCart.makeOrder();
		shoppingCart.clearCart();
		System.out.println("koniec testu 1.");
		//test 2 transakcja bedzie wycofana
		shoppingCart.addToCart(pid2, 3);
		shoppingCart.addToCart(pid3, 3);
		shoppingCart.makeOrder();
		shoppingCart.clearCart();
		System.out.println("koniec testu 2.");
		
		//testy CategoryDAO i ProdutDao
		Category newCategory = new Category();
		//newCategory.setId(1017L);
		Set<Category> catSet=new HashSet();
		catSet.add(catService.findById(1002L));
		newCategory.setChildCategories(catSet);
		newCategory.setParentCategory(catService.findById(1003L));
		newCategory.setName("NowaKategoria");
		catService.create(newCategory);
		Product newProduct = new Product();
		newProduct.setCategory(catService.findById(1003L));
		//newProduct.setId(2017L);
		newProduct.setName("NowyProdukt");
		newProduct.setStock(100);
		newProduct.setVersion(1);
		productService.create(newProduct);
		
		System.out.println("find by Id (dell xps): "+productService.findById(2005L));
		productService.deleteById(2005L);
	
		Category catEdytowana = catService.findById(1001L);
		catEdytowana.setName(catEdytowana.getName()+" kategoria edytowana");
		catService.update(catEdytowana);
		catService.deleteById(1009L);
		System.out.println("usunąłem kategorię Programy antywirusowe o id=1009");
		System.out.println("==Koniec testów==");
		
	}

}

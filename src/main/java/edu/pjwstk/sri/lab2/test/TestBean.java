package edu.pjwstk.sri.lab2.test;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.pjwstk.sri.lab2.model.Category;
import edu.pjwstk.sri.lab2.dao.CategoryDao;;

@Named("testBean")
@RequestScoped
public class TestBean implements Serializable {

	private static final long serialVersionUID = 24010203L;
	
	@PersistenceContext(unitName = "sri2-persistence-unit")
	private EntityManager em;
	
	@Inject
	private CategoryDao catService;
	
	public TestBean() {
	}
	
	public void test() {
		System.out.println("==========start testBean===========");
		List<Category> listAll = catService.getList();
//		System.out.println(listAll);
		for(Category c:listAll){
			System.out.println(c);
		}
		System.out.print(" ===========Działa! - koniec Test Bean:) ============");
	}

}

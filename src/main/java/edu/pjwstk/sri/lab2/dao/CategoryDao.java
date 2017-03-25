package edu.pjwstk.sri.lab2.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
//import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import edu.pjwstk.sri.lab2.model.Category;

/**
 * DAO for Category
 */
//@Stateless
@Startup//M
@Singleton//M
@TransactionManagement(TransactionManagementType.CONTAINER)//M
@TransactionAttribute(TransactionAttributeType.REQUIRED)//M //było REQUIRES_NEW
public class CategoryDao {
	@PersistenceContext(unitName = "sri2-persistence-unit")
	private EntityManager em;
	private List<Category> categories;
	public void create(Category entity) {
		em.persist(entity);
	}

	public void deleteById(Long id) {
		Category entity = em.find(Category.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)//M
	public Category findById(Long id) {
		return em.find(Category.class, id);
	}

	public Category update(Category entity) {
		return em.merge(entity);
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)//M
	@Schedule(minute="*/10",hour="*")//M minute="*/10"
	@PostConstruct//M
	private void listAll() {
		System.out.println("-----dziala-CategoryDao------");
					//public void listAll(Integer startPosition, Integer maxResult) {
					//public List<Category> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Category> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.parentCategory LEFT JOIN FETCH c.childCategories ORDER BY c.id",
						Category.class);
			/*		if (startPosition != null) {
				findAllQuery.setFirstResult(startPosition);
			}
			if (maxResult != null) {
				findAllQuery.setMaxResults(maxResult);
			}
		 	findAllQuery.getResultList();*/
	categories=findAllQuery.getResultList();
		System.out.println("-------koniec categoryDao------");
	}
	public List<Category> getList(){//M
		return categories;
	}
}

package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.ListItem;

/**
 * @author Amy Miles - almiles
 * CIS 175 - Fall 2021
 * Sep 28, 2021
 */
public class ListItemHelper {

	
		static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("ShoppingList");
	
		public void insertItem(ListItem li) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(li);
			em.getTransaction().commit();
			em.close();
		}
		
		public List<ListItem> showAllItems(){
			EntityManager em = emfactory.createEntityManager();
			List<ListItem> allItems = em.createQuery("SELECT i FROM ListItem i").getResultList();
			return allItems;
		}
		
		public void deleteItem(ListItem toDelete) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.store = :selectedStore and li.item = :selectedItem", ListItem.class);
			//substitute parameter with actual data from the todelete item
			typedQuery.setParameter("selectedStore", toDelete.getStore());
			typedQuery.setParameter("selectedItem", toDelete.getItem());
			
			//we only want one  result
			typedQuery.setMaxResults(1);
			
			//get the result and save it into a new list item
			ListItem result = typedQuery.getSingleResult();
			
			//remove it
			em.remove(result);
			em.getTransaction().commit();
			em.close();
		}

		/**
		 * @param idToEdit
		 * @return
		 */
		public ListItem searchForItemById(int idToEdit) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			ListItem found = em.find(ListItem.class, idToEdit);
			em.close();
			return found;
		}

		/**
		 * @param toEdit
		 */
		public void updateItem(ListItem toEdit) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			em.merge(toEdit);
			em.getTransaction().commit();
			em.close();
			
		}

		/**
		 * @param storeName
		 * @return
		 */
		public List<ListItem> searchForItemByStore(String storeName) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.store = :selectedStore",ListItem.class);
			typedQuery.setParameter("selectedStore", storeName);
			
			List<ListItem> foundItems = typedQuery.getResultList();
			em.close();
			return foundItems;
		}

		/**
		 * @param itemName
		 * @return
		 */
		public List<ListItem> searchForItemByItem(String itemName) {
			EntityManager em = emfactory.createEntityManager();
			em.getTransaction().begin();
			TypedQuery<ListItem> typedQuery = em.createQuery("select li from ListItem li where li.item = :selectedItem",ListItem.class);
			typedQuery.setParameter("selectedItem", itemName);
			
			List<ListItem> foundItems = typedQuery.getResultList();
			em.close();
			return foundItems;
		}
		
		public void cleanUp() {
			emfactory.close();
		}
		
		
	

}

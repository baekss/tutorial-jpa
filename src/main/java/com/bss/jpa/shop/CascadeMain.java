package com.bss.jpa.shop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Category;

public class CascadeMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		Category parent = new Category();
    		parent.setName("의류");
    		
    		Category child1 = new Category();
    		child1.setName("남성의류");
    		child1.setParent(parent);
    		Category child2 = new Category();
    		child2.setName("여성의류");
    		child2.setParent(parent);
    		
    		em.persist(parent);
    		
    		child2.setName("아동의류");
    		parent.getChild().remove(child1);
    		tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}

}

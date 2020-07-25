package com.bss.jpa.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;

public class JPQLJoinMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		String innerJoin ="SELECT c FROM Order o inner join o.client c";
    		String leftOuterJoin ="SELECT c FROM Order o left outer join o.client c";
    		String crossJoin ="SELECT c FROM Order o, Client c where o.status = c.name";
    		List<Client> clients = em.createQuery(innerJoin, Client.class).getResultList();
    		clients = em.createQuery(leftOuterJoin, Client.class).getResultList();
    		clients = em.createQuery(crossJoin, Client.class).getResultList();
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

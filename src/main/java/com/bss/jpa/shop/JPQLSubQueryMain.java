package com.bss.jpa.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Order;

public class JPQLSubQueryMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		/**
	    	select
	            order0_.ORDER_ID as ORDER_ID1_14_,
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_,
	            order0_.DELIVERY_ID as DELIVERY5_14_,
	            order0_.orderDate as orderDat2_14_,
	            order0_.status as status3_14_ 
	        from
	            ORDERS order0_ 
	        where
	            exists (
	                select
	                    client1_.CLIENT_ID 
	                from
	                    Client client1_ 
	                where
	                    order0_.FK_CLIENT_ID=client1_.CLIENT_ID 
	                    and client1_.CLIENT_ID > 10
	            )
    		*/
    		
    		String queryEXISTS ="SELECT o FROM Order o "
    							+ "WHERE EXISTS (SELECT c FROM o.client c WHERE c.id > :id)";
    		List<Order> clients = em.createQuery(queryEXISTS, Order.class).setParameter("id", 10L).getResultList();
    		
    		/**
	    	select
	            order0_.ORDER_ID as ORDER_ID1_14_,
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_,
	            order0_.DELIVERY_ID as DELIVERY5_14_,
	            order0_.orderDate as orderDat2_14_,
	            order0_.status as status3_14_ 
	        from
	            ORDERS order0_ 
	        where
	            order0_.FK_CLIENT_ID in (
	                select
	                    client1_.CLIENT_ID 
	                from
	                    Client client1_
	            )
    		*/
    		String queryIN ="SELECT o FROM Order o WHERE o.client "
					+ "IN (SELECT c FROM Client c)";
    		clients = em.createQuery(queryIN, Order.class).getResultList();
    		
    		/**
	    	select
	            order0_.ORDER_ID as ORDER_ID1_14_,
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_,
	            order0_.DELIVERY_ID as DELIVERY5_14_,
	            order0_.orderDate as orderDat2_14_,
	            order0_.status as status3_14_ 
	        from
	            ORDERS order0_ 
	        where
	            order0_.FK_CLIENT_ID=any (
	                select
	                    client1_.CLIENT_ID 
	                from
	                    Client client1_
	            )
    		*/
    		String queryANY ="SELECT o FROM Order o WHERE o.client = "
					+ "ANY (SELECT c FROM Client c)";
    		clients = em.createQuery(queryANY, Order.class).getResultList();
    		
    		/**
		    select
	            order0_.ORDER_ID as ORDER_ID1_14_,
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_,
	            order0_.DELIVERY_ID as DELIVERY5_14_,
	            order0_.orderDate as orderDat2_14_,
	            order0_.status as status3_14_ 
	        from
	            ORDERS order0_ 
	        where
	            order0_.FK_CLIENT_ID>all (
	                select
	                    client1_.CLIENT_ID 
	                from
	                    Client client1_ 
	                where
	                    client1_.CLIENT_ID=36
	            )
    		*/
    		//Client 테이블의 CLIENT_ID 36 보단 큰 FK_CLIENT_ID를 가진 ORDERS 테이블의 데이터가 나온다.
    		String queryALL ="SELECT o FROM Order o WHERE o.client > "
					+ "ALL (SELECT c FROM Client c WHERE c.id = :id)";
    		clients = em.createQuery(queryALL, Order.class).setParameter("id", 36L).getResultList();
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

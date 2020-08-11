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
    		/*
	    	select
	            client1_.CLIENT_ID as CLIENT_I1_4_,
	            client1_.city as city2_4_,
	            client1_.street as street3_4_,
	            client1_.zipcode as zipcode4_4_,
	            client1_.name as name5_4_ 
	        from
	            ORDERS order0_ 
	        inner join
	            Client client1_ 
	                on order0_.FK_CLIENT_ID=client1_.CLIENT_ID
    		*/
    		List<Client> clients = em.createQuery(innerJoin, Client.class).getResultList();
    		
    		/*
	    	select
	            client1_.CLIENT_ID as CLIENT_I1_4_,
	            client1_.city as city2_4_,
	            client1_.street as street3_4_,
	            client1_.zipcode as zipcode4_4_,
	            client1_.name as name5_4_ 
	        from
	            ORDERS order0_ 
	        left outer join
	            Client client1_ 
	                on order0_.FK_CLIENT_ID=client1_.CLIENT_ID
    		*/
    		clients = em.createQuery(leftOuterJoin, Client.class).getResultList();
    		/*
	    	select
	            client1_.CLIENT_ID as CLIENT_I1_4_,
	            client1_.city as city2_4_,
	            client1_.street as street3_4_,
	            client1_.zipcode as zipcode4_4_,
	            client1_.name as name5_4_ 
	        from
	            ORDERS order0_ cross 
	        join
	            Client client1_ 
	        where
	            order0_.status=client1_.name
    		*/
    		clients = em.createQuery(crossJoin, Client.class).getResultList();
    		
    		System.out.println("----------------묵시적 조인----------------");
    		//객체 그래프 탐색만으로 조인쿼리가 발생
			/*
			select
		        orders2_.ORDER_ID as ORDER_ID1_14_,
		        orders2_.FK_CLIENT_ID as FK_CLIEN4_14_,
		        orders2_.DELIVERY_ID as DELIVERY5_14_,
		        orders2_.orderDate as orderDat2_14_,
		        orders2_.status as status3_14_ 
		    from
	            ORDERS order0_ cross 
	        join
	            Client client1_ 
	        inner join
	            ORDERS orders2_ 
	                on client1_.CLIENT_ID=orders2_.FK_CLIENT_ID 
	        where
	            order0_.FK_CLIENT_ID=client1_.CLIENT_ID
			*/
    		em.createQuery("SELECT o.client.orders FROM Order o").getResultList();
    		
    		System.out.println("----------------명시적 조인----------------");
    		//명시적 조인으로 조인쿼리 발생 여부도 예측하고 컬렉션 타입의 필드의 객체그래프 탐색도 도모한다.
    		/*
			select
	            orders1_.status as col_0_0_ 
	        from
	            Client client0_ 
	        inner join
	            ORDERS orders1_ 
	                on client0_.CLIENT_ID=orders1_.FK_CLIENT_ID
			*/
    		em.createQuery("SELECT o.status FROM Client c inner join c.orders o").getResultList();
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

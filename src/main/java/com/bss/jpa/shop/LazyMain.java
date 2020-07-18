package com.bss.jpa.shop;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class LazyMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		//ORDERS 테이블의 CLIENT_ID FK 제약으로 인해 auto DDL시 DROP Table 순서 중요하다.
    		Client client = new Client();
    		client.setName("S.S. BAEK");
    		client.setCity("서울");
    		client.setStreet("억수로");
    		client.setZipcode("012-345-6-789");
    		
    		em.persist(client);
    		
    		Order order = new Order();
    		order.setClient(client);
    		order.setOrderDate(LocalDateTime.now());
    		order.setStatus(OrderStatus.ORDER);
    		em.persist(order);
    		
    		em.flush();
    		em.clear();
    		
    		/*
    		fetch=FetchType.EAGER 일때 outer join으로 모든 테이블 다 가져온다.
    		
    		from
            ORDERS order0_ 
        		left outer join
            Client client1_ 
            	on order0_.FK_CLIENT_ID=client1_.CLIENT_ID 
        		left outer join
            Delivery delivery2_ 
                on order0_.DELIVERY_ID=delivery2_.DELIVERY_ID 
             */
    		
    		/**
    		fetch=FetchType.LAZY 일때 해당 테이블만 가져온다. 나머지 연관관계 것들은 프록시로 할당한다.
    		
    		from
        	ORDERS order0_
             */
    		Order findOrder = em.find(Order.class, order.getId());
    		
    		findOrder.getClient()
    		/*
    		 fetch=FetchType.LAZY 일때 프록시 객체의 값이 사용될 때 쿼리 한다.
    		 Client 객체의 값 사용. 테이블 쿼리 함.
    		 */
    		.getOrders()
    		.forEach(i->System.out.println(findOrder==i));
    		
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

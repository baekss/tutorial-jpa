package com.bss.jpa.shop;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderStatus;

public class Main {

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
    		
    		System.out.println("<-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		em.persist(client);//시퀀스로 primary key 가져오느라 call next value for hibernate_sequence
    		System.out.println("</-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		
    		Order order = new Order();
    		order.setClient(client);
    		order.setOrderDate(LocalDateTime.now());
    		order.setStatus(OrderStatus.ORDER);
    		System.out.println("<-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		em.persist(order); //시퀀스로 primary key 가져오느라 call next value for hibernate_sequence
    		System.out.println("</-------시퀀스로 primary key 가져오느라 call next value for hibernate_sequence------->");
    		
        	tx.commit(); //insert문 flush and commit
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}

}
